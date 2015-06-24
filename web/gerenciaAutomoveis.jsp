<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="automovelDAO" class="dao.AutomovelDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Automóveis</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Automóveis -->
        <div align="center">
            <h1>Automóveis Cadastrados</h1><h1></h1>
            <h1></h1>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Nome</th>
                        <th class="text-center">Marca</th>
                        <th class="text-center">Ano do Modelo</th>
                        <th class="text-center">Data de Aquisição</th>
                        <th class="text-center">Placa</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="automovel" varStatus="status" items="${automovelDAO.lerAutomoveis(loggedInUser.id)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>           
                            <td align="center">${automovel.nome}</td>
                            <td align="center">${automovel.marca}</td>
                            <td align="center">${automovel.anoModelo}</td>
                            <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${automovel.dataAquisicao}"/></td>
                            <td align="center">${automovel.placa}</td>
                            <form method="post" action="cadastroAutomovel.jsp">
                                <td align="center">
                                    <input type="hidden" name="altAutomovel" id="altAutomovel" value="${automovel.id}">
                                    <button type="submit" class="btn btn-sm btn-warning">
                                        <span class="glyphicon glyphicon-cog"></span></button>
                                </td>
                            </form>
                            <form method="post" action="AutomovelServlet">
                                <td align="center">
                                    <input type="hidden" name="excAutomovel" id="excAutomovel" value="${automovel.id}">
                                    <input type="hidden" id="operacao" name="operacao" value="apagar">
                                    <button type="submit" class="btn btn-sm btn-danger" 
                                            onclick="return confirm('Tem certeza que deseja excluir este automóvel?\n\n\
Todos os dados associados à ele também SERÃO APAGADOS!\
 (Abastecimentos, Trocas de Óleo, Seguros, Lembretes e Serviços)')"><span class="glyphicon glyphicon-trash"></span></button>
                                </td>
                            </form>
                        </tr>
                    </c:forEach >
                </table>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAutomovel.jsp'"
                        style="font-weight: bold;">Cadastrar Automóveis</button>
                <button type="button" class="btn btn-primary" onclick="location.href='cadastroAbastecimento.jsp'" 
                        style="margin-left: 3px; font-weight: bold;">Cadastrar Abastecimentos</button>
                <br><br>
            </div>
        </div>
                
    </body>
</html>
