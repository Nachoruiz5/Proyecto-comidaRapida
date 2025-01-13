package com.rotiseria.modeladoDeSoftware.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.rotiseria.modeladoDeSoftware.model.Producto;
import com.rotiseria.modeladoDeSoftware.repository.ProductoRepository;

@Controller
public class ProductoController {
	@Autowired
	private ProductoRepository productoRepository;
	
	@GetMapping("/productos")
	public String verProductos(Model modelo) {
		List<Producto> productos=productoRepository.findAll();
		modelo.addAttribute("productos", productos);
		return "productos";
	}
	
	@GetMapping("/nuevoProducto")
	public String verRegistroProducto(Model modelo) {
		modelo.addAttribute("producto", new Producto());
		return "nuevoProducto";
	}
	
	@PostMapping("/nuevoProducto")
	public String guardarProducto(@Validated Producto producto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo, @RequestParam("file") MultipartFile imagen) throws IOException {
	    if (bindingResult.hasErrors()) {
	        modelo.addAttribute("producto", producto);
	        return "nuevoProducto";
	    }

	    if (!imagen.isEmpty()) {
	        // Obtener la ruta dentro de la carpeta 'static/img' de recursos estáticos de Spring Boot
	        Path directorioImagenes = Paths.get("src/main/resources/static/img");
	        // Crear la carpeta si no existe
	        if (!Files.exists(directorioImagenes)) {
	            Files.createDirectories(directorioImagenes);
	        }

	        try {
	            // Guardar la imagen en la carpeta
	            byte[] bytesImg = imagen.getBytes();
	            Path rutaCompleta = directorioImagenes.resolve(imagen.getOriginalFilename());
	            Files.write(rutaCompleta, bytesImg);

	            // Establecer el nombre de la imagen en el producto
	            producto.setimage(imagen.getOriginalFilename());
	        } catch (IOException e) {
	            e.printStackTrace();
	            // Manejo de errores
	        }
	    }

	    producto.setBorrado(false);
	    productoRepository.save(producto);

	    // Mensaje de éxito
	    redirect.addFlashAttribute("mensajeExito", "El producto ha sido agregado");
	    return "redirect:/productos";
	}

	@GetMapping("/home/{idProducto}")
	public String detalleProducto(@PathVariable Long idProducto, @Validated Producto producto, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) {
		Producto productoDB = productoRepository.getById(idProducto);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("producto", producto);
			return "nuevoProducto";
		}
		productoDB.setNombre(producto.getNombre());
		productoDB.setPrecio(producto.getPrecio());
		
		productoRepository.save(productoDB);
		redirect.addFlashAttribute("mensajeExito", "El producto ha sido actualizado");
		return "usuarioVista/home";

	}
	
	@GetMapping("/{idProducto}/actualizar")
	public String verActualizacionProducto(@PathVariable Long idProducto,Model modelo) {
		Producto producto = productoRepository.getById(idProducto);
		modelo.addAttribute("producto", producto);
		return "nuevoProducto";
	}

	@PostMapping("/{idProducto}/actualizar")
	public String actualizarProducto(@PathVariable Long idProducto,@Validated Producto producto,BindingResult bindingResult,RedirectAttributes redirect,Model modelo) {
		Producto productoDB = productoRepository.getById(idProducto);
		if(bindingResult.hasErrors()) {
			modelo.addAttribute("producto", producto);
			return "nuevoProducto";
		}
		productoDB.setNombre(producto.getNombre());
		productoDB.setPrecio(producto.getPrecio());
		
		productoRepository.save(productoDB);
		redirect.addFlashAttribute("mensajeExito", "El producto ha sido actualizado");
		return "redirect:/productos";
	}
	
	@PostMapping("/{idProducto}/eliminar")
	public String eliminarProducto(@PathVariable Long idProducto,RedirectAttributes redirect) {
		Producto producto = productoRepository.getById(idProducto);
		productoRepository.delete(producto);
		redirect.addFlashAttribute("mensajeExito", "El producto ha sido eliminado");
		return "redirect:/productos";
	}
}
