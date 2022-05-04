package br.com.andre.laranja.hqguide.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.andre.laranja.hqguide.annotation.Privado;
import br.com.andre.laranja.hqguide.annotation.Publico;
import br.com.andre.laranja.hqguide.model.Avaliacao;
import br.com.andre.laranja.hqguide.repository.AvaliacaoRepositoy;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {

	@Autowired
	private AvaliacaoRepositoy repository;

	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao) {
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/" + avaliacao.getId())).body(avaliacao);
	}

	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Avaliacao> findAvaliacao(@PathVariable("id") Long id) {
		// busca quadrinho
		Optional<Avaliacao> avaliacao = repository.findById(id);
		if (avaliacao.isPresent()) {
			return ResponseEntity.ok(avaliacao.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Publico
	@RequestMapping(value = "/quadrinho/{idQuadrinho}", method = RequestMethod.GET)
	public List<Avaliacao> getQuadrinhoById(@PathVariable("idQuadrinho") Long idQuadrinho) {
		return repository.findByQuadrinhoId(idQuadrinho);
	}

}
