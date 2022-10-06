package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.entities.Pedido;
import com.grupo1.FlipFloppin.enums.EstadoPedido;
import com.grupo1.FlipFloppin.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService implements BaseService<Pedido>{
    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    @Transactional
    public List<Pedido> findAll() throws Exception {
        try {
            List<Pedido> pedidos=pedidoRepository.findAll();
            return pedidos;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido findById(Long id) throws Exception {
        try{
            Optional<Pedido> opt=this.pedidoRepository.findById(id);
            Pedido pedido=opt.get();
            return pedido;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido update(Pedido entity, Long id) throws Exception {
        try{
            if(pedidoRepository.existsById(id)) {
                Pedido pedido = this.pedidoRepository.save(entity);
                return pedido;
            }
            throw new Exception("No existe un pedido con el id: " + id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Pedido save(Pedido entity) throws Exception {
        try{
            Pedido pedido = this.pedidoRepository.save(entity);
            return pedido;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) throws Exception {
        try{
            Optional<Pedido> opt=this.pedidoRepository.findById(id);
            if(!opt.isEmpty()){
                Pedido pedido=opt.get();
                if(pedido.getEstado().equals(EstadoPedido.ACTIVE)){
                    pedido.setEstado(EstadoPedido.INACTIVE);
                }
            }else {
                throw new Exception("No existe un pedido con el id: " + id);
            }
            return true;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
