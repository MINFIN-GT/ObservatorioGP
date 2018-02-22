var app = angular.module('main', ['chart.js','smart-table','ui.grid','ui.grid.pinning']);
app.controller('mainController',['$rootScope','$scope','$http', function($rootScope,$scope,$http){
	var mi = this;
	mi.etiqueta = 'Bienvenido';
	
	$http.post('/STest',{accion: 'getData'}).then(
		function(response){
			if (response.data.success){
				mi.dato = [];
				mi.dato = response.data.valores;
				mi.gridOptions.data = mi.dato;
				
				mi.rowCollection = [];
				mi.rowCollection = mi.dato;
				
				mi.displayedCollection = [].concat(mi.rowCollection);
				
				mi.labels = [];
				mi.series = ['Cantidad de personas'];
				
				mi.data = [];
				mi.cantidades = [];
				mi.totales = 0;
				for(var i=0; i<mi.dato.length; i++){
					mi.cantidades.push(mi.dato[i].cantidad);
					mi.labels.push(mi.dato[i].lugar);
					mi.totales += mi.dato[i].cantidad;
				}
				
				mi.data.push(mi.cantidades);
			}
		});
	
	mi.options = {
		legend: {
			display: true,
			position: 'bottom',
		},
		scales: {
			yAxes: [
			{
				id: 'y-axis-1',
				type: 'linear',
				display: true,
				position: 'left',
				scaleLabel: {
                    display: true,
                    labelString: 'Cantidad'
                }
			}
			],
			xAxes: [{
		    	  scaleLabel: {
                     display: true,
                     labelString: 'Municipios'
                   }
		      }
		      ]
		}
	};
	
	mi.gridOptions = {
		enableFiltering: true,
		enablePinning: true,
	    showColumnFooter: true,
		columnDefs : [
			{ name: 'lugar', displayName: 'Lugares', cellClass: 'grid-align-right'},
		    { name: 'cantidad', displayName: 'Cantidad de personas', type: 'number', cellClass: 'grid-align-left' },
			{ name: 'zona', displayName: 'Zonas', cellClass: 'grid-align-right'}
		]
	};	
}])

app.directive('stFilteredCollection', function () {
	  return {
	    require: '^stTable',
	    link: function (scope, element, attr, ctrl) {
	      scope.$watch(ctrl.getFilteredCollection, function(val) {
	        scope.filteredCollection = val;
	        
	        scope.ctrl.totales = 0;
	        
	        for(x in val){
	        	scope.ctrl.totales = scope.ctrl.totales + val[x].cantidad;
	        }
	      })
	    }
	  }
	});