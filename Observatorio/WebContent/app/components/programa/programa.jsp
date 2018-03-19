<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div ng-controller="programaController as ctrl">
    	<div align="center" class="row" style="width: 90%; margin: 0 auto; font-size: 11px;">
    		<h6 align="left">{{ctrl.subtitulo}}</h6>
    		<br>
	    	<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped cuerpotabla">
				<thead>
					<tr>
						<th style="width: 50%"></th>
						<th style="text-align: center; width: 400px;" colspan="5">{{ctrl.anio - 4}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 3}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 2}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio - 1}}</th>
						<th style="text-align: center;" colspan="5">{{ctrl.anio}}</th>
					</tr>
					<tr>
						<th st-sort="metaDescripcion">Nombre</th>
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
					<tr>
						<th colspan="1"><input st-search="nombre_programa" class="form-control" placeholder="Programa..." type="text"/></th>
					</tr>
				</thead>
				<tbody>
					<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaIndividual(row);" ng-dblclick="ctrl.irSubprograma(row.id, row.nombre_programa)">
						<td align="left" style="white-space: nowrap;">
							{{row.nombre_programa}}
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[0][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[0][12] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[0][24] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[0][36])*100  | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[0][48])*100 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
												<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[1][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[1][12] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[1][24] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[1][36])*100  | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[1][48])*100 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[2][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[2][12] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[2][24] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[2][36])*100  | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[2][48])*100 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[3][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[3][12] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[3][24] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[3][36])*100  | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[3][48])*100 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[4][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[4][12] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.ejercicio_data[4][24] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[4][36])*100  | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{(row.ejercicio_data[4][48])*100 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th style="text-align: right;">Totales:</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_ejecucion_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_avance_4 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_ejecucion_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_avance_3 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_ejecucion_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_avance_2 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_ejecucion_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_avance_1 | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_asignado | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_ejecucion | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_p_avance | formatoMillonesSinTipo : ctrl.decimales}}%</div>
						</th>
					</tr>
				</tfoot>
			</table>
			<div style="text-align: center; font-size: 11px;">Fuente de información SICOIN. Cifras en millones de Quetzales y porcentajes.</div>
	    </div>
	    <br>
	    <br>
	    <br>
	    <div>
			<div class="titulo_grafica">{{ ctrl.tituloGrafica }}</div>
			<div align="right" style="width: 90%;margin: 0 auto;">
				<div class="btn-group btn-group-xs">
					<label class="btn btn-default" ng-click="ctrl.cambioMensualP();"><span>Mensual</span></label>
					<label class="btn btn-default" ng-click="ctrl.cambioAnualP();"><span>Anual</span></label>
				</div>
			</div>
			<div align="center" class="row" style="width: 80%;margin: 0 auto;">
				<div align="center">
					<canvas id="line" class="chart chart-line" chart-data="ctrl.data2"
						chart-labels="ctrl.labels2" chart-series="ctrl.series2" chart-options="ctrl.options2"
						chart-colors="ctrl.linealColors" chart-legend="true">
					</canvas>
				</div>
			</div>
			<div style="text-align: center;">Fuente de información SICOIN. Cifras en porcentajes.</div>
			<br>
		</div>
    </div>