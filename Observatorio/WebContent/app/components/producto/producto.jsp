<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div ng-controller="productoController as ctrl">
		<div align="center" class="row" style="width: 80%; margin: 0 auto; font-size: 11px;">
			<h6 align="left">{{ctrl.subtitulo}}</h6>
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
					<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaIndividual(row);">
						<td align="left">
							{{row.nombre_producto}}
						</td>
						<td align="left">
							{{row.unidad_medida}}
						</td>
						<td align="right">
							{{row.ejercicio_data[0][11] * 100 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.ejercicio_data[1][11] * 100 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.ejercicio_data[2][11] * 100 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.ejercicio_data[3][11] * 100 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.ejercicio_data[4][11] * 100 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th></th>
						<th style="text-align: right;">Totales:</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
					</tr>
				</tfoot>
			</table>
			<div style="text-align: center; font-size: 11px;">Fuente de información SICOIN. Cifras en porcentajes.</div>
		</div>
		<br>
		<br>
		<br>
		<div class="titulo_grafica">{{ ctrl.tituloGrafica }}</div>
		<div align="right" style="width: 80%;margin: 0 auto; font-size: 11px;">
			<div class="btn-group btn-group-xs">
				<label class="btn btn-default" ng-click="ctrl.cambioMensualP();"><span>Mensual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAnualP();"><span>Anual</span></label>
				<label class="btn btn-default" ng-click="ctrl.cambioAcumuladoMensualContinua();"><span>Continua</span></label>
			</div>
		</div>
		<div align="center" class="row" style="width: 80%;margin: 0 auto; font-size: 11px;">
			<div align="center">
				<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
					chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
					chart-legend="true">
				</canvas>
			</div>
		</div>
		<div style="text-align: center; font-size: 11px;">Fuente de información SICOIN. Cifras en porcentajes.</div>
		<br>
	</div>