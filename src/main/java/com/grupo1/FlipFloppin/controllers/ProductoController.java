package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoIndividualDTO;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.mappers.ProductoMapper;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    @GetMapping("/listado/{nro}")
    public String listadoProductos(ModelMap model,@PathVariable(value = "nro") int pageNo) {
        try {
            int pageSize = 5;
            Page <ProductoDTO> page = productoService.findAllActivePaginated(pageNo, pageSize);
            List <ProductoDTO> productos = page.getContent();

            model.addAttribute("currentPage", pageNo);
            if(pageNo>0){
                model.addAttribute("previousPage",pageNo-1);
            }
            if(pageNo+1<page.getTotalPages()){
                model.addAttribute("nextPage",pageNo+1);
            }

            model.addAttribute("totalPages", page.getTotalPages()-1);
            model.addAttribute("totalItems", page.getTotalElements());
            model.addAttribute("productos", productos);

            return "listado_producto.html";
        } catch (ProductoException e) {
            e.printStackTrace();
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/listado/filtro/{nro}")
    public String find(Model model, @RequestParam(value = "attribute", required = false) String attribute,
                       @RequestParam(value = "value", required = false) String value,@PathVariable(value = "nro") int pageNo){
        try{
                int pageSize=5;
                Page<ProductoDTO> page = productoService.findByParam(attribute, value,pageNo,pageSize);
                List <ProductoDTO> productos = page.getContent();
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
                model.addAttribute("productos", productos);
                model.addAttribute("currentPage", pageNo);
                 if(pageNo>0){
                model.addAttribute("previousPage",pageNo-1);
                }
                if(pageNo+1<page.getTotalPages()){
                model.addAttribute("nextPage",pageNo+1);
                }
                model.addAttribute("totalPages", page.getTotalPages()-1);
                model.addAttribute("totalItems", page.getTotalElements());
                model.addAttribute("productos", productos);
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
