package servlets;

import dao.ServicoDAO;
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
import modelo.Servico;

@WebServlet(name = "ServicoServlet", urlPatterns = {"/ServicoServlet"})
public class ServicoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            ServicoDAO servicoDAO = new ServicoDAO();
            Servico servico = new Servico();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                servico.setAutomovel((Automovel) session.getAttribute("selectedAutomovel"));
                servico.setData(new Date(dateFormat.parse(request.getParameter("data")).getTime()));
                servico.setDescricao(request.getParameter("descricao"));
                servico.setValorGasto(Double.parseDouble(request.getParameter("valorGasto")));
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (servicoDAO.salvarServico(servico)) {
                        mensagem = "<font color=\"green\">Serviço cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro do serviço!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroServico.jsp");
                    break;
                }
                case "alterar": {
                    servico.setId(Integer.parseInt(request.getParameter("idServico")));
                    if (servicoDAO.alterarServico(servico)) {
                        mensagem = "<font color=\"green\">Serviço alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o serviço!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaServicos.jsp");
                    break;
                }
                case "apagar": {
                    if (servicoDAO.apagarServico(Integer.parseInt(request.getParameter("excServico")))) {
                        mensagem = "<font color=\"green\">Serviço apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o serviço!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaServicos.jsp");
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
