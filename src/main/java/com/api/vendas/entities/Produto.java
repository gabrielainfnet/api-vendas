package com.api.vendas.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "produtos")
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 120)
	private String nome;

	@Column(length = 250)
	private String descricao;

	private BigDecimal preco;

	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;

	private Boolean ativo;

	@DateTimeFormat(iso = ISO.DATE)
	@Column(name = "data_validade", columnDefinition = "DATE")
	private LocalDate dataValidade;

	// Muitos Produtos para uma Categoria
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;

}
