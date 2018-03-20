<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
		.cuerpotabla{
			overflow-y: hidden;
			overflow-x: scroll;
		    display: inline-block;
		    font-size: 13px;
		}
    </style>
    <div ng-controller="puestosController as ctrl">
	    <div align="center" class="row" style="width: 90%; margin: 0 auto; font-size: 11px;">
	    	<h6 align="left">{{ctrl.subtitulo}}</h6>
			<br>
	    	<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" st-tree-table st-tree-init-open="false" st-selected-row="selectedRow" class="table table-striped cuerpotabla">
				<thead>
					<tr>
						<th style="min-width: 250px;" class="sticky-col"></th>
						<th style="text-align: center;" colspan="3">{{ctrl.anio - 4}}</th>
						<th style="text-align: center;" colspan="3">{{ctrl.anio - 3}}</th>
						<th style="text-align: center;" colspan="3">{{ctrl.anio - 2}}</th>
						<th style="text-align: center;" colspan="3">{{ctrl.anio - 1}}</th>
						<th style="text-align: center;" colspan="3">{{ctrl.anio}}</th>
					</tr>
					<tr>
						<th st-sort="nombre" style="width: 250px;" class="sticky-col">Nombre</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
						<th style="text-align: center; min-width: 50px;">Asignado</th>
						<th style="text-align: center; min-width: 50px;">Vigente</th>
						<th style="text-align: center; min-width: 50px;">Ejecutado</th>
					</tr>
					<tr>
						<th colspan="1"><input st-search="nombre" class="form-control" placeholder="Puesto..." type="text"/></th>
					</tr>					
				</thead>
				<tbody>
					<tr st-tree-row st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="" ng-dblclick="">
						<td align="left" style="white-space: nowrap; width: 250px;"  ng-style="{{row.styleToggle}}" class="sticky-col">
							<div ng-show="row.showToggle" style="float: left; padding-right: 5px;"><st-tree-caret></st-tree-caret></div>{{row.nombre}}
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[0][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[0][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[0][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[1][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[1][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[1][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[2][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[2][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[2][2] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[3][0] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[3][1] | formatoMillones : ctrl.decimales}}</div>
						</td>
						<td align="right">
							<div style="white-space: nowrap;">{{row.data_ejercicio[3][2] | formatoMillones : ctrl.decimales}}</div>
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
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_3 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_3 | formatoMillones : ctrl.decimales}}</div>
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
							<div style="white-space: nowrap;">{{ctrl.tot_asignado_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_vigente_1 | formatoMillones : ctrl.decimales}}</div>
						</th>
						<th style="text-align: right;">
							<div style="white-space: nowrap;">{{ctrl.tot_ejecutado_1 | formatoMillones : ctrl.decimales}}</div>
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
					</tr>
				</tfoot>
			</table>
			<div style="text-align: center;">Fuente de información SICOIN. Cifras en millones de Quetzales.</div>
	    </div>
    </div>