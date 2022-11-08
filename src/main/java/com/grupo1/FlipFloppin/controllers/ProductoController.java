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

    @GetMapping("/abm_productos")
    public String getAll(Model model) {
        try {
            List<ProductoDTO> productos = productoService.findAll();
            model.addAttribute("productos", productos);
            List<Integer> stocks=new ArrayList<>();
            /* Agrego stocks totales*/
            for(ProductoDTO dto : productos){
                Integer stockT=0;
                for(DetalleProductoDTO detalle : dto.getDetalle()){
                    stockT+=detalle.getStock();
                }
                stocks.add(stockT);
            }
            model.addAttribute("stocks",stocks);
            return "abm_productos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/formulario/{id}")
    public String formularioProducto(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("estados", EstadoProducto.values());
            model.addAttribute("categorias", Categoria.values());
            model.addAttribute("sexos", Sexo.values());
            if (id == 0) {
                model.addAttribute("producto", new ProductoDTO());
            } else {
                model.addAttribute("producto", productoService.findById(id));
            }
            return "formulario_producto";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/{id}")
    public String postFormularioProducto(ModelMap modelo,
                                         @PathVariable Long id,
                                         @ModelAttribute ProductoDTO productoDTO,
                                         @RequestParam("archivosImagenes") List<MultipartFile> imagenes) {
        ProductoDTO persistedProduct;
        try {
            if (productoDTO.getId() == 0) {
                persistedProduct = productoService.save(productoDTO, imagenes);
            } else {
                persistedProduct = productoService.update(productoDTO, id, imagenes);
            }
            return "redirect:/detalle_producto/formulario/"+0+"?idProducto="+persistedProduct.getId();
        } catch (ProductoException | IOException e) {
            return "redirect:/producto/abm_productos?error="+e.getMessage();
        }
    }

    @PostMapping("/baja_producto/{id}")
    public String bajarProducto(Model model, @PathVariable("id") long id) {
        try {
            productoService.deleteById(id);
            return "redirect:/producto/abm_productos";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
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
                return "listadoProducto.html";
        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}")
    public String productoIndividual(Model model, @PathVariable("id") Long idProducto, @RequestParam(value = "indexDetalle", required = false) Integer indexDetalle){
        try{
            ProductoIndividualDTO producto = productoService.getProductoIndividual(idProducto, indexDetalle);
            model.addAttribute("producto", producto);
            model.addAttribute("sourceURL", "/producto/"+idProducto+"?indexDetalle="+(indexDetalle == null ? 0 : indexDetalle));
            return "productoIndividual";
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
