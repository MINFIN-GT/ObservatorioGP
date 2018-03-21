<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <div ng-controller="hospitalesController as ctrl">
	    <div align="center" class="row" style="width: 90%; margin: 0 auto; font-size: 11px;">
	    	<table style="width: 90%;" st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" st-tree-table st-tree-init-open="false" st-selected-row="selectedRow" class="table table-striped cuerpotabla">
				<thead>
					<tr>
						<th style="min-width: 250px;" class="sticky-col"></th>
						<th style="text-align: center;" colspan="2">{{ctrl.anio - 4}}</th>
						<th style="text-align: center;" colspan="2">{{ctrl.anio - 3}}</th>
						<th style="text-align: center;" colspan="2">{{ctrl.anio - 2}}</th>
						<th style="text-align: center;" colspan="2">{{ctrl.anio - 1}}</th>
						<th style="text-align: center;" colspan="4">{{ctrl.anio}}</th>
					</tr>
					<tr>
						<th st-sort="nombre" style="width: 250px;" class="sticky-col">Nombre</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">%</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">%</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">%</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">%</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">%</th>
					</tr>
					<tr>
						<th colspan="1"><input st-search="nombre" class="form-control" placeholder="Hospital..." type="text"/></th>
					</tr>					
				</thead>
				<tbody>
					<tr st-tree-row st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="" ng-dblclick="">
						<td align="left" style="white-space: nowrap; width: 250px;" ng-style="{{row.styleToggle}}" class="sticky-col">
							<div ng-show="row.showToggle" style="float: left; padding-right: 5px;"><st-tree-caret></st-tree-caret></div>{{row.nombre}}
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ row.data_ejercicio[0][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ (row.data_ejercicio[0][1] > 0 ? row.data_ejercicio[0][2]*100/row.data_ejercicio[0][1] : 0) | formatoPorcentaje }}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[1][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ (row.data_ejercicio[1][1] > 0 ? row.data_ejercicio[1][2]*100/row.data_ejercicio[1][1] : 0) | formatoPorcentaje }}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[2][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ (row.data_ejercicio[2][1] > 0 ? row.data_ejercicio[2][2]*100/row.data_ejercicio[2][1] : 0) | formatoPorcentaje }}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[3][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ (row.data_ejercicio[3][1] > 0 ? row.data_ejercicio[3][2]*100/row.data_ejercicio[3][1] : 0) | formatoPorcentaje }}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[4][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[4][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[4][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{ (row.data_ejercicio[4][1] > 0 ? row.data_ejercicio[4][2]*100/row.data_ejercicio[4][1] : 0) | formatoPorcentaje}}</div>
						</td>						
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<th style="text-align: right;">Totales:</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_4 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ (ctrl.tot_vigente_4>0 ? ctrl.tot_ejecutado_4*100/ctrl.tot_vigente_4 : 0) | formatoPorcentaje}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ (ctrl.tot_vigente_3>0 ? ctrl.tot_ejecutado_3*100/ctrl.tot_vigente_3 : 0) | formatoPorcentaje}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_2 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{(ctrl.tot_vigente_2>0 ? ctrl.tot_ejecutado_2*100/ctrl.tot_vigente_2 : 0) | formatoPorcentaje}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{(ctrl.tot_vigente_1>0 ? ctrl.tot_ejecutado_1*100/ctrl.tot_vigente_1 : 0) | formatoPorcentaje}}</div>
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
							<div style="white-space: nowrap;">{{(ctrl.tot_vigente>0 ? ctrl.tot_ejecutado*100/ctrl.tot_vigente : 0) | formatoPorcentaje }}</div>
						</th>						
					</tr>
				</tfoot>
			</table>
			<div style="text-align: center;">Fuente de información SICOIN. Cifras en millones de Quetzales.</div>
	    </div>
	    <br>
		<br>
		<br>
		<div>
			<div align="center" class="row" style="width: 80%;margin: 0 auto;">
				<div align="center">
					<canvas id="line" class="chart chart-line" chart-data="ctrl.data"
						chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.options"
						chart-colors="ctrl.linealColors" chart-legend="true">
					</canvas>
				</div>
			</div>
			<div style="text-align: center;">Fuente de información SICOIN. Cifras en millones de Quetzales.</div>
			<br>
		</div>
    </div>