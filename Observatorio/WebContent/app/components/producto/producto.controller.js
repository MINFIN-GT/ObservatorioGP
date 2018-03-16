angular.module('productoController', []).controller('productoController',['$rootScope','$scope','$http','$routeParams', '$window',
	function($rootScope,$scope,$http,$routeParams, $window){
	var mi = this;
	
	$rootScope.page_title = 'Presupuesto por Resultados [Metas]';
	
	mi.arregloSubtitulo = JSON.parse($window.localStorage.getItem("\"" + $routeParams.t + "\""));
	if(mi.arregloSubtitulo != null)
		mi.subtitulo = mi.arregloSubtitulo[0] + " / " + mi.arregloSubtitulo[1] + " / " + mi.arregloSubtitulo[2] + " / " + mi.arregloSubtitulo[3];
		
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth() + 1;
	mi.etiquetaX = "";
	mi.labels = [];
	mi.data = [];
	mi.decimales = false;
	
	mi.tot_p_avance_4 = 0;
	mi.tot_p_avance_3 = 0;
	mi.tot_p_avance_2 = 0;
	mi.tot_p_avance_1 = 0;
	mi.tot_p_avance = 0;
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	mi.entidad = $routeParams.entidad;
	mi.programa = $routeParams.programa;
	mi.subPrograma = $routeParams.subprograma;
	mi.actividad = $routeParams.actividad;
	mi.tipo_resultado = $routeParams.tipo_resultado;
	
	$http.post('/SProducto',
		{
			accion: 'getEjecucionFisica',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subPrograma: mi.subPrograma,
			actividad: mi.actividad,
			tipo_resultado: mi.tipo_resultado,
			t: new Date().getTime()
		}).then(			
		function(response){
			if (response.data.success){
				mi.dato = [];
				mi.dato = response.data.productos;
				
				mi.rowCollection = [];
				mi.rowCollection = mi.dato;
				
				mi.displayedCollection = [].concat(mi.rowCollection);
				
				if(mi.dato.length > 0){
					mi.tipoDatos = 1;
					mi.getGraficaGeneral(mi.dato);
				}
			}
		});
	
	
	mi.getGraficaGeneral = function(datos){
		mi.series = ['% Físico']
		
		mi.labels = [];
		
		mi.mensualPFisico = new Array(60).fill(0);
		mi.mensualPFisicoAcum = new Array(60).fill(0);
		mi.anualPFisico = new Array(5).fill(0);
		
		var mAEjecu = new Array(60).fill(0);
		var mAMod = new Array(60).fill(0);
		
		var acumuladoCant = 0;
		var acumuladoEje = 0;
		var acumuladoMod = 0;
		
		if(datos.length > 0){
			for(var i=0; i<datos.length; i++){//row
				acumuladoEje = 0;
				acumuladoMod = 0;
				for(var j=0; j<datos[i].ejercicios.length; j++){//años
					var pos = datos[i].ejercicios[j] - mi.anio + 4;
					for(var h=0; h<datos[i].ejercicio_data[pos].length; h++){
						if((h>=0) && (h<=11)){ //porcentaje fisico
							if(pos==0 && h==11)
								mi.tot_p_avance_4 += datos[i].ejercicio_data[pos][11];
							if(pos==1 && h==11)
								mi.tot_p_avance_3 += datos[i].ejercicio_data[pos][11];
							if(pos==2 && h==11)
								mi.tot_p_avance_2 += datos[i].ejercicio_data[pos][11];
							if(pos==3 && h==11)
								mi.tot_p_avance_1 += datos[i].ejercicio_data[pos][11];
							if(pos==4 && h==11)
								mi.tot_p_avance += datos[i].ejercicio_data[pos][11];
							
							var posarr = pos==0 ? h : pos==1 ? (h-12+12) : pos==2 ? (h-12+24) : pos==3 ? (h-12+36) : pos==4 ? (h-12+48) : 0;
							mi.mensualPFisico[posarr] += datos[i].ejercicio_data[pos][h];
							
							if(h==11){
								posarr = pos==0 ? 0 : pos==1 ? 1 : pos==2 ? 2 : pos==3 ? 3 : pos==4 ? 4 : 0;
								mi.anualPFisico[posarr] += datos[i].ejercicio_data[pos][h];								
							}
						}else if((h>=13) && (h<=24)){
							var posarr = pos==0 ? (h-13) : pos==1 ? (h-13+12) : pos==2 ? (h-13+24) : pos==3 ? (h-13+36) : pos==4 ? (h-13+48) : 0;
							
							acumuladoEje += datos[i].ejercicio_data[pos][h];
							acumuladoMod += datos[i].ejercicio_data[pos][h+12];
							
							if(h==13){
								acumuladoCant += datos[i].ejercicio_data[pos][12];
								if(pos>0){
									acumuladoEje += datos[i].ejercicio_data[pos-1][24];
									acumuladoMod += datos[i].ejercicio_data[pos-1][36];
								}
							}
							
							var p_ejecucion = acumuladoEje > 0 ? (acumuladoEje / ((acumuladoMod + acumuladoCant) > 0 ? acumuladoMod + acumuladoCant : 1)) : 0;
							mi.mensualPFisicoAcum[posarr] += p_ejecucion;
						}
					}
				}
			}
			
			mi.tot_p_avance_4 = (mi.tot_p_avance_4 / mi.dato.length) * 100;
			mi.tot_p_avance_3 = (mi.tot_p_avance_3 / mi.dato.length) * 100;
			mi.tot_p_avance_2 = (mi.tot_p_avance_2 / mi.dato.length) * 100;
			mi.tot_p_avance_1 = (mi.tot_p_avance_1 / mi.dato.length) * 100;
			mi.tot_p_avance = (mi.tot_p_avance / mi.dato.length) * 100;
			
			for(var i=0; i<1; i++){
				for(var h=0; h<mi.dato[i].ejercicios.length;h++){
					for(var j=0; j<12; j++){
						if(mi.dato[i].ejercicios[h] == mi.anio && j==mi.mes)
							break;
						mi.labels.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
					}
				}
			}
			
			for(var i=0; i<mi.mensualPFisico.length;i++){
				mi.mensualPFisico[i] = (mi.mensualPFisico[i] / 5) * 100;
			}
			
			for(var i=0; i<mi.anualPFisico.length;i++){
				mi.anualPFisico[i] = (mi.anualPFisico[i] / mi.dato.length) * 100;
			}
			
			for(var i=0; i<mi.mensualPFisicoAcum.length;i++){
				mi.mensualPFisicoAcum[i] = (mi.mensualPFisicoAcum[i] / 5);
			}
			
			mi.tipoDatos = 1;
			mi.data.push(mi.mensualPFisico);
			
			mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
		}
	}
	
	mi.getGraficaIndividual = function(row){
		mi.labels = [];
		
		mi.data = [];
		mi.mensualPFisico = [];		
		mi.anualPFisico = [];
		
		for(var j=0; j<row.ejercicios.length; j++){
			var pos = row.ejercicios[j] - mi.anio + 4;
			for(var h=0; h<row.ejercicio_data[pos].length; h++){
				if((h>=0) && (h<=11)){ //porcentaje fisico
					mi.mensualPFisico.push(row.ejercicio_data[pos][h] * 100);
					
					if(h==11)
						mi.anualPFisico.push(row.ejercicio_data[pos][h] * 100);
				}
			}
		}
		
		for(var i=0; i<1; i++){
			for(var h=0; h<row.ejercicios.length;h++){
				for(var j=0; j<12; j++){
					if(row.ejercicios[h] == mi.anio && j==mi.mes)
						break;
					mi.labels.push(mi.meses[j] + row.ejercicios[h]);
				}
			}
		}
		
		mi.tipoDatos = 2;
		mi.data.push(mi.mensualPFisico);
	}
	
	mi.cambioMensualP = function(){
		mi.labels = [];
		mi.data = [];

		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				for(var j=0; j<12; j++){
					if(mi.dato[i].ejercicios[h] == mi.anio && j==mi.mes)
						break;
					mi.labels.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
				}
			}
		}
		
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
		mi.data.push(mi.mensualPFisico);
	}
	
	mi.cambioAnualP = function(){
		mi.labels = [];
		mi.data = [];

		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				mi.labels.push(mi.dato[i].ejercicios[h]);
			}
		}
		
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
		mi.data.push(mi.anualPFisico);
	}
	
	
	
	
	
	
	mi.getGraficaProducto = function(row){
		if(row.isSelected){
			mi.tituloGrafica = row.metaDescripcion;
			mi.getInfoGraficas(row);	
		}		
	}
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
	
		$http.post('/SProducto',{
				accion: 'getVectoresValores', 
				entidad: mi.entidad,
				unidadEjecutora: mi.unidadEjecutora,
				programa: mi.programa,
				subPrograma: mi.subPrograma,
				actividad: mi.actividad,
				codigo_meta: row.codigo_meta,
				tipo_resultado: mi.tipo_resultado,
				t: new Date().getTime()
			}).then(
			function(response){
				if (response.data.success){
					var datoMensual = [];
					datoMensual = response.data.vectorValores;
					
					mi.datosAcumulado = [];
					var acumuladoCant = 0;
					var acumuladoEje = 0;
					var acumuladoMod = 0;
					
					/*---------------------------------------------------------------------*/
					mi.series = ['% Ejecucion'];
					mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
					
					var cantidades = [];
					mi.datoAcumuladoMensual = [];
					mi.datoAcumuladoAnual = [];
					
					var acumulado = 0;
					
					for(var i=0; i < datoMensual.length; i++){
						if(mi.mes == datoMensual[i].mes && mi.anio == datoMensual[i].ejercicio)
							break;
						
						acumuladoEje = mi.datoAcumuladoMensual.length != 0 ? mi.datoAcumuladoMensual[i-1].ejecucion + datoMensual[i].ejecucion: datoMensual[i].ejecucion;	
						acumuladoMod = mi.datoAcumuladoMensual.length != 0 ? mi.datoAcumuladoMensual[i-1].modificacion + datoMensual[i].modificacion : datoMensual[i].modificacion;	
						
						if(datoMensual[i].mes == 1){
							acumuladoCant = datoMensual[i].cantidad + acumuladoCant
							acumuladoEje = 0;
						}
						
						var p_ejecucion = acumuladoEje / (acumuladoMod + datoMensual[i].cantidad);
						mi.datoAcumuladoMensual.push({mes: datoMensual[i].mes, ejercicio: datoMensual[i].ejercicio, p_ejecucion: p_ejecucion * 100, ejecucion: acumuladoEje, modificacion: acumuladoMod});
						
						if(datoMensual[i].mes == 12)
							mi.datoAcumuladoAnual.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: p_ejecucion * 100});
					}
					
					for(var i=0; i<mi.datoAcumuladoMensual.length; i++){
						mi.labels.push(mi.meses[mi.datoAcumuladoMensual[i].mes - 1] + mi.datoAcumuladoMensual[i].ejercicio);
						cantidades.push(Number(mi.datoAcumuladoMensual[i].p_ejecucion).toFixed(2));
					}
					
					mi.data.push(cantidades);
					
					/*---------------------------------------------------------------------*/
					for(var i=0; i < datoMensual.length; i++){
						if(mi.mes == datoMensual[i].mes && mi.anio == datoMensual[i].ejercicio)
							break;
						
						acumuladoEje = mi.datosAcumulado.length != 0 ? mi.datosAcumulado[i-1].ejecucion + datoMensual[i].ejecucion: datoMensual[i].ejecucion;	
						acumuladoMod = mi.datosAcumulado.length != 0 ? mi.datosAcumulado[i-1].modificacion + datoMensual[i].modificacion : datoMensual[i].modificacion;	
						
						if(datoMensual[i].mes == 1){
							acumuladoCant = datoMensual[i].cantidad + acumuladoCant; 
						}
						
						var p_ejecucion = acumuladoEje / (acumuladoMod + acumuladoCant);
						mi.datosAcumulado.push({mes: datoMensual[i].mes, ejercicio: datoMensual[i].ejercicio, p_ejecucion: p_ejecucion * 100, ejecucion: acumuladoEje, modificacion: acumuladoMod});	
					}
				}
			});
	}
	
	mi.cambioAcumuladoMensualContinua = function(){
		mi.labels = [];
		var cantidades = [];
		mi.data = [];
		
		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				for(var j=0; j<12; j++){
					if(mi.dato[i].ejercicios[h] == mi.anio && j==mi.mes)
						break;
					mi.labels.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
				}
			}
		}
		
		for(var i=0; i<mi.mensualPFisicoAcum.length; i++){
			cantidades.push(Number(mi.mensualPFisicoAcum[i] * 100).toFixed(2));
		}
		
		mi.data.push(cantidades);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
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
		},
		tooltips: {
	           mode: 'label',
	           label: 'mylabel',
	           callbacks: {
	               label: function(tooltipItem, data) {
	            	   return numeral(tooltipItem.yLabel).format(' 0.00')+'%';
	               } 
	           }
	        }
	};
}])