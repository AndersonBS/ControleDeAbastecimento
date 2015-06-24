<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="trocaDeOleoDAO" class="dao.TrocaDeOleoDAO"/>

<c:if test="${param != null && param.altTrocaDeOleo != null}">
    <c:set var="trocaDeOleo" value="${trocaDeOleoDAO.lerTrocaDeOleo(param.altTrocaDeOleo)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Troca de Óleo</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Trocas de Óleo -->
        <h1 align="center">Cadastro de Trocas de Óleo</h1><h1></h1>
        <form method="post" class="form-horizontal" action="TrocaDeOleoServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altTrocaDeOleo != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="nomeOleo" class="col-sm-2 col-sm-offset-3 control-label">Nome do Óleo:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" maxlength="32" class="form-control" name="nomeOleo" id="nomeOleo"
                            placeholder="Digite o nome do óleo"
                            <c:if test="${trocaDeOleo != null}">
                                value=${trocaDeOleo.nomeOleo}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="tipoOleo" class="col-sm-2 col-sm-offset-3 control-label">Tipo do Óleo:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" maxlength="16" class="form-control" name="tipoOleo" id="tipoOleo"
                            placeholder="Digite o tipo do óleo"
                            <c:if test="${trocaDeOleo != null}">
                                value=${trocaDeOleo.tipoOleo}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="dataTroca" class="col-sm-2 col-sm-offset-3 control-label">Data da Troca:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="dataTroca" id="dataTroca"
                        <c:if test="${trocaDeOleo != null}">
                            value=${trocaDeOleo.dataTroca}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="dataProximaTroca" class="col-sm-2 col-sm-offset-3 control-label">Data da Próxima Troca:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="dataProximaTroca" id="dataProximaTroca"
                        <c:if test="${trocaDeOleo != null}">
                            value=${trocaDeOleo.dataProximaTroca}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="trocaFiltro" class="col-sm-2 col-sm-offset-3 control-label">Trocou o Filtro?</label>
                <div class="col-sm-3 input-group" id="trocaFiltro"> 
                    <select name="trocaFiltro" id="trocaFiltro" class="form-control">
                        <option value="${true}">Sim</option>
                        <option value="${false}">Não</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="preco" class="col-sm-2 col-sm-offset-3 control-label">Preço Total:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" required="true" step="0.01" min="0" class="form-control" name="preco" id="preco" 
                            placeholder="Digite o preço total da troca de óleo"
                            <c:if test="${trocaDeOleo != null}">
                                value=${trocaDeOleo.preco}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${trocaDeOleo != null}">
                            <input type="hidden" name="idTrocaDeOleo" id="idTrocaDeOleo" value="${trocaDeOleo.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaTrocasDeOleo.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
