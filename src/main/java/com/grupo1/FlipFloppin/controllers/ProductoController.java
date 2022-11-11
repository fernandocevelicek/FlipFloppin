package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoIndividualDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/listado")
    public String listadoProductos(ModelMap model) {
        try {
            List<ProductoDTO> productos = productoService.findAll();
            model.addAttribute("productos", productos);

            return "listado_producto.html";
        } catch (ProductoException e) {
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/filtro")
    public String find(Model model, @RequestParam(value = "attribute", required = false) String attribute, @RequestParam(value = "value", required = false) String value){
        try{
                List<Producto> productos = productoService.findByParam(attribute, value);
                model.addAttribute("productos", productos);
                if(!attribute.equals("NOMBRE") && attribute!=null){
                    model.addAttribute("filtro",attribute.toLowerCase());
                    if(value!=null){
                        model.addAttribute("value",value.toLowerCase());
                    }
                }
                if(attribute.equals("NOMBRE") && value!=null){
                    model.addAttribute("filtro",attribute.toLowerCase());
                    model.addAttribute("value",value.toLowerCase());
                }
                return "listado_producto.html";
        }catch (ProductoException e){
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String productoIndividual(Model model, @PathVariable("id") Long idProducto, @RequestParam(value = "indexDetalle", required = false) Integer indexDetalle){
        try{
            ProductoIndividualDTO producto = productoService.getProductoIndividual(idProducto, indexDetalle);
            model.addAttribute("producto", producto);
            model.addAttribute("index",indexDetalle==null?0:indexDetalle);
            model.addAttribute("sourceURL", "/producto/"+idProducto+"?indexDetalle="+(indexDetalle == null ? 0 : indexDetalle));
            return "producto_individual";
        }catch (ProductoException e){
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

}
