package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.enums.Rol;
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
public class UsuarioService implements BaseService<Usuario>, UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public List<Usuario> findAll() throws Exception {
        try {
            List<Usuario> usuarios=usuarioRepository.findAll();
            return usuarios;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario findById(Long id) throws Exception {
        try{
            Optional<Usuario> opt=this.usuarioRepository.findById(id);
            Usuario usuario=opt.get();
            return usuario;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario update(Usuario entity, Long id) throws Exception {
        try{
            if(usuarioRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Usuario usuario = this.usuarioRepository.save(entity);
                return usuario;
            }
            throw new Exception("No existe un usuario con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Usuario save(Usuario entity) throws Exception {
        try{
            entity.setFechaAlta(new Date());
            Usuario usuario = this.usuarioRepository.save(entity);
            return usuario;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            Optional<Usuario> opt=this.usuarioRepository.findById(id);
            if(!opt.isEmpty()){
                Usuario usuario=opt.get();
                usuario.setEstado(EstadoUsuario.INACTIVE);
                usuario.setFechaBaja(new Date());
                usuarioRepository.save(usuario);
            }else {
                throw new Exception("No existe un usuario con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void registrarUsuario(String nombre, String apellido, String email, String password, String password_confirmation, Rol rol) throws Exception {
        //TODO: metodo validacion de datos

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        String encriptada = new BCryptPasswordEncoder().encode(password);
        usuario.setPassword(encriptada);
        usuario.setRol(rol);
        usuario.setEstado(EstadoUsuario.ACTIVE);

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
}
