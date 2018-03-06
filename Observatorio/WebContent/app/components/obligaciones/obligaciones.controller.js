angular.module('obligacionesController',[]).controller('obligacionesController',['$rootScope','$scope','$http','$filter',
	function($rootScope,$scope,$http,$filter){
	var mi = this;
	
	mi.hoy = moment();
	mi.ano = mi.hoy.year();
	
	mi.tot_dano1 = 0;
	mi.tot_dano2 = 0;
	mi.tot_dano3 = 0;
	mi.tot_dano4 = 0;
	
	mi.grafica_data = [];
	mi.grafica_series = ['Aporte'];
	mi.grafica_labels = [mi.ano-4, mi.ano-3, mi.ano-2, mi.ano-1];
	mi.grafica_titulo = "Todas la entidades";
	
	$rootScope.page_title = 'Obligaciones del Estado a cargo del tesoro'
	
	mi.grafica_opciones = {
			elements: {
		        line: {
		            tension: 0
		        },
		        point:{
		        	radius: 5
		        }
		    },
			legend: {
				display: true,
				position: 'bottom',
			},
			scales: {
				yAxes: [
				{
					type: 'linear',
					display: true,
					position: 'left',
					ticks: {
		        	     callback: function (value) {
		        	    	 if (true)
		        	    		 value = value.toFixed(2);
		        	    	 return 'Q '+numeral(value).format('0,000.00');
	                   }
					},
					scaleLabel: {
	                    display: true,
	                    labelString: 'Millones de quetzales'
	                }
				}
				],
				xAxes: [{
			    	  	scaleLabel: {
	                     display: true,
	                     labelString: 'Años'
	                   }
			      	}
			      ]
			},
			tooltips: {
		           mode: 'label',
		           label: 'mylabel',
		           callbacks: {
		               label: function(tooltipItem, data) {
		                   return 'Q ' + numeral(tooltipItem.yLabel).format('0,000.00'); 
		               } 
		           }
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
						
						var serie = [mi.tot_dano1/1000000, mi.tot_dano2/1000000, mi.tot_dano3/1000000, mi.tot_dano4/1000000];
						mi.grafica_data.push(serie);
					}
				}
			});
	
	mi.getGraficaEntidad =  function(row){
		mi.grafica_data = [];
		if(row.isSelected){
			mi.grafica_titulo = row.entidad_nombre;
			var serie = [row.d2014/1000000,row.d2015/1000000,row.d2016/1000000,row.d2017/1000000];
			mi.grafica_data.push(serie);
		}
		else{
			var serie = [mi.tot_dano1/1000000, mi.tot_dano2/1000000, mi.tot_dano3/1000000, mi.tot_dano4/1000000];
			mi.grafica_data.push(serie);
			mi.grafica_titulo = "Todas la entidades";
		}
	}
	
}]);