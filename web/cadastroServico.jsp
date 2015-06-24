<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="servicoDAO" class="dao.ServicoDAO"/>

<c:if test="${param != null && param.altServico != null}">
    <c:set var="servico" value="${servicoDAO.lerServico(param.altServico)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Serviço</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Serviços -->
        <h1 align="center">Cadastro de Serviços</h1><h1></h1>
        <form method="post" class="form-horizontal" action="ServicoServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altServico != null}">
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
                        <c:if test="${servico != null}">
                            value=${servico.data}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="descricao" class="col-sm-2 col-sm-offset-3 control-label">Descrição do Serviço:</label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${servico != null}">
                            <textarea class="form-control" rows="3" name="descricao" maxlength="256" required="true"
                                      id="descricao">${servico.descricao}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea class="form-control" rows="3" name="descricao" maxlength="256" required="true"
                                      id="descricao"></textarea>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label for="valorGasto" class="col-sm-2 col-sm-offset-3 control-label">Valor Gasto:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" step="0.01" min="0" class="form-control" name="valorGasto" id="valorGasto" required="true"
                            placeholder="Digite o valor gasto"
                            <c:if test="${servico != null}">
                                value=${servico.valorGasto}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${servico != null}">
                            <input type="hidden" name="idServico" id="idServico" value="${servico.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaServicos.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
