package br.com.andre.laranja.hqguide.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.andre.laranja.hqguide.model.Quadrinho;
import br.com.andre.laranja.hqguide.repository.QuadrinhoRepository;
import br.com.andre.laranja.hqguide.repository.TipoRepository;

@Controller
public class QuadrinhoController {
	@Autowired
	private TipoRepository repTipo;
	
	@Autowired
	private QuadrinhoRepository repHQ;
	
	@RequestMapping("formQuadrinho")
	public String form (Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "HQ/quadrinhoForm";
	}
	@RequestMapping(value = "salvarQuadrinho")
	public String salvarHQ(@Valid Quadrinho quadrinho, BindingResult result, RedirectAttributes attr, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos...");
			return "redirect:formQuadrinho";
		}
		try {
			System.out.println(fileFotos.length);
			//repHQ.save(quadrinho);
			attr.addFlashAttribute("mensagemSucesso", "Novo Quadrinho cadastrado com sucesso. ID:" + quadrinho.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar esse Quadrinho:" + e.getMessage());
		}
		return "redirect:formQuadrinho";
	}
}
