package servlets;

import dao.CombustivelDAO;
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
import modelo.Combustivel;
import modelo.Usuario;

@WebServlet(name = "CombustivelServlet", urlPatterns = {"/CombustivelServlet"})
public class CombustivelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            CombustivelDAO combustivelDAO = new CombustivelDAO();
            Combustivel combustivel = new Combustivel();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                Usuario usuario = (Usuario) session.getAttribute("loggedInUser");
                combustivel.setUsuario(usuario);
                combustivel.setTipo(request.getParameter("tipo"));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (combustivelDAO.salvarCombustivel(combustivel)) {
                        mensagem = "<font color=\"green\">Combustível cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro do combustível!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroCombustivel.jsp");
                    break;
                }
                case "alterar": {
                    combustivel.setId(Integer.parseInt(request.getParameter("idCombustivel")));
                    if (combustivelDAO.alterarCombustivel(combustivel)) {
                        mensagem = "<font color=\"green\">Combustível alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o combustível!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaCombustiveis.jsp");
                    break;
                }
                case "apagar": {
                    if (combustivelDAO.apagarCombustivel(Integer.parseInt(request.getParameter("excCombustivel")))) {
                        mensagem = "<font color=\"green\">Combustível apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o combustível!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaCombustiveis.jsp");
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
