package com.api.vendas.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.api.vendas.dtos.CredenciaisDto;
import com.api.vendas.dtos.TokenDto;
import com.api.vendas.dtos.UsuarioDto;
import com.api.vendas.entities.Usuario;
import com.api.vendas.services.UsuarioService;
import com.api.vendas.services.security.UserDetailsServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vendas-api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService service;
	private final UserDetailsServiceImpl userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioDto registrar(@RequestBody Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return userService.salvar(usuario);
	}

	@PostMapping("/auth")
	public TokenDto autenticar(@RequestBody CredenciaisDto credenciais) {
		try {
			Usuario usuario = Usuario.builder()
					.login(credenciais.getLogin())
					.senha(credenciais.getSenha())
					.build();

			return userService.autenticar(usuario);
		} catch (RuntimeException re) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, re.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<Usuario> updateUsuario(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.ok(service.update(usuario));
	}

	@PutMapping("/update-password")
	public ResponseEntity<Usuario> updatePassword(@Valid @RequestBody Usuario usuario) {
		return ResponseEntity.ok(service.updatePassword(
				usuario.getId(), 
				passwordEncoder.encode(usuario.getSenha()))); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> findUsuarioById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@DeleteMapping("/{id}")
	public void deleteUsuarioById(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> allUsers() {
		return ResponseEntity.ok(service.findAll());
	}
}
