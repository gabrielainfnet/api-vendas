package com.api.vendas.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.vendas.entities.Usuario;
import com.api.vendas.exceptions.AppException;
import com.api.vendas.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository repository;

	public Usuario update(Usuario usuario) {
		Usuario user = findById(usuario.getId());

		if (user != null) {
			user.setNome(usuario.getNome());
			user.setLogin(usuario.getLogin());
			user.setAdmin(usuario.getAdmin()); 
			repository.save(user);
		}

		return user;
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Usuario findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AppException("Usuário não encontrado", HttpStatus.NOT_FOUND));
	}

	public List<Usuario> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

	public Usuario updatePassword(Long id, String senha) {
		Usuario user = findById(id);

		if (user != null) {
			user.setSenha(senha);
			repository.save(user);
		}

		return user;
	}

}
