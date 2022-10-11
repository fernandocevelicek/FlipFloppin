package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.mappers.ProductoMapper;
import com.grupo1.FlipFloppin.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements BaseService<ProductoDTO> {
    @Autowired
    private ProductoRepository productoRepository;

    private ProductoMapper productoMapper = ProductoMapper.getInstance();

    @Override
    @Transactional
    public List<ProductoDTO> findAll() throws Exception {
        try {
            List<ProductoDTO> productos = productoMapper.toDTOsList(productoRepository.findAll());
            return productos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO findById(Long id) throws Exception {
        try{
            Optional<Producto> opt=this.productoRepository.findById(id);
            Producto producto=opt.get();
            return productoMapper.toDTO(producto);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO update(ProductoDTO dto, Long id) throws Exception {
        try{
            Producto entity = productoMapper.toEntity(dto);
            if(productoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Producto producto = this.productoRepository.save(entity);
                return productoMapper.toDTO(producto);
            }
            throw new Exception("No existe un producto con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO save(ProductoDTO dto) throws Exception {
        try{
            Producto entity = productoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Producto producto = this.productoRepository.save(entity);
            return productoMapper.toDTO(producto);
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
                producto.setEstado(EstadoProducto.INACTIVE);
                producto.setFechaBaja(new Date());
                productoRepository.save(producto);
            }else {
                throw new Exception("No existe un producto con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
