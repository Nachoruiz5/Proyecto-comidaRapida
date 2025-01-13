package com.rotiseria.modeladoDeSoftware.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rotiseria.modeladoDeSoftware.model.DetallePedido;
import com.rotiseria.modeladoDeSoftware.repository.IDetallePedidoRepository;

@Service
public class DetallePedidoServiceImpl implements IDetallePedidoService{
	
	@Autowired
	private IDetallePedidoRepository detallePedidoRepository;
	
	@Override
	public DetallePedido save(DetallePedido detallePedido) {
		return detallePedidoRepository.save(detallePedido);
	}
	
}
