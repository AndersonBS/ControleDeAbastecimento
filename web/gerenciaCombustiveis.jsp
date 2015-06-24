<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="combustivelDAO" class="dao.CombustivelDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Combustíveis</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Combustíveis -->
        <div align="center">
            <h1>Automóveis Cadastrados</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-6 col-sm-offset-3">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Tipo do Combustível</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="combustivel" varStatus="status" items="${combustivelDAO.lerCombustiveis(loggedInUser.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>           
                            <td align="center">${combustivel.tipo}</td>
                            <form method="post" action="cadastroCombustivel.jsp">
                                <td align="center">
                                    <input type="hidden" name="altCombustivel" id="altCombustivel" value="${combustivel.id}">
                                    <button type="submit" class="btn btn-sm btn-warning">
                                        <span class="glyphicon glyphicon-cog"></span></button>
                                </td>
                            </form>
                            <form method="post" action="CombustivelServlet">
                                <td align="center">
                                    <input type="hidden" name="excCombustivel" id="excCombustivel" value="${combustivel.id}">
                                    <input type="hidden" id="operacao" name="operacao" value="apagar">
                                    <button type="submit" class="btn btn-sm btn-danger" 
                                            onclick="return confirm('Tem certeza que deseja excluir este combustível?')">
                                        <span class="glyphicon glyphicon-trash"></span></button>
                                </td>
                            </form>
                        </tr>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroCombustivel.jsp'"
                        style="font-weight: bold;">Cadastrar Combustíveis</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
