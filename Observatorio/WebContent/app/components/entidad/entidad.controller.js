var app = angular.module('entidadController',[]).controller('entidadController', ['$rootScope','$scope','$http','$routeParams', '$window',
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
	
	mi.tipo_resultado = $routeParams.tipo_resultado;
	
	switch(mi.tipo_resultado){
		case '1': $rootScope.page_title = 'Resultados institucionales'; break;
		case '2': $rootScope.page_title = 'Resultados estratégicos'; break;
		case '3': $rootScope.page_title = 'Sin resultado'; break;
	}
	
	$http.post('/SEntidad',{
		accion: 'getEntidades',
		tipo_resultado: mi.tipo_resultado,
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.dato = [];
			mi.dato = response.data.entidades;
			
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
		
		mi.mensualVigente = new Array(60).fill(0);
		mi.mensualEjecutado = new Array(60).fill(0);
		mi.mensualPFinanciero = new Array(60).fill(0);
		mi.mensualPFisico = new Array(60).fill(0);
		
		mi.anualVigente = [];
		mi.anualEjecutado = [];
		mi.anualPFinanciero = [];
		mi.anualPFisico = [];
		
		var sumar = false;
		if(datos.length > 0){
			for(var i=0; i<datos.length; i++){//row
				for(var j=0; j<datos[i].ejercicios.length; j++){//años
					var pos = datos[i].ejercicios[j] - mi.anio + 4;
					for(var h=0; h<datos[i].ejercicio_data[pos].length; h++){
						if(h==0){
							if(pos==0)
								mi.tot_asignado_4 += datos[i].ejercicio_data[pos][h];
							if(pos==1)
								mi.tot_asignado_3 += datos[i].ejercicio_data[pos][h];
							if(pos==2)
								mi.tot_asignado_2 += datos[i].ejercicio_data[pos][h];
							if(pos==3)
								mi.tot_asignado_1 += datos[i].ejercicio_data[pos][h];
							if(pos==4)
								mi.tot_asignado += datos[i].ejercicio_data[pos][h];
						}else if((h>=1) && (h<=12)){ //vigente
							if(pos==0 && h==12)
								mi.tot_vigente_4 += datos[i].ejercicio_data[pos][h];
							if(pos==1 && h==12)
								mi.tot_vigente_3 += datos[i].ejercicio_data[pos][h];
							if(pos==2 && h==12)
								mi.tot_vigente_2 += datos[i].ejercicio_data[pos][h];
							if(pos==3 && h==12)
								mi.tot_vigente_1 += datos[i].ejercicio_data[pos][h];
							if(pos==4 && h==12)
								mi.tot_vigente += datos[i].ejercicio_data[pos][h];
							
							var posarr = pos==0 ? (h-1+0) : pos==1 ? (h-1+12) : pos==2 ? (h-1+24) : pos==3 ? (h-1+36) : pos==4 ? (h-1+48) : 0;
							mi.mensualVigente[posarr] += datos[i].ejercicio_data[pos][h];
							
							if(h==12)
								mi.anualVigente.push(datos[i].ejercicio_data[pos][h]);
							
						}else if((h>=13) && (h<=24)){ //ejecutado
							if(pos==0 && h==24)
								mi.tot_ejecutado_4 += datos[i].ejercicio_data[pos][h];
							if(pos==1 && h==24)
								mi.tot_ejecutado_3 += datos[i].ejercicio_data[pos][h];
							if(pos==2 && h==24)
								mi.tot_ejecutado_2 += datos[i].ejercicio_data[pos][h];
							if(pos==3 && h==24)
								mi.tot_ejecutado_1 += datos[i].ejercicio_data[pos][h];
							if(pos==4 && h==24)
								mi.tot_ejecutado += datos[i].ejercicio_data[pos][h];
							
							var posarr = pos==0 ? (h-13+0) : pos==1 ? (h-13+12) : pos==2 ? (h-13+24) : pos==3 ? (h-13+36) : pos==4 ? (h-13+48) : 0;							
							mi.mensualEjecutado[posarr] += datos[i].ejercicio_data[pos][h];
							
							if(h==24)
								mi.anualEjecutado.push(datos[i].ejercicio_data[pos][h]);
							
						}else if((h>=25) && (h<=36)){ //porcentaje financiero presupuestario
							if(pos==0 && h==36)
								mi.tot_p_ejecucion_4 += datos[i].ejercicio_data[pos][h];
							if(pos==1 && h==36)
								mi.tot_p_ejecucion_3 += datos[i].ejercicio_data[pos][h];
							if(pos==2 && h==36)
								mi.tot_p_ejecucion_2 += datos[i].ejercicio_data[pos][h];
							if(pos==3 && h==36)
								mi.tot_p_ejecucion_1 += datos[i].ejercicio_data[pos][h];
							if(pos==4 && h==36)
								mi.tot_p_ejecucion += datos[i].ejercicio_data[pos][h];
							
							var posarr = pos==0 ? (h-25+0) : pos==1 ? (h-25+12) : pos==2 ? (h-25+24) : pos==3 ? (h-25+36) : pos==4 ? (h-25+48) : 0;
							mi.mensualPFinanciero[posarr] += datos[i].ejercicio_data[pos][h];
							
							if(h==36)
								mi.anualPFinanciero.push(datos[i].ejercicio_data[pos][h] * 100);

						}else if((h>=37) && (h<=48)){ //porcentaje fisico
							if(pos==0 && h==48)
								mi.tot_p_avance_4 += datos[i].ejercicio_data[pos][h];
							if(pos==1 && h==48)
								mi.tot_p_avance_3 += datos[i].ejercicio_data[pos][h];
							if(pos==2 && h==48)
								mi.tot_p_avance_2 += datos[i].ejercicio_data[pos][h];
							if(pos==3 && h==48)
								mi.tot_p_avance_1 += datos[i].ejercicio_data[pos][h];
							if(pos==4 && h==48)
								mi.tot_p_avance += datos[i].ejercicio_data[pos][h];
							
							var posarr = pos==0 ? (h-37+0) : pos==1 ? (h-37+12) : pos==2 ? (h-37+24) : pos==3 ? (h-37+36) : pos==4 ? (h-37+48) : 0;
							mi.mensualPFisico[posarr] += datos[i].ejercicio_data[pos][h];
							
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
			
			for(var i=0; i<mi.mensualPFinanciero.length;i++){
				mi.mensualPFinanciero[i] = (mi.mensualPFinanciero[i] / mi.dato.length) * 100;
				mi.mensualPFisico[i] = (mi.mensualPFisico[i] / mi.dato.length) * 100;
			}
			
			mi.tipoDatos = 1;
			mi.data.push(mi.mensualVigente, mi.mensualEjecutado);
			mi.data2.push(mi.mensualPFinanciero, mi.mensualPFisico);
			
			mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
			mi.options2.scales.xAxes["0"].scaleLabel.labelString = "Meses";
		}
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
				if((h>=1) && (h<=12)){//vigente
					var posarr = pos==0 ? (h-1+0) : pos==1 ? (h-1+12) : pos==2 ? (h-1+24) : pos==3 ? (h-1+36) : pos==4 ? (h-1+48) : 0;
					mi.mensualVigente.push(row.ejercicio_data[pos][h]);
					
					if(h==12)
						mi.anualVigente.push(row.ejercicio_data[pos][h]);
				}else if((h>=13) && (h<=24)){//ejecutado
					mi.mensualEjecutado.push(row.ejercicio_data[pos][h]);
					
					if(h==24)
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
		        	    	 return 'Q' + numeral(value).format('0,000.00')
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
	
	mi.irPrograma = function(entidad_id){
		window.location = "main.jsp#!/programa/" + mi.tipo_resultado + "/" + entidad_id;
	}
}])