package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.Carrito;
import com.grupo1.FlipFloppin.mappers.CarritoMapper;
import com.grupo1.FlipFloppin.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService implements BaseService<CarritoDTO> {

    @Autowired
    CarritoRepository carritoRepository;


    private CarritoMapper carritoMapper = CarritoMapper.getInstance();

    @Override
    @Transactional
    public List<CarritoDTO> findAll() throws Exception {
        try {
            List<CarritoDTO> carritos = carritoMapper.toDTOsList(carritoRepository.findAll());
            return carritos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO findById(Long id) throws Exception {
        try{
            Optional<Carrito> opt = carritoRepository.findById(id);
            Carrito carrito = opt.get();
            return carritoMapper.toDTO(carrito);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO update(CarritoDTO dto, Long id) throws Exception {
        try{
            Carrito entity = carritoMapper.toEntity(dto);
            if(carritoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Carrito carrito = carritoRepository.save(entity);
                return carritoMapper.toDTO(carrito);
            }
            throw new Exception("No existe un carrito con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO save(CarritoDTO dto) throws Exception {
        try{
            Carrito entity = carritoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Carrito carrito = carritoRepository.save(entity);
            return carritoMapper.toDTO(carrito);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            if(carritoRepository.existsById(id)){
                carritoRepository.deleteById(id);
            } else {
                throw new Exception("No existe un carrito con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}