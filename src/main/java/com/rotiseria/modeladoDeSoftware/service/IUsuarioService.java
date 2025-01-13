package com.rotiseria.modeladoDeSoftware.service;
import java.util.List;
import java.util.Optional;

import com.rotiseria.modeladoDeSoftware.model.Usuario;

public interface IUsuarioService {
	List<Usuario> findAll();
	Optional<Usuario> findById(Integer id);
	Usuario save (Usuario usuario);
	Optional<Usuario> findByEmail(String email);
}
