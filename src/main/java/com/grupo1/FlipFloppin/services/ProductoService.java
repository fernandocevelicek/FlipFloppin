package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements BaseService<Producto> {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    @Transactional
    public List<Producto> findAll() throws Exception {
        try {
            List<Producto> productos=productoRepository.findAll();
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Producto findById(Long id) throws Exception {
        try{
            Optional<Producto> opt=this.productoRepository.findById(id);
            Producto producto=opt.get();
            return producto;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Producto update(Producto entity, Long id) throws Exception {
        try{
            if(productoRepository.existsById(id)) {
                Producto producto = this.productoRepository.save(entity);
                return producto;
            }
            throw new Exception("No existe un producto con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Producto save(Producto entity) throws Exception {
        try{
            Producto producto = this.productoRepository.save(entity);
            return producto;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            Optional<Producto> opt=this.productoRepository.findById(id);
            if(!opt.isEmpty()){
                Producto producto=opt.get();
                if(producto.getEstado().equals(EstadoProducto.ACTIVE)){
                    producto.setEstado(EstadoProducto.INACTIVE);
                }
            }else {
                throw new Exception("No existe un producto con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
