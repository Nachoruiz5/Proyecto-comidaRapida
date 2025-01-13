package com.rotiseria.modeladoDeSoftware.service;

import java.util.List;
import java.util.Optional;

import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.model.Usuario;

public interface IPedidoService {
	List<Pedido> findAll();
	Pedido save (Pedido pedido);
	List<Pedido> findByUsuario (Usuario usuario);
	Optional<Pedido> findById(Long id);
}
