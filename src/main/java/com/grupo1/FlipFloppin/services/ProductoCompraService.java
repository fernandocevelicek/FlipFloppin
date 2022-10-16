package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.ProductoCompraDTO;
import com.grupo1.FlipFloppin.entities.ProductoCompra;
import com.grupo1.FlipFloppin.mappers.ProductoCompraMapper;
import com.grupo1.FlipFloppin.repositories.ProductoCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoCompraService implements BaseService<ProductoCompraDTO> {

    @Autowired
    ProductoCompraRepository productoCompraRepository;

    private ProductoCompraMapper productoCompraMapper = ProductoCompraMapper.getInstance();

    @Override
    @Transactional
    public List<ProductoCompraDTO> findAll() throws Exception {
        try {
            List<ProductoCompraDTO> productosCompra = productoCompraMapper.toDTOsList(productoCompraRepository.findAll());
            return productosCompra;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompraDTO findById(Long id) throws Exception {
        try{
            Optional<ProductoCompra> opt = productoCompraRepository.findById(id);
            ProductoCompra productoCompra = opt.get();
            return productoCompraMapper.toDTO(productoCompra);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompraDTO update(ProductoCompraDTO dto, Long id) throws Exception {
        try{
            ProductoCompra entity = productoCompraMapper.toEntity(dto);
            if(productoCompraRepository.existsById(id)) {
                ProductoCompra productoCompra = productoCompraRepository.save(entity);
                return productoCompraMapper.toDTO(productoCompra);
            }
            throw new Exception("No existe un producto compra con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoCompraDTO save(ProductoCompraDTO dto) throws Exception {
        try{
            ProductoCompra entity = productoCompraMapper.toEntity(dto);
            ProductoCompra productoCompra = this.productoCompraRepository.save(entity);
            return productoCompraMapper.toDTO(productoCompra);
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
