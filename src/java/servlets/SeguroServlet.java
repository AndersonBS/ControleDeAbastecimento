package servlets;

import dao.SeguroDAO;
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
import modelo.Seguro;

@WebServlet(name = "SeguroServlet", urlPatterns = {"/SeguroServlet"})
public class SeguroServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            SeguroDAO seguroDAO = new SeguroDAO();
            Seguro seguro = new Seguro();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                seguro.setAutomovel((Automovel) session.getAttribute("selectedAutomovel"));
                seguro.setInicioVigencia(new Date(dateFormat.parse(request.getParameter("inicioVigencia")).getTime()));
                seguro.setFimVigencia(new Date(dateFormat.parse(request.getParameter("fimVigencia")).getTime()));
                seguro.setValorParcela(Double.parseDouble(request.getParameter("valorParcela")));
                seguro.setQuantidadeParcelas(Integer.parseInt(request.getParameter("quantidadeParcelas")));
                seguro.setValorFranquia(Double.parseDouble(request.getParameter("valorFranquia")));
                seguro.setValorCobertura(Double.parseDouble(request.getParameter("valorCobertura")));
                seguro.setAnotacoes(request.getParameter("anotacoes"));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (seguroDAO.salvarSeguro(seguro)) {
                        mensagem = "<font color=\"green\">Seguro cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro do seguro!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroSeguro.jsp");
                    break;
                }
                case "alterar": {
                    seguro.setId(Integer.parseInt(request.getParameter("idSeguro")));
                    if (seguroDAO.alterarSeguro(seguro)) {
                        mensagem = "<font color=\"green\">Seguro alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o seguro!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaSeguros.jsp");
                    break;
                }
                case "apagar": {
                    if (seguroDAO.apagarSeguro(Integer.parseInt(request.getParameter("excSeguro")))) {
                        mensagem = "<font color=\"green\">Seguro apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o seguro!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaSeguros.jsp");
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
