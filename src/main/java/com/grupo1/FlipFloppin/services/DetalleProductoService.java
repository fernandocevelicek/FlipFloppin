package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.entities.DetalleProducto;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.exceptions.DetalleProductoException;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.mappers.DetalleProductoMapper;
import com.grupo1.FlipFloppin.repositories.DetalleProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleProductoService implements BaseService<DetalleProductoDTO> {

    @Autowired
    DetalleProductoRepository detalleProductoRepository;

    @Autowired
    ProductoService productoService;

    private DetalleProductoMapper detalleProductoMapper = DetalleProductoMapper.getInstance();

    @Override
    @Transactional
    public List<DetalleProductoDTO> findAll() throws Exception {
        try {
            List<DetalleProductoDTO> detalleProductos = detalleProductoMapper.toDTOsList(detalleProductoRepository.findAll());
            return detalleProductos;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public DetalleProductoDTO findById(Long id) {
        Optional<DetalleProducto> opt = detalleProductoRepository.findById(id);
        DetalleProducto detalleProducto = opt.get();
        return detalleProductoMapper.toDTO(detalleProducto);
    }

    @Override
    @Transactional
    public DetalleProductoDTO update(DetalleProductoDTO dto, Long id) throws DetalleProductoException {
        System.out.println(dto.getProducto());
        DetalleProducto entity = detalleProductoMapper.toEntity(dto);
        if (detalleProductoRepository.existsById(id)) {
            System.out.println(entity.getProducto());
            DetalleProducto detalleProducto = detalleProductoRepository.save(entity);
            return detalleProductoMapper.toDTO(detalleProducto);
        }
        throw new DetalleProductoException("No existe un detalle producto con el id: " + id);
    }

    @Transactional
    public DetalleProductoDTO update(DetalleProductoDTO dto, Long id, Long idProducto) throws DetalleProductoException, ProductoException {
        if (!productoService.validateIdDetalle(id, idProducto)) {
            throw new DetalleProductoException("El detalle seleccionado no corresponde al producto indicado");
        }

        if (detalleProductoRepository.existsById(id)) {
            dto.setProducto(productoService.findById(idProducto));
            DetalleProducto entity = detalleProductoMapper.toEntity(dto);
            DetalleProducto detalleProducto = detalleProductoRepository.save(entity);
            return detalleProductoMapper.toDTO(detalleProducto);
        }

        throw new DetalleProductoException("No existe un detalle producto con el id: " + id);
    }

    @Override
    @Transactional
    public DetalleProductoDTO save(DetalleProductoDTO dto) {
        DetalleProducto entity = detalleProductoMapper.toEntity(dto);
        DetalleProducto detalleProducto = detalleProductoRepository.save(entity);
        return detalleProductoMapper.toDTO(detalleProducto);
    }

    @Transactional
    public DetalleProductoDTO save(DetalleProductoDTO dto, Long idProducto) throws DetalleProductoException, ProductoException {
        ProductoDTO producto = productoService.findById(idProducto);
        dto.setProducto(producto);

        DetalleProducto entity = detalleProductoMapper.toEntity(dto);
        entity = detalleProductoRepository.save(entity);

        return detalleProductoMapper.toDTO(entity);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws DetalleProductoException {
        if (detalleProductoRepository.existsById(id)) {
            detalleProductoRepository.deleteById(id);
        } else {
            throw new DetalleProductoException("No existe un detalle producto con el id: " + id);
        }
        return true;
    }

    public void quitarStock(Long id, Integer cantidad) throws CarritoException {
        DetalleProducto detalle = detalleProductoRepository.findById(id).get();

        if(detalle.getStock() < cantidad) throw new CarritoException("No hay suficiente stock disponible para agregar su compra.");

        detalle.setStock(detalle.getStock() - cantidad);
    }

    public void agregarStock(Long id, Integer cantidad) {
        DetalleProducto detalle = detalleProductoRepository.findById(id).get();
        detalle.setStock(detalle.getStock() + cantidad);
    }
}
