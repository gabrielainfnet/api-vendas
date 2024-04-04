package com.api.vendas.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.vendas.entities.Produto;
import com.api.vendas.services.ProdutoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas-api")
@RequiredArgsConstructor
public class ProdutoController {

	private final ProdutoService service;

	@GetMapping("/produtos")
	public ResponseEntity<List<Produto>> allProdutos() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/produtos")
	public ResponseEntity<Produto> createProduto(@Valid @RequestBody Produto produto) {
		Produto createProduto = service.saveOrUpdate(produto);
		return ResponseEntity.created(URI.create("/vendas-api/produtos" + produto.getId())).body(createProduto);
	}

	@PutMapping("/produtos")
	public ResponseEntity<Produto> updateProduto(@Valid @RequestBody Produto produto) {
		return ResponseEntity.ok(service.saveOrUpdate(produto));
	}

	@GetMapping("/produtos/{id}")
	public ResponseEntity<Produto> findProdutoById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/produtos/{id}")
	public void deleteProdutoById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
