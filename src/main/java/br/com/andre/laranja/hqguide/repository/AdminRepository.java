package br.com.andre.laranja.hqguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.andre.laranja.hqguide.model.Administrador;

@Repository
public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{

}
