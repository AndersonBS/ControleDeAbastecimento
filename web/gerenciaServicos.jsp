<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="servicoDAO" class="dao.ServicoDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Serviços</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Serviços -->
        <div align="center">
            <h1>Serviços Cadastradas</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Data</th>
                        <th class="text-center">Descrição</th>
                        <th class="text-center">Valor Gasto</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="servico" varStatus="status" items="${servicoDAO.lerServicos(selectedAutomovel.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>       
                        <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${servico.data}"/></td>
                        <td align="center">${servico.descricao}</td>
                        <td align="center">R$ ${servico.valorGasto}</td>
                        <form method="post" action="cadastroServico.jsp">
                            <td align="center">
                                <input type="hidden" name="altServico" id="altServico" value="${servico.id}">
                                <button type="submit" class="btn btn-sm btn-warning">
                                    <span class="glyphicon glyphicon-cog"></span></button>
                            </td>
                        </form>
                        <form method="post" action="ServicoServlet">
                            <td align="center">
                                <input type="hidden" name="excServico" id="excServico" value="${servico.id}">
                                <input type="hidden" id="operacao" name="operacao" value="apagar">
                                <button type="submit" class="btn btn-sm btn-danger" 
                                        onclick="return confirm('Tem certeza que deseja excluir este serviço?')">
                                    <span class="glyphicon glyphicon-trash"></span></button>
                            </td>
                        </form>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroServico.jsp'"
                        style="font-weight: bold;">Cadastrar Serviços</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
        
    </body>
</html>
