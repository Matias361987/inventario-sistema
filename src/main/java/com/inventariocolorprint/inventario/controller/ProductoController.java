package com.inventariocolorprint.inventario.controller;

import com.inventariocolorprint.inventario.entity.Producto;
import com.inventariocolorprint.inventario.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional; // <--- IMPORTANTE
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository repositorio;

    // 1. LOGIN
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 2. HOME (DASHBOARD DE CARPETAS)
    @GetMapping("/")
    public String mostrarCategorias(Model model) {
        List<String> categorias = repositorio.findDistinctCategorias();
        model.addAttribute("categorias", categorias);
        return "home";
    }

    // 3. VER TABLA DE UNA CATEGORÍA
    @GetMapping("/categoria/{nombre}")
    public String listarPorCategoria(@PathVariable String nombre, Model model) {
        List<Producto> productos = repositorio.findByCategoriaOrderByIdAsc(nombre);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaActual", nombre);
        return "lista_productos";
    }

    // 4. FORMULARIO NUEVO
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("producto", new Producto());
        return "formulario";
    }

    // 5. GUARDAR
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        if(producto.getCategoria() != null) {
            producto.setCategoria(producto.getCategoria().toUpperCase());
        }
        repositorio.save(producto);
        return "redirect:/";
    }

    // 6. STOCK RÁPIDO
    @GetMapping("/producto/{id}/stock/{cantidad}")
    public String actualizarStock(@PathVariable Long id, @PathVariable Integer cantidad) {
        Producto producto = repositorio.findById(id).orElse(null);
        if (producto != null) {
            int nuevoStock = producto.getCantidad() + cantidad;
            if (nuevoStock < 0) nuevoStock = 0;
            producto.setCantidad(nuevoStock);
            repositorio.save(producto);
            return "redirect:/categoria/" + producto.getCategoria();
        }
        return "redirect:/";
    }

    // 7. NUEVO: ELIMINAR CATEGORÍA COMPLETA
    @GetMapping("/categoria/eliminar/{nombre}")
    @Transactional // Necesario para borrar varios registros de golpe
    public String eliminarCategoria(@PathVariable String nombre) {
        repositorio.deleteByCategoria(nombre);
        return "redirect:/";
    }
}