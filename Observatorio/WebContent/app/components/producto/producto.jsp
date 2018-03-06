<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div ng-controller="productoController as ctrl">
		<div align="center" class="row" style="width: 80%; margin: 0 auto;">
			<h3>Productos</h3>
			<br>
			<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 40%" st-sort="metaDescripcion">Descripción</th>
						<th style="width: 10%" st-sort="unidad_medida">Unidad</th>
						<th style="text-align: right; width: 10%">{{ctrl.anio - 4}}</th>
						<th style="text-align: right; width: 10%">{{ctrl.anio - 3}}</th>
						<th style="text-align: right; width: 10%">{{ctrl.anio - 2}}</th>
						<th style="text-align: right; width: 10%">{{ctrl.anio - 1}}</th>
						<th style="text-align: right; width: 10%">{{ctrl.anio}}</th>
					</tr>
				</thead>
				<tbody>
					<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaProducto(row);">
						<td align="left">
							{{row.metaDescripcion}}
						</td>
						<td align="left">
							{{row.unidad_medida}}
						</td>
						<td align="right">
							{{row.p_ejecucion_4 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion_3 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion_2 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion_1 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th></th>
						<th style="text-align: right;">Totales:</th>
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
			<div style="text-align: center;">Fuente de información SICOIN. Cifras en porcentajes.</div>
		</div>
		<br>
		<br>
		<br>
		<div class="titulo_grafica">{{ ctrl.tituloGrafica }}</div>
		<div align="right" style="width: 80%;margin: 0 auto;">
			<div class="btn-group btn-group-xs">
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoMensual();"><span>Mensual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoAnual();"><span>Anual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoMensualContinua();"><span>Continua</span></label>
			</div>
		</div>
		<div align="center" class="row" style="width: 80%;margin: 0 auto;">
			<div align="center">
				<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
					chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
					chart-legend="true">
				</canvas>
			</div>
		</div>
	</div>