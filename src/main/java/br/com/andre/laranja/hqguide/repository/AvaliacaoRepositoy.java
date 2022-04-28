package br.com.andre.laranja.hqguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.Avaliacao;

@Repository
public interface AvaliacaoRepositoy extends PagingAndSortingRepository<Avaliacao, Long>{
	
	public List<Avaliacao> findByQuadrinhoId(Long idQuadrinho);
	
}
