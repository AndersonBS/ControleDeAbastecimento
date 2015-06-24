<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate var="ano" value="${now}" pattern="yyyy" />
<fmt:formatDate var="mes" value="${now}" pattern="MM" />

<jsp:useBean id="abastecimentoDAO" class="dao.AbastecimentoDAO"/>
<jsp:useBean id="trocaDeOleoDAO" class="dao.TrocaDeOleoDAO"/>
<jsp:useBean id="seguroDAO" class="dao.SeguroDAO"/>
<jsp:useBean id="servicoDAO" class="dao.ServicoDAO"/>
<jsp:useBean id="combustivelDAO" class="dao.CombustivelDAO"/>

<!DOCTYPE html>
<html>
    <head>
        
        <title>${loggedInUser.nome}</title>
        
        <!-- Inclusão de CSS e JavaScript padrão -->
        <jsp:include page="include.jsp" />
        
        <script src="JS/Chart.min.js" type="text/javascript"></script>
        
    </head>
    <body>
        
        <!-- Inclusão do Menu Superior padrão -->
        <jsp:include page="navbar.jsp" />
        
        <!-- Coluna da Esquerda -->
        <div class="col-sm-4">
            <div align="center">
                <h2>Despesar Gerais (R$)</h2>
                <div class="box-chart">
                    <canvas id="GraficoPizzaDespesas" style="width:100%;"></canvas>
                </div>
            </div>
        </div>
                    
        <!-- Coluna Central -->
        <div class="col-sm-4">
            <div align="center">
                <h2>Consumo Médio (KM/L)</h2>
                <div class="box-chart">
                    <canvas id="GraficoLinhaConsumo" style="width:100%; background: white;" class="myTable"></canvas>
                </div>
            </div>
        </div>
        
        <!-- Coluna da Direita -->
        <div class="col-sm-4">
            <div align="center">
                <h2>Despesar por Combustível (R$)</h2>
                <div class="box-chart">
                    <canvas id="GraficoPizzaCombustivel" style="width:100%;"></canvas>
                </div>
            </div>
        </div>
        
        <!-- Coluna Esquerda -->
        <div class="col-sm-10 col-lg-offset-1">
            <div align="center">
                <h1></h1>
                <table class="table table-striped table-hover myTable">
                    <tr class="myTh">
                        <th class="text-center"></th>
                        <th class="text-center">Abastecimentos</th>
                        <th class="text-center">Trocas de Óleo</th>
                        <th class="text-center">Seguros</th>
                        <th class="text-center">Serviços</th>
                        <th class="text-center">Odômetro</th>
                    </tr>
                    <tr class="info myRow">
                        <td align="center"><b>Mês ${mes}:</b></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimentoDAO.despesasMes(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${trocaDeOleoDAO.despesasMes(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">-</td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${servicoDAO.despesasMes(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">-</td>
                    </tr>
                    <tr class="active myRow">
                        <td align="center"><b>Ano ${ano}:</b></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimentoDAO.despesasAno(selectedAutomovel.id)}"></fmt:formatNumber></td></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${trocaDeOleoDAO.despesasAno(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">-</td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${servicoDAO.despesasAno(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">-</td>
                    </tr>
                    <tr class="info myRow">
                        <td align="center"><b>Total:</b></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimentoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${trocaDeOleoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${seguroDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">R$ <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${servicoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber></td>
                        <td align="center">KM <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimentoDAO.odometroTotal(selectedAutomovel.id)}"></fmt:formatNumber></td>
                    </tr>
                </table>
                <br><br>
                
            </div>
        </div>
        
        <script type="text/javascript">
            
            var colors = ["#FDB45C", "#F7464A",  "#46BFBD", "#49FF00", "#FF00E1", "#4D5360"];
            var highlights = ["#FFC870", "#FF5A5E",  "#5AD3D1", "#6BFF30", "#FF3DE8", "#616774"];
            
            var options = {
                responsive: true,
                scaleFontSize: 14
            };
            
            var data0 = [
            {   label: "Abastecimentos",
                value: "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimentoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber>",
                color: colors[0],
                highlight: highlights[0]    },
            {   label: "Trocas de Óleo",
                value: "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${trocaDeOleoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber>",
                color: colors[1],
                highlight: highlights[1]    },
            {   label: "Seguros",
                value: "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${seguroDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber>",
                color: colors[2],
                highlight: highlights[2]    },
            {   label: "Serviços",
                value: "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${servicoDAO.despesasTotal(selectedAutomovel.id)}"></fmt:formatNumber>",
                color: colors[3],
                highlight: highlights[3]    }
            ]

            var data1 = {
                labels: [
                    <c:forEach var="abastecimento" varStatus="loop" items="${abastecimentoDAO.lerAbastecimentos(selectedAutomovel.id)}">
                        "<fmt:formatDate pattern="dd/MM/yyyy" value="${abastecimento.data}"/>"<c:if test="${!loop.last}">,</c:if>
                    </c:forEach>
                ],
                datasets: [
                    {
                        label: "Consumo por KM",
                        fillColor: "rgba(151,187,205,0.2)",
                        strokeColor: "rgba(151,187,205,1)",
                        pointColor: "rgba(151,187,205,1)",
                        pointStrokeColor: "#fff",
                        pointHighlightFill: "#fff",
                        pointHighlightStroke: "rgba(151,187,205,1)",
                        data: [
                            <c:forEach var="abastecimento" varStatus="loop" items="${abastecimentoDAO.lerAbastecimentos(selectedAutomovel.id)}">
                                "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${abastecimento.getConsumoPorKM()}"></fmt:formatNumber>"<c:if test="${!loop.last}">,</c:if>
                            </c:forEach>
                        ]
                    }
                ]
            };
            
            var data2 = [
                <c:forEach var="combustivel" varStatus="loop" items="${combustivelDAO.despesasCombustivel(selectedAutomovel.id)}">{
                    label: "${combustivel.tipo}",
                    value: "<fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2"
                                          value="${combustivel.total}"></fmt:formatNumber>",
                    color: colors[${loop.index}], 
                    highlight: highlights[${loop.index}] }<c:if test="${!loop.last}">,</c:if>
                </c:forEach>
            ]
            
            window.onload = function(){
                var ctx0 = document.getElementById("GraficoPizzaDespesas").getContext("2d");
                var GraficoPizzaDespesas = new Chart(ctx0).Doughnut(data0, options); //PolarArea
                var ctx1 = document.getElementById("GraficoLinhaConsumo").getContext("2d");
                var GraficoLinhaConsumo = new Chart(ctx1).Line(data1, options);
                var ctx2 = document.getElementById("GraficoPizzaCombustivel").getContext("2d");
                var GraficoPizzaCombustivel = new Chart(ctx2).Pie(data2, options);
            }  
        </script>
        
    </body>
</html>
