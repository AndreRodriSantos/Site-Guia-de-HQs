package br.com.andre.laranja.hqguide.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.andre.laranja.hqguide.annotation.Publico;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		System.out.println(uri);
		System.out.println(handler.toString());
		// verifica se o handler é um handlermathod o que indica que foi encontrado um
		// metodo em algum controller para a requisição
		if (handler instanceof HandlerMethod) {
			// liberar o acesso a pagina inicial
			if (uri.equals("/")) {
				return true;
			}
			if (uri.endsWith("/error")) {
				return true;
			}
			// fazer o cating para handlerMethod
			HandlerMethod metodoChamado = (HandlerMethod) handler;
			if (uri.startsWith("/api")) {

				return true;
			} else {
				// se o method for público libera
				if (metodoChamado.getMethodAnnotation(Publico.class) != null) {
					return true;
				}
				// verifica se existe um usuario logado
				if (request.getSession().getAttribute("usuarioLogado") != null) {
					return true;
				} else {
					response.sendRedirect("/");
					return false;
				}
			}
		}
		return true;
	}
}
