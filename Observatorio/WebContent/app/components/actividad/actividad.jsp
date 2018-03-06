<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
    	.bordeIzquierda{
		    border-left: 1px double #ccc!important;
		}
    </style>
    <div ng-controller="actividadController as ctrl">
	    <div align="center" class="row" style="width: 90%; margin: 0 auto;">
	    	<h3>Actividades</h3>
			<br>
	    	<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped">
				<thead>
					<tr>
						<th style="width: 50%" st-sort="metaDescripcion">Descripción</th>
						<th style="text-align: center; width: 10%" colspan="2">{{ctrl.anio - 4}}</th>
						<th style="text-align: center; width: 10%" colspan="2">{{ctrl.anio - 3}}</th>
						<th style="text-align: center; width: 10%" colspan="2">{{ctrl.anio - 2}}</th>
						<th style="text-align: center; width: 10%" colspan="2">{{ctrl.anio - 1}}</th>
						<th style="text-align: center; width: 10%" colspan="2">{{ctrl.anio}}</th>
					</tr>
					<tr>
						<th></th>
						<th style="text-align: center;">% Fin</th>
						<th style="text-align: center;">% Fís</th>
						<th style="text-align: center;">% Fin</th>
						<th style="text-align: center;">% Fís</th>
						<th style="text-align: center;">% Fin</th>
						<th style="text-align: center;">% Fís</th>
						<th style="text-align: center;">% Fin</th>
						<th style="text-align: center;">% Fís</th>
						<th style="text-align: center;">% Fin</th>
						<th style="text-align: center;">% Fis</th>
					</tr>
				</thead>
				<tbody>
					<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaActividad(row);">
						<td align="left">
							{{row.descripcionActividad}}
						</td>
						<td align="right">
							{{row.p_ejecucion_4 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion_3 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right" class="bordeIzquierda">
							{{row.p_ejecucion_2 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_ejecucion_1 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right" class="bordeIzquierda">
							{{row.p_ejecucion | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_avance_4 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right" class="bordeIzquierda">
							{{row.p_avance_3 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right" class="bordeIzquierda">
							{{row.p_avance_1 | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
						<td align="right">
							{{row.p_avance | formatoMillonesSinTipo : ctrl.decimales}}%
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
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
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_4}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_3}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_2}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_1}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance}}%</div>
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
			</div>
		</div>
		<div align="center" class="row" style="width: 80%;margin: 0 auto;">
			<div align="center">
				<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
					chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
					chart-colors="ctrl.linealColors" chart-legend="true">
				</canvas>
			</div>
		</div>
    </div>