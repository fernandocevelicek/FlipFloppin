package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Domicilio;
import com.grupo1.FlipFloppin.entities.Usuario;
import com.grupo1.FlipFloppin.entities.enums.Estado;
import com.grupo1.FlipFloppin.repositories.DomicilioRepository;
import com.grupo1.FlipFloppin.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements BaseService<Domicilio> {
    @Autowired
    private DomicilioRepository domicilioRepository;

    @Override
    @Transactional
    public List<Domicilio> findAll() throws Exception {
        try {
            List<Domicilio> domicilios=domicilioRepository.findAll();
            return domicilios;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Domicilio findById(Long id) throws Exception {
        try{
            Optional<Domicilio> opt=this.domicilioRepository.findById(id);
            Domicilio domicilio=opt.get();
            return domicilio;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Domicilio update(Domicilio entity, Long id) throws Exception {
        try{
            Optional<Domicilio> opt = this.domicilioRepository.findById(id);
            Domicilio domicilio = opt.get();
            domicilio = this.domicilioRepository.save(entity);
            return domicilio;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Domicilio save(Domicilio entity) throws Exception {
        try{
            Domicilio domicilio = this.domicilioRepository.save(entity);
            return domicilio;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            Optional<Domicilio> opt=this.domicilioRepository.findById(id);
            if(!opt.isEmpty()){
                Domicilio domicilio=opt.get();
                if(domicilio.getEstado().equals(Estado.ACTIVE)){
                    domicilio.setEstado(Estado.INACTIVE);
                }
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
