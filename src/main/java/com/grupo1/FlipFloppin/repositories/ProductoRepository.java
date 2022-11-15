package com.grupo1.FlipFloppin.repositories;

import com.grupo1.FlipFloppin.entities.Producto;
import com.grupo1.FlipFloppin.enums.Sexo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {

    @Query(value = "SELECT * FROM Producto p WHERE p.nombre LIKE %?1% "+
                    "OR p.marca LIKE %?1%" +
                    "OR p.sexo LIKE %?1%" +
                    "OR p.precio LIKE %?1%", nativeQuery = true)
    List<Producto> findByFilter(String searchFilter);


    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    List<Producto> searchByNombre(@Param("nombre") String nombre);

    @Query("SELECT p FROM Producto p WHERE p.marca Like %:marca%")
    List<Producto> searchByMarca(@Param("marca") String marca);

    @Query("SELECT p FROM Producto p WHERE p.sexo = :sexo")
    List<Producto> searchBySexo(@Param("sexo") Sexo sexo);

    @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre% AND p.fechaBaja IS NULL")
    Page<Producto> searchByNombrePaged(@Param("nombre") String nombre, Pageable pageable);

    @Query(value = "SELECT p FROM Producto p WHERE p.marca Like %:marca%  AND p.fechaBaja IS NULL")
    Page<Producto> searchByMarcaPaged(@Param("marca") String marca, Pageable pageable);

    @Query("SELECT p FROM Producto p WHERE p.sexo = :sexo AND p.fechaBaja IS NULL")
    Page<Producto> searchBySexoPaged(@Param("sexo") Sexo sexo, Pageable pageable);

    @Query(value = "SELECT * FROM producto p WHERE p.fecha_baja IS NULL ORDER BY p.id DESC limit 5", nativeQuery = true)
    List<Producto> findLastFive();

    @Query("SELECT p FROM Producto p WHERE p.fechaBaja IS NULL")
    Page<Producto> findAllActive(Pageable pageable);

}
