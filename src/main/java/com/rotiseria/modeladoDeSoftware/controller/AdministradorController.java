package com.rotiseria.modeladoDeSoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.service.IPedidoService;
import com.rotiseria.modeladoDeSoftware.service.IUsuarioService;

@Controller
@RequestMapping("/administrador")
public class AdministradorController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IPedidoService pedidoService;
	
	@GetMapping("/usuarios")
	public String usuarios(Model model) {
		model.addAttribute("usuarios", usuarioService.findAll());
		return "usuarios";
	}
	
	@GetMapping("/pedidos")
	public String ordenes(Model model) {
		model.addAttribute("pedidos", pedidoService.findAll());
		return "pedidos";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalle(Model model, @PathVariable Long id) {
		Pedido pedido = pedidoService.findById(id).get();
		model.addAttribute("detalles", pedido.getDetallesPedido());
		return "detalleCompraAdmin";
	}
}
