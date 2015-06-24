package servlets;

import dao.UsuarioDAO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import modelo.Usuario;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = null;
            String mensagem = "";
            switch (request.getParameter("operation")) {
                case "login": {
                    Usuario usuario = usuarioDAO.isUsuario(request.getParameter("login_email"), request.getParameter("login_password"));
                    if (usuario != null) {
                        request.getSession().setAttribute("loggedInUser", usuario);
                        requestDispatcher = servletContext.getRequestDispatcher("/home.jsp");
                    } else {
                        mensagem = "<font color=\"red\">E-mail e/ou Senha inválidos!</font>";
                        requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
                    }
                    break;
                }
                case "register": {
                    Usuario usuario = new Usuario();
                    usuario.setNome(request.getParameter("register_name"));
                    usuario.setEmail(request.getParameter("register_email"));
                    usuario.setSenha(request.getParameter("register_password"));
                    
                    Part foto = request.getPart("foto");
                    InputStream input = foto.getInputStream();
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[10240];
                    for (int length = 0; (length = input.read(buffer)) > 0;) output.write(buffer, 0, length);
                    usuario.setFoto(output.toByteArray());
                    
                    if (usuarioDAO.salvarUsuario(usuario)) {
                        mensagem = "<font color=\"green\">Usuário cadastrado com sucesso!</font>";
                    } else {
                        mensagem = "<font color=\"red\">Ocorreu um erro na tentativa de cadastrar o usuário!</font>";
                    }
                    request.getSession().setAttribute("selectedAutomovel", null);
                    requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
                    break;
                }
                case "logout": {
                    request.getSession().setAttribute("loggedInUser", null);
                    request.getSession().setAttribute("selectedAutomovel", null);
                    requestDispatcher = servletContext.getRequestDispatcher("/index.jsp");
                    break;
                }
                case "retrieve": {
                    Properties props = new Properties();
                    Session session = Session.getDefaultInstance(props, null);
                    try {
                        Message email = new MimeMessage(session);
                        email.setFrom(new InternetAddress("anderson.sensolo@gmail.com", "Gmail.com Anderson"));
                        email.addRecipient(Message.RecipientType.TO, new InternetAddress(request.getParameter("retrieve_email")));
                        email.setSubject("Recuperar Senha");
                        email.setText("Teste!!");
                        Transport.send(email);
                    } catch (AddressException addressException) {
                        System.out.println(addressException.getMessage());
                    } catch (MessagingException messagingException) {
                        System.out.println(messagingException.getMessage());
                    }
                    break;
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
    }// </editor-fold>

}
