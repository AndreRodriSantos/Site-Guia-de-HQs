package br.com.andre.laranja.hqguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.andre.laranja.hqguide.annotation.Publico;
import br.com.andre.laranja.hqguide.model.Quadrinho;
import br.com.andre.laranja.hqguide.repository.QuadrinhoRepository;

@RequestMapping("/api/quadrinho")
@RestController
public class HQRestController {

	@Autowired
	private QuadrinhoRepository repository;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<Quadrinho> getQuadrinho() {
		return repository.findAll();
	}

	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Quadrinho> findHQ(@PathVariable("id") Long id) {
		// busca o quadrinho
		Optional<Quadrinho> quadrinho = repository.findById(id);
		if (quadrinho.isPresent()) {
			return ResponseEntity.ok(quadrinho.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	@Publico
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET)
	public List<Quadrinho> getQuadrinhoByTipo(@PathVariable("idTipo") Long idTipo) {
		return repository.findByTipoId(idTipo);
	}
	
	@RequestMapping(value = "classificacaoIndicativa/{classIndicativa}")
	public List<Quadrinho> getQuadrinhoByClassInd(@PathVariable("classIndicativa")String classInd){
		return repository.findByClassIndicativa(classInd);
	}
}
