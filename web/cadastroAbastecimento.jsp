<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="abastecimentoDAO" class="dao.AbastecimentoDAO"/>
<jsp:useBean id="now" class="java.util.Date" />

<c:if test="${param != null && param.altAbastecimento != null}">
    <c:set var="abastecimento" value="${abastecimentoDAO.lerAbastecimento(param.altAbastecimento)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Abastecimento</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <c:choose>
            <c:when test="${param != null && param.altAbastecimento != null}">
                <h1 align="center">Alteração de Abastecimento</h1><h1></h1>
            </c:when>
            <c:otherwise>
                <h1 align="center">Cadastro de Abastecimentos</h1><h1></h1>
            </c:otherwise>
        </c:choose>
        <form method="post" class="form-horizontal" action="AbastecimentoServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altAbastecimento != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="data" class="col-sm-2 col-sm-offset-3 control-label">Data:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="data" id="data"
                        <c:choose>
                            <c:when test="${abastecimento != null}">
                                value="${abastecimento.data}"
                            </c:when>
                            <c:otherwise>
                                value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" />"
                            </c:otherwise>
                        </c:choose> >
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="combustivel" class="col-sm-2 col-sm-offset-3 control-label">Combustível:</label>
                <div class="col-sm-3 input-group" id="selectCombustivelDiv"> 
                    <select name="combustivel" id="combustivel" class="form-control">
                        <jsp:useBean id="combustivelDAO" class="dao.CombustivelDAO"/>
                        <c:forEach var="combustivel" items="${combustivelDAO.lerCombustiveis(loggedInUser.id)}">
                            <option value="${combustivel.id}"
                                <c:if test="${abastecimento != null && abastecimento.combustivel.tipo == combustivel.tipo}">
                                    selected
                                </c:if> >${combustivel.tipo}</option>
                        </c:forEach> 
                    </select>
                </div>
                <button type="button" class="btn btn-primary" name="novoCombustivel" id="novoCombustivel" 
                        onclick="location.href='cadastroCombustivel.jsp'" style="font-weight: bold;">+</button>
            </div>
            <div class="form-group">
                <label for="odometro" class="col-sm-2 col-sm-offset-3 control-label">Odômetro:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">KM</div>
                    <input type="number" required="true" step="0.01" min="0" class="form-control" name="odometro" id="odometro"
                            placeholder="Digite a quantidade de quilômetros"
                            <c:if test="${abastecimento != null}">
                                value=${abastecimento.odometro}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="litros" class="col-sm-2 col-sm-offset-3 control-label">Litros:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">L</div>
                    <input type="number" required="true" step="0.01" min="0" class="form-control" name="litros" id="litros" 
                            placeholder="Digite a quantidade de litros abastecidos"
                            <c:if test="${abastecimento != null}">
                                value=${abastecimento.litros}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="valorTotal" class="col-sm-2 col-sm-offset-3 control-label">Valor Total:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" required="true" step="0.01" min="0" class="form-control" name="valorTotal" id="valorTotal" 
                            placeholder="Digite o valor total do abastecimento"
                            <c:if test="${abastecimento != null}">
                                value=${abastecimento.valorTotal}
                            </c:if> >
                </div>
            </div>
            <div class="form-group form-inline">
                <label for="posto" class="col-sm-2 col-sm-offset-3 control-label">Posto:</label>
                <div class="col-sm-3 input-group" id="selectPostoDiv"> 
                    <select name="posto" id="posto" class="form-control">
                        <jsp:useBean id="postoDAO" class="dao.PostoDAO"/>
                        <c:forEach var="posto" items="${postoDAO.lerPostos(loggedInUser.id)}">
                            <option value="${posto.id}"
                                <c:if test="${abastecimento != null && abastecimento.posto.nome == posto.nome}">
                                    selected
                                </c:if> >${posto.nome}</option>
                        </c:forEach>
                    </select>
                </div>
                <button type="button" class="btn btn-primary" name="novoPosto" id="novoPosto" 
                        onclick="location.href='cadastroPosto.jsp'" style="font-weight: bold;">+</button>
            </div>
            <div class="form-group">
                <label for="anotacoes" class="col-sm-2 col-sm-offset-3 control-label">Anotações:</label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${abastecimento != null}">
                            <textarea class="form-control" rows="3" name="anotacoes" maxlength="256"
                                      id="anotacoes">${abastecimento.anotacoes}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea class="form-control" rows="3" name="anotacoes" maxlength="256"
                                      id="anotacoes"></textarea>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${abastecimento != null}">
                            <input type="hidden" name="idAbastecimento" id="idAbastecimento" value="${abastecimento.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaAbastecimentos.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
    </body>
</html>

