<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<div ng-controller="obligacionesController as ctrl">
			<div align="center" class="row" style="width: 80%; margin: 0 auto;">
					<table st-table="ctrl.displayedCollection" st-safe-src="ctrl.rowCollection" class="table table-striped" st-filtered-collection>
						<thead>
							<tr>
								<th st-sort="entidad_nombre">Entidad</th>
								<th style="text-align: right" st-sort="d2014">{{ ctrl.ano - 4 }}</th>
								<th style="text-align: right" st-sort="d2015">{{ ctrl.ano - 3 }}</th>
								<th style="text-align: right" st-sort="d2016">{{ ctrl.ano - 2 }}</th>
								<th style="text-align: right" st-sort="d2017">{{ ctrl.ano - 1 }}</th>
							</tr>
						</thead>
						<tbody>
							<tr ng-repeat="row in ctrl.displayedCollection" ng-click="ctrl.getGraficaEntidad(row);">
								<td align="left">
									{{row.entidad_nombre }}
								</td>
								<td align="right">
									{{row.d2014 | formatoMillones : true }} 
								</td>
								<td align="right">
									{{row.d2015 | formatoMillones : true }}
								</td>
								<td align="right">
									{{row.d2016 | formatoMillones : true }}
								</td>
								<td align="right">
									{{row.d2017 | formatoMillones : true }}
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th style="text-align: right;">Totales</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot_dano1 | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot_dano2 | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot_dano3 | formatoMillones : true}}</div>
								</th>
								<th style="text-align: right;">
									<div>{{ctrl.tot_dano4 | formatoMillones : true}}</div>
								</th>
							</tr>
						</tfoot>
					</table>
				</div>
				<br/>
				<br/>
				<div align="center" class="row" style="width: 80%; margin: 0 auto;">
					<div align="center">
						<canvas id="line" class="chart chart-line" chart-data="ctrl.grafica_data"
							chart-labels="ctrl.labels" chart-series="ctrl.series" chart-options="ctrl.grafica_opciones"
							chart-legend="true">
						</canvas>
					</div>
				</div>
				
</div>