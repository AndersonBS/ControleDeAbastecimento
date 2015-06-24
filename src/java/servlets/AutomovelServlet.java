package servlets;

import dao.AutomovelDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Automovel;
import modelo.Usuario;

@WebServlet(name = "AutomovelServlet", urlPatterns = {"/AutomovelServlet"})
public class AutomovelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            AutomovelDAO automovelDAO = new AutomovelDAO();
            Automovel automovel = new Automovel();
            HttpSession session = request.getSession(false);
            automovel.setUsuario((Usuario) session.getAttribute("loggedInUser"));
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                automovel.setNome(request.getParameter("nome"));
                automovel.setMarca(request.getParameter("marca"));
                automovel.setAnoModelo(Integer.parseInt(request.getParameter("anoModelo")));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                automovel.setDataAquisicao(new Date(dateFormat.parse(request.getParameter("dataAquisicao")).getTime()));
                automovel.setPlaca(request.getParameter("placa"));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (automovelDAO.salvarAutomovel(automovel)) {
                        mensagem = "<font color=\"green\">'" + automovel.getNome() + ": " + automovel.getMarca()
                                + " (" + automovel.getPlaca() + ")' Cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroAutomovel.jsp");
                    break;
                }
                case "selecionar": {
                    automovel.setId(Integer.parseInt(request.getParameter("automovel")));
                    session.setAttribute("selectedAutomovel", automovel);
                    requestDispatcher = servletContext.getRequestDispatcher(request.getParameter("jsp_request_uri"));
                    break;
                }
                case "alterar": {
                    automovel.setId(Integer.parseInt(request.getParameter("idAutomovel")));
                    if (automovelDAO.alterarAutomovel(automovel)) {
                        mensagem = "<font color=\"green\">Automóvel alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o automóvel!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaAutomoveis.jsp");
                    break;
                }
                case "apagar": {
                    if (automovelDAO.apagarAutomovel(Integer.parseInt(request.getParameter("excAutomovel")))) {
                        mensagem = "<font color=\"green\">Automóvel apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o automóvel!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaAutomoveis.jsp");
                }
            }
            request.setAttribute("message", mensagem);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        } catch (ParseException parseException) {
            System.out.println(parseException.getMessage());
            Logger.getLogger(AutomovelServlet.class.getName()).log(Level.SEVERE, null, parseException);
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
