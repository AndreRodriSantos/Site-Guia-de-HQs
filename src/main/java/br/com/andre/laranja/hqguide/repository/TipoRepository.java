package br.com.andre.laranja.hqguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.TipoGibi;

@Repository
public interface TipoRepository extends PagingAndSortingRepository<TipoGibi, Long> {
	@Query("select t from TipoGibi t where t.palavraChave like %:pc%")
	public List<TipoGibi> procurarPelaPalavra(@Param("pc") String palavra);

	@Query("select t from TipoGibi t where t.descricao like %:pc%")
	public List<TipoGibi> procurarPelaDescricao(@Param("pc") String palavra);

	public List<TipoGibi> findByNomeLike(String palavra);
	
	public List<TipoGibi> findAllByOrderByNomeAsc();
}
