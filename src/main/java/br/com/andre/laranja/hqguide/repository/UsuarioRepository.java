package br.com.andre.laranja.hqguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.andre.laranja.hqguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

	public Usuario findByEmailAndSenha(String email, String senha);

}
