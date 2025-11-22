package com.inventariocolorprint.inventario.repository;

import com.inventariocolorprint.inventario.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Spring Data JPA hace la magia aquí, no necesitas agregar código.
}