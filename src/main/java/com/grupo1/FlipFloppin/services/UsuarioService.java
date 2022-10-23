package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.UsuarioDTO;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
import com.grupo1.FlipFloppin.mappers.UsuarioMapper;
import com.grupo1.FlipFloppin.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    private UsuarioMapper usuarioMapper = UsuarioMapper.getInstance();

    @Override
    @Transactional
    public List<UsuarioDTO> findAll() throws Exception {
        try {
            List<UsuarioDTO> usuarios = usuarioMapper.toDTOsList(usuarioRepository.findAll());
            return usuarios;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UsuarioDTO findById(Long id) throws Exception {
        try {
            Optional<Usuario> opt = this.usuarioRepository.findById(id);
            Usuario usuario = opt.get();
            return usuarioMapper.toDTO(usuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UsuarioDTO update(UsuarioDTO dto, Long id) throws Exception {
        try {
            System.out.println(dto.getId());
            Usuario entity = usuarioMapper.toEntity(dto);
            System.out.println(entity.getId());
            if (usuarioRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Usuario usuario = this.usuarioRepository.save(entity);
                return usuarioMapper.toDTO(usuario);
            }
            throw new Exception("No existe un usuario con el id: " + id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public UsuarioDTO save(UsuarioDTO dto) throws Exception {
        try {
            Usuario entity = usuarioMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Usuario usuario = this.usuarioRepository.save(entity);
            return usuarioMapper.toDTO(usuario);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try {
            Optional<Usuario> opt = this.usuarioRepository.findById(id);
            if (!opt.isEmpty()) {
                Usuario usuario = opt.get();
                usuario.setEstado(EstadoUsuario.INACTIVE);
                usuario.setFechaBaja(new Date());
                usuarioRepository.save(usuario);
            } else {
                throw new Exception("No existe un usuario con el id: " + id);
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String email, String password, String password_confirmation, Rol rol) throws Exception {
        //TODO: metodo validacion de datos

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.ACTIVE);

        this.save(usuario);
    }

    @Transactional
    public void altaUsuarioCompleto(String nombre, String apellido, String email, String password, String password_confirmation, Rol rol, EstadoUsuario estado) throws Exception {
        //TODO: metodo validacion de datos

        UsuarioDTO usuario = new UsuarioDTO();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setRol(rol);
        usuario.setEstado(estado);

        this.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String emailUsuario) throws UsernameNotFoundException {

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
    public void modificarUsuario(long id, String nombre, String apellido, String email, String password, String password_confirmation, Rol rol) throws Exception {

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
            throw new Exception("No existe el usuario");
        }
    }

    @Transactional
    public void modificarUsuarioCompleto(long id, String nombre, String apellido, String email, String password, String password_confirmation, Rol rol, EstadoUsuario estado) throws Exception {

        UsuarioDTO usuario = new UsuarioDTO();
        Optional<Usuario> opt = this.usuarioRepository.findById(id);
        if (opt.isPresent()) {
            usuario.setId(opt.get().getId());
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEmail(email);
            String encriptada = new BCryptPasswordEncoder().encode(password);
            usuario.setPassword(encriptada);
            usuario.setRol(rol);
            usuario.setEstado(estado);
            this.save(usuario);
        } else {
            throw new Exception("No existe el usuario");
        }
    }

}



