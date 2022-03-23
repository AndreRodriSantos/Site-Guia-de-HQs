package br.com.andre.laranja.hqguide.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
		if(result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos...");
			return "redirect:formAdmin";
		}
		try {
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID:"+ admin.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador:"+ e.getMessage());
		}
		return "redirect:formAdmin";
	}
}
