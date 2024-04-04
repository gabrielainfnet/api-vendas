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

import com.api.vendas.entities.Categoria;
import com.api.vendas.services.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas-api")
@RequiredArgsConstructor
public class CategoriaController {

	private final CategoriaService service;

	@GetMapping("/categorias")
	public ResponseEntity<List<Categoria>> allCategorias() {
		return ResponseEntity.ok(service.findAll());
	}

	@PostMapping("/categorias")
	public ResponseEntity<Categoria> createCategoria(@Valid @RequestBody Categoria categoria) {
		Categoria createCategoria = service.saveOrUpdate(categoria);
		return ResponseEntity.created(URI.create("/vendas-api/categorias" + categoria.getId())).body(createCategoria);
	}

	@PutMapping("/categorias")
	public ResponseEntity<Categoria> updateCategoria(@Valid @RequestBody Categoria categoria) {
		return ResponseEntity.ok(service.saveOrUpdate(categoria));
	}

	@GetMapping("/categorias/{id}")
	public ResponseEntity<Categoria> findCategoriaById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/categorias/{id}")
	public void deleteCategoriaById(@PathVariable Long id) {
		service.deleteById(id);
	}

}
