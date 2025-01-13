package com.rotiseria.modeladoDeSoftware.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rotiseria.modeladoDeSoftware.model.Pedido;
import com.rotiseria.modeladoDeSoftware.model.Usuario;

@Repository
public interface IPedidoRepository extends JpaRepository<Pedido, Long>{
	List<Pedido> findByUsuario (Usuario usuario);
}
