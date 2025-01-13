package com.rotiseria.modeladoDeSoftware.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.model.Usuario;
import com.rotiseria.modeladoDeSoftware.repository.IPedidoRepository;

@Service
public class PedidoServiceImpl implements IPedidoService{
	
	@Autowired
	private IPedidoRepository pedidoRepository;
	
	@Override
	public Pedido save(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	@Override
	public List<Pedido> findByUsuario(Usuario usuario) {
		return pedidoRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Pedido> findById(Long id) {
		return pedidoRepository.findById(id);
	}

}
