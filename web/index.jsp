<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt">
    <head>
        
        <title>Controle de Combustível</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Menu Superior (Navbar) -->
        <nav class="navbar navbar-inverse" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
                            data-target="#navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <img alt="Brand" src="IMG/icone.png" height="50" width="50">
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><button class="navbar-btn btn btn-primary" data-toggle="modal" href="#login_modal">Entrar</button>
                        <button class="navbar-btn btn btn-primary" data-toggle="modal" href="#register_modal">Registrar</button></li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <!-- Capa -->
        <div align="center">
            <img src="IMG/UNOESC.png" width="225" height="175">
            <h1>UNIVERSIDADE DO OESTE DE SANTA CATARINA</h1>
            <h3>CURSO: ENGENHARIA DA COMPUTAÇÃO</h3>
            <h3>DISCIPLINA: PROGRAMAÇÃO PARA WEB II</h3>
            <h3>PROFESSOR: EDUARDO COMIN</h3>
            <h3>ALUNO: ANDERSON B. SENSOLO</h3>
        </div>
        
        <!-- Modal de Login -->
        <div class="modal fade" id="login_modal">
            <div class="modal-dialog">
                <div class="modal-content" align="center">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">x</button>
                        <h3>Efetue o Login</h3>
                    </div>
                    <div class="modal-body">
                        <form method="post" class="form-horizontal" action="UsuarioServlet">
                            <input type="hidden" id="operation" name="operation" value="login">
                            <input type="email" required="true" maxlength="32" class="form-control" name="login_email" 
                                   id="login_email" placeholder="Digite seu E-mail" style="margin-bottom: 10px;">
                            <input type="password" required="true" maxlength="32" class="form-control" name="login_password" 
                                   id="login_password" placeholder="Digite sua Senha" style="margin-bottom: 10px;">
                            <button type="submit" class="btn btn-primary" name="login_submit" id="login_submit" 
                                    style="margin-right: 5px;">Login</button>
                            <a name="login_retrieve" id="login_retrieve" data-toggle="modal" data-target="#retrieve_modal">Esqueceu a senha?</a>
                            <c:if test="${message != null && !message.isEmpty()}">
                                <h4 align="center" name="message" id="message">${message}</h4>
                            </c:if>
                        </form>
                    </div>
                    <div class="modal-footer">
                        Ainda não possui cadastro?
                        <a name="login_register" id="login_register" class="btn btn-primary" data-toggle="modal" data-target="#register_modal"
                           style="margin-left: 10px;">Registrar</a>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal de Registro de Usuário -->
        <div class="modal fade" id="register_modal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">x</button>
                        <h3 align="center">Cadastro de Usuário</h3>
                    </div>
                    <div class="modal-body">
                        <form method="post" class="form-horizontal" action="UsuarioServlet" enctype="multipart/form-data">
                            <input type="hidden" id="operation" name="operation" value="register">
                            <input type="text" required="true" maxlength="32" class="form-control" name="register_name" 
                                   id="register_name" placeholder="Digite seu Nome" style="margin-bottom: 10px;">
                            <input type="email" required="true" maxlength="32" class="form-control" name="register_email" 
                                   id="register_email" placeholder="Digite seu E-mail" style="margin-bottom: 10px;">
                            <input type="password" required="true" maxlength="32" class="form-control" name="register_password" 
                                   id="register_password" placeholder="Digite sua Senha" style="margin-bottom: 10px;">
                            <input type="password" required="true" maxlength="32" class="form-control" name="register_cnf_password" 
                                   id="register_cnf_password" placeholder="Digite sua Senha novamente" style="margin-bottom: 10px;">
                            <div align="center">
                                <label for="foto"><img name="show_foto" id="show_foto" src="IMG/sem_foto.png" width="145" height="145"/></label>
                                <input type="file" name="foto" id="foto" accept="image/*" style="visibility: hidden;" onchange="readURL(this)">
                            </div>
                            <p align="center"><input type="checkbox" checked name="register_updates" id="register_updates">
                                Desejo receber atualizações diárias</p>
                            <p align="center"><input type="checkbox" name="register_terms" id="register_terms">
                                Concordo com os<a href="#">Termos e Condições</a></p>
                            <p align="center">
                                <button type="reset" class="btn btn-primary" 
                                        onclick="$('#show_foto').attr('src', 'IMG/sem_foto.png');">Limpar</button>
                                <button type="submit" class="btn btn-primary" name="register_submit" id="register_submit" 
                                        disabled="">Cadastrar</button>
                            </p>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal de Recuperação de Senha -->
        <div class="modal fade" id="retrieve_modal">
            <div class="modal-dialog">
                <div class="modal-content" align="center">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">x</button>
                        <h3 align="center">Recuperar Senha</h3>
                    </div>
                    <div class="modal-body">
                        <form method="post" class="form-horizontal" action="UsuarioServlet">
                            <input type="hidden" id="operation" name="operation" value="retrieve">
                            <p>Um email contendo as informações de como recuperar sua senha será encaminhado!</p>
                            <input type="email" required="true" maxlength="32" class="form-control" name="retrieve_email" id="retrieve_email" 
                                   placeholder="Digite seu E-mail" style="margin-bottom: 10px;">
                            <button name="retrieve_submit" id="retrieve_submit" type="submit" class="btn btn-primary">Enviar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

