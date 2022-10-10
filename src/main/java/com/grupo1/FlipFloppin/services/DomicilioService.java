package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Domicilio;
import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.repositories.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
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
            if(domicilioRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Domicilio domicilio = this.domicilioRepository.save(entity);
                return domicilio;
            }
            throw new Exception("No existe un domicilio con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Domicilio save(Domicilio entity) throws Exception {
        try{
            entity.setFechaAlta(new Date());
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
                domicilio.setEstado(EstadoDomicilio.INACTIVE);
                domicilio.setFechaBaja(new Date());
                domicilioRepository.save(domicilio);
            } else {
                throw new Exception("No existe un domicilio con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
