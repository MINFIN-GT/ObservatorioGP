<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
    	.nivel_atencion{
    		cursor: pointer;
    	}
    	
    	.nivel_atencion .numero{
    		font-size: 48px;
    		float: left;
    	}
    	
    	.nivel_atencion .ordinal{
    		font-size: 18px;
    		float: left;
    	}
    	
    	.tab1{
    		padding-left:20px;
    	}
    	
    	table { border-collapse: separate; border-spacing: 6px; width: 100%;}
    	td { white-space: nowrap; }
    	
    	.chart-legend {
		    list-style-type: none;
		    margin-top: 5px;
		    text-align: center;
		    -webkit-padding-start: 0;
		    -moz-padding-start: 0;
		    padding-left: 0;
		}
		
		.chart-legend li {
		    display: inline-block;
		    white-space: nowrap;
		    position: relative;
		    margin-bottom: 4px;
		    border-radius: 5px;
		    padding: 2px 8px 2px 28px;
		    font-size: smaller;
		    cursor: default;
		}
		
		.chart-legend li span {
		    display: block;
		    position: absolute;
		    left: 0;
		    top: 0;
		    width: 20px;
		    height: 20px;
		    border-radius: 5px;
		}
		
    </style>
    <div ng-controller="centrosController as ctrl">
	    <div align="center" class="row" style="width: 90%; margin: 0 auto; font-size: 11px;">
	    	<div class="table">
	    		<div class="row" style="margin-bottom: 25px;">
	    			<div class="col-sm-1 nivel_atencion" ng-click="ctrl.go(1)"><div class="numero">1</div><div class="ordinal">er</div></div>
	    			<div class="col-sm-7 text-left"><table>
	    				<thead>
	    					<tr>
	    						<th></th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-2 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-1 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year() }}</th>
	    					</tr>
	    					<tr>
	    						<th>Rubro</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(1,3)">E</th>
	    					</tr>
	    				</thead>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_1">
	    					<td ng-class="row.treeLevel==0 ? 'tab1' : ''">{{ row.nombre }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][0]  | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][1] | formatoMillones : true)  : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][2] | formatoMillones : true) : ''}}</td>
	    				</tr>
	    				<tr class="text-right">
	    					<td><strong>Totales</strong></td>
	    					<td>{{ ctrl.totales_1[2][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[2][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[2][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][2] | formatoMillones : true}}</td>
	    				</tr>
	    			</table></div>
	    			<div class="col-sm-4 text-center">
	    				<canvas id="line" class="chart chart-bar" chart-data="ctrl.grafica_nivel1" chart-labels="ctrl.grafica_labels" 
	    					chart-series="ctrl.grafica_series" chart-options="ctrl.grafica_opciones"
	    					chart-dataset-override="ctrl.grafica_dataset" chart-colors="ctrl.chart_colors"></canvas>
	    				<div><div class="row">
	    					<ol class="chart-legend">
						        <li><span style="background-color: #992038"></span>{{ ctrl.today.year()-2 }}</li>
						        <li><span style="background-color: #F1A944"></span>{{ ctrl.today.year()-1 }}</li>
						        <li><span style="background-color: #4169E1"></span>{{ ctrl.today.year() }}</li>
						    </ol>
	    				</div></div>
	    				<div>{{ ctrl.titulo_grafica_nivel1 }}</div>
	    			</div>
	    		</div>
	    		<hr/>
	    		<div class="row" style="margin-bottom: 25px;">
	    			<div class="col-sm-1 nivel_atencion" ng-click="ctrl.go(2)"><div class="numero">2</div><div class="ordinal">do</div></div>
	    			<div class="col-sm-7 text-left"><table>
	    				<thead>
	    					<tr>
	    						<th></th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-2 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-1 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year() }}</th>
	    					</tr>
	    					<tr>
	    						<th>Rubro</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(2,3)">E</th>
	    					</tr>
	    				</thead>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_2">
	    					<td ng-class="row.treeLevel==0 ? 'tab1' : ''">{{ row.nombre }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][0]  | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][1] | formatoMillones : true)  : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][2] | formatoMillones : true) : ''}}</td>
	    				</tr>
	    				<tr class="text-right">
	    					<td><strong>Totales</strong></td>
	    					<td>{{ ctrl.totales_2[2][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[2][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[2][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[3][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[3][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[3][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[4][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[4][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_2[4][2] | formatoMillones : true}}</td>
	    				</tr>
	    			</table></div>
	    			<div class="col-sm-4 text-center">
	    				<canvas id="line" class="chart chart-bar" chart-data="ctrl.grafica_nivel2" chart-labels="ctrl.grafica_labels" 
	    					chart-series="ctrl.grafica_series" chart-options="ctrl.grafica_opciones"
	    					chart-dataset-override="ctrl.grafica_dataset" chart-colors="ctrl.chart_colors"></canvas>
	    				<div class="row">
	    					<ol class="chart-legend">
						        <li><span style="background-color: #992038"></span>{{ ctrl.today.year()-2 }}</li>
						        <li><span style="background-color: #F1A944"></span>{{ ctrl.today.year()-1 }}</li>
						        <li><span style="background-color: #4169E1"></span>{{ ctrl.today.year() }}</li>
						    </ol>
	    				</div>
	    				<div>{{ ctrl.titulo_grafica_nivel2 }}</div>
	    			</div>
	    		</div>
	    		<hr/>
	    		<div class="row" style="margin-bottom: 25px;">
	    			<div class="col-sm-1 nivel_atencion" ng-click="ctrl.go(3)"><div class="numero">3</div><div class="ordinal">er</div></div>
	    			<div class="col-sm-7 text-left"><table>
	    				<thead>
	    					<tr>
	    						<th></th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-2 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-1 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year() }}</th>
	    					</tr>
	    					<tr>
	    						<th>Rubro</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(3,3)">E</th>
	    					</tr>
	    				</thead>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_3">
	    					<td ng-class="row.treeLevel==0 ? 'tab1' : ''">{{ row.nombre }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][0]  | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][1] | formatoMillones : true)  : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[2][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[3][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ row.codigo>1 ? (row.data_ejercicio[4][2] | formatoMillones : true) : ''}}</td>
	    				</tr>
	    				<tr class="text-right">
	    					<td><strong>Totales</strong></td>
	    					<td>{{ ctrl.totales_3[2][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[2][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[2][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[3][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[3][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[3][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[4][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[4][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_3[4][2] | formatoMillones : true}}</td>
	    				</tr>
	    			</table></div>
	    			<div class="col-sm-4 text-center">
	    				<canvas id="line" class="chart chart-bar" chart-data="ctrl.grafica_nivel3" chart-labels="ctrl.grafica_labels" 
	    					chart-series="ctrl.grafica_series" chart-options="ctrl.grafica_opciones"
	    					chart-dataset-override="ctrl.grafica_dataset" chart-colors="ctrl.chart_colors"></canvas>
	    				<div class="row">
	    					<ol class="chart-legend">
						        <li><span style="background-color: #992038"></span>{{ ctrl.today.year()-2 }}</li>
						        <li><span style="background-color: #F1A944"></span>{{ ctrl.today.year()-1 }}</li>
						        <li><span style="background-color: #4169E1"></span>{{ ctrl.today.year() }}</li>
						    </ol>
	    				</div>
	    				<div>{{ ctrl.titulo_grafica_nivel3 }}</div>
	    			</div>
	    		</div>
	    	</div>
	    	<hr/>
	    	<div class="row" style="margin-bottom: 25px;">
	    			<div class="col-sm-1 nivel_atencion"><div class="numero">T</div></div>
	    			<div class="col-sm-7 text-left"><table>
	    				<thead>
	    					<tr>
	    						<th></th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-2 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year()-1 }}</th>
	    						<th colspan="3" class="text-center">{{ ctrl.today.year() }}</th>
	    					</tr>
	    					<tr>
	    						<th>Rubro</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,3)">E</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,1)">A</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,2)">V</th>
	    						<th class="text-center" style="cursor: pointer;" ng-click="ctrl.changeGrafica(4,3)">E</th>
	    					</tr>
	    				</thead>
	    				<tr ng-repeat="row in ctrl.rubros_totales track by $index ">
	    					<td ng-class="$index==1 || $index==2 || $index==3 ? 'tab1' : ''">{{ ctrl.rubros_nivel_1[$index].nombre }}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[2][0]  | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[2][1] | formatoMillones : true)  : ''}}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[2][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[3][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[3][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[3][2] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[4][0] | formatoMillones : true) : '' }}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[4][1] | formatoMillones : true) : ''}}</td>
	    					<td class="text-right">{{ $index>0 ? (row.data_ejercicio[4][2] | formatoMillones : true) : ''}}</td>
	    				</tr>
	    				<tr class="text-right">
	    					<td><strong>Totales</strong></td>
	    					<td>{{ ctrl.totales_1[2][0] + ctrl.totales_2[2][0] + ctrl.totales_3[2][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[2][1] + ctrl.totales_2[2][1] + ctrl.totales_3[2][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[2][2] + ctrl.totales_2[2][2] + ctrl.totales_3[2][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][0] + ctrl.totales_2[3][0] + ctrl.totales_3[3][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][1] + ctrl.totales_2[3][1] + ctrl.totales_3[3][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[3][2] + ctrl.totales_2[3][2] + ctrl.totales_3[3][2] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][0] + ctrl.totales_2[4][0] + ctrl.totales_3[4][0] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][1] + ctrl.totales_2[1][1] + ctrl.totales_3[4][1] | formatoMillones : true}}</td>
	    					<td>{{ ctrl.totales_1[4][2] + ctrl.totales_2[4][2] + ctrl.totales_3[4][2] | formatoMillones : true}}</td>
	    				</tr>
	    			</table></div>
	    			<div class="col-sm-4 text-center">
	    				<canvas id="line" class="chart chart-bar" chart-data="ctrl.grafica_total" chart-labels="ctrl.grafica_labels" 
	    					chart-series="ctrl.grafica_series" chart-options="ctrl.grafica_opciones"
	    					chart-dataset-override="ctrl.grafica_dataset" chart-colors="ctrl.chart_colors"></canvas>
	    				<div class="row">
	    					<ol class="chart-legend">
						        <li><span style="background-color: #992038"></span>{{ ctrl.today.year()-2 }}</li>
						        <li><span style="background-color: #F1A944"></span>{{ ctrl.today.year()-1 }}</li>
						        <li><span style="background-color: #4169E1"></span>{{ ctrl.today.year() }}</li>
						    </ol>
	    				</div>
	    				<div>{{ ctrl.titulo_grafica_total }}</div>
	    			</div>
	    		</div>
	    	</div>
	    </div>
    </div>