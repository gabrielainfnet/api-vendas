package com.api.vendas.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.api.vendas.entities.Produto;
import com.api.vendas.exceptions.AppException;
import com.api.vendas.repositories.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoService {

	private final ProdutoRepository repository;

	public Produto saveOrUpdate(Produto produto) {
		return repository.save(produto);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Produto findById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AppException("Produto não encontrado", HttpStatus.NOT_FOUND));
	}

	public List<Produto> findAll() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}

}
