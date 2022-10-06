package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.enums.EstadoUsuario;
import com.grupo1.FlipFloppin.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements BaseService<Usuario> {
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
                if(usuario.getEstado().equals(EstadoUsuario.ACTIVE)){
                    usuario.setEstado(EstadoUsuario.INACTIVE);
                }
            }else {
                throw new Exception("No existe un usuario con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
