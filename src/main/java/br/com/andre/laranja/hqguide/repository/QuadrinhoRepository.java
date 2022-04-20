package br.com.andre.laranja.hqguide.repository;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.Quadrinho;

@Repository
public interface QuadrinhoRepository extends PagingAndSortingRepository<Quadrinho, Long>{
	
	public List<Quadrinho> findByTipoId(Long idTipo);
	
	public List<Quadrinho> findByClassIndicativa(String classIndicativa);
}
