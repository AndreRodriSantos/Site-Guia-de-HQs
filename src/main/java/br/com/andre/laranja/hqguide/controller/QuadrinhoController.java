package br.com.andre.laranja.hqguide.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.andre.laranja.hqguide.model.Quadrinho;
import br.com.andre.laranja.hqguide.model.TipoGibi;
import br.com.andre.laranja.hqguide.repository.QuadrinhoRepository;
import br.com.andre.laranja.hqguide.repository.TipoRepository;
import br.com.andre.laranja.hqguide.util.FirebaseUtil;

@Controller
public class QuadrinhoController {
	@Autowired
	private TipoRepository repTipo;

	@Autowired
	private QuadrinhoRepository repHQ;

	@Autowired
	private FirebaseUtil firebaseUtil;

	@RequestMapping("formQuadrinho")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "HQ/quadrinhoForm";
	}

	@RequestMapping(value = "salvarQuadrinho")
	public String salvarHQ(@Valid Quadrinho quadrinho, BindingResult result, RedirectAttributes attr,
			@RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// String para a URL das fotos
		String fotos = quadrinho.getFotos();

		// percorrer cada arquivo no formulário
		for (MultipartFile arquivo : fileFotos) {
			// verificar se o arquivo está vazio
			if (arquivo.getOriginalFilename().isEmpty()) {
				// vai para o próximo arquivo
				continue;
			}
			// faz o upload para a nuvem e obtem a url
				try {
					fotos += firebaseUtil.uploadFile(arquivo) + ";";
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		quadrinho.setFotos(fotos);
		repHQ.save(quadrinho);
		if (result.hasErrors()) {
			attr.addFlashAttribute("mensagemErro", "Verifique os Campos...");
			return "redirect:formQuadrinho";
		}
		try {
			System.out.println(fileFotos.length);
			attr.addFlashAttribute("mensagemSucesso", "Novo Quadrinho cadastrado com sucesso. ID:" + quadrinho.getId());
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar esse Quadrinho:" + e.getMessage());
		}
		return "redirect:formQuadrinho";
	}
	
	@RequestMapping("listarQuadrinhos/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// criar um pageable com 6 elementos de forma ascendente ordenado pelo nome
		PageRequest pageable = PageRequest.of(page - 1, 10, Sort.Direction.ASC, "nome");
		// cria a pagina atual atraves do repository
		Page<Quadrinho> pagina = repHQ.findAll(pageable);
		// descobrir o total de paginas
		int totalpages = pagina.getTotalPages();
		// cria um lista de inteiros para representar as paginas
		List<Integer> pageNumbers = new ArrayList<Integer>();
		for (int i = 0; i < totalpages; i++) {
			pageNumbers.add(i + 1);
		}
		
		// adiciona as variaveis no Model
		model.addAttribute("hqs", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalpages);
		model.addAttribute("numPaginas", pageNumbers);
		return "HQ/listaQuadrinho";
	}
	
	@RequestMapping("alterarQuadrinho")
	public String alterarTipo(Model model, Long id) {
		Quadrinho hq = repHQ.findById(id).get();
		model.addAttribute("hq", hq);
		return "forward:formQuadrinho";
	}

	@RequestMapping("excluirQuadrinho")
		public String excluirQuadrinho(Long id) {
			Quadrinho hq = repHQ.findById(id).get();
			if(hq.getFotos().length() > 0) {
				for (String foto : hq.verFotos()) {
					firebaseUtil.deletar(foto);
				}
			}
			repHQ.delete(hq);
		return "redirect:listarQuadrinhos/1";
	}
	@RequestMapping("excluirFoto")
	public String excluirFoto(Long idQuadrinho, int numFoto, Model model) {
		//busca o restaurante no banco de dados
		Quadrinho hq = repHQ.findById(idQuadrinho).get();
		//pegar a String da foto a ser excluida
		String fotoUrl = hq.verFotos()[numFoto];
		//excluir do firebase
		firebaseUtil.deletar(fotoUrl);
		hq.setFotos(hq.getFotos().replace(fotoUrl + ";", ""));
		//salva no BD o objeto
		repHQ.save(hq);
		model.addAttribute("hq", hq);
		return "forward:formQuadrinho";
		
	}
}
