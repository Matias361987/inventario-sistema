package com.inventariocolorprint.inventario.repository;

import com.inventariocolorprint.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // 1. Buscar productos por categoría ordenados por ID
    List<Producto> findByCategoriaOrderByIdAsc(String categoria);

    // 2. Buscar carpetas (categorías) ÚNICAS, pero ignorando las vacías o nulas
    // El cambio clave está en el "WHERE"
    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.categoria IS NOT NULL AND p.categoria != ''")
    List<String> findDistinctCategorias();

    // 3. Borrar todos los productos de una categoría
    void deleteByCategoria(String categoria);
}