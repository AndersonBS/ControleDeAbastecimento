<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="seguroDAO" class="dao.SeguroDAO"/>

<c:if test="${param != null && param.altSeguro != null}">
    <c:set var="seguro" value="${seguroDAO.lerSeguro(param.altSeguro)}" scope="page"></c:set>
</c:if>

<!DOCTYPE html>
<html>
    <head>
        
        <title>Cadastro de Seguro</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Formulário: Cadastro de Seguros -->
        <h1 align="center">Cadastro de Seguros</h1><h1></h1>
        <form method="post" class="form-horizontal" action="SeguroServlet">
            <input type="hidden" id="operacao" name="operacao"
                   <c:choose>
                       <c:when test="${param != null && param.altSeguro != null}">
                           value="alterar"
                       </c:when>
                       <c:otherwise>
                           value="salvar"
                       </c:otherwise>
                   </c:choose> >
            <div class="form-group">
                <label for="inicioVigencia" class="col-sm-2 col-sm-offset-3 control-label">Data Início Vigência:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="inicioVigencia" id="inicioVigencia"
                        <c:if test="${seguro != null}">
                            value=${seguro.inicioVigencia}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="fimVigencia" class="col-sm-2 col-sm-offset-3 control-label">Data Fim Vigência:</label>
                <div class="col-sm-3 input-group">
                    <input type="date" required="true" class="form-control" name="fimVigencia" id="fimVigencia"
                        <c:if test="${seguro != null}">
                            value=${seguro.fimVigencia}
                        </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="valorParcela" class="col-sm-2 col-sm-offset-3 control-label">Valor da Parcela:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" step="0.01" min="0" class="form-control" name="valorParcela" id="valorParcela" required="true"
                            placeholder="Digite o valor da parcela"
                            <c:if test="${seguro != null}">
                                value=${seguro.valorParcela}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="quantidadeParcelas" class="col-sm-2 col-sm-offset-3 control-label">Quantidade de Parcelas:</label>
                <div class="col-sm-3 input-group">
                    <input type="number" required="true" step="1.0" min="0" class="form-control" name="quantidadeParcelas" id="quantidadeParcelas" 
                           placeholder="Digite a quantidade de parcelas"
                            <c:if test="${seguro != null}">
                                value=${seguro.quantidadeParcelas}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="valorFranquia" class="col-sm-2 col-sm-offset-3 control-label">Valor da Franquia:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" step="0.01" min="0" class="form-control" name="valorFranquia" id="valorFranquia" required="true"
                            placeholder="Digite o valor da franquia"
                            <c:if test="${seguro != null}">
                                value=${seguro.valorFranquia}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="valorCobertura" class="col-sm-2 col-sm-offset-3 control-label">Valor de Cobertura:</label>
                <div class="col-sm-3 input-group">
                    <div class="input-group-addon">R$</div>
                    <input type="number" step="0.01" min="0" class="form-control" name="valorCobertura" id="valorCobertura" required="true"
                            placeholder="Digite o valor de cobertura"
                            <c:if test="${seguro != null}">
                                value=${seguro.valorCobertura}
                            </c:if> >
                </div>
            </div>
            <div class="form-group">
                <label for="anotacoes" class="col-sm-2 col-sm-offset-3 control-label">Anotações:</label>
                <div class="col-sm-3 input-group">
                    <c:choose>
                        <c:when test="${seguro != null}">
                            <textarea class="form-control" rows="3" name="anotacoes" maxlength="256"
                                      id="anotacoes">${seguro.anotacoes}</textarea>
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
                        <c:when test="${seguro != null}">
                            <input type="hidden" name="idSeguro" id="idSeguro" value="${seguro.id}">
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Salvar Alterações</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary" style="font-weight: bold;">Cadastrar</button>
                            <button type="reset" class="btn btn-primary" style="margin-left: 5px; font-weight: bold;">Limpar</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" class="btn btn-primary" onclick="location.href='gerenciaSeguros.jsp'" 
                        style="margin-left: 5px; font-weight: bold;">Gerenciar</button>
                </div>
            </div>
            <c:if test="${message != null && !message.isEmpty()}">
                <h3 align="center" id="message">${message}</h3>
            </c:if>
        </form>
        
    </body>
</html>
