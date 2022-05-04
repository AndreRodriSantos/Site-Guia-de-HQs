package br.com.andre.laranja.hqguide.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.andre.laranja.hqguide.annotation.Privado;
import br.com.andre.laranja.hqguide.annotation.Publico;
import br.com.andre.laranja.hqguide.rest.UsuarioRestController;

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
				String token = null;
				if (metodoChamado.getMethodAnnotation(Privado.class) != null) {
					try {
						// obtem o token da request
						token = request.getHeader("Authorization");
						Algorithm algoritimo = Algorithm.HMAC256(UsuarioRestController.SECRET);
						JWTVerifier verifier = JWT.require(algoritimo).withIssuer(UsuarioRestController.EMISSOR)
								.build();
						DecodedJWT jwt = verifier.verify(token);
						//extrair os dados do payload
						Map<String, Claim> payloadMap = jwt.getClaims();
					} catch (Exception e) {
						if(token == null) {
							response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
						}else {
							response.sendError(HttpStatus.FORBIDDEN.value(), e.getMessage());
						}
						return false;
					}
				}
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
