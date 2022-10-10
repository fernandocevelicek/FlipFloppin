package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Carrito;
import com.grupo1.FlipFloppin.repositories.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService implements BaseService<Carrito> {

    @Autowired
    CarritoRepository carritoRepository;

    @Override
    @Transactional
    public List<Carrito> findAll() throws Exception {
        try {
            List<Carrito> carritos = carritoRepository.findAll();
            return carritos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Carrito findById(Long id) throws Exception {
        try{
            Optional<Carrito> opt = carritoRepository.findById(id);
            Carrito carrito = opt.get();
            return carrito;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Carrito update(Carrito entity, Long id) throws Exception {
        try{
            if(carritoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Carrito carrito = carritoRepository.save(entity);
                return carrito;
            }
            throw new Exception("No existe un carrito con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Carrito save(Carrito entity) throws Exception {
        try{
            entity.setFechaAlta(new Date());
            Carrito carrito = carritoRepository.save(entity);
            return carrito;
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
