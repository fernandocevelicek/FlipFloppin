package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.DetalleProducto;
import com.grupo1.FlipFloppin.repositories.DetalleProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleProductoService implements BaseService<DetalleProducto> {

    @Autowired
    DetalleProductoRepository detalleProductoRepository;

    @Override
    @Transactional
    public List<DetalleProducto> findAll() throws Exception {
        try {
            List<DetalleProducto> detalleProductos = detalleProductoRepository.findAll();
            return detalleProductos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DetalleProducto findById(Long id) throws Exception {
        try{
            Optional<DetalleProducto> opt = detalleProductoRepository.findById(id);
            DetalleProducto detalleProducto = opt.get();
            return detalleProducto;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DetalleProducto update(DetalleProducto entity, Long id) throws Exception {
        try{
            if(detalleProductoRepository.existsById(id)) {
                DetalleProducto detalleProducto = detalleProductoRepository.save(entity);
                return detalleProducto;
            }
            throw new Exception("No existe un detalle producto con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DetalleProducto save(DetalleProducto entity) throws Exception {
        try{
            DetalleProducto detalleProducto = detalleProductoRepository.save(entity);
            return detalleProducto;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            if(detalleProductoRepository.existsById(id)){
                detalleProductoRepository.deleteById(id);
            } else {
                throw new Exception("No existe un detalle producto con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
