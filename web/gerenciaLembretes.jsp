<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="lembreteDAO" class="dao.LembreteDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Lembretes</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Lembretes -->
        <div align="center">
            <h1>Lembretes Cadastradas</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Data Prevista</th>
                        <th class="text-center">Valor Orçado</th>
                        <th class="text-center">Descrição do Local</th>
                        <th class="text-center">Descrição do Serviço</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="lembrete" varStatus="status" items="${lembreteDAO.lerLembretes(selectedAutomovel.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>       
                        <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${lembrete.dataPrevista}"/></td>
                        <td align="center">R$ ${lembrete.valorOrcado}</td>
                        <td align="center">${lembrete.descricaoLocal}</td>
                        <td align="center">${lembrete.descricaoServico}</td>
                        <form method="post" action="cadastroLembrete.jsp">
                            <td align="center">
                                <input type="hidden" name="altLembrete" id="altLembrete" value="${lembrete.id}">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <span class="glyphicon glyphicon-cog"></span></button>
                            </td>
                        </form>
                        <form method="post" action="LembreteServlet">
                            <td align="center">
                                <input type="hidden" name="excLembrete" id="excLembrete" value="${lembrete.id}">
                                <input type="hidden" id="operacao" name="operacao" value="apagar">
                                <button type="submit" class="btn btn-sm btn-danger" 
                                        onclick="return confirm('Tem certeza que deseja excluir este lembrete?')">
                                    <span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroLembrete.jsp'"
                        style="font-weight: bold;">Cadastrar Lembretes</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
