package br.com.andre.laranja.hqguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.TipoGibi;

	@Repository
	public interface TipoRepository extends PagingAndSortingRepository<TipoGibi, Long>{
		
		@Query("select t from tipo_gibi t where t.nome Like %:t% OR t.descricao Like %:t% OR t.palavraChave Like %:t%")
		public List<TipoGibi> procurarTipo(@Param("t") String parametro);
	}

