package com.grupo1.FlipFloppin.controllers;

import com.grupo1.FlipFloppin.dtos.DetalleProductoDTO;
import com.grupo1.FlipFloppin.enums.Color;
import com.grupo1.FlipFloppin.enums.TallePrenda;
import com.grupo1.FlipFloppin.exceptions.DetalleProductoException;
import com.grupo1.FlipFloppin.exceptions.ProductoException;
import com.grupo1.FlipFloppin.services.DetalleProductoService;
import com.grupo1.FlipFloppin.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('ROLE_ADMINISTRADOR')")
@Controller
@RequestMapping("/detalle_producto")
public class DetalleProductoAdminController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private DetalleProductoService detalleProductoService;

    @GetMapping("/formulario/{id}")
    public String formularioDetalleProducto(Model model, @PathVariable("id") long id, @RequestParam("idProducto") Long idProducto) {
        try {
            model.addAttribute("categoria", productoService.getCategoriaById(idProducto));
            model.addAttribute("tallesPrendas", TallePrenda.values());
            model.addAttribute("colores", Color.values());
            model.addAttribute("idProducto", idProducto);
            if (id == 0) {
                model.addAttribute("detalle", new DetalleProductoDTO());
            } else {
                DetalleProductoDTO detalleProductoDTO = detalleProductoService.findById(id);
                System.out.println(detalleProductoDTO);
                model.addAttribute("detalle", detalleProductoDTO);
            }
            return "formulario_detalle_producto";
        } catch (ProductoException e) {
            model.addAttribute("codigo", 500);
            model.addAttribute("mensaje", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/formulario/{id}")
    public String postFormularioProducto(ModelMap modelo,
                                         @PathVariable Long id,
                                         @ModelAttribute DetalleProductoDTO detalleProductoDTO,
                                         @RequestParam("idProducto") Long idProducto) {
        try {
            if (detalleProductoDTO.getId() == 0) {
                detalleProductoService.save(detalleProductoDTO, idProducto);
            } else {
                detalleProductoService.update(detalleProductoDTO, id, idProducto);
            }
            return "redirect:/detalle_producto/formulario/"+0+"?idProducto="+idProducto;
        } catch (DetalleProductoException | ProductoException e) {
            return "redirect:/producto/abm_productos/0?error="+e.getMessage();
        }
    }

}
