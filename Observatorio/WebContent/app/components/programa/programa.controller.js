angular.module('programaController',[]).controller('programaController', ['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	var mi = this;
	
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth();
	mi.etiquetaX = "";
	mi.labels = [];
	mi.labels2 = [];
	mi.data = [];
	mi.data2 = [];
	mi.decimales = false;
	mi.linealColors = ['#8ecf4c', '#88b4df', '#d92a27'];
	
	mi.entidad = $routeParams.entidad;
	mi.unidadEjecutora = $routeParams.unidadejecutora;
	mi.programa = $routeParams.programa;
	
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	mi.tot_asignado_4 = 0;mi.tot_asignado_3 = 0;mi.tot_asignado_2 = 0;mi.tot_asignado_1 = 0;mi.tot_asignado = 0;
	mi.tot_vigente_4 = 0;mi.tot_vigente_3 = 0;mi.tot_vigente_2 = 0;mi.tot_vigente_1 = 0;mi.tot_vigente = 0;
	mi.tot_ejecutado_4 = 0;mi.tot_ejecutado_3 = 0;mi.tot_ejecutado_2 = 0;mi.tot_ejecutado_1 = 0;mi.tot_ejecutado = 0;
	mi.tot_ep_4 = 0;mi.tot_ep_3 = 0;mi.tot_ep_2 = 0;mi.tot_ep_1 = 0;mi.tot_ep = 0;
	mi.tot_ef_4 = 0;mi.tot_ef_3 = 0;mi.tot_ef_2 = 0;mi.tot_ef_1 = 0;mi.tot_ef = 0;
	
	$http.post('/SPrograma',{
		accion: 'getProgramas',
		entidad: mi.entidad,
		unidadEjecutora: mi.unidadEjecutora,
		programa: mi.programa,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.dato = [];
			mi.dato = response.data.subprogramas;
			
			mi.rowCollection = [];
			mi.rowCollection = mi.dato;
			
			mi.displayedCollection = [].concat(mi.rowCollection);
			
			for(var i=0; i<mi.dato.length; i++){
				mi.tot_asignado_4 += mi.dato[i].asignado4; mi.tot_asignado_3 += mi.dato[i].asignado3; mi.tot_asignado_2 += mi.dato[i].asignado2; mi.tot_asignado_1 += mi.dato[i].asignado1; mi.tot_asignado += mi.dato[i].asignado; 
				mi.tot_vigente_4 += mi.dato[i].vigente4; mi.tot_vigente_3 += mi.dato[i].vigente3; mi.tot_vigente_2 += mi.dato[i].vigente2; 	mi.tot_vigente_1 += mi.dato[i].vigente1; mi.tot_vigente += mi.dato[i].vigente;
				mi.tot_ejecutado_4 += mi.dato[i].ejecutado4; mi.tot_ejecutado_3 += mi.dato[i].ejecutado3; mi.tot_ejecutado_2 += mi.dato[i].ejecutado2; mi.tot_ejecutado_1 += mi.dato[i].ejecutado1; mi.tot_ejecutado += mi.dato[i].ejecutado;
				mi.tot_ep_4 += mi.dato[i].ep4; mi.tot_ep_3 += mi.dato[i].ep3; mi.tot_ep_2 += mi.dato[i].ep2; mi.tot_ep_1 += mi.dato[i].ep1; mi.tot_ep += mi.dato[i].ep; 
				mi.tot_ef_4 += mi.dato[i].ef4; mi.tot_ef_3 += mi.dato[i].ef3; mi.tot_ef_2 += mi.dato[i].ef2; mi.tot_ef_1 += mi.dato[i].ef1; mi.tot_ef += mi.dato[i].ef4;
			}
			
			mi.tot_ep_4 = mi.tot_ep_4 / mi.dato.length; mi.tot_ep_3 = mi.tot_ep_3 / mi.dato.length; mi.tot_ep_2 = mi.tot_ep_2 / mi.dato.length; mi.tot_ep_1 = mi.tot_ep_1 / mi.dato.length; mi.tot_ep = mi.tot_ep / mi.dato.length;
			mi.tot_ef_4 = mi.tot_ef_4 / mi.dato.length; mi.tot_ef_3 = mi.tot_ef_3 / mi.dato.length; mi.tot_ef_2 = mi.tot_ef_2 / mi.dato.length; mi.tot_ef_1 = mi.tot_ef_1 / mi.dato.length; mi.tot_ef = mi.tot_ef / mi.dato.length;
			
			mi.getGraficaSubprograma({subprogramaNombre:'Todos los sub programas', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa});
		}
	})
	
	mi.getGraficaSubprograma = function(row){
		mi.tituloGrafica = row.subprogramaNombre;
		mi.getInfoGraficas(row);
	}
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
		
		$http.post('/SPrograma',{
			accion: 'getInfoMensual',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subPrograma: row.subprograma,
			t: new Date().getTime()
		}).then(function(response){
			if(response.data.success){
				var datoMensual = [];
				datoMensual = response.data.informacionMensual;
				
				mi.series = ['Asignado', 'Vigente', 'Ejecutado'];
				mi.series2 = ['% Financiero' , '% Físico']
				
				mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
				mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Meses";
				
				var cantidadesFin = [];
				var cantidadesFis = [];
				var cantidadesVig = [];
				var cantidadesEje = [];
				var cantidadesAsi = [];
				
				mi.asignado = [];
				mi.datoAcumuladoMensualAsi = [];
				mi.datoAcumuladoMensualVig = [];
				mi.datoAcumuladoMensualEje = [];
				mi.datoAcumuladoMensualFin = [];
				mi.datoAcumuladoMensualFis = [];
				
				mi.datoAcumuladoAnualVig = [];
				mi.datoAcumuladoAnualEje = [];
				mi.datoAcumuladoAnualFin = [];
				mi.datoAcumuladoAnualFis = [];
				mi.datoAcumuladoAnualAsi = [];
				
				var acumuladoFin = 0;
				var acumuladoFis = 0;
				var acumuladoVig = 0;
				var acumuladoEje = 0;
				var acumuladoAsi = 0;
				
				for(var i=0; i<datoMensual.length; i++){
					mi.datoAcumuladoAnualAsi.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: datoMensual[i].mensualidad.asignado});
					for(var j=0; j<12; j++){
						if(datoMensual[i].ejercicio == mi.anio && j==mi.mes)
							break;
						
						if(j==0){
							acumuladoVig = 0;
							acumuladoEje = 0;
							acumuladoFin = 0;
							acumuladoFis = 0;
							acumuladoAsi = datoMensual[i].mensualidad.asignado;
						}else{
							acumuladoVig = mi.datoAcumuladoMensualVig.length != 0 ? mi.datoAcumuladoMensualVig[j-1].p_ejecucion + datoMensual[i].mensualidad.vigente[j] : datoMensual[i].mensualidad.vigente[j];
							acumuladoEje = mi.datoAcumuladoMensualEje.length != 0 ? mi.datoAcumuladoMensualEje[j-1].p_ejecucion + datoMensual[i].mensualidad.ejecutado[j] : datoMensual[i].mensualidad.ejecutado[j];
							acumuladoFin = mi.datoAcumuladoMensualFin.length != 0 ? mi.datoAcumuladoMensualFin[j-1].p_ejecucion + datoMensual[i].mensualidad.p_ejecutado_fin[j] : datoMensual[i].p_ejecutado_fin[j]; 
							acumuladoFis = mi.datoAcumuladoMensualFis.length != 0 ? mi.datoAcumuladoMensualFis[j-1].p_ejecucion + datoMensual[i].mensualidad.p_avance_fis[j] : datoMensual[i].p_avance_fis[j];
							acumuladoAsi = 0;
						}
						
						mi.datoAcumuladoMensualVig.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoVig});
						mi.datoAcumuladoMensualEje.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoEje});
						mi.datoAcumuladoMensualFin.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFin});
						mi.datoAcumuladoMensualFis.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFis});
						mi.datoAcumuladoMensualAsi.push({mes: j+1, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoAsi});
						
						if(j == 11){							
							mi.datoAcumuladoAnualVig.push({ejercicio: datoMensual[i].ejericio, p_ejecucion: acumuladoVig});
							mi.datoAcumuladoAnualEje.push({ejercicio: datoMensual[i].ejericio, p_ejecucion: acumuladoEje});
							mi.datoAcumuladoAnualFin.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFin});
							mi.datoAcumuladoAnualFis.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumuladoFis});
						}	
					}
				}
				
				for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
					mi.labels.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
					mi.labels2.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
					cantidadesAsi.push(Number(mi.datoAcumuladoMensualAsi[i].p_ejecucion).toFixed(2));
					cantidadesVig.push(Number(mi.datoAcumuladoMensualVig[i].p_ejecucion).toFixed(2));
					cantidadesEje.push(Number(mi.datoAcumuladoMensualEje[i].p_ejecucion).toFixed(2));
					cantidadesFin.push(Number(mi.datoAcumuladoMensualFin[i].p_ejecucion*100).toFixed(2));
					cantidadesFis.push(Number(mi.datoAcumuladoMensualFis[i].p_ejecucion*100).toFixed(2));
				}
				
				mi.data.push(cantidadesAsi, cantidadesVig, cantidadesEje);
				mi.data2.push(cantidadesFin, cantidadesFis);
			}
		});
	}
	
	mi.cambioAcumuladoMensual = function(){
		mi.labels = [];
		var cantidadesVig = [];
		var cantidadesEje = [];
		var cantidadesAsi = [];
		
		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
			mi.labels.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
			cantidadesAsi.push(Number(mi.datoAcumuladoMensualAsi[i].p_ejecucion).toFixed(2));
			cantidadesVig.push(Number(mi.datoAcumuladoMensualVig[i].p_ejecucion).toFixed(2));
			cantidadesEje.push(Number(mi.datoAcumuladoMensualEje[i].p_ejecucion).toFixed(2));
		}
		
		mi.data.push(cantidadesAsi, cantidadesVig, cantidadesEje)
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
	}
	
	mi.cambioAcumuladoMensualP = function(){
		mi.labels2 = [];
		var cantidadesFin = [];
		var cantidadesFis = [];
		
		mi.data2 = [];
		
		for(var i=0; i<mi.datoAcumuladoMensualFin.length; i++){
			mi.labels2.push(mi.meses[mi.datoAcumuladoMensualFin[i].mes - 1] + mi.datoAcumuladoMensualFin[i].ejercicio);
			cantidadesFin.push(Number(mi.datoAcumuladoMensualFin[i].p_ejecucion*100).toFixed(2));
			cantidadesFis.push(Number(mi.datoAcumuladoMensualFis[i].p_ejecucion*100).toFixed(2));
		}
		
		mi.data2.push(cantidadesFin, cantidadesFis);
		mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Meses";
	}
	
	mi.cambioAcumuladoAnual = function(){
		mi.labels = [];
		var cantidadesVig = [];
		var cantidadesEje = [];
		var cantidadesAsi = [];

		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoAnualFin.length; i++){
			mi.labels.push(mi.datoAcumuladoAnualFin[i].ejercicio);
			
			cantidadesAsi.push(Number(mi.datoAcumuladoAnualAsi[i].p_ejecucion).toFixed(2));
			cantidadesVig.push(Number(mi.datoAcumuladoAnualVig[i].p_ejecucion).toFixed(2));
			cantidadesEje.push(Number(mi.datoAcumuladoAnualEje[i].p_ejecucion).toFixed(2));			
		}
		
		mi.data.push(cantidadesAsi, cantidadesVig, cantidadesEje);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
	}
	
	mi.cambioAcumuladoAnualP = function(){
		mi.labels2 = [];
		var cantidadesFin = [];
		var cantidadesFis = [];

		mi.data2 = [];
		
		for(var i=0; i<mi.datoAcumuladoAnualFin.length; i++){
			mi.labels2.push(mi.datoAcumuladoAnualFin[i].ejercicio);
					
			cantidadesFin.push(Number(mi.datoAcumuladoAnualFin[i].p_ejecucion*100).toFixed(2));
			cantidadesFis.push(Number(mi.datoAcumuladoAnualFis[i].p_ejecucion*100).toFixed(2));
		}
		
		mi.data2.push(cantidadesFin, cantidadesFis);
		mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Años";
	}
	
	mi.limpiarData = function(){
		mi.labels = [];
		mi.data = [];
		mi.labels2 = [];
		mi.data2 = [];
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
		        	    	 return 'Q' + numeral(value).format(' 0.00')
	                   }
					},
					scaleLabel: {
	                    display: true,
	                    labelString: 'Q Ejecucion'
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
	
	mi.options2 = {
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