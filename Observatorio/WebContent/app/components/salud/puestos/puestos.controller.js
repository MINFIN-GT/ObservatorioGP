var app = angular.module('puestosController', []).controller('puestosController',['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	var mi = this;
	var fecha = new Date();
	mi.decimales = true;
	mi.linealColors = ['#8ecf4c', '#88b4df', '#d92a27'];
	
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
		accion: 'getPuestos',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.datos = response.data.puestos;
			
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
			
			mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
			mi.series = ['Ejecutado' , 'Vigente'];
			mi.labels = [mi.anio - 4, mi.anio - 3, mi.anio - 2, mi.anio - 1, mi.anio];
			
			var ejecucion = [];
			ejecucion.push(mi.tot_ejecutado_4);
			ejecucion.push(mi.tot_ejecutado_3);
			ejecucion.push(mi.tot_ejecutado_2);
			ejecucion.push(mi.tot_ejecutado_1);
			ejecucion.push(mi.tot_ejecutado);
			
			var vigente = [];
			vigente.push(mi.tot_vigente_4);
			vigente.push(mi.tot_vigente_3);
			vigente.push(mi.tot_vigente_2);
			vigente.push(mi.tot_vigente_1);
			vigente.push(mi.tot_vigente);
			
			mi.data = [];
			mi.data.push(ejecucion, vigente);
		}
	})
	
	mi.options = {
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
				id: 'y-axis-1',
				type: 'linear',
				display: true,
				position: 'left',
				ticks: {
	        	     callback: function (value) {
	        	    	 if (true)
	        	    		 value = value.toFixed(2);
	        	    	 return 'Q ' + numeral(value).format('0,000.00');
                   }
				},
				scaleLabel: {
                    display: true,
                    labelString: 'Millones de Quetzales (Q)'
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
		},
		tooltips: {
           mode: 'label',
           label: 'mylabel',
           callbacks: {
               label: function(tooltipItem, data) {
            	   return 'Q '+ numeral(tooltipItem.yLabel).format('0,000.00');
               } 
           }
        }
	};
}])