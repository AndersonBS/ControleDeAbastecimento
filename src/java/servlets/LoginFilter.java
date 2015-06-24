package servlets;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@WebFilter(filterName = "LoginFilter", 
        urlPatterns = {"/home.jsp", "/cadastroAbastecimento.jsp", "/cadastroAutomovel.jsp", "/cadastroCombustivel.jsp",
        "/cadastroLembrete.jsp", "/cadastroPosto.jsp", "/cadastroSeguro.jsp", "/cadastroServico.jsp", "/cadastroTrocaDeOleo.jsp",
        "/gerenciaAbastecimentos.jsp", "/gerenciaAutomoveis.jsp", "/gerenciaCombustiveis.jsp", "/gerenciaLembretes.jsp",
        "/gerenciaPostos.jsp", "/gerenciaSeguros.jsp", "/gerenciaServicos.jsp", "/gerenciaTrocasDeOleo.jsp", "/perfil.jsp"}, 
        servletNames = {"AbastecimentoServlet", "AutomovelServlet", "CombustivelServlet", "LembreteServlet", "PostoServlet",
        "SeguroServlet", "ServicoServlet", "TrocaDeOleoServlet", "ImageServlet"}, 
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE})
public class LoginFilter implements Filter {   
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } else {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = (Usuario) session.getAttribute("loggedInUser");
            if (usuarioDAO.isUsuarioHash(usuario.getId(), usuario.getHash())) {
                chain.doFilter(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
        }
    }

    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) {        
    }
    
}
