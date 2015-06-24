<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="automovelDAO" class="dao.AutomovelDAO"/>

<c:if test="${param != null && param.altAutomovel != null}">
    <c:set var="automovel" value="${automovelDAO.lerAutomovel(param.altAutomovel)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Automóveis</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Automóveis -->
        <h1 align="center">Cadastro de Automóveis</h1><h1></h1>
        <form method="post" class="form-horizontal" action="AutomovelServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altAutomovel != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="nome" class="col-sm-2 col-sm-offset-3 control-label">Nome:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" required="true" maxlength="32" class="form-control" name="nome" id="nome" 
                            placeholder="Digite o nome do veículo"
                            <c:if test="${automovel != null}">
                                value=${automovel.nome}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="marca" class="col-sm-2 col-sm-offset-3 control-label">Marca:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" required="true" maxlength="32" class="form-control" name="marca" id="marca" 
                            placeholder="Digite a marca do veículo"
                            <c:if test="${automovel != null}">
                                value=${automovel.marca}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="anoModelo" class="col-sm-2 col-sm-offset-3 control-label">Ano do Modelo:</label>
                <div class="col-sm-3 input-group">
                    <jsp:useBean id="dataCorrente" class="java.util.Date" />
                    <fmt:formatDate var="anoCorrente" value="${dataCorrente}" pattern="yyyy" />
                    <input type="number" required="true" step="1.0" min="1900" max="${anoCorrente + 1}" 
                            class="form-control" name="anoModelo" id="anoModelo" placeholder="Digite o ano do modelo do veículo"
                            <c:if test="${automovel != null}">
                                value=${automovel.anoModelo}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="dataAquisicao" class="col-sm-2 col-sm-offset-3 control-label">Data de Aquisição:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="dataAquisicao" id="dataAquisicao"
                            <c:if test="${automovel != null}">
                                value=${automovel.dataAquisicao}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="placa" class="col-sm-2 col-sm-offset-3 control-label">Placa:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" required="true" maxlength="7" class="form-control" name="placa" id="placa" 
                            placeholder="Digite a placa do veículo"
                            <c:if test="${automovel != null}">
                                value=${automovel.placa}
                            </c:if> >
                </div>
            </div> 
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${automovel != null}">
                            <input type="hidden" name="idAutomovel" id="idAutomovel" value="${automovel.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaAutomoveis.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
                           
    </body>
</html>
