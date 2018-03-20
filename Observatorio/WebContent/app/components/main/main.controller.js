var app = angular.module('observatorio', ['ngRoute','chart.js','smart-table','ngUtilidades','loadOnDemand',
	'uiGmapgoogle-maps']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
	$locationProvider.hashPrefix('!');
	   //$locationProvider.html5Mode(true);
	   $routeProvider
	   		/*.when('/main',{
     		templateUrl : '',
     		resolve:{
     			main: function main(){
     				window.location.href = '/main.jsp';
     			}
     		}
     	})*/
		 .when('/obligaciones',{
         	template: '<div load-on-demand="\'obligacionesController\'" class="all_page"></div>'
         })
         .when('/deuda',{
         	template: '<div load-on-demand="\'deudaController\'" class="all_page"></div>'
         })
         .when('/deuda_detalle',{
         	template: '<div load-on-demand="\'deudadetalleController\'" class="all_page"></div>'
         })
         .when('/producto/:tipo_resultado/:entidad/:unidad_ejecutora/:programa/:subprograma/:proyecto/:actividad/:obra/:t',{
         	template: '<div load-on-demand="\'productoController\'" class="all_page"></div>'
         })
         .when('/actividad/:tipo_resultado/:entidad/:unidad_ejecutora/:programa/:subprograma/:proyecto/:t',{
         	template: '<div load-on-demand="\'actividadController\'" class="all_page"></div>'
         })
         .when('/proyecto/:tipo_resultado/:entidad/:unidad_ejecutora/:programa/:subprograma/:t',{
         	template: '<div load-on-demand="\'proyectoController\'" class="all_page"></div>'
         })
         .when('/subprograma/:tipo_resultado/:entidad/:unidad_ejecutora/:programa/:t',{
         	template: '<div load-on-demand="\'subprogramaController\'" class="all_page"></div>'
         })
         .when('/programa/:tipo_resultado/:entidad/:unidad_ejecutora/:t',{
         	template: '<div load-on-demand="\'programaController\'" class="all_page"></div>'
         })
         .when('/unidadejecutora/:tipo_resultado/:entidad/:t',{
         	template: '<div load-on-demand="\'unidadEjecutoraController\'" class="all_page"></div>'
         })
         .when('/entidad/:tipo_resultado',{
         	template: '<div load-on-demand="\'entidadController\'" class="all_page"></div>'
         })
         .when('/hospitales',{
         	template: '<div load-on-demand="\'hospitalesController\'" class="all_page"></div>'
         })
         .when('/centros',{
         	template: '<div load-on-demand="\'centrosController\'" class="all_page"></div>'
         })
         .when('/puestos',{
         	template: '<div load-on-demand="\'puestosController\'" class="all_page"></div>'
         })
         .when("/:redireccion?",{
            	controller:"MainController"
            })
         /*.when('/salir',{
         	templateUrl : '<div></div>',
         	resolve:{
         		logout: function logout($http){
         			$http.post('/SLogout', '').then(function(response){
	        				    if(response.data.success)
	        				    	window.location.href = '/login.jsp';
	        			 	}, function errorCallback(response){
	        			 		
	        			 	}
	        			 );
         			return true;
         		}
         	}
         });*/
 }]);

app.config(['$loadOnDemandProvider', function ($loadOnDemandProvider) {
	   var modules = [
	       {
	    	   name: 'obligacionesController',
	    	   script: '/app/components/obligaciones/obligaciones.controller.js',
	    	   template: '/app/components/obligaciones/obligaciones.jsp'
	       },
	       {
	    	   name: 'deudaController',
	    	   script: '/app/components/deuda/deuda.controller.js',
	    	   template: '/app/components/deuda/deuda.jsp'
	       },
	       {
	    	   name: 'deudadetalleController',
	    	   script: '/app/components/deuda_detalle/deuda_detalle.controller.js',
	    	   template: '/app/components/deuda_detalle/deuda_detalle.jsp'
	       },
	       {
	    	   name: 'productoController',
	    	   script: '/app/components/producto/producto.controller.js',
	    	   template: '/app/components/producto/producto.jsp'
	       },
	       {
	    	   name: 'actividadController',
	    	   script: '/app/components/actividad/actividad.controller.js',
	    	   template: '/app/components/actividad/actividad.jsp'
	       },
	       {
	    	   name: 'programaController',
	    	   script: '/app/components/programa/programa.controller.js',
	    	   template: '/app/components/programa/programa.jsp'
	       },
	       {
	    	   name: 'subprogramaController',
	    	   script: '/app/components/subprograma/subprograma.controller.js',
	    	   template: '/app/components/subprograma/subprograma.jsp'
	       },
	       {
	    	   name: 'unidadEjecutoraController',
	    	   script: '/app/components/unidadejecutora/unidadejecutora.controller.js',
	    	   template: '/app/components/unidadejecutora/unidadejecutora.jsp'
	       },
	       {
	    	   name: 'entidadController',
	    	   script: '/app/components/entidad/entidad.controller.js',
	    	   template: '/app/components/entidad/entidad.jsp'
	       },
	       {
	    	   name: 'proyectoController',
	    	   script: '/app/components/proyecto/proyecto.controller.js',
	    	   template: '/app/components/proyecto/proyecto.jsp'
	       },
	       {
	    	   name: 'hospitalesController',
	    	   script: '/app/components/hospitales/hospitales.controller.js',
	    	   template: '/app/components/hospitales/hospitales.jsp'
	       },
	       {
	    	   name: 'centrosController',
	    	   script: '/app/components/centros/centros.controller.js',
	    	   template: '/app/components/centros/centros.jsp'
	       },
	       {
	    	   name: 'puestosController',
	    	   script: '/app/components/puestos/puestos.controller.js',
	    	   template: '/app/components/puestos/puestos.jsp'
	       }
	   ];
	   $loadOnDemandProvider.config(modules);
}]);

app.config(['uiGmapGoogleMapApiProvider',function(uiGmapGoogleMapApiProvider) {
 uiGmapGoogleMapApiProvider.configure({
     key: 'AIzaSyBPq-t4dJ1GV1kdtXoVZfG7PtfEAHrhr00',
     v: '3.', //defaults to latest 3.X anyhow
     libraries: 'weather,geometry,visualization'
 });
}]);


app.controller('MainController',['$scope','$document','$rootScope','$location','$window','$http',
	   function($scope,$document,$rootScope,$location,$window, $http){
		
		$rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
			if (location.hostname !== "localhost" || location.hostname !== "127.0.0.1"){
				$window.ga('create', 'UA-74443600-3', 'auto');
	    		$window.ga('send', 'pageview', $location.path());
			}
	    });
	}]);