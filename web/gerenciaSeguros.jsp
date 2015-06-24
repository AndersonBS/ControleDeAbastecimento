<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="seguroDAO" class="dao.SeguroDAO"/>


<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Seguros</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Seguros -->
        <div align="center">
            <h1>Seguros Cadastradas</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Início da Vigência</th>
                        <th class="text-center">Fim da Vigência</th>
                        <th class="text-center">Valor da Parcela</th>
                        <th class="text-center">Parcelas</th>
                        <th class="text-center">Valor da Franquia</th>
                        <th class="text-center">Valor da Cobertura</th>
                        <th class="text-center">Anotações</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="seguro" varStatus="status" items="${seguroDAO.lerSeguros(selectedAutomovel.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>       
                        <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.inicioVigencia}"/></td>
                        <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${seguro.fimVigencia}"/></td>
                        <td align="center">R$ ${seguro.valorParcela}</td>
                        <td align="center">${seguro.quantidadeParcelas}</td>
                        <td align="center">R$ ${seguro.valorFranquia}</td>
                        <td align="center">R$ ${seguro.valorCobertura}</td>
                        <td align="center">${seguro.anotacoes}</td>
                        <form method="post" action="cadastroSeguro.jsp">
                            <td align="center">
                                <input type="hidden" name="altSeguro" id="altSeguro" value="${seguro.id}">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <span class="glyphicon glyphicon-cog"></span></button>
                            </td>
                        </form>
                        <form method="post" action="SeguroServlet">
                            <td align="center">
                                <input type="hidden" name="excSeguro" id="excSeguro" value="${seguro.id}">
                                <input type="hidden" id="operacao" name="operacao" value="apagar">
                                <button type="submit" class="btn btn-sm btn-danger" 
                                        onclick="return confirm('Tem certeza que deseja excluir este seguro?')">
                                    <span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroSeguro.jsp'"
                        style="font-weight: bold;">Cadastrar Seguros</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
