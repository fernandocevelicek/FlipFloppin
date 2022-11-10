package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.CarritoDTO;
import com.grupo1.FlipFloppin.entities.*;
import com.grupo1.FlipFloppin.enums.EstadoPedido;
import com.grupo1.FlipFloppin.exceptions.CarritoException;
import com.grupo1.FlipFloppin.exceptions.PedidoException;
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
    @Autowired
    PedidoService pedidoService;

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

    public void quitarProducto(Long idProducto, Long idDetalle, Long idUsuario, Integer cantidad) throws ProductoCompraException {
        Carrito carrito = carritoRepository.findByIdUsuario(idUsuario);
        carrito.setFechaModificacion(new Date());

        Producto producto = productoRepository.findById(idProducto).get();
        ProductoCompra productoCompra = getProductoCompra(carrito, producto);

        Integer newCantidad = productoCompra.getCantidad() - cantidad;
        if(newCantidad <= 0) {
            carrito.setTotal(carrito.getTotal() - (productoCompra.getProducto().getPrecio()*productoCompra.getCantidad()));
            carrito.getProductos().remove(productoCompra);
            productoCompraService.deleteById(productoCompra.getId());
            if (carrito.getProductos().size() == 0) {
                carritoRepository.delete(carrito);
                return;
            }

            carritoRepository.save(carrito);
            return;
        }

        productoCompra.setCantidad(newCantidad);
        productoCompra.setSubtotal(productoCompra.getSubtotal() - (productoCompra.getProducto().getPrecio()*cantidad));

        detalleProductoService.agregarStock(idDetalle, cantidad);
        carrito.setTotal(carrito.getTotal() - (productoCompra.getProducto().getPrecio()*cantidad));

        carritoRepository.save(carrito);
    }

    public void confirmarCompra(Long idUsuario, Long idDomicilio) throws PedidoException {
        Carrito carrito = carritoRepository.findByIdUsuario(idUsuario);
        Usuario usuario = carrito.getUsuario();
        Domicilio domicilio = getUbicacionEntrega(usuario, idDomicilio);

        Pedido pedido = new Pedido();
        pedido.setEstado(EstadoPedido.PENDING);
        pedido.setUbicacionEntrega(domicilio);

        List<ProductoCompra> productos = new ArrayList<>();
        for (ProductoCompra p : carrito.getProductos()) {
            productos.add(new ProductoCompra(p));
        }
        pedido.setProductos(carrito.getProductos());

        pedido.setTotal(carrito.getTotal());
        pedido.setUsuario(usuario);

        carritoRepository.delete(carrito);
        pedidoService.save(pedido);
    }

    private Domicilio getUbicacionEntrega(Usuario usuario, Long idDomicilio) {
        for (Domicilio domicilio : usuario.getUbicacionesEnvio()) {
            if (domicilio.getId().equals(idDomicilio)) return domicilio;
        }
        return null;
    }

    private ProductoCompra getProductoCompra(Carrito carrito, Producto producto) {
        for (ProductoCompra productoCompra : carrito.getProductos()) {
            if (productoCompra.getProducto().equals(producto)) return productoCompra;
        }
        return null;
    }
}