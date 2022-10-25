package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Categoria;
import com.grupo1.FlipFloppin.enums.EstadoProducto;
import com.grupo1.FlipFloppin.enums.Sexo;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.grupo1.FlipFloppin.utils.Constants.BASE_VIEW_PATH;

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
            return BASE_VIEW_PATH + "abm_productos";
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
            return BASE_VIEW_PATH + "formulario_producto";
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
    @RequestMapping(" / ")
    public String find(Model model, @Param("search") String search){
        try{
                List<Producto> findProducto = productoService.find(search);

                model.addAttribute("findProducto", findProducto);
                model.addAttribute("search",search);
                return " ";

        }catch (Exception e){
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
