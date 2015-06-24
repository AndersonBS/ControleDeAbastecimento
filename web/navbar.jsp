<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>        
        <nav class="navbar navbar-inverse" role="navigation">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" 
                            data-target="#navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <img alt="Brand" src="IMG/icone.png" height="50" width="65" style="padding-right: 15px;">
                </div>
                <div class="collapse navbar-collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="home.jsp">GERAL</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">CADASTROS<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li role="presentation" class="dropdown-header">Geral</li>
                                <li><a href="cadastroAutomovel.jsp">AUTOMÓVEL</a></li>
                                <li><a href="cadastroCombustivel.jsp">COMBUSTÍVEL</a></li>
                                <li><a href="cadastroPosto.jsp">POSTO</a></li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation" class="dropdown-header">Automóvel</li>
                                <li><a href="cadastroAbastecimento.jsp">ABASTECIMENTO</a></li>
                                <li><a href="cadastroTrocaDeOleo.jsp">TROCA DE ÓLEO</a></li>
                                <li><a href="cadastroSeguro.jsp">SEGURO</a></li>
                                <li><a href="cadastroLembrete.jsp">LEMBRETE</a></li>
                                <li><a href="cadastroServico.jsp">SERVIÇO</a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">GERENCIAR<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li role="presentation" class="dropdown-header">Geral</li>
                                <li><a href="gerenciaAutomoveis.jsp">AUTOMÓVEIS</a></li>
                                <li><a href="gerenciaCombustiveis.jsp">COMBUSTÍVEIS</a></li>
                                <li><a href="gerenciaPostos.jsp">POSTOS</a></li>
                                <li role="presentation" class="divider"></li>
                                <li role="presentation" class="dropdown-header">Automóvel</li>
                                <li><a href="gerenciaAbastecimentos.jsp">ABASTECIMENTOS</a></li>
                                <li><a href="gerenciaTrocasDeOleo.jsp">TROCAS DE ÓLEO</a></li>
                                <li><a href="gerenciaSeguros.jsp">SEGUROS</a></li>
                                <li><a href="gerenciaLembretes.jsp">LEMBRETES</a></li>
                                <li><a href="gerenciaServicos.jsp">SERVIÇOS</a></li>
                            </ul>
                        </li>
                        <li><img alt="Brand" src="IMG/vehicle.png" height="50" width="50" style="margin-left: 85px;"></li>
                        <li>
                            <form class="input-group" method="post" action="AutomovelServlet" name="automovel_form" id="automovel_form">
                                <input type="hidden" id="operacao" name="operacao" value="selecionar">
                                <input type="hidden" id="jsp_request_uri" name="jsp_request_uri" value="${pageContext.request.servletPath}">
                                <select name="automovel" id="automovel" class="navbar-form btn btn-primary" 
                                        style="font-size: 20px; font-weight: bold; padding-top: 4px; padding-bottom: 4px; margin-left: 15px;">
                                    <jsp:useBean id="automovelDAO" class="dao.AutomovelDAO"/>
                                    <c:forEach var="automovel" items="${automovelDAO.lerAutomoveis(loggedInUser.id)}">
                                        <option align="center" value="${automovel.id}"
                                            <c:if test="${selectedAutomovel != null && selectedAutomovel.id == automovel.id}">
                                                selected
                                            </c:if> > ${automovel.nome}: ${automovel.marca} (${automovel.placa})</option>
                                            <c:if test="${selectedAutomovel == null }">
                                                <c:set var="selectedAutomovel" value="${automovel}" scope="session"></c:set>
                                            </c:if>
                                    </c:forEach>   
                                </select>
                            </form>
                        </li>
                    </ul>
                    <form class="navbar-form navbar-right" name="search" id="search" role="search" hidden="true">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="O que você procura?" style="font-weight: normal;">
                            <span class="input-group-btn">
                                <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
                            </span>
                        </div>
                    </form>
                    <ul class="nav navbar-nav navbar-right"><span class="">
                            <button type="button" name="search_toggle" id="search_toggle" class="navbar-btn btn btn-primary">
                        <span class="glyphicon glyphicon-search"></span></button></span>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <li class="dropdown">
                            <a name="b_user" id="b_user" class="dropdown-toggle" data-toggle="dropdown">
                                ${loggedInUser.nome}<span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="perfil.jsp">Perfil</a></li>
                                <li><a href="#">Configurações</a></li>
                                <li><a id="quit">Logout</a></li>
                                <li><form name="quit_form" id="quit_form" method="post" class="navbar-form" action="UsuarioServlet" hidden="true">
                                    <input type="hidden" id="operation" name="operation" value="logout">
                                </form></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
