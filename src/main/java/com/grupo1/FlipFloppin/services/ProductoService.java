package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoIndividualDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.mappers.ProductoMapper;
import com.grupo1.FlipFloppin.repositories.ProductoRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService implements BaseService<ProductoDTO>{
    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ImagenProductoService imagenProductoService;

    private ProductoMapper productoMapper = ProductoMapper.getInstance();

    @Override
    @Transactional
    public List<ProductoDTO> findAll() throws ProductoException {
        try {
            List<ProductoDTO> productos = productoMapper.toDTOsList(productoRepository.findAll());
            return productos;
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO findById(Long id) throws ProductoException {
        try {
            Optional<Producto> opt = this.productoRepository.findById(id);
            if (!opt.isPresent()) throw new ProductoException("No existe un producto con el id " + id);
            Producto producto = opt.get();
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductoDTO update(ProductoDTO dto, Long id) throws ProductoException {
        try {
            Producto entity = productoMapper.toEntity(dto);
            if (productoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Producto producto = this.productoRepository.save(entity);
                return productoMapper.toDTO(producto);
            }
            throw new Exception("No existe un producto con el id: " + id);
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    @Transactional(rollbackOn = {ProductoException.class})
    public ProductoDTO update(ProductoDTO dto, Long id, List<MultipartFile> imagenes) throws ProductoException, IOException {
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
            e.printStackTrace();
            if(!rutasImagenes.isEmpty()) {
                imagenProductoService.eliminarImagenes(rutasImagenes);
            }
            throw new ProductoException(e.getMessage());
        }
    }

    @Override
    public ProductoDTO save(ProductoDTO dto) throws ProductoException {
        try {
            Producto entity = productoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Producto producto = this.productoRepository.save(entity);
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    @Transactional(rollbackOn = {ProductoException.class})
    public ProductoDTO save(ProductoDTO dto, List<MultipartFile> imagenes) throws ProductoException, IOException {
        List<String> rutasImagenes = new ArrayList<>();
        try {
            Producto entity = productoMapper.toEntity(dto);

            rutasImagenes = imagenProductoService.persistirImagenes(dto, imagenes);

            entity.setImagenes(rutasImagenes);
            entity.setFechaAlta(new Date());
            Producto producto = productoRepository.save(entity);
            return productoMapper.toDTO(producto);
        } catch (Exception e) {
            e.printStackTrace();
            if(!rutasImagenes.isEmpty()) {
                imagenProductoService.eliminarImagenes(rutasImagenes);
            }
            throw new ProductoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws ProductoException {
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
            throw new ProductoException(e.getMessage());
        }
    }

    public Categoria getCategoriaById(Long id) throws ProductoException {
        return findById(id).getCategoria();
    }

    public boolean validateIdDetalle(Long idDetalle, Long idProducto) throws ProductoException {
        ProductoDTO producto = findById(idProducto);
        for (DetalleProductoDTO detalle : producto.getDetalle()) {
            if (detalle.getId().equals(idDetalle)) return true;
        }
        return false;
    }
    public List<Producto> find(String searchFilter){
        if(searchFilter != null){
            return productoRepository.findByFilter(searchFilter);
        } else {
            return productoRepository.findAll();
        }
    }

    public Page<ProductoDTO> findByParam(String attribute, String value,int pageNo,int pageSize) throws ProductoException {
        Pageable pageable=PageRequest.of(pageNo,pageSize);

        if(Strings.isNotBlank(attribute)){
            if (attribute.equalsIgnoreCase("NOMBRE") && Strings.isBlank(value)) {
                value = "";
            }

            Sexo sexo = null;
            if (attribute.equalsIgnoreCase("SEXO")) {
                try {
                    sexo = Sexo.valueOf(value.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new ProductoException("Valor de Sexo invalido.");
                }
            }

            switch (attribute.toUpperCase()) {
                case "NOMBRE":
                    return productoRepository.searchByNombrePaged(value,pageable).map(entity -> productoMapper.toDTO(entity));
                case "MARCA":
                    return productoRepository.searchByMarcaPaged(value,pageable).map(entity -> productoMapper.toDTO(entity));
                case "SEXO":
                    return productoRepository.searchBySexoPaged(sexo,pageable).map(entity -> productoMapper.toDTO(entity));
                default:
                    throw new ProductoException("Atributo de filtrado invalido.");
            }
        } else {

            return productoRepository.findAllActive(pageable).map(entity -> productoMapper.toDTO(entity));
        }
    }

    public ProductoIndividualDTO getProductoIndividual(Long idProducto, Integer indexDetalle) throws ProductoException {
        ProductoDTO producto = findById(idProducto);
        ProductoIndividualDTO productoIndividualDTO = new ProductoIndividualDTO(producto);

        if (indexDetalle == null) {
            indexDetalle = 0;
        }
        DetalleProductoDTO detalleProductoDTO = productoIndividualDTO.getDetalle().get(indexDetalle);
        productoIndividualDTO.setDetalleActual(detalleProductoDTO);

        return productoIndividualDTO;
    }
    public List<ProductoDTO> getLastFive() throws ProductoException {
        try {
            List<ProductoDTO> productos = productoMapper.toDTOsList(productoRepository.findLastFive());
            return productos;
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    public Page<ProductoDTO> findAllPaginated(int pageNo,int pageSize) throws ProductoException {
        try {
            Pageable pageable=PageRequest.of(pageNo,pageSize);
            Page<Producto> productosEntity = productoRepository.findAll(pageable);
            Page<ProductoDTO> dtoPage = productosEntity.map(entity -> productoMapper.toDTO(entity));

            return dtoPage;
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }

    public Page<ProductoDTO> findAllActivePaginated(int pageNo,int pageSize) throws ProductoException {
        try {
            Pageable pageable=PageRequest.of(pageNo,pageSize);
            Page<Producto> productosEntity = productoRepository.findAllActive(pageable);
            Page<ProductoDTO> dtoPage = productosEntity.map(entity -> productoMapper.toDTO(entity));

            return dtoPage;
        } catch (Exception e) {
            throw new ProductoException(e.getMessage());
        }
    }
}

