var app = angular.module('observatorio', ['chart.js','smart-table','ui.bootstrap','ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'ngUtilidades']);
app.controller('mainController',['$rootScope','$scope','$http', function($rootScope,$scope,$http){
	var mi = this;
	mi.etiqueta = 'Bienvenido';
	
	mi.resultados_institucionales={
		p_fisico: 0,
		p_presupuestario: 0,
		num_resultados: 0,
		ejecutado: 0,
		vigente: 0
	};
	
	mi.resultados_estrategicos={
			p_fisico: 0,
			p_presupuestario: 0,
			num_resultados: 0,
			ejecutado: 0,
			vigente: 0
		};
	
	mi.resultados_otros={
			p_fisico: 0,
			p_presupuestario: 0,
			num_resultados: 0,
			ejecutado: 0,
			vigente: 0
		};
	
	mi.deuda={
		ejecutado: 0,
		vigente: 0,
		p_presupuestario: 0
	};
	
	mi.obligaciones={
			ejecutado: 0,
			vigente: 0,
			p_presupuestario: 0
		};
	
	
	$http.post('/SInfo',{
		accion: 'getTipoResultado',
		tipo_resultado: 1,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.resultados_institucionales.p_fisico = response.data.tiporesultado[0].p_fisico;
			mi.resultados_institucionales.p_presupuestario = response.data.tiporesultado[0].p_presupuestario;
			mi.resultados_institucionales.num_resultados = response.data.tiporesultado[0].cantidad;
			mi.resultados_institucionales.ejecutado = response.data.tiporesultado[0].ejecutado;
			mi.resultados_institucionales.vigente = response.data.tiporesultado[0].vigente;
		}
	});
	
	$http.post('/SInfo',{
		accion: 'getTipoResultado',
		tipo_resultado: 2,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.resultados_estrategicos.p_fisico = response.data.tiporesultado[0].p_fisico;
			mi.resultados_estrategicos.p_presupuestario = response.data.tiporesultado[0].p_presupuestario;
			mi.resultados_estrategicos.num_resultados = response.data.tiporesultado[0].cantidad;
			mi.resultados_estrategicos.ejecutado = response.data.tiporesultado[0].ejecutado;
			mi.resultados_estrategicos.vigente = response.data.tiporesultado[0].vigente;
		}
	});
	
	$http.post('/SInfo',{
		accion: 'getTipoResultado',
		tipo_resultado: 3,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.resultados_otros.p_fisico = response.data.tiporesultado[0].p_fisico;
			mi.resultados_otros.p_presupuestario = response.data.tiporesultado[0].p_presupuestario;
			mi.resultados_otros.num_resultados = response.data.tiporesultado[0].cantidad;
			mi.resultados_otros.ejecutado = response.data.tiporesultado[0].ejecutado;
			mi.resultados_otros.vigente = response.data.tiporesultado[0].vigente;
		}
	});
	
	$http.post('/SInfo',{
		accion: 'getDeuda',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.deuda.ejecutado=response.data.tiporesultado.ejecutado;
			mi.deuda.vigente=response.data.tiporesultado.vigente;
			mi.deuda.p_presupuestario=response.data.tiporesultado.ejecutado/response.data.tiporesultado.vigente;
		}
	});
	
	$http.post('/SInfo',{
		accion: 'getObligaciones',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.obligaciones.ejecutado=response.data.tiporesultado.ejecutado;
			mi.obligaciones.vigente=response.data.tiporesultado.vigente;
			mi.obligaciones.p_presupuestario=response.data.tiporesultado.ejecutado/response.data.tiporesultado.vigente;
		}
	});
	
	mi.go = function(level){
		switch(level){
			case 1: window.location.href = '/main.jsp#!/entidad/1'; break;
			case 2: window.location.href = '/main.jsp#!/entidad/2'; break;
			case 3: window.location.href = '/main.jsp#!/entidad/3'; break;
			case 4: window.location.href = '/main.jsp#!/deuda'; break;
			case 5: window.location.href = '/main.jsp#!/obligaciones'; break;
		}
	}
	
	
	/*-----------------------CAROUSEL-----------------------------------*/
	
	mi.interval = 5000;
	mi.wrap = false;
	mi.active = 0;
	mi.slides = [];
	var currIndex = 0;
	
	for (var i = 0; i < 4; i++) {
		mi.slides.push({
		      image: '/assets/img/slider/' + i + '.jpg',
		      text: '',
		      id: i
		 });
	 }
	
	/*-----------------------MENU REDONDO-----------------------------------*/
	
	$scope.menuConfig = {
			  "buttonWidth": 60,
			  "menuRadius": 160,
			  "color": "#ff7f7f",
			  "offset":25,
			  "textColor": "#ffffff",
			  "showIcons":false,
			  "gutter": {
			    "top": 130,
			    "right": 30,
			    "bottom": 30,
			    "left": 30
			  },
			  "angles": {
			    "topLeft": 0,
			    "topRight": 90,
			    "bottomRight": 180,
			    "bottomLeft": 270
			  }
			};


			$scope.menuItems = [{
			  "title": "iPad",
			  "color": "#ea2a29",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-tablet","size": 35}
			}, {
			  "title": "iMac",
			  "color": "#f16729",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-laptop","size": 30}
			}, {
			  "title": "iPhone",
			  "color": "#f89322",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-mobile","size": 30}
			}, {
			  "title": "iWatch",
			  "color": "#ffcf14",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-clock-o","size": 30}
			}];
			

	
}])
