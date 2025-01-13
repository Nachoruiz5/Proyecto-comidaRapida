package com.rotiseria.modeladoDeSoftware.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.model.Usuario;
import com.rotiseria.modeladoDeSoftware.service.IPedidoService;
import com.rotiseria.modeladoDeSoftware.service.IUsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	BCryptPasswordEncoder passEncode= new BCryptPasswordEncoder();
	
	@GetMapping("/registro")
	public String create() {
		return "usuarioVista/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario) {
		usuario.setTipo("USER");
		usuario.setPassword(passEncode.encode(usuario.getPassword()));
		usuarioService.save(usuario);
		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuarioVista/login";
	}
	
	@GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session) {
		Optional<Usuario> user=usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			
			if (user.get().getTipo().equals("ADMIN")) {
				return "redirect:/productos";
			}else {
				return "redirect:/";
			}
		}
		return "redirect:/";
	}
	
	@GetMapping("/pedidosUsuario")
	public String verPedidos(Model model, HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		Usuario usuario= usuarioService.findById(  Integer.parseInt(session.getAttribute("idusuario").toString()) ).get();
		List<Pedido> pedidos= pedidoService.findByUsuario(usuario);
		model.addAttribute("pedidos", pedidos);
		return "usuarioVista/pedidosUsuario";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Long id, HttpSession session, Model model) {
		Optional<Pedido> pedido=pedidoService.findById(id);
		model.addAttribute("detalles", pedido.get().getDetallesPedido());
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuarioVista/detalleCompra";
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion(HttpSession session) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
