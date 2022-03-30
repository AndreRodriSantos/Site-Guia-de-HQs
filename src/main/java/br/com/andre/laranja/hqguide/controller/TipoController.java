package br.com.andre.laranja.hqguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.andre.laranja.hqguide.model.Administrador;
import br.com.andre.laranja.hqguide.model.TipoGibi;
import br.com.andre.laranja.hqguide.repository.TipoRepository;

@Controller
public class TipoController {
	
	@Autowired
	private TipoRepository repository;
	
	@RequestMapping("formCategorias")
	public String form_adm(Model model) {
		return "formTipos";
	}
	@RequestMapping(value = "salvarTipo", method = RequestMethod.POST)
	public String salvarTipo(@Valid TipoGibi tipo, BindingResult result, RedirectAttributes attr) {
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos...");
			return "redirect:formCategorias";
		}

		try {
			repository.save(tipo);
			attr.addFlashAttribute("mensagemSucesso", "Nova Categoria cadastrada com sucesso. ID:" + tipo.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar essa Categoria:" + e.getMessage());
		}
		return "redirect:formCategorias";
	}
	
	@RequestMapping("listarTipos/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// criar um pageable com 6 elementos de forma ascendente ordenado pelo nome
		PageRequest pageable = PageRequest.of(page - 1, 10, Sort.Direction.ASC, "nome");
		// cria a pagina atual atraves do repository
		Page<TipoGibi> pagina = repository.findAll(pageable);
		// descobrir o total de paginas
		int totalpages = pagina.getTotalPages();
		// cria um lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalpages; i++) {
			pageNumbers.add(i + 1);
		}
		// adiciona as variaveis no Model
		model.addAttribute("tipos", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalpages);
		model.addAttribute("numPaginas", pageNumbers);
		return "listaTipos";
	}
	
	@RequestMapping("alterarTipo")
	public String alterarTipo(Model model, Long id) {
		TipoGibi tipo = repository.findById(id).get();
		model.addAttribute("tipo", tipo);
		return "forward:formCategorias";
	}

	@RequestMapping("excluirTipo")
	public String excluirTipo(Long id) {
		repository.deleteById(id);
		return "redirect:listarTipos/1";
	}
	
	}
