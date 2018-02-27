angular.module('obligacionesController',[]).controller('obligacionesController',['$rootScope','$scope','$http', 
	function($rootScope,$scope,$http){
	var mi = this;
	
	mi.hoy = moment();
	mi.ano = mi.hoy.year();
	
	mi.tot_dano1 = 0;
	mi.tot_dano2 = 0;
	mi.tot_dano3 = 0;
	mi.tot_dano4 = 0;
	
	mi.grafica_opciones = {
			line : { tension : 0 },
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
					ticks: {
		        	     callback: function (value) {
		        	    	 if (true)
		        	    		 value = value.toFixed(2);
		        	    	 return 'Q '+numeral(value).format(' 0,000.00');
	                   }
					},
					scaleLabel: {
	                    display: true,
	                    labelString: 'Quetzales'
	                }
				}
				],
				xAxes: [{
			    	  scaleLabel: {
	                     display: true,
	                     labelString: mi.etiquetaX
	                   }
			      }
			      ]
			}
		};
	
	
	$http.post('/SObligaciones',
			{
				accion: 'getObligaciones',
				t: new Date().getTime()
			}).then(			
			function(response){
				if (response.data.success){
					mi.dato = [];
					mi.dato = response.data.obligaciones;
					
					mi.rowCollection = [];
					mi.rowCollection = mi.dato;
					
					mi.displayedCollection = [].concat(mi.rowCollection);
					
					if(mi.dato.length > 0){
						for(var i=0; i<mi.dato.length;i++){
							mi.tot_dano1 += mi.dato[i].d2014;
							mi.tot_dano2 += mi.dato[i].d2015;
							mi.tot_dano3 += mi.dato[i].d2016;
							mi.tot_dano4 += mi.dato[i].d2017;
						}
					}
				}
			});
	
}]);