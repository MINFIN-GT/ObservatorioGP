<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ include file="/app/shared/includes.jsp" %>
<script type="text/javascript" src="app/components/main/main.controller.js"></script>
<script type="text/javascript" src="app/components/recursos/recursos.controller.js"></script>
<style>
#foot {
background-color: #222222;
height: 80px;
}
#head {
background-color: #222222;
}
</style>
<title>Observatorio del Gasto P�blico</title>
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
	
	<div class="col-sm-7">
	
  	<div style="height: 405px">
    		<div uib-carousel active="ctrl.active" interval="ctrl.interval" no-wrap="ctrl.wrap">
      			<div uib-slide ng-repeat="slide in ctrl.slides track by slide.id" index="slide.id">
        			<img ng-src="{{slide.image}}" style="margin:auto;">
        			<div class="carousel-caption">
          				<h4>Slide {{slide.id}}</h4>
          				<p>{{slide.text}}</p>
        			</div>
     	 		</div>
    		</div>
  	</div>	

	
	
	</div>
	
	<div class="col-sm-3">
	</div>
	<div class="col-sm-1">
	</div>
	</div>
	<div class="row">
		<div class="col-sm-1">
		</div>
		<div class="col-sm-10 col-lg-10 text-center">
			<h3 align="center">Cantidad de personas por lugar de origen del departamento de Retalhuleu</h3>
			<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
				chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
				chart-dataset-override="ctrl.datasetOverride" chart-legend="true">
			</canvas>
		</div>
		<div class="col-sm-1">
		</div>
	</div>
	<br />
	<div class="row">
		<div class="col-sm-1"></div>
		<div class="col-sm-10">
			<h3 align="center">Smart Table</h3>
			<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped" st-filtered-collection>
				<thead>
					<tr>
						<th style="text-align: center">Rango de edades</th>
						<th style="text-align: center">Cantidad de personas</th>
						<th style="text-align: center">Zonas</th>
					</tr>
					<tr>
						<th colspan="3"><input st-search="" class="form-control" placeholder="busqueda global ..." type="text"/></th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="row in ctrl.displayedCollection">
						<td align="left">
							{{row.lugar}}
						</td>
						<td align="right">
							{{row.cantidad}}
						</td>
						<td align="left">
							{{row.zona}}
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th>Totales:</th>
						<th style="text-align: right;">{{ctrl.totales}}</th>
					</tr>
				</tfoot>
			</table>
		</div>
		<div class="col-sm-1"></div>
	</div>
	<br />
	<br />

<div id="foot" class="row">
	<div>- MINFIN 2018 -
	</div>
</div>
</body>
</html>