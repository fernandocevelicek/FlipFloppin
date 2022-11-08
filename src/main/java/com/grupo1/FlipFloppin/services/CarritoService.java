package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.Carrito;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.entities.ProductoCompra;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.exceptions.ProductoCompraException;
import com.grupo1.FlipFloppin.mappers.CarritoMapper;
import com.grupo1.FlipFloppin.repositories.CarritoRepository;
import com.grupo1.FlipFloppin.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CarritoService implements BaseService<CarritoDTO> {

    @Autowired
    CarritoRepository carritoRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DetalleProductoService detalleProductoService;

    @Autowired
    ProductoCompraService productoCompraService;

    private CarritoMapper carritoMapper = CarritoMapper.getInstance();

    @Override
    @Transactional
    public List<CarritoDTO> findAll() throws CarritoException {
        try {
            List<CarritoDTO> carritos = carritoMapper.toDTOsList(carritoRepository.findAll());
            return carritos;
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO findById(Long id) throws CarritoException {
        try{
            Optional<Carrito> opt = carritoRepository.findById(id);
            Carrito carrito = opt.get();
            return carritoMapper.toDTO(carrito);
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    @Transactional
    public CarritoDTO findByUsuarioId(Long id) throws CarritoException {
        try{
            Carrito carrito = carritoRepository.findByIdUsuario(id);
            return carritoMapper.toDTO(carrito);
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO update(CarritoDTO dto, Long id) throws CarritoException {
        try{
            Carrito entity = carritoMapper.toEntity(dto);
            if(carritoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Carrito carrito = carritoRepository.save(entity);
                return carritoMapper.toDTO(carrito);
            }
            throw new CarritoException("No existe un carrito con el id: " + id);
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public CarritoDTO save(CarritoDTO dto) throws CarritoException {
        try{
            Carrito entity = carritoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Carrito carrito = carritoRepository.save(entity);
            return carritoMapper.toDTO(carrito);
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws CarritoException {
        try{
            if(carritoRepository.existsById(id)){
                carritoRepository.deleteById(id);
            } else {
                throw new CarritoException("No existe un carrito con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new CarritoException(e.getMessage());
        }
    }

    public void agregarProducto(Long idProducto, Long idDetalle, Long idUsuario, Integer cantidad) throws ProductoCompraException {
        Carrito carrito = carritoRepository.findByIdUsuario(idUsuario);
        if (carrito == null) {
            carrito = new Carrito();
            carrito.setTotal(0.0);
            carrito.setProductos(new ArrayList<>());
            carrito.setFechaAlta(new Date());
            carrito.setUsuario(usuarioService.findEntityById(idUsuario));
        } else {
            carrito.setFechaModificacion(new Date());
        }

        Producto producto = productoRepository.findById(idProducto).get();
        ProductoCompra productoCompra = getProductoCompra(carrito, producto);
        if(productoCompra != null) {
            productoCompra.setCantidad(productoCompra.getCantidad() + cantidad);
            productoCompra.setSubtotal(productoCompra.getSubtotal() + (productoCompra.getProducto().getPrecio()*cantidad));
        } else {
            productoCompra = new ProductoCompra();
            productoCompra.setIdDetalle(idDetalle);
            productoCompra.setProducto(producto);
            productoCompra.setCantidad(cantidad);
            productoCompra.setSubtotal(cantidad*producto.getPrecio());
            productoCompra = productoCompraService.save(productoCompra);
            carrito.getProductos().add(productoCompra);
        }
        detalleProductoService.quitarStock(idDetalle, cantidad);
        carrito.setTotal(carrito.getTotal() + (productoCompra.getProducto().getPrecio()*cantidad));

        carritoRepository.save(carrito);
    }

    private ProductoCompra getProductoCompra(Carrito carrito, Producto producto) {
        for (ProductoCompra productoCompra : carrito.getProductos()) {
            if (productoCompra.getProducto().equals(producto)) return productoCompra;
        }
        return null;
    }
}