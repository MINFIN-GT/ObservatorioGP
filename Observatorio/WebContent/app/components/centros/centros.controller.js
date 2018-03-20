var app = angular.module('centrosController', []).controller('centrosController',['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
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
	
	$http.post('/SSalud',{
		accion: 'getCentros',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.datos = response.data.centros;
			
			for(var i=0; i<mi.datos.length; i++){
				if(mi.datos[i].treeLevel==3){
					mi.tot_asignado_4 += mi.datos[i].data_ejercicio[0][0];
					mi.tot_vigente_4 += mi.datos[i].data_ejercicio[0][1];
					mi.tot_ejecutado_4 += mi.datos[i].data_ejercicio[0][2];
					
					mi.tot_asignado_3 += mi.datos[i].data_ejercicio[1][0];
					mi.tot_vigente_3 += mi.datos[i].data_ejercicio[1][1];
					mi.tot_ejecutado_3 += mi.datos[i].data_ejercicio[1][2];
					
					mi.tot_asignado_2 += mi.datos[i].data_ejercicio[2][0];
					mi.tot_vigente_2 += mi.datos[i].data_ejercicio[2][1];
					mi.tot_ejecutado_2 += mi.datos[i].data_ejercicio[2][2];
					
					mi.tot_asignado_1 += mi.datos[i].data_ejercicio[3][0];
					mi.tot_vigente_1 += mi.datos[i].data_ejercicio[3][1];
					mi.tot_ejecutado_1 += mi.datos[i].data_ejercicio[3][2];
					
					mi.tot_asignado += mi.datos[i].data_ejercicio[4][0];
					mi.tot_vigente += mi.datos[i].data_ejercicio[4][1];
					mi.tot_ejecutado += mi.datos[i].data_ejercicio[4][2];
				}
				if(mi.datos[i].treeLevel===3 || mi.datos[i].treeLevel===2)
					mi.datos[i].showToggle = true;
				if(mi.datos[i].codigo===1 && mi.datos[i].treeLevel===1)
					mi.datos[i].showToggle = true;
				if(mi.datos[i].treeLevel===0)
					mi.datos[i].styleToggle = { 'padding-left': '55px'};
				else if(mi.datos[i].treeLevel===1 && (mi.datos[i].codigo >= 2 && mi.datos[i].codigo <= 7))
					mi.datos[i].styleToggle = { 'padding-left': '35px'};
			}
			
			mi.rowCollection = [];
			mi.rowCollection = mi.datos;
			mi.displayedCollection = [].concat(mi.rowCollection);
		}
	})
}])