package br.com.andre.laranja.hqguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.andre.laranja.hqguide.util.HashUtil;
import lombok.Data;

//para gerar o get e o set
@Data
//para mapear como uma entidade JPA
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(unique = true)
	private String email;
	private String senha;
	
	//metodo para setar a senha apliacando o hash
	public void setSenha(String senha) {
		//aplica o hash e "seta" a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	
}
