package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.dtos.producto.ProductoIndividualDTO;
import com.grupo1.FlipFloppin.entities.DetalleProducto;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@Controller
@RequestMapping("/producto")
public class ProductoAdminController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/abm_productos/{nro}")
    public String abmProductos(Model model,@PathVariable(value = "nro") int pageNo) {
        try {
            int pageSize = 10;
            Page< Producto > page = productoService.findAllPaginated(pageNo, pageSize);
            List < Producto > productos = page.getContent();
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
            List<Integer> stocks=new ArrayList<>();
            /* Agrego stocks totales*/
            for(Producto dto : productos){
                Integer stockT=0;
                for(DetalleProducto detalle : dto.getDetalle()){
                    stockT+=detalle.getStock();
                }
                stocks.add(stockT);
            }

            model.addAttribute("stocks",stocks);
            return "abm_productos";
        } catch (ProductoException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
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
        } catch (ProductoException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
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
            e.printStackTrace();
            return "redirect:/producto/abm_productos?error=al cargar el producto";
        }
    }

    @PostMapping("/baja_producto/{id}")
    public String bajarProducto(Model model, @PathVariable("id") long id) {
        try {
            productoService.deleteById(id);
            return "redirect:/producto/abm_productos";
        } catch (ProductoException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }


}
