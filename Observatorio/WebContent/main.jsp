<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/app/shared/includes.jsp" %>
<script type="text/javascript" src="app/components/main/main.controller.js"></script>

<title>Observatorio del Gasto Público</title>
</head>
<body ng-app="observatorio" ng-controller="MainController as ctrl">
	<div class="row" style="margin: 0px;">
			<div class="col-sm-1">
			</div>
			<!-- header izquierdo -->
			<div class="col-sm-4" style="height: 105px">
				<br />
				<a href="main-botones.jsp"><img src="/assets/img/logo.png" alt="Observatorio" /></a>
			</div>
			<!-- header derecho -->
			<div class="col-sm-7">
			
			
			<nav id="main-nav">
		
		<ul>
			<li class='current'>
		    	<a href="main-botones.jsp" data-description="Acá iniciamos">Inicio</a>
				<ul>
					<li><a href="#">¿Quiénes somos?</a></li>
					<li><a href="#">¿Cómo funciona el OGP?</a></li>
					<li><a href="#">¿Qué es Gestión por Resultados (GpR)?</a></li>
					<li><a href="#">Marco Jurídico</a></li>
				</ul>

			<li>
			
			<a href="#" data-description="Prioritarios">Bases de datos</a>
				<ul>
					<li><a href="#">Proyecciones de población INE</a></li>
					
					<!--	<ul>
							<li><a href="#">We are hiring</a></li>
						</ul> -->	
				</ul>
			</li>
			
			<li>

				<a href="#" data-description="Presupuesto">INFÓRMATE</a>
				<ul>
					<li><a href="#">Noticias</a></li>
					<li><a href="#">Presupuesto nacional</a></li>
					<li><a href="#">Ejes prioritarios</a>
						<ul>
							<li><a href="#">Salud</a></li>
							<li><a href="#">Educación</a></li>
							<li><a href="#">Seguridad</a></li>
							<li><a href="#">Infraestructura</a></li>
						</ul>
					</li>
					<li><a href="#">Análisis estadísticos</a>
							
					
					</li>
				</ul>
			</li>
			<!--<li>
				<a href="#" data-description="Reportes y documentos">TRANSPARENCIA</a>
				<ul>
					<li><a href="#">Administración central</a></li>
					<li><a href="#">Entidades descentralizadas, autónomas y empresas públicas</a></li>
					<li><a href="#">Deuda pública</a></li>
					<li><a href="#">Transferencias del gobierno central a los municipios</a></li>
					<li><a href="#">Fideicomisos</a></li>
				</ul>
			</li>-->
			<li>
				<a href="#" data-description="Sitios relacionados">Multimedia</a>
					<ul>
							<li><a href="#">Infografías</a></li>
							<li><a href="#">Videos</a></li>
							<li><a href="#">Enlaces de interés</a></li>
					</ul>
			</li>
		</ul>

	</nav><!-- end #main-nav -->
			
			
			</div>
			
	</div>
	<div>
		<div id="head">
			{{ $root.page_title }}
		</div>
	</div>
	<div id="mainview">
		<div ng-view></div>
    </div>
    <footer id="footer" class="clearfix">

	<div class="container">

		<div class="three-fourth">

			<nav id="footer-nav" class="clearfix">

				<ul>
					<li><a href="main-botones.jsp">Inicio</a></li>
					<li><a href="#">Bases de Datos</a></li>
					<li><a href="#">Infórmate</a></li>
					<li><a href="#">Multimedia</a></li>
					
				</ul>
				
			</nav><!-- end #footer-nav -->

			<ul class="contact-info">
				<li class="address">8a. Avenida 20-59 Zona 1, Centro Cívico, Guatemala - 01001</li>
				<li class="phone">+502 23743000</li>
				<li class="email"><a href="mailto:observatorio@minfin.gob.gt">observatorio@minfin.gob.gt</a></li>
			</ul><!-- end .contact-info -->
			
		</div><!-- end .three-fourth -->


		
	</div><!-- end .container -->

</footer><!-- end #footer -->

<footer id="footer-bottom" class="clearfix">

	<div class="container">

		<ul>
			<li>Ministerio de Finanzas Públicas &copy; 2018</li>
			<li><a href="#"></a></li>
			<li><a href="#"></a></li>
		</ul>

	</div><!-- end .container -->

</footer><!-- end #footer-bottom -->
</div>
</body>
</html>