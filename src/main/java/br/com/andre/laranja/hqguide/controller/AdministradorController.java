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
import br.com.andre.laranja.hqguide.repository.AdminRepository;

@Controller
public class AdministradorController {

	@Autowired
	private AdminRepository repository;

	@RequestMapping("formAdmin")
	public String form_adm(Model model) {
		return "formAdministrador";
	}

	@RequestMapping(value = "salvarAdmin", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos...");
			return "redirect:formAdmin";
		}
		try {
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:" + admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador:" + e.getMessage());
		}
		return "redirect:formAdmin";
	}

	// request mapping para listar, informando a página desejada
	@RequestMapping("listarAdmin/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// criar um pageable com 6 elementos de forma ascendente ordenado pelo nome
		PageRequest pageable = PageRequest.of(page - 1, 10, Sort.Direction.ASC, "nome");
		// cria a pagina atual atraves do repository
		Page<Administrador> pagina = repository.findAll(pageable);
		// descobrir o total de paginas
		int totalpages = pagina.getTotalPages();
		// cria um lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalpages; i++) {
			pageNumbers.add(i + 1);
		}
		// adiciona as variaveis no Model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalpages);
		model.addAttribute("numPaginas", pageNumbers);
		return "listaAdmin";
	}
	
	@RequestMapping("alterarAdmin")
	public String alterarAdmin(Model model, Long id) {
		Administrador admin = repository.findById(id).get();
		model.addAttribute("admin", admin);
		return "forward:formAdmin";
	}

	@RequestMapping("excluirAdmin")
	public String excluirAdmin(Long id) {
		repository.deleteById(id);
		return "redirect:listarAdmin/1";
	}
}
