var app = angular.module('historiaController',[]).controller('historiaController',['$rootScope','$scope','$http','$routeParams','$timeout','$uibModal',
	function($rootScope,$scope,$http,$routeParams, $timeout, $uibModal){
	var mi = this;
	mi.anioInicial = 2000;
	mi.anioFinal = 2018;
	mi.anio = mi.anioFinal;
	mi.seconds = 0;
	mi.tiempo;
	mi.icono = "glyphicon glyphicon-play";
	mi.data = [];
	mi.pause = true;
	
	$http.post('/SSalud',{
		accion: 'getHistoria',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.datos = response.data.historia;
			mi.data = mi.datos[mi.datos.length-1].data_ejercicio;
		}
	});
    
    mi.addBubble = function(){
    	mi.data = mi.datos[mi.seconds].data_ejercicio;
    }
    
    mi.startTime = function(){
    	mi.pause = false;
    	
    	if(mi.seconds==0){
    		mi.anio=mi.anioInicial;
    		mi.icono = "glyphicon glyphicon-pause";
    	}
    	
    	if(mi.pause==false)
        	mi.icono = "glyphicon glyphicon-pause";
    	if(mi.anio == mi.anioFinal){  
    		$timeout.cancel(mi.tiempo);
    		mi.seconds = 0;
    		mi.icono = "glyphicon glyphicon-play";
    		mi.pause = true;
    	}else{
    		mi.tiempo = $timeout(mi.startTime, 1000);
    		mi.addBubble();
    		mi.anio++;
    		mi.seconds++;
    	}
    }
    
    mi.stopTime = function(){
        $timeout.cancel(mi.tiempo);
        mi.pause = true;
        mi.icono = "glyphicon glyphicon-play";
    };
    
    mi.trackBubble = function(id_bubble){
    	alert('Bubble click ' + id_bubble);
    }
    
    mi.options = {
		onClick: function(e) {
	        var element = this.getElementAtEvent(e);
	        // If you click on at least 1 element ...
	        if (element.length > 0) {
	            // Logs it
	            //console.log(element[0]);
	            // Here we get the data linked to the clicked bubble ...
	            var datasetLabel = this.config.data.datasets[element[0]._datasetIndex].label;
	            // data gives you `x`, `y` and `r` values
	            var data = this.config.data.datasets[element[0]._datasetIndex].data[element[0]._index];
	            mi.trackBubble(element[0]._datasetIndex);
	        }
	    },
	    tooltips: {
	    	callbacks: {
	            label: function(t, d) {
	               return d.datasets[t.datasetIndex].data[0].label + 
	                      ': (Gasto:' + t.xLabel + ', Otros:' + t.yLabel + ', Poblacion:' + d.datasets[t.datasetIndex].data[0].r + ')';
	            }
	    	},
	    	mode: 'single'
	    }
    }
    
    mi.opciones1 = function(){
    	var modalInstance = $uibModal.open({
			animation : 'true',
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'opciones1.jsp',
			controller : 'modalAvance',
			controllerAs : 'controller',
			backdrop : 'static',
			size : 'md',
			resolve : {
				
			}
		});
    	
    	modalInstance.result.then(function(itemSeleccionado) {
			
		});
    }
    
    mi.opciones2 = function(){
    	var modalInstance = $uibModal.open({
			animation : 'true',
			ariaLabelledBy : 'modal-title',
			ariaDescribedBy : 'modal-body',
			templateUrl : 'opciones1.jsp',
			controller : 'modalAvance',
			controllerAs : 'controller',
			backdrop : 'static',
			size : 'md',
			resolve : {
				
			}
		});
    	
    	modalInstance.result.then(function(itemSeleccionado) {
			
		});
    }
}]);

app.controller('modalAvance', [ '$uibModalInstance', '$scope', '$http', '$rootScope', modalAvance ]);
function modalAvance($uibModalInstance, $scope, $http, $interval,i18nService, $rootScope) {
	var mi = this;	
	
	mi.cerrar = function() {
		$uibModalInstance.dismiss('cancel');
	};
}