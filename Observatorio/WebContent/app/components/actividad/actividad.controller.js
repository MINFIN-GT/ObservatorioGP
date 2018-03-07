angular.module('actividadController',[]).controller('actividadController', ['$rootScope','$scope','$http','$routeParams', '$window',
	function($rootScope,$scope,$http,$routeParams, $window){
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
	mi.tipoDatos = 0;

	mi.tot_asignado_4 = 0;
	mi.tot_vigente_4 = 0;
	mi.tot_ejecutado_4 = 0;
	mi.tot_p_ejecucion_4 = 0;
	mi.tot_p_avance_4 = 0;
	
	mi.tot_asignado_3 = 0;
	mi.tot_vigente_3 = 0;
	mi.tot_ejecutado_3 = 0;
	mi.tot_p_ejecucion_3 = 0;
	mi.tot_p_avance_3 = 0;
	
	mi.tot_asignado_2 = 0;
	mi.tot_vigente_2 = 0;
	mi.tot_ejecutado_2 = 0;
	mi.tot_p_ejecucion_2 = 0;
	mi.tot_p_avance_2 = 0;
	
	mi.tot_asignado_1 = 0;
	mi.tot_vigente_1 = 0;
	mi.tot_ejecutado_1 = 0;
	mi.tot_p_ejecucion_1 = 0;
	mi.tot_p_avance_1 = 0;
	
	mi.tot_asignado = 0;
	mi.tot_vigente = 0;
	mi.tot_ejecutado = 0;
	mi.tot_p_ejecucion = 0;
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
			
			mi.tipoDatos = 1;
			mi.getGraficaGeneral(mi.dato);
		}
	});
	
	mi.getGraficaGeneral = function(datos){
		mi.series = ['Vigente', 'Ejecutado'];
		mi.series2 = ['% Financiero' , '% Físico']
		mi.tot_asignado = 0;
		
		mi.labels = [];
		mi.lables2 = [];
		
		mi.mensualVigente = [];
		mi.mensualEjecutado = [];
		mi.mensualPFinanciero = [];
		mi.mensualPFisico = [];
		
		mi.anualVigente = [];
		mi.anualEjecutado = [];
		mi.anualPFinanciero = [];
		mi.anualPFisico = [];
		
		for(var i=0; i<datos.length; i++){//row
			for(var j=0; j<datos[i].ejercicios.length; j++){//años
				var pos = datos[i].ejercicios[j] - mi.anio + 4;
				for(var h=0; h<datos[i].ejercicio_data[pos].length; h++){
					if(h==0){
						if(pos==0)
							mi.tot_asignado_4 += datos[i].ejercicio_data[pos][0];
						if(pos==1)
							mi.tot_asignado_3 += datos[i].ejercicio_data[pos][0];
						if(pos==2)
							mi.tot_asignado_2 += datos[i].ejercicio_data[pos][0];
						if(pos==3)
							mi.tot_asignado_1 += datos[i].ejercicio_data[pos][0];
						if(pos==4)
							mi.tot_asignado += datos[i].ejercicio_data[pos][0];
					}else if((h>=13) && (h<=24)){ //vigente
						if(pos==0 && h==24)
							mi.tot_vigente_4 += datos[i].ejercicio_data[pos][24];
						if(pos==1 && h==24)
							mi.tot_vigente_3 += datos[i].ejercicio_data[pos][24];
						if(pos==2 && h==24)
							mi.tot_vigente_2 += datos[i].ejercicio_data[pos][24];
						if(pos==3 && h==24)
							mi.tot_vigente_1 += datos[i].ejercicio_data[pos][24];
						if(pos==4 && h==24)
							mi.tot_vigente += datos[i].ejercicio_data[pos][24];
						
						mi.mensualVigente.push(datos[i].ejercicio_data[pos][h]);
						
						if(h==24)
							mi.anualVigente.push(datos[i].ejercicio_data[pos][h]);
						
					}else if((h>=1) && (h<=12)){ //ejecutado
						if(pos==0 && h==12)
							mi.tot_ejecutado_4 += datos[i].ejercicio_data[pos][12];
						if(pos==1 && h==12)
							mi.tot_ejecutado_3 += datos[i].ejercicio_data[pos][12];
						if(pos==2 && h==12)
							mi.tot_ejecutado_2 += datos[i].ejercicio_data[pos][12];
						if(pos==3 && h==12)
							mi.tot_ejecutado_1 += datos[i].ejercicio_data[pos][12];
						if(pos==4 && h==12)
							mi.tot_ejecutado += datos[i].ejercicio_data[pos][12];
						
						mi.mensualEjecutado.push(datos[i].ejercicio_data[pos][h]);
						
						if(h==12)
							mi.anualEjecutado.push(datos[i].ejercicio_data[pos][h]);
						
					}else if((h>=25) && (h<=36)){ //porcentaje financiero presupuestario
						if(pos==0 && h==36)
							mi.tot_p_ejecucion_4 += datos[i].ejercicio_data[pos][36];
						if(pos==1 && h==36)
							mi.tot_p_ejecucion_3 += datos[i].ejercicio_data[pos][36];
						if(pos==2 && h==36)
							mi.tot_p_ejecucion_2 += datos[i].ejercicio_data[pos][36];
						if(pos==3 && h==36)
							mi.tot_p_ejecucion_1 += datos[i].ejercicio_data[pos][36];
						if(pos==4 && h==36)
							mi.tot_p_ejecucion += datos[i].ejercicio_data[pos][36];
						
						mi.mensualPFinanciero.push(datos[i].ejercicio_data[pos][h] * 100);
						
						if(h==36)
							mi.anualPFinanciero.push(datos[i].ejercicio_data[pos][h] * 100);

					}else if((h>=37) && (h<=48)){ //porcentaje fisico
						if(pos==0 && h==48)
							mi.tot_p_avance_4 += datos[i].ejercicio_data[pos][48];
						if(pos==1 && h==48)
							mi.tot_p_avance_3 += datos[i].ejercicio_data[pos][48];
						if(pos==2 && h==48)
							mi.tot_p_avance_2 += datos[i].ejercicio_data[pos][48];
						if(pos==3 && h==48)
							mi.tot_p_avance_1 += datos[i].ejercicio_data[pos][48];
						if(pos==4 && h==48)
							mi.tot_p_avance += datos[i].ejercicio_data[pos][48];
						
						mi.mensualPFisico.push(datos[i].ejercicio_data[pos][h] * 100);
						
						if(h==48)
							mi.anualPFisico.push(datos[i].ejercicio_data[pos][h] * 100);
					}
				}
			}
		}
		
		mi.tot_p_ejecucion_4 = (mi.tot_p_ejecucion_4 / mi.dato.length) * 100;
		mi.tot_p_ejecucion_3 = (mi.tot_p_ejecucion_3 / mi.dato.length) * 100;
		mi.tot_p_ejecucion_2 = (mi.tot_p_ejecucion_2 / mi.dato.length) * 100;
		mi.tot_p_ejecucion_1 = (mi.tot_p_ejecucion_1 / mi.dato.length) * 100;
		mi.tot_p_ejecucion = (mi.tot_p_ejecucion / mi.dato.length) * 100;
		
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
					mi.labels2.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
				}
			}
		}
		
		mi.tipoDatos = 1;
		mi.data.push(mi.mensualVigente, mi.mensualEjecutado);
		mi.data2.push(mi.mensualPFinanciero, mi.mensualPFisico);
		
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
		mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Meses";
	}
	
	mi.getGraficaIndividual = function(row){
		mi.labels = [];
		mi.labels2 = [];
		
		mi.data = [];
		mi.data2 = [];
		
		mi.mensualVigente = [];
		mi.mensualEjecutado = [];
		mi.mensualPFinanciero = [];
		mi.mensualPFisico = [];
		
		mi.anualVigente = [];
		mi.anualEjecutado = [];
		mi.anualPFinanciero = [];
		mi.anualPFisico = [];
		
		for(var j=0; j<row.ejercicios.length; j++){
			var pos = row.ejercicios[j] - mi.anio + 4;
			for(var h=0; h<row.ejercicio_data[pos].length; h++){
				if((h>=13) && (h<=24)){//vigente
					mi.mensualVigente.push(row.ejercicio_data[pos][h]);
					
					if(h==24)
						mi.anualVigente.push(row.ejercicio_data[pos][h]);
				}else if((h>=1) && (h<=12)){//ejecutado
					mi.mensualEjecutado.push(row.ejercicio_data[pos][h]);
					
					if(h==12)
						mi.anualEjecutado.push(row.ejercicio_data[pos][h]);
				}else if((h>=25) && (h<=36)){ //porcentaje financiero presupuestario
					mi.mensualPFinanciero.push(row.ejercicio_data[pos][h] * 100);
					
					if(h==36)
						mi.anualPFinanciero.push(row.ejercicio_data[pos][h] * 100);
				}else if((h>=37) && (h<=48)){ //porcentaje fisico
					mi.mensualPFisico.push(row.ejercicio_data[pos][h] * 100);
					
					if(h==48)
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
					mi.labels2.push(mi.meses[j] + row.ejercicios[h]);
				}
			}
		}
		
		mi.tipoDatos = 2;
		mi.data.push(mi.mensualVigente, mi.mensualEjecutado);
		mi.data2.push(mi.mensualPFinanciero, mi.mensualPFisico);
	}
	
	mi.cambioMensual = function(){
		mi.data = [];
		mi.labels = [];
		
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
		mi.data.push(mi.mensualVigente, mi.mensualEjecutado);	
	}
	
	mi.cambioAnual =  function(){
		mi.labels = [];
		mi.data = [];

		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				mi.labels.push(mi.dato[i].ejercicios[h]);
			}
		}
		
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
		mi.data.push(mi.anualVigente, mi.anualEjecutado);
	}
	
	mi.cambioMensualP = function(){
		mi.labels2 = [];
		mi.data2 = [];

		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				for(var j=0; j<12; j++){
					if(mi.dato[i].ejercicios[h] == mi.anio && j==mi.mes)
						break;
					mi.labels2.push(mi.meses[j] + mi.dato[i].ejercicios[h]);
				}
			}
		}
		
		mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Meses";
		mi.data2.push(mi.mensualPFinanciero, mi.mensualPFisico);
	}
	
	mi.cambioAnualP = function(){
		mi.labels2 = [];
		mi.data2 = [];

		for(var i=0; i<1; i++){
			for(var h=0; h<mi.dato[i].ejercicios.length;h++){
				mi.labels2.push(mi.dato[i].ejercicios[h]);
			}
		}
		
		mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Años";
		mi.data2.push(mi.anualPFinanciero, mi.anualPFisico);
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
	
		mi.irProducto = function(actividad_id){
			window.location = "main.jsp#!/producto/" + mi.tipo_resultado + "/" + mi.entidad + "/" + mi.unidadEjecutora + "/" + mi.programa + "/" + mi.subPrograma + "/" +  actividad_id;
		}
}]);
