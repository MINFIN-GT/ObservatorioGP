angular.module('deudaController',[]).controller('deudaController',['$rootScope','$scope','$http','$window', 
	function($rootScope,$scope,$http,$window){
	var mi = this;
	
	mi.hoy = moment();
	mi.ano = mi.hoy.year();
	
	mi.tot = [];
	
	mi.grafica_data = [];
	mi.grafica_series = ['Asignado','Vigente','Ejecutado'];
	mi.grafica_labels = [mi.ano-4, mi.ano-3, mi.ano-2, mi.ano-1, mi.ano];
	mi.grafica_titulo = "Presupuesto de Deuda Pública";
	
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
	
	
	$http.post('/SDeuda',
			{
				accion: 'getDeudaDetalle',
				t: new Date().getTime()
			}).then(			
			function(response){
				if (response.data.success){
					mi.dato = [];
					mi.dato = response.data.deuda;
					
					mi.rowCollection = [];
					mi.rowCollection = mi.dato;
					
					mi.displayedCollection = [].concat(mi.rowCollection);
					
					if(mi.dato.length > 0){
						mi.tot = [[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]];
						for(var i=0; i<mi.dato.length;i++){
							mi.tot[0][0] += mi.dato[i].ejercicio_data[0][0];
							mi.tot[0][1] += mi.dato[i].ejercicio_data[0][12];
							mi.tot[0][2] += mi.dato[i].ejercicio_data[0][24];
							mi.tot[1][0] += mi.dato[i].ejercicio_data[1][0];
							mi.tot[1][1] += mi.dato[i].ejercicio_data[1][12];
							mi.tot[1][2] += mi.dato[i].ejercicio_data[1][24];
							mi.tot[2][0] += mi.dato[i].ejercicio_data[2][0];
							mi.tot[2][1] += mi.dato[i].ejercicio_data[2][12];
							mi.tot[2][2] += mi.dato[i].ejercicio_data[2][24];
							mi.tot[3][0] += mi.dato[i].ejercicio_data[3][0];
							mi.tot[3][1] += mi.dato[i].ejercicio_data[3][12];
							mi.tot[3][2] += mi.dato[i].ejercicio_data[3][24];
							mi.tot[4][0] += mi.dato[i].ejercicio_data[4][0];
							mi.tot[4][1] += mi.dato[i].ejercicio_data[4][12];
							mi.tot[4][2] += mi.dato[i].ejercicio_data[4][24];
						}
						mi.grafia_data=[];
						for(var i=0; i<3; i++)
							mi.grafica_data.push([mi.tot[0][i]/1000000,mi.tot[1][i]/1000000, mi.tot[2][i]/1000000, mi.tot[3][i]/1000000, mi.tot[4][i]/1000000]);
					}
				}
			});
	
	mi.getGraficaEntidad =  function(row){
		mi.grafica_data = [];
		if(row.isSelected){
			mi.grafica_titulo = row.entidad_nombre;
			mi.grafica_data.push([row.ejercicio_data[0][0]/1000000,row.ejercicio_data[1][0]/1000000, row.ejercicio_data[2][0]/1000000, row.ejercicio_data[3][0]/1000000, row.ejercicio_data[4][0]/1000000]);
			mi.grafica_data.push([row.ejercicio_data[0][12]/1000000,row.ejercicio_data[1][12]/1000000, row.ejercicio_data[2][12]/1000000, row.ejercicio_data[3][12]/1000000, row.ejercicio_data[4][12]/1000000]);
			mi.grafica_data.push([row.ejercicio_data[0][24]/1000000,row.ejercicio_data[1][24]/1000000, row.ejercicio_data[2][24]/1000000, row.ejercicio_data[3][24]/1000000, row.ejercicio_data[4][24]/1000000]);
		}
		else{
			mi.grafica_titulo = "Presupuesto de Deuda Pública - Detalle";
			for(var i=0; i<3; i++)
				mi.grafica_data.push([mi.tot[0][i]/1000000,mi.tot[1][i]/1000000, mi.tot[2][i]/1000000, mi.tot[3][i]/1000000, mi.tot[4][i]/1000000]);
		}
		
	}
	
}]);