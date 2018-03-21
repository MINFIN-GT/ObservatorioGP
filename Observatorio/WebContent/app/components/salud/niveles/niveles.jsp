<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
    	.nivel_atencion{
    		
    	}
    	
    	.nivel_atencion .numero{
    		font-size: 48px;
    		float: left;
    	}
    	
    	.nivel_atencion .ordinal{
    		font-size: 18px;
    		float: left;
    	}
    </style>
    <div ng-controller="centrosController as ctrl">
	    <div align="center" class="row" style="width: 90%; margin: 0 auto; font-size: 11px;">
	    	<div class="table">
	    		<div class="row">
	    			<div class="col-sm-1 nivel_atencion"><div class="numero">1</div><div class="ordinal">er</div></div>
	    			<div class="col-sm-11"><table>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_1">
	    					<td>{{ row.rubro_nombre }}</td>
	    					<td>{{ row.data_ejercicio[4][0] }}</td>
	    					<td>{{ row.data_ejercicio[4][1] }}</td>
	    					<td>{{ row.data_ejercicio[4][2] }}</td>
	    					<td></td>
	    				</tr>
	    			</table></div>
	    		</div>
	    		<div class="row">
	    			<div class="col-sm-1 nivel_atencion"><div class="numero">2</div><div class="ordinal">do</div></div>
	    			<div class="col-sm-11"><table>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_2">
	    					<td>{{ row.rubro_nombre }}</td>
	    					<td>{{ row.data_ejercicio[4][0] }}</td>
	    					<td>{{ row.data_ejercicio[4][1] }}</td>
	    					<td>{{ row.data_ejercicio[4][2] }}</td>
	    					<td></td>
	    				</tr>
	    			</table></div>
	    		</div>
	    		<div class="row">
	    			<div class="col-sm-1 nivel_atencion"><div class=" numero">3</div><div class="ordinal">er</div></div>
	    			<div class="col-sm-11"><table>
	    				<tr ng-repeat="row in ctrl.rubros_nivel_3">
	    					<td>{{ row.rubro_nombre }}</td>
	    					<td>{{ row.data_ejercicio[4][0] }}</td>
	    					<td>{{ row.data_ejercicio[4][1] }}</td>
	    					<td>{{ row.data_ejercicio[4][2] }}</td>
	    					<td></td>
	    				</tr>
	    			</table></div>
	    		</div>
	    	</div>
	    </div>
    </div>