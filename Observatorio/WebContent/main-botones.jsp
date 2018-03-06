<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/app/shared/includes.jsp" %>
<script type="text/javascript" src="app/components/main/main.controller-botones.js"></script>

<style>

#foot {
background-color: #222222;
height: 80px;
}
#head {
background-color: #222222;
}
</style>
<title>Observatorio del Gasto Público</title>
</head>
<body ng-app="main" ng-controller="mainController as ctrl">
<div class="row">
			<div class="col-sm-1">
			</div>
			<!-- header izquierdo -->
			<div class="col-sm-4" style="height: 105px">
				<br />
				<img src="/assets/img/logo.png" alt="Observatorio" />
				
			</div>
			<!-- header derecho -->
			<div class="col-sm-7">
	
	<nav id="main-nav">
		
		<ul>
			<li class='current'>
		    	<a href="#" data-description="Acá iniciamos">Inicio</a>
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
	<div class="row">
		<div id="head" class="col-sm-12">
		.
		</div>
	</div>
	
	<div class="row">
	<div class="col-sm-1">
	</div>
	
	<div class="col-sm-10">
	
  	<div style="height: 405px">
    		<div uib-carousel active="ctrl.active" interval="ctrl.interval" no-wrap="ctrl.wrap">
      			<div uib-slide ng-repeat="slide in ctrl.slides track by slide.id" index="slide.id">
        			<img ng-src="{{slide.image}}" style="margin:auto;">
        			
     	 		</div>
    		</div>
  	</div>
	
	</div>
	<div class="col-sm-1">
	</div>
	</div>
	
	<div class="row">
	<div class="col-sm-1"></div>
	<div class="col-sm-10">
<center>
<img name="links" src="/assets/img/presupuesto/links.png" width="942" height="400" id="links" usemap="#m_links" alt="" />
<map name="m_links" id="m_links">
<area shape="poly" coords="58,220,191,220,191,361,58,361,58,220" href="main.jsp#!/re" alt="" />
<area shape="poly" coords="233,220,366,220,366,361,233,361,233,220" href="main.jsp#!/ri" alt="" />
<area shape="poly" coords="410,221,543,221,543,362,410,362,410,221" href="main.jsp#!/obligaciones" alt="" />
<area shape="poly" coords="582,220,715,220,715,361,582,361,582,220" href="main.jsp#!/deuda" alt="" />
<area shape="poly" coords="759,220,892,220,892,361,759,361,759,220" href="main.jsp#!/ot" alt="" />
<area shape="rect" coords="616,12,853,155" href="main.jsp#!/in" alt="" />
</map>
</center>
	</div>
	<div class="col-sm-1"></div>
	</div>


	<div class="row">
		<div class="col-sm-1">
		</div>
		<div class="col-sm-10">
			<br />
			<iframe src="https://calendar.google.com/calendar/embed?height=600&amp;wkst=1&amp;bgcolor=%23ffffff&amp;src=observatorio.calidad.gasto%40gmail.com&amp;color=%23B1440E&amp;ctz=America%2FGuatemala" style="border-width:0" width="100%" height="500px" frameborder="0" scrolling="no"></iframe>	
		</div>
		<div class="col-sm-1">
		</div>
	
	</div>
	<br />
	<br />

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
</body>
</html>