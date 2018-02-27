<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/app/shared/includes.jsp" %>
<script type="text/javascript" src="app/components/main/main.controller.js"></script>

<title>Observatorio</title>
</head>
<body ng-app="observatorio" ng-controller="mainController as ctrl">
<div id="mainview" class="all_page">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6">
				<h3 align="center">Ejecución Física</h3>
				<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped" st-filtered-collection>
					<thead>
						<tr>
							<th>Descripción</th>
							<th style="text-align: center">{{ctrl.ano_p_ejecucion_4}}</th>
							<th style="text-align: center">{{ctrl.ano_p_ejecucion_3}}</th>
							<th style="text-align: center">{{ctrl.ano_p_ejecucion_2}}</th>
							<th style="text-align: center">{{ctrl.ano_p_ejecucion_1}}</th>
							<th style="text-align: center">{{ctrl.ano_p_ejecucion}}</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaProducto(row);">
							<td align="left">
								{{row.metaDescripcion}}
							</td>
							<td align="right">
								{{row.p_ejecucion_4 | formatoMillonesSinTipo : ctrl.millones}}%
							</td>
							<td align="right">
								{{row.p_ejecucion_3 | formatoMillonesSinTipo : ctrl.millones}}%
							</td>
							<td align="right">
								{{row.p_ejecucion_2 | formatoMillonesSinTipo : ctrl.millones}}%
							</td>
							<td align="right">
								{{row.p_ejecucion_1 | formatoMillonesSinTipo : ctrl.millones}}%
							</td>
							<td align="right">
								{{row.p_ejecucion | formatoMillonesSinTipo : ctrl.millones}}%
							</td>
						</tr>
					</tbody>
					<tfoot>
						<tr>
							<th>Totales:</th>
							<th style="text-align: right;">
								<div>{{ctrl.tot_p_ejecucion_4}}%</div>
							</th>
							<th style="text-align: right;">
								<div>{{ctrl.tot_p_ejecucion_3}}%</div>
							</th>
							<th style="text-align: right;">
								<div>{{ctrl.tot_p_ejecucion_2}}%</div>
							</th>
							<th style="text-align: right;">
								<div>{{ctrl.tot_p_ejecucion_1}}%</div>
							</th>
							<th style="text-align: right;">
								<div>{{ctrl.tot_p_ejecucion}}%</div>
							</th>
						</tr>
					</tfoot>
				</table>
			</div>
			<div class="col-sm-3"></div>
		</div>
		<br>
		<h3 align="center">{{ctrl.tituloGrafica}}</h3>
		<div align="right" style="width: 80%;margin: 0 auto; display: table;">
			<div class="btn-group btn-group-xs	">
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoMensual();"><span>Mensual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoAnual();"><span>Anual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoMensualNormal();"><span>Continua</span></label>
			</div>
		</div>
		<div align="center" class="row" style="width: 80%;margin: 0 auto; display: table;">
			<div align="center">
				<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
					chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
					chart-dataset-override="ctrl.datasetOverride" chart-legend="true">
				</canvas>
			</div>
		</div>
	</div>
<div class="footer" id="footer"><div style="text-align: center; width: 100%;" class="label-form">- MINFIN {{ctrl.anio}} -</div></div>
</div>
</body>
</html>