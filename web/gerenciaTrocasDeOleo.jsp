<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="trocaDeOleoDAO" class="dao.TrocaDeOleoDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Trocas de Óleo</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Trocas de Óleo -->
        <div align="center">
            <h1>Trocas de Óleo Cadastradas</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Nome do Óleo</th>
                        <th class="text-center">Tipo do Óleo</th>
                        <th class="text-center">Data da Troca</th>
                        <th class="text-center">Data da Próxima Troca</th>
                        <th class="text-center">Preço</th>
                        <th class="text-center">Filtro</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="trocaDeOleo" varStatus="status" items="${trocaDeOleoDAO.lerTrocasDeOleo(selectedAutomovel.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>           
                            <td align="center">${trocaDeOleo.nomeOleo}</td>
                            <td align="center">${trocaDeOleo.tipoOleo}</td>
                            <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${trocaDeOleo.dataTroca}"/></td>
                            <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${trocaDeOleo.dataProximaTroca}"/></td>
                            <td align="center">R$ ${trocaDeOleo.preco}</td>
                            <td align="center">
                                <c:choose>
                                    <c:when test="${trocaDeOleo.trocaFiltro}">
                                        <span class="glyphicon glyphicon-ok"></span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="glyphicon glyphicon-remove"></span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        <form method="post" action="cadastroTrocaDeOleo.jsp">
                            <td align="center">
                                <input type="hidden" name="altTrocaDeOleo" id="altTrocaDeOleo" value="${trocaDeOleo.id}">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <span class="glyphicon glyphicon-cog"></span></button>
                            </td>
                        </form>
                        <form method="post" action="TrocaDeOleoServlet">
                            <td align="center">
                                <input type="hidden" name="excTrocaDeOleo" id="excTrocaDeOleo" value="${trocaDeOleo.id}">
                                <input type="hidden" id="operacao" name="operacao" value="apagar">
                                <button type="submit" class="btn btn-sm btn-danger" 
                                        onclick="return confirm('Tem certeza que deseja excluir esta troca de óleo?')">
                                    <span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroTrocaDeOleo.jsp'"
                        style="font-weight: bold;">Cadastrar Trocas de Óleo</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
