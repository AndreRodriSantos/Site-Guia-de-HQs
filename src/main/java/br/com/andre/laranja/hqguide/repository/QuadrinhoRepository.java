package br.com.andre.laranja.hqguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.Quadrinho;
import br.com.andre.laranja.hqguide.model.TipoGibi;

@Repository
public interface QuadrinhoRepository extends PagingAndSortingRepository<Quadrinho, Long>{
}
