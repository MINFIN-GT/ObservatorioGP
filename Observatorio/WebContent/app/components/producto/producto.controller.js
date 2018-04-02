angular.module('productoController', []).controller('productoController',['$rootScope','$scope','$http','$routeParams', '$window',
	function($rootScope,$scope,$http,$routeParams, $window){
	var mi = this;
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth() + 1;
	mi.etiquetaX = "";
	mi.labels = [];
	mi.data = [];
	mi.decimales = true;
	
	
	mi.tipo_resultado = $routeParams.tipo_resultado;
	mi.entidad = $routeParams.entidad;
	mi.unidad_ejecutora = $routeParams.unidad_ejecutora;
	mi.programa = $routeParams.programa;
	mi.subprograma = $routeParams.subprograma;
	mi.proyecto = $routeParams.proyecto;
	mi.actividad = $routeParams.actividad;
	mi.obra = $routeParams.obra;
	
	switch(mi.tipo_resultado){
		case '0': $rootScope.page_title = 'Institucional [Metas]'; break;
		case '1': $rootScope.page_title = 'Resultados estratégicos [Metas]'; break;
		case '2': $rootScope.page_title = 'Resultados institucionales [Metas]'; break;
		case '3': $rootScope.page_title = 'Sin resultado [Metas]'; break;
	}
	
	mi.arregloSubtitulo = JSON.parse($window.localStorage.getItem("\"" + $routeParams.t + "\""));
	mi.subtitulo = ((mi.tipo_resultado=='1' || mi.tipo_resultado=='2') ? mi.arregloSubtitulo[0] + '\\' : '') + 
	mi.arregloSubtitulo[1] + (mi.tipo_resultado=='0' ? ' \\ ' + mi.arregloSubtitulo[2] : '' ) + ' \\ ' + mi.arregloSubtitulo[3] + 
	' \\ ' + mi.arregloSubtitulo[4] + (mi.tipo_resultado=='0' ? ' \\ ' + mi.arregloSubtitulo[5] : '' ) + ' \\ ' +  mi.arregloSubtitulo[6];
	
	mi.tot_p_avance_4 = 0;
	mi.tot_p_avance_3 = 0;
	mi.tot_p_avance_2 = 0;
	mi.tot_p_avance_1 = 0;
	mi.tot_p_avance = 0;
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	$http.post('/SProducto',
		{
			accion: 'getEjecucionFisica',
			entidad: mi.entidad,
			unidad_ejecutora: mi.unidad_ejecutora,
			programa: mi.programa,
			subprograma: mi.subprograma,
			proyecto: mi.proyecto,
			actividad: mi.actividad,
			obra: mi.obra,
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
				mi.mensualPFisico[i] = (mi.mensualPFisico[i] / mi.dato.length) * 100;
			}
			
			for(var i=0; i<mi.anualPFisico.length;i++){
				mi.anualPFisico[i] = (mi.anualPFisico[i] / mi.dato.length) * 100;
			}
			
			for(var i=0; i<mi.mensualPFisicoAcum.length;i++){
				mi.mensualPFisicoAcum[i] = (mi.mensualPFisicoAcum[i] / mi.dato.length) * 100;
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
			cantidades.push(Number(mi.mensualPFisicoAcum[i]).toFixed(2));
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
	
	$http.post('/SLastupdate', { dashboard: 'ejecucionpresupuestaria', t: new Date().getTime() }).then(function(response){
	    if(response.data.success){
	    	mi.lastupdate = response.data.lastupdate;
		}
	});
}])