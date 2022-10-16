package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.DomicilioDTO;
import com.grupo1.FlipFloppin.entities.Domicilio;
import com.grupo1.FlipFloppin.enums.EstadoDomicilio;
import com.grupo1.FlipFloppin.mappers.DomicilioMapper;
import com.grupo1.FlipFloppin.repositories.DomicilioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DomicilioService implements BaseService<DomicilioDTO> {

    @Autowired
    private DomicilioRepository domicilioRepository;

    private DomicilioMapper domicilioMapper = DomicilioMapper.getInstance();
    @Override
    @Transactional
    public List<DomicilioDTO> findAll() throws Exception {
        try {
            List<DomicilioDTO> domicilios = domicilioMapper.toDTOsList(domicilioRepository.findAll());
            return domicilios;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DomicilioDTO findById(Long id) throws Exception {
        try{
            Optional<Domicilio> opt=this.domicilioRepository.findById(id);
            Domicilio domicilio=opt.get();
            return domicilioMapper.toDTO(domicilio);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DomicilioDTO update(DomicilioDTO dto, Long id) throws Exception {
        try{
            System.out.println(dto.getId());
            Domicilio entity = domicilioMapper.toEntity(dto);
            System.out.println(entity.getId());
            if(domicilioRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Domicilio domicilio = this.domicilioRepository.save(entity);
                return domicilioMapper.toDTO(domicilio);
            }
            throw new Exception("No existe un domicilio con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DomicilioDTO save(DomicilioDTO dto) throws Exception {
        try{
            Domicilio entity = domicilioMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Domicilio domicilio = this.domicilioRepository.save(entity);
            return domicilioMapper.toDTO(domicilio);
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
