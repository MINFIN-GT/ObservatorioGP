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
			<div class="col-sm-5" style="height: 105px">
				<br />
				<img src="/assets/img/logo.png" alt="Observatorio" />
				
			</div>
			<!-- header derecho -->
			<div class="col-sm-5">
	

	
	
	   			
			</div>
			<div class="col-sm-1">
			</div>
</div>
	<div class="row">
		<div id="head" class="col-sm-12">
		test
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

<div id="foot" class="row">
	<div>- MINFIN 2018 -
	</div>
</div>
</body>
</html>