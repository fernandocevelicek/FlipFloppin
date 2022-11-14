package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.exceptions.UsuarioException;
import com.grupo1.FlipFloppin.mappers.UsuarioMapper;
import com.grupo1.FlipFloppin.repositories.UsuarioRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements BaseService<UsuarioDTO>, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private NotificacionMail notificacionMail;

    private UsuarioMapper usuarioMapper = UsuarioMapper.getInstance();

    @Override
    @Transactional
    public List<UsuarioDTO> findAll() {
        List<UsuarioDTO> usuarios = usuarioMapper.toDTOsList(usuarioRepository.findAll());
        return usuarios;
    }

    @Override
    @Transactional
    public UsuarioDTO findById(Long id) {
        Optional<Usuario> opt = this.usuarioRepository.findById(id);
        Usuario usuario = opt.get();
        return usuarioMapper.toDTO(usuario);
    }

    @Transactional
    public Usuario findEntityById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).get();
        return usuario;
    }

    @Override
    @Transactional
    public UsuarioDTO update(UsuarioDTO dto, Long id) throws UsuarioException {
        System.out.println(dto.getId());
        Usuario entity = usuarioMapper.toEntity(dto);
        System.out.println(entity.getId());
        if (usuarioRepository.existsById(id)) {
            entity.setFechaModificacion(new Date());
            Usuario usuario = this.usuarioRepository.save(entity);
            return usuarioMapper.toDTO(usuario);
        }
        throw new UsuarioException("No existe un usuario con el id: " + id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public UsuarioDTO save(UsuarioDTO dto) throws UsuarioException {
        try {
            Usuario entity = usuarioMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Usuario usuario = this.usuarioRepository.save(entity);
            return usuarioMapper.toDTO(usuario);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            throw new UsuarioException("Ya existe el mail ingresado");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la persistencia");
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws UsuarioException {
        Optional<Usuario> opt = this.usuarioRepository.findById(id);
        if (!opt.isEmpty()) {
            Usuario usuario = opt.get();
            usuario.setEstado(EstadoUsuario.INACTIVE);
            usuario.setFechaBaja(new Date());
            usuarioRepository.save(usuario);
        } else {
            throw new UsuarioException("No existe un usuario con el id: " + id);
        }
        return true;
    }

    public void registrarUsuario(String nombre, String apellido, String email, String password, String password_confirmation, Rol rol) throws UsuarioException {
        validarDatosUsuario(nombre, apellido, email, password, password_confirmation);

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.ACTIVE);

        this.save(usuario);

        notificacionMail.enviar("Bienvenido, se ha registrado correctamente.", "FlipFloppin - Registro", usuario.getEmail());
    }

    @Transactional
    public void altaUsuarioCompleto(String nombre, String apellido, String email, String password, String password_confirmation, EstadoUsuario estado) throws UsuarioException {
        validarDatosUsuario(nombre, apellido, email, password, password_confirmation);

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setRol(Rol.USUARIO);
        usuario.setEstado(estado);

        this.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario);

        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
            permisos.add(p1);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuario_session", usuario);

            User user = new User(usuario.getEmail(), usuario.getPassword(), permisos);

            return user;
        } else {
            return null;
        }
    }

    @Transactional
    public void modificarUsuario(long id, String nombre, String apellido, String email, String password, String password_confirmation, Rol rol) throws UsuarioException {
        validarDatosUsuario(nombre, apellido, email, password, password_confirmation);

        UsuarioDTO usuario = new UsuarioDTO();
        Optional<Usuario> opt = this.usuarioRepository.findById(id);
        if (opt.isPresent()) {
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(encriptada);
            usuario.setRol(rol);
            usuario.setEstado(EstadoUsuario.ACTIVE);
            this.save(usuario);
        } else {
            throw new UsuarioException("No existe el usuario");
        }
    }

    @Transactional
    public void modificarUsuarioCompleto(long id, String nombre, String apellido, String email, String password, String password_confirmation, EstadoUsuario estado) throws UsuarioException {
        validarDatosUsuario(nombre, apellido, email, password, password_confirmation);

        UsuarioDTO usuario = new UsuarioDTO();
        Optional<Usuario> opt = this.usuarioRepository.findById(id);
        if (opt.isPresent()) {
            usuario.setId(opt.get().getId());
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(encriptada);
            usuario.setRol(Rol.USUARIO);
            usuario.setEstado(estado);
            this.save(usuario);
        } else {
            throw new UsuarioException("No existe el usuario");
        }
    }

    public void validarDatosUsuario(String nombre, String apellido, String email, String password, String password_confirmation) throws UsuarioException {
        if(Strings.isBlank(nombre)) {
            throw new UsuarioException("El nombre no puede ser vacio o nulo.");
        }

        if(Strings.isBlank(apellido)) {
            throw new UsuarioException("El apellido no puede ser vacio o nulo.");
        }

        if(Strings.isBlank(email)) {
            throw new UsuarioException("El email no puede ser vacio o nulo.");
        }

        if(password.length() < 8 || !contieneCaracterEspecial(password)) {
            throw new UsuarioException("La contraseña debe tener 8 o más caracteres e incluir por lo menos un caracter especial.");
        }

        if(Strings.isBlank(password) || Strings.isBlank(password_confirmation)) {
            throw new UsuarioException("La contraseña no puede estar vacia o nula.");
        }

        if (!password.equals(password_confirmation)) {
            throw new UsuarioException("Ambas contraseñas deben coincidir.");
        }
    }
    public Page<Usuario> findAllPaginated(int pageNo, int pageSize) throws ProductoException {
        try {
            Pageable pageable= PageRequest.of(pageNo,pageSize);
            Page<Usuario> usuarios = usuarioRepository.findAll(pageable);
            return usuarios;

        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    private boolean contieneCaracterEspecial(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i)) && !Character.isLetter(s.charAt(i)) && !Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

}



