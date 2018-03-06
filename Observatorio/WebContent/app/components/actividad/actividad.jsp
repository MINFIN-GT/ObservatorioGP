<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
    	.bordeIzquierda{
		    border-left: 1px double #ccc!important;
		}
		.cuerpotabla{
			overflow-y: hidden;
			overflow-x: scroll;
		    display: inline-block;
		    font-size: 13px;
		}
    </style>
    <div ng-controller="actividadController as ctrl">
	    <div align="center" class="row" style="width: 80%; margin: 0 auto;">
	    	<h3>Actividades</h3>
			<br>
	    	<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped cuerpotabla">
				<thead>
					<tr>
						<th style="width: 50%" st-sort="metaDescripcion">Descripción</th>
						<th style="text-align: center; width: 400px;" colspan="5">{{ctrl.anio - 4}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 3}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 2}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 1}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio}}</th>
					</tr>
					<tr>
						<th></th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Asig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Vig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Ejec.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EP</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EF</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Asig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Vig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Ejec.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EP</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EF</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Asig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Vig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Ejec.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EP</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EF</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Asig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Vig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Ejec.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EP</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EF</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Asig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Vig.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">Ejec.</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EP</th>
						<th style="text-align: center; min-width: 50px; max-width: 50px;">% EF</th>
					</tr>
				</thead>
				<tbody>
					<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaActividad(row);">
						<td align="left">
							{{row.descripcionActividad}}
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.asignado4 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.vigente4 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.ejecutado4 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_ejecucion_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_avance_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right" class="bordeIzquierda">
							<div style="min-width: 50px;">{{row.asignado3 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.vigente3 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.ejecutado3 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_ejecucion_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_avance_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right" class="bordeIzquierda">
							<div style="min-width: 50px;">{{row.asignado2 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.vigente2 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.ejecutado2 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_ejecucion_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right" class="bordeIzquierda">
							<div style="min-width: 50px;">{{row.asignado1 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.vigente1 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.ejecutado1 | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_ejecucion_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right" class="bordeIzquierda">
							<div style="min-width: 50px;">{{row.asignado | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.vigente | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.ejecutado | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_ejecucion | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="min-width: 50px;">{{row.p_avance | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th style="text-align: right;">Totales:</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_asignado_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_vigente_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_ejecutado_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_ejecucion_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_asignado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_vigente_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_ejecutado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_ejecucion_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_asignado_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_vigente_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_ejecutado_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_ejecucion_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_asignado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_vigente_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_ejecutado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_ejecucion_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_asignado | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_vigente | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_ejecutado | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_ejecucion | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div>{{ctrl.tot_p_avance | formatoMillonesSinTipo : ctrl.decimales}}%</div>
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