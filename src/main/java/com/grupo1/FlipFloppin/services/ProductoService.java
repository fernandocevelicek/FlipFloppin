package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.exceptions.ImagenProductoException;
import com.grupo1.FlipFloppin.exceptions.SaveProductoException;
import com.grupo1.FlipFloppin.mappers.ProductoMapper;
import com.grupo1.FlipFloppin.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.grupo1.FlipFloppin.utils.Constants.IMAGENES_PRODUCTO_PATH;

@Service
public class ProductoService implements BaseService<ProductoDTO>{
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ImagenProductoService imagenProductoService;

    private ProductoMapper productoMapper = ProductoMapper.getInstance();

    @Override
    @Transactional
    public List<ProductoDTO> findAll() throws Exception {
        try {
            List<ProductoDTO> productos = productoMapper.toDTOsList(productoRepository.findAll());
            return productos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO findById(Long id) throws Exception {
        try {
            Optional<Producto> opt = this.productoRepository.findById(id);
            Producto producto = opt.get();
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO update(ProductoDTO dto, Long id) throws Exception {
        try {
            Producto entity = productoMapper.toEntity(dto);
            if (productoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Producto producto = this.productoRepository.save(entity);
                return productoMapper.toDTO(producto);
            }
            throw new Exception("No existe un producto con el id: " + id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackOn = {SaveProductoException.class})
    public ProductoDTO update(ProductoDTO dto, Long id, List<MultipartFile> imagenes) throws Exception {
        List<String> rutasImagenes = new ArrayList<>();
        try {
            Producto updatedEntity = productoMapper.toEntity(dto);
            Optional<Producto> productOpt = productoRepository.findById(id);

            if (productOpt.isPresent()) {
                Producto oldEntity = productOpt.get();
                if (!imagenes.isEmpty() && !imagenes.get(0).getOriginalFilename().isBlank()) {
                    rutasImagenes = imagenProductoService.modificarImagenes(dto, oldEntity, imagenes);
                    updatedEntity.setImagenes(rutasImagenes);
                } else {
                    updatedEntity.setImagenes(oldEntity.getImagenes());
                }

                updatedEntity.setFechaModificacion(new Date());
                Producto producto = this.productoRepository.save(updatedEntity);
                return productoMapper.toDTO(producto);
            }
            throw new Exception("No existe un producto con el id: " + id);
        } catch (Exception e) {
            if(!rutasImagenes.isEmpty()) {
                imagenProductoService.eliminarImagenes(rutasImagenes);
            }
            throw new SaveProductoException(e.getMessage());
        }
    }

    @Override
    public ProductoDTO save(ProductoDTO dto) throws Exception {
        try {
            Producto entity = productoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Producto producto = this.productoRepository.save(entity);
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackOn = {SaveProductoException.class})
    public ProductoDTO save(ProductoDTO dto, List<MultipartFile> imagenes) throws Exception {
        List<String> rutasImagenes = new ArrayList<>();
        try {
            Producto entity = productoMapper.toEntity(dto);

            rutasImagenes = imagenProductoService.persistirImagenes(dto, imagenes);

            entity.setImagenes(rutasImagenes);
            entity.setFechaAlta(new Date());
            Producto producto = productoRepository.save(entity);
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            if(!rutasImagenes.isEmpty()) {
                imagenProductoService.eliminarImagenes(rutasImagenes);
            }
            throw new SaveProductoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try {
            Optional<Producto> opt = this.productoRepository.findById(id);
            if (!opt.isEmpty()) {
                Producto producto = opt.get();
                producto.setEstado(EstadoProducto.INACTIVE);
                producto.setFechaBaja(new Date());
                productoRepository.save(producto);
            } else {
                throw new Exception("No existe un producto con el id: " + id);
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
