package servlets;

import dao.PostoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Posto;
import modelo.Usuario;

@WebServlet(name = "PostoServlet", urlPatterns = {"/PostoServlet"})
public class PostoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            PostoDAO postoDAO = new PostoDAO();
            Posto posto = new Posto();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                Usuario usuario = (Usuario) session.getAttribute("loggedInUser");
                posto.setUsuario(usuario);
                posto.setNome(request.getParameter("nome"));
                posto.setMarca(request.getParameter("marca"));
                posto.setEndereco(request.getParameter("endereco"));
                posto.setTelefone(request.getParameter("telefone"));
                posto.setCartao(Boolean.parseBoolean(request.getParameter("cartao")));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (postoDAO.salvarPosto(posto)) {
                        mensagem = "<font color=\"green\">Posto cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro do posto!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroPosto.jsp");
                    break;
                }
                case "alterar": {
                    posto.setId(Integer.parseInt(request.getParameter("idPosto")));
                    if (postoDAO.alterarPosto(posto)) {
                        mensagem = "<font color=\"green\">Posto alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o posto!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaPostos.jsp");
                    break;
                }
                case "apagar": {
                    if (postoDAO.apagarPosto(Integer.parseInt(request.getParameter("excPosto")))) {
                        mensagem = "<font color=\"green\">Posto apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o posto!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaPostos.jsp");
                }
            }
            request.setAttribute("message", mensagem);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
