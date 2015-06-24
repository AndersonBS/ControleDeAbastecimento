<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="postoDAO" class="dao.PostoDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Postos</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Postos -->
        <div align="center">
            <h1>Postos Cadastrados</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Nome</th>
                        <th class="text-center">Marca</th>
                        <th class="text-center">Endereço</th>
                        <th class="text-center">Telefone</th>
                        <th class="text-center">Cartão</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="posto" varStatus="status" items="${postoDAO.lerPostos(loggedInUser.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>           
                            <td align="center">${posto.nome}</td>
                            <td align="center">${posto.marca}</td>
                            <td align="center">${posto.endereco}</td>
                            <td align="center">${posto.telefone}</td>
                            <td align="center">
                                <c:choose>
                                    <c:when test="${posto.cartao}">
                                        <span class="glyphicon glyphicon-ok"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        <form method="post" action="cadastroPosto.jsp">
                            <td align="center">
                                <input type="hidden" name="altPosto" id="altPosto" value="${posto.id}">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <span class="glyphicon glyphicon-cog"></span></button>
                            </td>
                        </form>
                        <form method="post" action="PostoServlet">
                            <td align="center">
                                <input type="hidden" name="excPosto" id="excPosto" value="${posto.id}">
                                <input type="hidden" id="operacao" name="operacao" value="apagar">
                                <button type="submit" class="btn btn-sm btn-danger" 
                                        onclick="return confirm('Tem certeza que deseja excluir este posto?')">
                                    <span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroPosto.jsp'"
                        style="font-weight: bold;">Cadastrar Postos</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
