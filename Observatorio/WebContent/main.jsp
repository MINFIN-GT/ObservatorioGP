<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="shortcut icon" type="image/x-icon" href="/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/app/shared/includes.jsp" %>
<script type="text/javascript" src="app/components/main/main.controller.js"></script>

<title>Observatorio</title>
</head>
<body ng-app="observatorio" ng-controller="MainController as ctrl">
	<div class="row" style="margin: 0px;">
			<div class="col-sm-1">
			</div>
			<!-- header izquierdo -->
			<div class="col-sm-5" style="height: 105px">
				<br />
				<img src="/assets/img/logo.png" alt="Observatorio" />
			</div>
			<!-- header derecho -->
			<div class="col-sm-5">
			</div>
			<div class="col-sm-1">
			</div>
	</div>
	<div>
		<div id="head">
			{{ $root.page_title }}
		</div>
	</div>
	<div id="mainview">
		<div ng-view></div>
    </div>
    <div id="foot">
	<div>- MINFIN 2018 -
	</div>
</div>
</body>
</html>