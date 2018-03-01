angular.module('actividadController',[]).controller('actividadController', ['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	var mi = this;
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth();
	mi.etiquetaX = "";
	mi.labels = [];
	mi.data = [];
	mi.decimales = false;
	mi.linealColors = ['#8ecf4c', '#88b4df'];
	
	mi.tot_p_ejecucion_4 = 0;
	mi.tot_p_ejecucion_3 = 0;
	mi.tot_p_ejecucion_2 = 0;
	mi.tot_p_ejecucion_1 = 0;
	mi.tot_p_ejecucion = 0;
	mi.tot_p_avance_4 = 0;
	mi.tot_p_avance_3 = 0;
	mi.tot_p_avance_2 = 0;
	mi.tot_p_avance_1 = 0;
	mi.tot_p_avance = 0;
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	mi.entidad = $routeParams.entidad;
	mi.unidadEjecutora = $routeParams.unidadejecutora;
	mi.programa = $routeParams.programa;
	mi.subPrograma = $routeParams.subprograma;
	
	$http.post('/SActividad',{
		accion: 'getActividades',
		entidad: mi.entidad,
		unidadEjecutora: mi.unidadEjecutora,
		programa: mi.programa,
		subPrograma: mi.subPrograma,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.actividades){
			mi.dato = [];
			mi.dato = response.data.actividades;
			
			mi.rowCollection = [];
			mi.rowCollection = mi.dato;
			
			mi.displayedCollection = [].concat(mi.rowCollection);
			
			for(var i=0; i<mi.dato.length; i++){
				mi.tot_p_ejecucion_4 += mi.dato[i].p_ejecucion_4;
				mi.tot_p_ejecucion_3 += mi.dato[i].p_ejecucion_3;
				mi.tot_p_ejecucion_2 += mi.dato[i].p_ejecucion_2;
				mi.tot_p_ejecucion_1 += mi.dato[i].p_ejecucion_1;
				mi.tot_p_ejecucion += mi.dato[i].p_ejecucion;
				mi.tot_p_avance_4 += mi.dato[i].p_avance_4;
				mi.tot_p_avance_3 += mi.dato[i].p_avance_3;
				mi.tot_p_avance_2 += mi.dato[i].p_avance_2;
				mi.tot_p_avance_1 += mi.dato[i].p_avance_1;
				mi.tot_p_avance += mi.dato[i].p_avance;
			}
			
			mi.tot_p_ejecucion_4 = (mi.tot_p_ejecucion_4 / mi.dato.length).toFixed(2);
			mi.tot_p_ejecucion_3 = (mi.tot_p_ejecucion_3 / mi.dato.length).toFixed(2);
			mi.tot_p_ejecucion_2 = (mi.tot_p_ejecucion_2 / mi.dato.length).toFixed(2);
			mi.tot_p_ejecucion_1 = (mi.tot_p_ejecucion_1 / mi.dato.length).toFixed(2);
			mi.tot_p_ejecucion = (mi.tot_p_ejecucion / mi.dato.length).toFixed(2);
			mi.tot_p_avance_4 = (mi.tot_p_avance_4 / mi.dato.length).toFixed(2);
			mi.tot_p_avance_3 = (mi.tot_p_avance_3 / mi.dato.length).toFixed(2);
			mi.tot_p_avance_2 = (mi.tot_p_avance_2 / mi.dato.length).toFixed(2);
			mi.tot_p_avance_1 = (mi.tot_p_avance_1 / mi.dato.length).toFixed(2);
			mi.tot_p_avance = (mi.tot_p_avance / mi.dato.length).toFixed(2);
			
			mi.getGraficaActividad({descripcionActividad:'Todos los productos y sub productos', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa, subPrograma: mi.subPrograma});
		}
	})
	
	mi.getGraficaActividad = function(row){
		mi.tituloGrafica = row.descripcionActividad;
		mi.getInfoGraficas(row);
	}
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
		
		$http.post('/SActividad',{
			accion: 'getInfoMensual',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subPrograma: mi.subPrograma,
			actividad: row.id,
			t: new Date().getTime()
		}).then(function(response){
			if(response.data.success){
				var datoMensual = [];
				datoMensual = response.data.informacionMensual;
				
				mi.series = ['% Financiera', '% Fisica'];
				mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
				
				var cantidadesFin = [];
				var cantidadesFis = [];
				mi.datoAcumuladoMensualFin = [];
				mi.datoAcumuladoMensualFis = [];
				
				mi.datoAcumuladoAnualFin = [];
				mi.datoAcumuladoAnualFis = [];
				
				var acumuladoFin = 0;
				var acumuladoFis = 0;
				for(var i=0; i< datoMensual.length; i++){
					for(var j=0; j<12; j++){
						if(datoMensual[i].ejercicio==mi.anio && j==mi.mes)
							break;
						
						if(j==0){
							acumuladoFin = 0;
							acumuladoFis = 0;
						}else{
							acumuladoFin = mi.datoAcumuladoMensualFin.length != 0 ? mi.datoAcumuladoMensualFin[j-1].p_ejecucion + datoMensual[i].ejecucionFisicaFinanciera.financiera.mes[j] : datoMensual[i].financiera.mes[j]; 
							acumuladoFis = mi.datoAcumuladoMensualFis.length != 0 ? mi.datoAcumuladoMensualFis[j-1].p_ejecucion + datoMensual[i].ejecucionFisicaFinanciera.fisica.mes[j] : datoMensual[i].fisica.mes[j];
						}
						
						mi.datoAcumuladoMensualFin.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFin});
						mi.datoAcumuladoMensualFis.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFis});
						
						if(j == 11){
							mi.datoAcumuladoAnualFin.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFin});
							mi.datoAcumuladoAnualFis.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFis});
						}							
					}
				}
				
				for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
					mi.labels.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
					cantidadesFin.push(Number(mi.datoAcumuladoMensualFin[i].p_ejecucion*100).toFixed(2));
					cantidadesFis.push(Number(mi.datoAcumuladoMensualFis[i].p_ejecucion*100).toFixed(2));
					
				}
				
				mi.data.push(cantidadesFin, cantidadesFis);
			}
		})
	}
	
	mi.cambioAcumuladoMensual = function(){
		mi.labels = [];
		var cantidadesFin = [];
		var cantidadesFis = [];
		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
			mi.labels.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
			cantidadesFin.push(Number(mi.datoAcumuladoMensualFin[i].p_ejecucion*100).toFixed(2));
			cantidadesFis.push(Number(mi.datoAcumuladoMensualFis[i].p_ejecucion*100).toFixed(2));
		}
		
		mi.data.push(cantidadesFin, cantidadesFis);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
	}
	
	mi.cambioAcumuladoAnual = function(){
		mi.labels = [];
		var cantidadesFin = [];
		var cantidadesFis = [];
		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoAnualFin.length; i++){
			mi.labels.push(mi.datoAcumuladoAnualFin[i].ejercicio);
			cantidadesFin.push(Number(mi.datoAcumuladoAnualFin[i].p_ejecucion*100).toFixed(2));
			cantidadesFis.push(Number(mi.datoAcumuladoAnualFis[i].p_ejecucion*100).toFixed(2));
		}
		
		mi.data.push(cantidadesFin, cantidadesFis);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
	}
	
	mi.limpiarData = function(){
		mi.labels = [];
		mi.data = [];
	}
	
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
		        	    	 return numeral(value).format(' 0.00')+'%'
	                   }
					},
					scaleLabel: {
	                    display: true,
	                    labelString: '% Ejecucion'
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
}]);
