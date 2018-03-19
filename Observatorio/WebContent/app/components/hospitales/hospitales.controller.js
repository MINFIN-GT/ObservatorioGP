var app = angular.module('hospitalesController',[]).controller('hospitalesController',['$rootScope','$scope','$http','$routeParams', '$window', 
	function($rootScope,$scope,$http,$routeParams,$window){
	var mi = this;
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth();
	
	mi.tot_asignado_4 = 0;
	mi.tot_vigente_4 = 0;
	mi.tot_ejecutado_4 = 0;
	
	mi.tot_asignado_3 = 0;
	mi.tot_vigente_3 = 0;
	mi.tot_ejecutado_3 = 0;
	
	mi.tot_asignado_2 = 0;
	mi.tot_vigente_2 = 0;
	mi.tot_ejecutado_2 = 0;
	
	mi.tot_asignado_1 = 0;
	mi.tot_vigente_1 = 0;
	mi.tot_ejecutado_1 = 0;
	
	mi.tot_asignado = 0;
	mi.tot_vigente = 0;
	mi.tot_ejecutado = 0;
	
	$http.post('/SHospitales',{
		accion: 'getHospitales',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.datos = response.data.hospitales;
			mi.rowCollection = [];
			mi.rowCollection = mi.datos;
			 
			mi.displayedCollection = [].concat(mi.rowCollection);
		}
	})
}])