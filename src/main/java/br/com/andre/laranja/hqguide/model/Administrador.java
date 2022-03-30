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
	
	//metodo para setar a senha aplicando o hash
	public void setSenha(String senha) {
		//aplica o hash e "seta" a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}
	// metodo para setar a senha sem fazer hash
	public void setSenhaComHash(String hash) {
		//"seta" o hash na senha
		this.senha = hash;
	}
}
