package com.inventariocolorprint.inventario.controller;

import com.inventariocolorprint.inventario.entity.Producto;
import com.inventariocolorprint.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort; // Importante para el ordenamiento
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository repositorio;

    // 1. PANTALLA DE LOGIN (Nueva)
    @GetMapping("/login")
    public String login() {
        return "login"; // Busca el archivo login.html
    }

    // 2. LISTAR PRODUCTOS (Ordenados por ID para evitar saltos visuales)
    @GetMapping("/")
    public String listarProductos(Model model) {
        // Usamos Sort.by para obligar a la base de datos a entregar la lista ordenada
        model.addAttribute("productos", repositorio.findAll(Sort.by(Sort.Direction.ASC, "id")));
        return "home";
    }

    // 3. MOSTRAR FORMULARIO DE NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    // 4. GUARDAR PRODUCTO EN LA BASE DE DATOS
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        repositorio.save(producto);
        return "redirect:/";
    }

    // 5. ACTUALIZAR STOCK RÁPIDO (+1 o -1)
    @GetMapping("/producto/{id}/stock/{cantidad}")
    public String actualizarStock(@PathVariable Long id, @PathVariable Integer cantidad) {
        Producto producto = repositorio.findById(id).orElse(null);

        if (producto != null) {
            // Sumamos la cantidad (si viene un -1, restará)
            int nuevoStock = producto.getCantidad() + cantidad;

            // Evitamos que el stock sea negativo
            if (nuevoStock < 0) {
                nuevoStock = 0;
            }

            producto.setCantidad(nuevoStock);
            repositorio.save(producto);
        }

        return "redirect:/";
    }
}