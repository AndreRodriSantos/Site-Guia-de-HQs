package br.com.andre.laranja.hqguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Quadrinho {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@ManyToOne
	private TipoGibi tipo;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String classIndicativa;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	private String editora;
	private String autor;
	private int qtdPaginas;
	
	public String[] verFotos() {
		return this.fotos.split(";");
	}
}
