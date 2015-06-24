<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="abastecimentoDAO" class="dao.AbastecimentoDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Gerencia Abastecimentos</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Lista Abastecimentos -->
        <div align="center">
            <h1>Abastecimentos Cadastrados</h1><h1></h1>
            <h1></h1>   
            <div class="panel-group col-sm-6 col-sm-offset-3" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-primary" style="background: transparent; border: none;">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" 
                               aria-expanded="true" aria-controls="collapseOne">
                                <b>FILTROS</b>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                        <div class="panel-body">
                            <form method="post" action="gerenciaAbastecimentos.jsp">
                                <table class="table table-striped table-hover myTable">
                                    <tr class="myTh">
                                        <th>Filtro</th>
                                        <th colspan="2" class="text-left">Parâmetros</th>
                                    </tr>
                                    <tr class="active">
                                        <td><b>Data (de até):</b></td>
                                        <td>
                                            <input type="date" class="form-control" name="filtroDataInicial" id="filtroDataInicial"
                                                    <c:if test="${param != null && param.filtroDataInicial != null}">
                                                        value=${param.filtroDataInicial}
                                                    </c:if> >
                                        </td>
                                        <td>
                                            <input type="date" class="form-control" name="filtroDataFinal" id="filtroDataFinal"
                                                    <c:if test="${param != null && param.filtroDataFinal != null}">
                                                        value=${param.filtroDataFinal}
                                                    </c:if>>
                                        </td>
                                    </tr>
                                    <tr class="info">
                                        <td><b>Combustível:</b></td>
                                        <td colspan="2"><input type="text" class="form-control" name="filtroCombustivel" id="filtroCombustivel" 
                                                placeholder="Digite o tipo do combustível que deseja filtrar"
                                                <c:if test="${param != null && param.filtroCombustivel != null}">
                                                    value=${param.filtroCombustivel}
                                                </c:if> >
                                        </td>
                                    </tr>
                                    <tr class="active">
                                        <td><b>Posto:</b></td>
                                        <td colspan="2"><input type="text" class="form-control" name="filtroPosto" id="filtroPosto" 
                                                placeholder="Digite o nome do posto que deseja filtrar"
                                                <c:if test="${param != null && param.filtroPosto != null}">
                                                    value=${param.filtroPosto}
                                                </c:if> >
                                        </td>
                                    </tr>
                                </table>
                                <input type="submit" class="btn btn-info" value="Aplicar Filtro" style="font-weight: bold;">
                                <input type="button" class="btn btn-info" value="Limpar Filtro" style="margin-left: 3px; font-weight: bold;"
                                       onclick="location.href='gerenciaAbastecimentos.jsp'">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-10 col-sm-offset-1">
                <c:if test="${message != null && !message.isEmpty()}">
                    <h3 align="center" id="message">${message}</h3>
                </c:if>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center">Data do Abastecimento</th>
                        <th class="text-center">Tipo de Combustível</th>
                        <th class="text-center">Valor por Litro</th>
                        <th class="text-center">Consumo Médio</th>
                        <th class="text-center">Valor Total</th>
                        <th class="text-center">Posto</th>
                        <th class="text-center">Anotações</th>
                        <th class="text-center" colspan="2" style="width: 1%">Ações</th>
                    </tr>
                    <c:forEach var="abastecimento" varStatus="status" items="${abastecimentoDAO.lerAbastecimentos(selectedAutomovel.id, 
                            param.filtroDataInicial, param.filtroDataFinal, param.filtroCombustivel, param.filtroPosto)}">
                        <c:choose>
                            <c:when test="${status.index % 2 == 0}">
                                <tr class="info myRow">
                            </c:when>
                            <c:otherwise>
                                <tr class="active myRow">
                            </c:otherwise>
                        </c:choose>           
                            <td align="center"><fmt:formatDate pattern="dd/MM/yyyy" value="${abastecimento.data}"/></td>
                            <td align="center">${abastecimento.combustivel.tipo}</td>
                            <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimento.valorPorLitro}"></fmt:formatNumber></td>
                            <td align="center"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimento.consumoPorKM}"></fmt:formatNumber> KM/L</td>
                            <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimento.valorTotal}"></fmt:formatNumber></td>
                            <td align="center">${abastecimento.posto.nome}</td>
                            <td align="center">${abastecimento.anotacoes}</td>
                            <form method="post" action="cadastroAbastecimento.jsp">
                                <td align="center">
                                    <input type="hidden" name="altAbastecimento" id="altAbastecimento" value="${abastecimento.id}">
                                    <button type="submit" class="btn btn-sm btn-warning">
                                        <span class="glyphicon glyphicon-cog"></span></button>
                                </td>
                            </form>
                            <form method="post" action="AbastecimentoServlet">
                                <td align="center">
                                    <input type="hidden" name="excAbastecimento" id="excAbastecimento" value="${abastecimento.id}">
                                    <input type="hidden" id="operacao" name="operacao" value="apagar">
                                    <button type="submit" class="btn btn-sm btn-danger" 
                                            onclick="return confirm('Tem certeza que deseja excluir este abastecimento?')">
                                            <span class="glyphicon glyphicon-trash"></span></button>
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

