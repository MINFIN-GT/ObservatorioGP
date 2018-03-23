<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
.glyphicon {
    font-size: 20px;
}
</style>

<div ng-controller="historiaController as ctrl" class="all_page">
	<script type="text/ng-template" id="opciones1.jsp">
    	<%@ include file="/app/components/salud/historia/opciones1.jsp"%>
  	</script>
	<div class="row" style="height: 100%" align="center">
		<div class="col-sm-12">
			<div style="width: 90%; height:100%; margin: 0 auto; font-size: 11px; text-align: center">
				<div class="divlateral" style="float: left; width: 100%;">
					<label class="verticalText label-form btn btn-default" ng-click="ctrl.opciones1()">Gasto Mensual</label>
				</div>
				<div style="float: right; width: 95%;">
					<div align="center" class="row" style="width: 100%; height:100%; margin: 0 auto; font-size: 11px; text-align: center">
						<div class="centerBubble">
							<canvas id="base" class="chart-bubble" chart-data="ctrl.data" chart-options="ctrl.options" chart-click="ctrl.onClick">
							</canvas>
							<div class="labelBubble">
								<label style="font-size: 3cm; color: #d9d9d9;">{{ctrl.anio}}</label>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<br>
	<div align="center" >
		<label class="label-form btn btn-default" ng-click="ctrl.opciones2()">Personas 2</label>
	</div>
	<br>
	<div align="center" class="row" style="width: 90%; margin: 0 auto;">
		<label class="btn btn-default btn-circle" ng-click="ctrl.pause == true ? ctrl.startTime() : ctrl.stopTime()"><i class="{{ctrl.icono}}"></i></label>
	</div>
</div>