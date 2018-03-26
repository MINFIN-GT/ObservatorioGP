<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="modal-header">
     <h3 class="modal-title">{{controller.nombre}}</h3>
</div>
<div class="modal-body" id="modal-body">
	<div class="row">
		<div class="col-sm-12">
			<div class="row">
				<div class="row">
				    <div class="col-sm-12">
				    	<div class="panel panel-default">

						</div>
				    </div>
				</div>
			</div>
			<br/>
			<div class="row">
			    <div class="col-sm-12 operation_buttons" align="right">
				    <div class="btn-group">
						<label class="btn btn-primary" ng-click="controller.cerrar()">Cerrar</label>
			    	</div>
			    </div>
	  		</div>
		</div>
	</div>
</div>