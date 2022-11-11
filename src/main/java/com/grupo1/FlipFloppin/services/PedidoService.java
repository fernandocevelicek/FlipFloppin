package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.PedidoDTO;
import com.grupo1.FlipFloppin.dtos.ProductoCompraDTO;
import com.grupo1.FlipFloppin.entities.Pedido;
import com.grupo1.FlipFloppin.enums.EstadoPedido;
import com.grupo1.FlipFloppin.exceptions.PedidoException;
import com.grupo1.FlipFloppin.mappers.PedidoMapper;
import com.grupo1.FlipFloppin.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements BaseService<PedidoDTO> {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetalleProductoService detalleProductoService;

    private PedidoMapper pedidoMapper = PedidoMapper.getInstance();

    @Override
    @Transactional
    public List<PedidoDTO> findAll() throws PedidoException {
        try {
            List<PedidoDTO> pedidos = pedidoMapper.toDTOsList(pedidoRepository.findAll());
            return pedidos;
        } catch (Exception e) {
            throw new PedidoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public PedidoDTO findById(Long id) throws PedidoException {
        try {
            Optional<Pedido> opt = this.pedidoRepository.findById(id);
            Pedido pedido = opt.get();
            return pedidoMapper.toDTO(pedido);
        } catch (Exception e) {
            throw new PedidoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public PedidoDTO update(PedidoDTO dto, Long id) throws PedidoException {
        try {
            Pedido entity = pedidoMapper.toEntity(dto);
            if (pedidoRepository.existsById(id)) {
                entity.setFechaModificacion(new Date());
                Pedido pedido = this.pedidoRepository.save(entity);
                return pedidoMapper.toDTO(pedido);
            }
            throw new PedidoException("No existe un pedido con el id: " + id);
        } catch (Exception e) {
            throw new PedidoException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public PedidoDTO save(PedidoDTO dto) throws PedidoException {
        try {
            Pedido entity = pedidoMapper.toEntity(dto);
            entity.setFechaAlta(new Date());
            Pedido pedido = this.pedidoRepository.save(entity);
            return pedidoMapper.toDTO(pedido);
        } catch (Exception e) {
            throw new PedidoException(e.getMessage());
        }
    }

    @Transactional
    public PedidoDTO save(Pedido entity) throws PedidoException {
        try {
            entity.setFechaAlta(new Date());
            Pedido pedido = this.pedidoRepository.save(entity);
            return pedidoMapper.toDTO(pedido);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PedidoException("Error al persistir el pedido.");
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws PedidoException {
        Optional<Pedido> opt = this.pedidoRepository.findById(id);
        if (!opt.isEmpty()) {
            Pedido pedido = opt.get();
            pedido.setEstado(EstadoPedido.INACTIVE);
            pedido.setFechaBaja(new Date());
            pedidoRepository.save(pedido);
        } else {
            throw new PedidoException("No existe un pedido con el id: " + id);
        }
        return true;
    }

    public void cancelarPedido(Long idPedido) throws PedidoException {
        List<ProductoCompraDTO> productos = findById(idPedido).getProductos();
        for (ProductoCompraDTO producto : productos) {
            detalleProductoService.agregarStock(producto.getIdDetalle(), producto.getCantidad());
        }

        deleteById(idPedido);
    }
}
