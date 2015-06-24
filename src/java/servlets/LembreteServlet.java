package servlets;

import dao.LembreteDAO;
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
import modelo.Lembrete;

@WebServlet(name = "LembreteServlet", urlPatterns = {"/LembreteServlet"})
public class LembreteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            LembreteDAO lembreteDAO = new LembreteDAO();
            Lembrete lembrete = new Lembrete();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                lembrete.setAutomovel((Automovel) session.getAttribute("selectedAutomovel"));
                lembrete.setDataPrevista(new Date(dateFormat.parse(request.getParameter("dataPrevista")).getTime()));
                lembrete.setValorOrcado(Double.parseDouble(request.getParameter("valorOrcado")));
                lembrete.setDescricaoLocal(request.getParameter("descricaoLocal"));
                lembrete.setDescricaoServico(request.getParameter("descricaoServico"));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (lembreteDAO.salvarLembrete(lembrete)) {
                        mensagem = "<font color=\"green\">Lembrete cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro do lembrete!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroLembrete.jsp");
                    break;
                }
                case "alterar": {
                    lembrete.setId(Integer.parseInt(request.getParameter("idLembrete")));
                    if (lembreteDAO.alterarLembrete(lembrete)) {
                        mensagem = "<font color=\"green\">Lembrete alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o lembrete!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaLembretes.jsp");
                    break;
                }
                case "apagar": {
                    if (lembreteDAO.apagarLembrete(Integer.parseInt(request.getParameter("excLembrete")))) {
                        mensagem = "<font color=\"green\">Lembrete apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o lembrete!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaLembretes.jsp");
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
