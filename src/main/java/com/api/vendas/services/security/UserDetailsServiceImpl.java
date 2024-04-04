package com.api.vendas.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.vendas.dtos.TokenDto;
import com.api.vendas.dtos.UsuarioDto;
import com.api.vendas.entities.Usuario;
import com.api.vendas.repositories.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private JwtService jwtService;

	@Transactional
	public UsuarioDto salvar(Usuario usuario) {
		Usuario user = repository.save(usuario);
		return new UsuarioDto(user.getId(), user.getNome(), user.getLogin(), user.getAdmin());
	}

	private Usuario getUsuario(String username) {
		return repository.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
	}

	public TokenDto autenticar(Usuario usuario) {
		UserDetails userDetails = loadUserByUsername(usuario.getLogin());

		boolean isMatch = encoder.matches(usuario.getSenha(), userDetails.getPassword());

		if (isMatch) {
			Usuario user = this.getUsuario(usuario.getLogin());
			UsuarioDto usuarioDto = new UsuarioDto(user.getId(), user.getNome(), user.getLogin(), user.getAdmin());
			String token = jwtService.gerarToken(userDetails);
			return new TokenDto(token, usuarioDto);
		}

		throw new RuntimeException("Login/Senha inválidos");
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = getUsuario(username);

		String[] roles = usuario.getAdmin() ? new String[] { "ADMIN", "USER" } : new String[] { "USER" };

		return User.builder().username(usuario.getLogin()).password(usuario.getSenha()).roles(roles).build();
	}

}
