package servlets;

import dao.TrocaDeOleoDAO;
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
import modelo.TrocaDeOleo;

@WebServlet(name = "TrocaDeOleoServlet", urlPatterns = {"/TrocaDeOleoServlet"})
public class TrocaDeOleoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            TrocaDeOleoDAO trocaDeOleoDAO = new TrocaDeOleoDAO();
            TrocaDeOleo trocaDeOleo = new TrocaDeOleo();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                trocaDeOleo.setAutomovel((Automovel) session.getAttribute("selectedAutomovel"));
                trocaDeOleo.setNomeOleo(request.getParameter("nomeOleo"));
                trocaDeOleo.setTipoOleo(request.getParameter("tipoOleo"));
                trocaDeOleo.setDataTroca(new Date(dateFormat.parse(request.getParameter("dataTroca")).getTime()));
                trocaDeOleo.setDataProximaTroca(new Date(dateFormat.parse(request.getParameter("dataProximaTroca")).getTime()));
                trocaDeOleo.setTrocaFiltro(Boolean.parseBoolean(request.getParameter("trocaFiltro")));
                trocaDeOleo.setPreco(Double.parseDouble(request.getParameter("preco")));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (trocaDeOleoDAO.salvarTrocaDeOleo(trocaDeOleo)) {
                        mensagem = "<font color=\"green\">Troca de óleo cadastrada com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro da troca de óleo!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroTrocaDeOleo.jsp");
                    break;
                }
                case "alterar": {
                    trocaDeOleo.setId(Integer.parseInt(request.getParameter("idTrocaDeOleo")));
                    if (trocaDeOleoDAO.alterarTrocaDeOleo(trocaDeOleo)) {
                        mensagem = "<font color=\"green\">Troca de óleo alterada com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar a troca de óleo!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaTrocasDeOleo.jsp");
                    break;
                }
                case "apagar": {
                    if (trocaDeOleoDAO.apagarTrocaDeOleo(Integer.parseInt(request.getParameter("excTrocaDeOleo")))) {
                        mensagem = "<font color=\"green\">Troca de óleo apagada com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar a troca de óleo!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaTrocasDeOleo.jsp");
                }
            }
            request.setAttribute("message", mensagem);
            if (requestDispatcher != null) {
                requestDispatcher.forward(request, response);
            }
        } catch (ParseException parseException) {
            System.out.println(parseException.getMessage());
            Logger.getLogger(AbastecimentoServlet.class.getName()).log(Level.SEVERE, null, parseException);
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
