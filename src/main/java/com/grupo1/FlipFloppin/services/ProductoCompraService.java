package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.ProductoCompra;
import com.grupo1.FlipFloppin.repositories.ProductoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoCompraService implements BaseService<ProductoCompra> {

    @Autowired
    ProductoCompraRepository productoCompraRepository;

    @Override
    @Transactional
    public List<ProductoCompra> findAll() throws Exception {
        try {
            List<ProductoCompra> productosCompra = productoCompraRepository.findAll();
            return productosCompra;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompra findById(Long id) throws Exception {
        try{
            Optional<ProductoCompra> opt = productoCompraRepository.findById(id);
            ProductoCompra productoCompra = opt.get();
            return productoCompra;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompra update(ProductoCompra entity, Long id) throws Exception {
        try{
            if(productoCompraRepository.existsById(id)) {
                ProductoCompra productoCompra = productoCompraRepository.save(entity);
                return productoCompra;
            }
            throw new Exception("No existe un producto compra con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompra save(ProductoCompra entity) throws Exception {
        try{
            ProductoCompra productoCompra = productoCompraRepository.save(entity);
            return productoCompra;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            if(productoCompraRepository.existsById(id)){
                productoCompraRepository.deleteById(id);
            } else {
                throw new Exception("No existe un producto compra con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
