package com.api.vendas.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.vendas.entities.Categoria;
import com.api.vendas.exceptions.AppException;
import com.api.vendas.repositories.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

	private final CategoriaRepository repository;

	public Categoria saveOrUpdate(Categoria categoria) {
		return repository.save(categoria);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Categoria findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AppException("Categoria não encontrada", HttpStatus.NOT_FOUND));
	}

	public List<Categoria> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

}
