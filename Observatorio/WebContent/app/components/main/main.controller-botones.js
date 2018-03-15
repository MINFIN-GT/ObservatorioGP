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
	
	mi.presupuesto_total=0.0;
	
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
		accion: 'getAll',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.resultados_institucionales.p_fisico = response.data.resultados_institucionales.p_fisico;
			mi.resultados_institucionales.p_presupuestario = response.data.resultados_institucionales.p_presupuestario;
			mi.resultados_institucionales.num_resultados = response.data.resultados_institucionales.cantidad;
			mi.resultados_institucionales.ejecutado = response.data.resultados_institucionales.ejecutado;
			mi.resultados_institucionales.vigente = response.data.resultados_institucionales.vigente;
			
			mi.presupuesto_total+=mi.resultados_institucionales.vigente;
			
			mi.resultados_estrategicos.p_fisico = response.data.resultados_estrategicos.p_fisico;
			mi.resultados_estrategicos.p_presupuestario = response.data.resultados_estrategicos.p_presupuestario;
			mi.resultados_estrategicos.num_resultados = response.data.resultados_estrategicos.cantidad;
			mi.resultados_estrategicos.ejecutado = response.data.resultados_estrategicos.ejecutado;
			mi.resultados_estrategicos.vigente = response.data.resultados_estrategicos.vigente;
			
			mi.presupuesto_total += response.data.resultados_estrategicos.vigente;
			
			mi.resultados_otros.p_fisico = response.data.resultados_otros.p_fisico;
			mi.resultados_otros.p_presupuestario = response.data.resultados_otros.p_presupuestario;
			mi.resultados_otros.num_resultados = response.data.resultados_otros.cantidad;
			mi.resultados_otros.ejecutado = response.data.resultados_otros.ejecutado;
			mi.resultados_otros.vigente = response.data.resultados_otros.vigente;
			
			mi.presupuesto_total += response.data.resultados_otros.vigente;
			
			mi.obligaciones.ejecutado=response.data.obligaciones.ejecutado;
			mi.obligaciones.vigente=response.data.obligaciones.vigente;
			mi.obligaciones.p_presupuestario=response.data.obligaciones.ejecutado/response.data.obligaciones.vigente;
			
			mi.presupuesto_total += response.data.obligaciones.vigente;
			
			mi.deuda.ejecutado=response.data.deuda.ejecutado;
			mi.deuda.vigente=response.data.deuda.vigente;
			mi.deuda.p_presupuestario=response.data.deuda.ejecutado/response.data.deuda.vigente;
			
			mi.presupuesto_total += response.data.deuda.vigente;
		}
	});
	
	$http.post('/SLastupdate', { dashboard: 'ejecucionpresupuestaria', t: new Date().getTime() }).then(function(response){
	    if(response.data.success){
	    	mi.lastupdate = response.data.lastupdate;
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
	
	mi.slide_button = ['slide-button','slide-button','slide-button','slide-button'];
	
	
	/*-----------------------CAROUSEL-----------------------------------*/
	
	mi.interval = 5000;
	mi.wrap = false;
	$scope.active = 0;
	mi.slides = [];
	var currIndex = 0;
	
	for (var i = 0; i < 4; i++) {
		mi.slides.push({
		      image: '/assets/img/slider/' + i + '.jpg',
		      text: '',
		      id: i
		 });
	}
	
	$scope.$watch('active', function() {
        mi.slide_button[$scope.active] ='slide-button active';
        mi.slide_button[($scope.active==0) ? mi.slide_button.length-1 : $scope.active-1] = 'slide-button';
        mi.slide_button[($scope.active==mi.slide_button.length-1) ? 0 : $scope.active+1] = 'slide-button';
    });
	
}])
