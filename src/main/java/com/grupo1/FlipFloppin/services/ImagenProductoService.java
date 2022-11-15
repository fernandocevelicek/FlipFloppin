package com.grupo1.FlipFloppin.services;

import com.grupo1.FlipFloppin.dtos.producto.ProductoDTO;
import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.exceptions.ImagenProductoException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.grupo1.FlipFloppin.utils.Constants.IMAGENES_PRODUCTO_PATH;

@Service
public class ImagenProductoService {

    public void eliminarImagenes(List<String> rutasImagenes) throws IOException {
        for (String imagen : rutasImagenes) {
            Path rutaAbsoluta = Paths.get(IMAGENES_PRODUCTO_PATH + "//" + imagen);
            Files.delete(rutaAbsoluta);
        }
    }

    public List<String> persistirImagenes(ProductoDTO dto, List<MultipartFile> archivosImagenes) throws ImagenProductoException, IOException {
        List<String> rutasImagenes = new ArrayList<>();

        for (MultipartFile archivo : archivosImagenes) {
            if (archivo.isEmpty()) {
                throw new ImagenProductoException("El archivo no puede estar vacio");
            }
            if (!validarExtensionImagen(archivo)) {
                throw new ImagenProductoException("La extension no es valida");
            }
            if (archivo.getSize() >= 15000000) {
                throw new ImagenProductoException("El archivo excede 15MB");
            }

            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "." + archivo.getOriginalFilename().substring(index + 1);
            String nombreFoto = dto.getNombre().replace(" ", "_") + "_" + Calendar.getInstance().getTimeInMillis() + extension;
            System.out.println("Nombre Foto: " + nombreFoto);
            Path rutaAbsoluta = Paths.get(IMAGENES_PRODUCTO_PATH + "/" + nombreFoto);

            Path carpetaImagenes = Paths.get(IMAGENES_PRODUCTO_PATH);
            if (!Files.exists(carpetaImagenes)) {
                System.out.println(IMAGENES_PRODUCTO_PATH + "NO EXISTE EL DIRECTORIO");
                new File(IMAGENES_PRODUCTO_PATH).mkdirs();
            }

            System.out.println("Antes Files.write");
            Files.write(rutaAbsoluta, archivo.getBytes());
            System.out.println("Despues Files.write");

            if (!Files.exists(rutaAbsoluta)) {
                System.out.println("NO GUARDO IMAGEN EN EL DIRECTORIO " + rutaAbsoluta.toUri());
            } else {
                System.out.println("SI GUARDO IMAGEN EN EL DIRECTORIO " + rutaAbsoluta.toUri());
            }
            rutasImagenes.add(nombreFoto);
        }

        return rutasImagenes;
    }

    public List<String> modificarImagenes(ProductoDTO dto, Producto entity, List<MultipartFile> archivosImagenes) throws ImagenProductoException, IOException {
        List<String> nuevasRutasImagenes = new ArrayList<>();

        for (MultipartFile archivo : archivosImagenes) {
            if (archivo.isEmpty()) {
                throw new ImagenProductoException("El archivo no puede estar vacio");
            }
            if (!validarExtensionImagen(archivo)) {
                throw new ImagenProductoException("La extension no es valida");
            }
            if (archivo.getSize() >= 15000000) {
                throw new ImagenProductoException("El archivo excede 15MB");
            }

            int index = archivo.getOriginalFilename().indexOf(".");
            String extension = "." + archivo.getOriginalFilename().substring(index + 1);
            String nombreFoto = dto.getNombre().replace(" ", "_") + "_" + Calendar.getInstance().getTimeInMillis() + extension;
            Path rutaAbsoluta = Paths.get(IMAGENES_PRODUCTO_PATH + "//" + nombreFoto);

            Files.write(rutaAbsoluta, archivo.getBytes());
            nuevasRutasImagenes.add(nombreFoto);
        }

        List<String> viejasRutasImagenes = entity.getImagenes();
        eliminarImagenes(viejasRutasImagenes);

        return nuevasRutasImagenes;
    }

    public boolean validarExtensionImagen(MultipartFile archivo) {
        try {
            ImageIO.read(archivo.getInputStream()).toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
