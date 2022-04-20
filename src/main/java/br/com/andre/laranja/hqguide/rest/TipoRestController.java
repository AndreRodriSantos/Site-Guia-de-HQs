package br.com.andre.laranja.hqguide.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.andre.laranja.hqguide.annotation.Publico;
import br.com.andre.laranja.hqguide.model.TipoGibi;
import br.com.andre.laranja.hqguide.repository.TipoRepository;

@RequestMapping("/api/tipo")
@RestController
public class TipoRestController {

	@Autowired
	private TipoRepository tiporepository;

	@Publico
	@RequestMapping(value = "", method = RequestMethod.GET)
	public Iterable<TipoGibi> getTipo() {
		return tiporepository.findAll();
	}

	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TipoGibi> findHQ(@PathVariable("id") Long id) {
		// busca o quadrinho
		Optional<TipoGibi> tipo = tiporepository.findById(id);
		if (tipo.isPresent()) {
			return ResponseEntity.ok(tipo.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
