package com.inventariocolorprint.inventario.repository;

import com.inventariocolorprint.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // 1. Busca productos de una categoría y los ordena por ID (para que no bailen)
    List<Producto> findByCategoriaOrderByIdAsc(String categoria);

    // 2. Busca las categorías ÚNICAS para mostrar en el menú principal (Carpetas)
    @Query("SELECT DISTINCT p.categoria FROM Producto p")
    List<String> findDistinctCategorias();
}