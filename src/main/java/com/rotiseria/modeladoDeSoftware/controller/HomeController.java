package com.rotiseria.modeladoDeSoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import com.rotiseria.modeladoDeSoftware.model.Producto;
import com.rotiseria.modeladoDeSoftware.model.Usuario;
import com.rotiseria.modeladoDeSoftware.model.DetallePedido;
import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.repository.ProductoRepository;
import com.rotiseria.modeladoDeSoftware.service.IDetallePedidoService;
import com.rotiseria.modeladoDeSoftware.service.IPedidoService;
import com.rotiseria.modeladoDeSoftware.service.IUsuarioService;

import javax.servlet.http.HttpSession;

import java.util.Date;


@Controller
@RequestMapping("/")
public class HomeController {
	@Autowired
	private ProductoRepository productoRepository;
	
	//para almacenar los detalles del pedido
	List<DetallePedido> detalles=new ArrayList<DetallePedido>();
	Pedido pedido = new Pedido();
	
	@Autowired
	private IPedidoService pedidoService;
	
	@Autowired
	private IDetallePedidoService detallePedidoService;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping("")
	public String home(Model model, HttpSession session) {
		model.addAttribute("productos",productoRepository.findAll());
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuarioVista/home";
	}
	
	@GetMapping("detalleProducto/{idProducto}")
		public String detalleProducto(@PathVariable Long idProducto, Model model) {
			Producto producto = productoRepository.getById(idProducto);
			model.addAttribute("producto",producto);
			return "usuarioVista/detalleProducto";
		}
	
	@PostMapping("/carrito")
	public String agregarAlCarrito(@RequestParam Long idProducto, @RequestParam Integer cantidad, Model model) {
		DetallePedido detallePedido = new DetallePedido();
		double sumaTotal=0;
		Producto producto = productoRepository.getById(idProducto);
		detallePedido.setCantidad(cantidad);
		detallePedido.setPrecio(producto.getPrecio());
		detallePedido.setNombre(producto.getNombre());
		detallePedido.setTotal(producto.getPrecio()*cantidad);
		detallePedido.setProducto(producto);
		
		//Validamos que el producto no se pueda agregar varias veces al carrito
		Long idProd=producto.getIdProducto();
		boolean ingresado=detalles.stream().anyMatch(p -> p.getProducto().getIdProducto()==idProd);		//Vemos si el id del Producto que se quiere agregar al carrito ya existe en la lista
		
		//Si el id del producto que se quiere agregar no existe en el carrito, entonces agregamos el producto al carrito.
		if(!ingresado) {
			detalles.add(detallePedido);
		}
		
		sumaTotal=detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		pedido.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("pedido", pedido);
		
		return "usuarioVista/carrito";
	}
	
	//Eliminar producto del carrito
	@GetMapping("/eliminar/carrito/{idProducto}")
	public String eliminarDelCarrito(@PathVariable Long idProducto, Model model) {
		//Lista nueva de productos
		List<DetallePedido> pedidoNuevo=new ArrayList<DetallePedido>();
		
		//Actualizamos la lista de productos del carrito
		for(DetallePedido detallePedido: detalles) {
			if(detallePedido.getProducto().getIdProducto()!=idProducto) {
				pedidoNuevo.add(detallePedido);
			}
		}
		
		//Guardamos la lista con los productos restantes
		detalles=pedidoNuevo;
		
		//Recalculamos el total del pedido
		double sumaTotal=0;
		sumaTotal=detalles.stream().mapToDouble(dt -> dt.getTotal()).sum();
		pedido.setTotal(sumaTotal);
		model.addAttribute("carrito", detalles);
		model.addAttribute("pedido", pedido);
		
		return "usuarioVista/carrito";
	}
	
	@GetMapping("/getCarrito")
	public String getCarrito(Model model, HttpSession session) {
		//Método para acceder al carrito desde cualquier parte de la aplicación a través de la barra de navegación.
		model.addAttribute("carrito", detalles);
		model.addAttribute("pedido", pedido);
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "/usuarioVista/carrito";
	}
	
	@GetMapping("/resumenPedido")
	public String verResumenPedido(Model model, HttpSession session) {
		//Para que el usuario que está en sesión se vea reflejado en sus pedidos:
		Usuario usuario = usuarioService.findById( Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		
		model.addAttribute("carrito", detalles);
		model.addAttribute("pedido", pedido);
		model.addAttribute("usuario", usuario);
		
		return "usuarioVista/resumenPedido";
	}
	
	@GetMapping("/guardarPedido")		
	public String guardarPedido(HttpSession session, @RequestParam(value = "total") Double total) {
		Date fechaCreacion = new Date();
		pedido.setFecha(fechaCreacion);
		
		pedido.setTotal(total);			//seteamos el total con el parámetro recibido desde resumenPedido por si hizo falta agregar el envío.
		
		//guardar usuario que hizo el pedido
		Usuario usuario = usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString())).get();
		pedido.setUsuario(usuario);
		
		pedidoService.save(pedido);
		
		//guardar detalles:
		for (DetallePedido dt:detalles) {
			dt.setPedido(pedido);
			detallePedidoService.save(dt);
		}
		//limpiar pedido por si el usuario quiere hacer otro:
		pedido = new Pedido();
		detalles.clear();
		return "redirect:/";
	}
}
