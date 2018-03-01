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
<body ng-app="observatorio" ng-controller="MainController as mainController">
	<div align="center" style="margin-top: 40px;">
		<div class="btn-group btn-group-lg">
			<label class="btn btn-default"><a href="/main.jsp#!/ri"><span>RI</span></a></label>
			<label class="btn btn-default"><a href="/main.jsp#!/re"><span>RE</span></a></label>
			<label class="btn btn-default"><a href="/main.jsp#!/otros"><span>Otros</span></a></label>
			<label class="btn btn-default"><a href="/main.jsp#!/deuda"><span>Deuda</span></a></label>
			<label class="btn btn-default"><a href="/main.jsp#!/obligaciones"><span>Obligaciones</span></a></label>
		</div>
	</div>
	<div id="mainview">
		<div ng-view></div>
    </div>
    <div class="footer"><div style="text-align: center; width: 100%;" class="label-form">- Minfin 2018 -</div></div>
</body>
</html>