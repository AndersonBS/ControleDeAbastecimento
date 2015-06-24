<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="lembreteDAO" class="dao.LembreteDAO"/>

<c:if test="${param != null && param.altLembrete != null}">
    <c:set var="lembrete" value="${lembreteDAO.lerLembrete(param.altLembrete)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Lembrete</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Lembretes -->
        <h1 align="center">Cadastro de Lembretes</h1><h1></h1>
        <form method="post" class="form-horizontal" action="LembreteServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altLembrete != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="dataPrevista" class="col-sm-2 col-sm-offset-3 control-label">Data Prevista:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="dataPrevista" id="dataPrevista"
                        <c:if test="${lembrete != null}">
                            value=${lembrete.dataPrevista}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="valorOrcado" class="col-sm-2 col-sm-offset-3 control-label">Valor Orçado:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" step="0.01" min="0" class="form-control" name="valorOrcado" id="valorOrcado" 
                            placeholder="Digite o valor orçado"
                            <c:if test="${lembrete != null}">
                                value=${lembrete.valorOrcado}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="descricaoLocal" class="col-sm-2 col-sm-offset-3 control-label">Descrição do Local:</label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${lembrete != null}">
                            <textarea class="form-control" rows="2" name="descricaoLocal" maxlength="128"
                                      id="descricaoLocal">${lembrete.descricaoLocal}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea class="form-control" rows="2" name="descricaoLocal" maxlength="128"
                                      id="descricaoLocal"></textarea>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label for="descricaoServico" class="col-sm-2 col-sm-offset-3 control-label">Descrição do Serviço:</label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${lembrete != null}">
                            <textarea class="form-control" rows="3" name="descricaoServico" maxlength="256" required="true"
                                      id="descricaoServico">${lembrete.descricaoServico}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea class="form-control" rows="3" name="descricaoServico" maxlength="256" required="true"
                                      id="descricaoServico"></textarea>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 col-sm-offset-3 control-label"></label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${lembrete != null}">
                            <input type="hidden" name="idLembrete" id="idLembrete" value="${lembrete.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaLembretes.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
