var app = angular.module('nivelesController', []).controller('centrosController',['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	var mi = this;
	
	$http.post('/SSalud',{
		accion: 'getCentros',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.datos = response.data.centros;
			
			
		}
	});
	
}]);