<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="postoDAO" class="dao.PostoDAO"/>

<c:if test="${param != null && param.altPosto != null}">
    <c:set var="posto" value="${postoDAO.lerPosto(param.altPosto)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Posto</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Postos -->
        <h1 align="center">Cadastro de Postos</h1><h1></h1>
        <form method="post" class="form-horizontal" action="PostoServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altPosto != null}">
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
                            placeholder="Digite o nome do posto"
                            <c:if test="${posto != null}">
                                value=${posto.nome}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="marca" class="col-sm-2 col-sm-offset-3 control-label">Marca:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" required="true" maxlength="16" class="form-control" name="marca" id="marca"
                            placeholder="Digite a marca do posto"
                            <c:if test="${posto != null}">
                                value=${posto.marca}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="endereco" class="col-sm-2 col-sm-offset-3 control-label">Endereço:</label>
                <div class="col-sm-3 input-group">
                    <input type="text" maxlength="32" class="form-control" name="endereco" id="endereco"
                            placeholder="Digite o endereço do posto"
                            <c:if test="${posto != null}">
                                value=${posto.endereco}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="telefone" class="col-sm-2 col-sm-offset-3 control-label">Telefone:</label>
                <div class="col-sm-3 input-group">
                    <input type="tel" maxlength="16" class="form-control" name="telefone" id="telefone"
                            placeholder="Digite o telefone do posto"
                            <c:if test="${posto != null}">
                                value=${posto.telefone}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="cartao" class="col-sm-2 col-sm-offset-3 control-label">Aceita cartão?</label>
                <div class="col-sm-3 input-group" id="selectCartao"> 
                    <select name="cartao" id="cartao" class="form-control">
                        <option value="${true}">Sim</option>
                        <option value="${false}">Não</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${posto != null}">
                            <input type="hidden" name="idPosto" id="idPosto" value="${posto.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaPostos.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
