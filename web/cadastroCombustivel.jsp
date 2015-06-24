<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="combustivelDAO" class="dao.CombustivelDAO"/>

<c:if test="${param != null && param.altCombustivel != null}">
    <c:set var="combustivel" value="${combustivelDAO.lerCombustivel(param.altCombustivel)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Combustível</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Combustíveis -->
        <h1 align="center">Cadastro de Combustíveis</h1><h1></h1>
        <form method="post" class="form-horizontal" action="CombustivelServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altCombustivel != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="tipo" class="col-sm-2 col-sm-offset-3 control-label">Tipo:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" required="true" maxlength="32" class="form-control" name="tipo" id="tipo"
                            placeholder="Digite o tipo do combustível"
                            <c:if test="${combustivel != null}">
                                value=${combustivel.tipo}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${combustivel != null}">
                            <input type="hidden" name="idCombustivel" id="idCombustivel" value="${combustivel.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaCombustiveis.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
