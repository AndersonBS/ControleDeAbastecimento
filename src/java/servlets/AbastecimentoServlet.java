package servlets;

import dao.AbastecimentoDAO;
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
import modelo.Abastecimento;
import modelo.Automovel;
import modelo.Combustivel;
import modelo.Posto;

@WebServlet(name = "AbastecimentoServlet", urlPatterns = {"/AbastecimentoServlet"})
public class AbastecimentoServlet extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            AbastecimentoDAO abastecimentoDAO = new AbastecimentoDAO();
            Abastecimento abastecimento = new Abastecimento();
            HttpSession session = request.getSession(false);
            
            if ("salvar".equals(request.getParameter("operacao")) || "alterar".equals(request.getParameter("operacao"))) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
                abastecimento.setData(new Date(dateFormat.parse(request.getParameter("data")).getTime()));
                abastecimento.setOdometro(Double.parseDouble(request.getParameter("odometro")));
                abastecimento.setLitros(Double.parseDouble(request.getParameter("litros")));
                abastecimento.setValorTotal(Double.parseDouble(request.getParameter("valorTotal")));
                abastecimento.setAnotacoes(request.getParameter("anotacoes"));

                Combustivel combustivel = new Combustivel();
                combustivel.setId(Integer.parseInt(request.getParameter("combustivel")));
                abastecimento.setCombustivel(combustivel);
                
                abastecimento.setAutomovel((Automovel) session.getAttribute("selectedAutomovel"));

                Posto posto = new Posto();
                posto.setId(Integer.parseInt(request.getParameter("posto")));
                abastecimento.setPosto(posto);
            }
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operacao")) {
                case "salvar": {
                    if (abastecimento.getAutomovel() == null) {
                        mensagem = "<font color=\"red\">Você ainda não possui nenhum automóvel cadastrado!</font>";
                    } else if (abastecimentoDAO.salvarAbastecimento(abastecimento)) {
                        mensagem = "<font color=\"green\">Abastecimento cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de realizar o cadastro!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/cadastroAbastecimento.jsp");
                    break;
                }
                case "alterar": {
                    abastecimento.setId(Integer.parseInt(request.getParameter("idAbastecimento")));
                    if (abastecimentoDAO.alterarAbastecimento(abastecimento)) {
                        mensagem = "<font color=\"green\">Abastecimento alterado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de alterar o abastecimento!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaAbastecimentos.jsp");
                    break;
                }
                case "apagar": {
                    if (abastecimentoDAO.apagarAbastecimento(Integer.parseInt(request.getParameter("excAbastecimento")))) {
                        mensagem = "<font color=\"green\">Abastecimento apagado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de apagar o abastecimento!</font>";
                    }
                    requestDispatcher = servletContext.getRequestDispatcher("/gerenciaAbastecimentos.jsp");
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
