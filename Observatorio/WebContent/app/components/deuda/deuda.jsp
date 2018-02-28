<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div ng-controller="deudaController as ctrl">
			<h3>Deuda</h3>
			<br/>
			<div align="center" class="row" style="width: 80%; margin: 0 auto;">
					<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped" st-filtered-collection>
						<thead>
							<tr>
								<th st-sort="entidad_nombre"></th>
								<th style="text-align: center" colspan="3">{{ ctrl.ano - 4 }}</th>
								<th style="text-align: center" colspan="3">{{ ctrl.ano - 3 }}</th>
								<th style="text-align: center" colspan="3">{{ ctrl.ano - 2 }}</th>
								<th style="text-align: center" colspan="3">{{ ctrl.ano - 1 }}</th>
								<th style="text-align: center" colspan="3">{{ ctrl.ano }}</th>
							</tr>
							<tr>
								<th></th>
								<th>Asignado</th>
								<th>Vigente</th>
								<th>Devengado</th>
								<th>Asignado</th>
								<th>Vigente</th>
								<th>Devengado</th>
								<th>Asignado</th>
								<th>Vigente</th>
								<th>Devengado</th>
								<th>Asignado</th>
								<th>Vigente</th>
								<th>Devengado</th>
								<th>Asignado</th>
								<th>Vigente</th>
								<th>Devengado</th>
							</tr>
						</thead>
						<tbody>
							<tr st-select-row="row" st-select-mode="single" ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaEntidad(row);">
								<td align="left">
									{{row.entidad_nombre }}
								</td>
								<td align="right">
									{{ row.ejercicio_data[0][0] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[0][12] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[0][24] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[1][0] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[1][12] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[1][24] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[2][0] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[2][12] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[2][24] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[3][0] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[3][12] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[3][24] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[4][0] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[4][12] | formatoMillones : true }} 
								</td>
								<td align="right">
									{{ row.ejercicio_data[4][24] | formatoMillones : true }} 
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th style="text-align: right;">Totales</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[0][0] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[0][1] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[0][2] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[1][0] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[1][1] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[1][2] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[2][0] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[2][1] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[2][2] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[3][0] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[3][1] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[3][2] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[4][0] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[4][1] | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot[4][2] | formatoMillones : true}}</div>
								</th>
							</tr>
						</tfoot>
					</table>
					<div style="text-align: center;">Fuente de información SICOIN. Cifras en millones.</div>
				</div>
				<br/>
				<br/>
				<br/>
				<br/>
				<div align="center" class="row" style="width: 80%; margin: 0 auto;">
					<div class="titulo_grafica">{{ ctrl.grafica_titulo }}</div>
					<br/>
					<div align="center">
						<canvas id="line" class="chart chart-line" chart-data="ctrl.grafica_data"
							chart-labels="ctrl.grafica_labels" chart-series="ctrl.grafica_series" chart-options="ctrl.grafica_opciones"
							chart-legend="true">
						</canvas>
					</div>
				</div>
				
</div>