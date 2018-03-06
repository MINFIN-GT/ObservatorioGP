angular.module('actividadController',[]).controller('actividadController', ['$rootScope','$scope','$http','$routeParams', '$window',
	function($rootScope,$scope,$http,$routeParams, $window){
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
	mi.tipo_resultado = $routeParams.tipo_resultado;
	
	$http.post('/SActividad',{
		accion: 'getActividades',
		entidad: mi.entidad,
		unidadEjecutora: mi.unidadEjecutora,
		programa: mi.programa,
		subPrograma: mi.subPrograma,
		tipo_resultado: mi.tipo_resultado,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.actividades){
			mi.dato = [];
			mi.dato = response.data.actividades;
			
			mi.rowCollection = [];
			mi.rowCollection = mi.dato;
				
			mi.displayedCollection = [].concat(mi.rowCollection);
			
			/*
			for(var i=0; i<mi.dato.length; i++){
				for(var h=0; h<mi.dato[i].ejercicios.length;h++){
					for(var j=0; j<12; j++){
						if(mi.dato[i].ejercicios[h] == mi.anio && j==mi.mes)
							break;
						mi.labels.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
					}
				}
			}
			
			var ejecutadoMensual = [];
			for(var i=0; i<mi.dato.length;i++){
				for(var h=0; h<mi.dato[i].ejercicio_data.length; h++){
					for(var j=0; j<12; j++){
						ejecutadoMensual.push(mi.dato.ejercicio_data[j]);
					}
				}
			}
			
			mi.data.push(ejecutadoMensual);*/
//			for(var i=0; i<mi.dato.length; i++){
//				mi.tot_p_ejecucion_4 += mi.dato[i].p_ejecucion_4;
//				mi.tot_p_ejecucion_3 += mi.dato[i].p_ejecucion_3;
//				mi.tot_p_ejecucion_2 += mi.dato[i].p_ejecucion_2;
//				mi.tot_p_ejecucion_1 += mi.dato[i].p_ejecucion_1;
//				mi.tot_p_ejecucion += mi.dato[i].p_ejecucion;
//				mi.tot_p_avance_4 += mi.dato[i].p_avance_4;
//				mi.tot_p_avance_3 += mi.dato[i].p_avance_3;
//				mi.tot_p_avance_2 += mi.dato[i].p_avance_2;
//				mi.tot_p_avance_1 += mi.dato[i].p_avance_1;
//				mi.tot_p_avance += mi.dato[i].p_avance;
//			}
//			
//			mi.tot_p_ejecucion_4 = (mi.tot_p_ejecucion_4 / mi.dato.length).toFixed(2);
//			mi.tot_p_ejecucion_3 = (mi.tot_p_ejecucion_3 / mi.dato.length).toFixed(2);
//			mi.tot_p_ejecucion_2 = (mi.tot_p_ejecucion_2 / mi.dato.length).toFixed(2);
//			mi.tot_p_ejecucion_1 = (mi.tot_p_ejecucion_1 / mi.dato.length).toFixed(2);
//			mi.tot_p_ejecucion = (mi.tot_p_ejecucion / mi.dato.length).toFixed(2);
//			mi.tot_p_avance_4 = (mi.tot_p_avance_4 / mi.dato.length).toFixed(2);
//			mi.tot_p_avance_3 = (mi.tot_p_avance_3 / mi.dato.length).toFixed(2);
//			mi.tot_p_avance_2 = (mi.tot_p_avance_2 / mi.dato.length).toFixed(2);
//			mi.tot_p_avance_1 = (mi.tot_p_avance_1 / mi.dato.length).toFixed(2);
//			mi.tot_p_avance = (mi.tot_p_avance / mi.dato.length).toFixed(2);
//			
//			mi.getGraficaActividad({descripcionActividad:'Todos los productos y sub productos', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa, subPrograma: mi.subPrograma});
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
				var datoMensualFisico = [];
				var datoMensualFinanciero = [];
				datoMensualFisico = response.data.informacionFisicaMensual;
				datoMensualFinanciero = response.data.informacionFinancieraMensual;
				
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
				
				for(var i=0; i< datoMensualFisico.length; i++){
					if(datoMensualFisico[i].ejercicio==mi.anio && datoMensualFisico[i].mes==mi.mes)
						break;
					
					mi.datoAcumuladoMensualFis.push({mes: datoMensualFisico[i].mes, ejercicio: datoMensualFisico[i].ejercicio, p_ejecucion: datoMensualFisico[i].p_ejecucion});
					
					if(datoMensualFisico[i].mes == 11){
						mi.datoAcumuladoAnualFis.push({ejercicio: datoMensualFisico[i].ejercicio, p_ejecucion: datoMensualFisico[i].p_ejecucion});
					}
				}
				
				for(var i=0; i<datoMensualFinanciero.length; i++){
					for(var j=0; j<12;j++){
						if(datoMensualFinanciero[i].ejercicio==mi.anio && j==mi.mes)
							break;
						
						mi.datoAcumuladoMensualFin.push({mes: j+1, ejercicio: datoMensualFinanciero[i].ejercicio, p_ejecucion: datoMensualFinanciero[i].mes[j]});
						
						if(j == 11){
							mi.datoAcumuladoAnualFin.push({ejercicio: datoMensualFinanciero[i].ejercicio, p_ejecucion: datoMensualFinanciero[i].mes[j]});
						}
					}
				}
				
				for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
					mi.labels.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
					cantidadesFin.push(Number(mi.datoAcumuladoMensualFin[i].p_ejecucion*100).toFixed(2));					
				}
				
				for(var i=0; i<mi.datoAcumuladoMensualFis.length; i++){
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
		}
		
		for(var i=0; i<mi.datoAcumuladoMensualFis.length; i++){
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
		}
		
		for(var i=0; i<mi.datoAcumuladoAnualFis.length; i++){
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
	
		mi.irProducto = function(actividad_id){
			window.location = "main.jsp#!/producto/" + mi.tipo_resultado + "/" + mi.entidad + "/" + mi.unidadEjecutora + "/" + mi.programa + "/" + mi.subPrograma + "/" +  actividad_id;
		}
}]);
