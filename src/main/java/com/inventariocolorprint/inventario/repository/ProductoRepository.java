package com.inventariocolorprint.inventario.repository;

import com.inventariocolorprint.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // 1. Buscar productos por categoría ordenados
    List<Producto> findByCategoriaOrderByIdAsc(String categoria);

    // 2. Buscar nombres de carpetas (categorías únicas)
    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();

    // 3. NUEVO: Borrar todos los productos de una categoría
    void deleteByCategoria(String categoria);
}
