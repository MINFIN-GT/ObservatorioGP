angular.module('productoController', []).controller('productoController',['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	var mi = this;
	var fecha = new Date();
	mi.anio = fecha.getFullYear();
	mi.mes = fecha.getMonth() + 1;
	mi.etiquetaX = "";
	mi.labels = [];
	mi.data = [];
	mi.decimales = false;
	
	mi.tot_p_ejecucion_4 = 0;
	mi.tot_p_ejecucion_3 = 0;
	mi.tot_p_ejecucion_2 = 0;
	mi.tot_p_ejecucion_1 = 0;
	mi.tot_p_ejecucion = 0;
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	mi.entidad = $routeParams.entidad;
	mi.unidadEjecutora = $routeParams.unidadejecutora;
	mi.programa = $routeParams.programa;
	mi.subPrograma = $routeParams.subprograma;
	mi.actividad = $routeParams.actividad;
	
	$http.post('/SProducto',
		{
			accion: 'getEjecucionFisica',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subProbrama: mi.subPrograma,
			actividad: mi.actividad,
			t: new Date().getTime()
		}).then(			
		function(response){
			if (response.data.success){
				mi.dato = [];
				mi.dato = response.data.ejecucionFisica;
				
				mi.rowCollection = [];
				mi.rowCollection = mi.dato;
				
				mi.displayedCollection = [].concat(mi.rowCollection);
				
				if(mi.dato.length > 0){
					for(var i=0; i<mi.dato.length;i++){
						mi.tot_p_ejecucion_4 += mi.dato[i].p_ejecucion_4;
						mi.tot_p_ejecucion_3 += mi.dato[i].p_ejecucion_3;
						mi.tot_p_ejecucion_2 += mi.dato[i].p_ejecucion_2;
						mi.tot_p_ejecucion_1 += mi.dato[i].p_ejecucion_1;
						mi.tot_p_ejecucion += mi.dato[i].p_ejecucion;
					}
					
					mi.tot_p_ejecucion_4 = ((mi.tot_p_ejecucion_4 / mi.dato.length)).toFixed(2);
					mi.tot_p_ejecucion_3 = ((mi.tot_p_ejecucion_3 / mi.dato.length)).toFixed(2);
					mi.tot_p_ejecucion_2 = ((mi.tot_p_ejecucion_2 / mi.dato.length)).toFixed(2);
					mi.tot_p_ejecucion_1 = ((mi.tot_p_ejecucion_1 / mi.dato.length)).toFixed(2);
					mi.tot_p_ejecucion = ((mi.tot_p_ejecucion / mi.dato.length)).toFixed(2);
					
					mi.getGraficaProducto({metaDescripcion:'Todos los productos y sub productos', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa, subPrograma: mi.subPrograma, actividad: mi.actividad});
				}
			}
		});
	
	mi.getGraficaProducto = function(row){
		mi.tituloGrafica = row.metaDescripcion;
		mi.getInfoGraficas(row);
	}
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
		$http.post('/SProducto', 
			{
				accion : 'getEjecucionFisicaMensual',
				entidad: mi.entidad,
				unidadEjecutora: mi.unidadEjecutora,
				programa: mi.programa,
				subProbrama: mi.subPrograma,
				actividad: mi.actividad,
				codigo_meta: row.codigo_meta,
				t: new Date().getTime()
			}).then(function(response){
				if (response.data.success){
					var datoMensual = [];
					datoMensual = response.data.ejecucionFisicaMensual;
					
					mi.series = ['% Ejecucion'];
					mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
					
					var cantidades = [];
					mi.datoAcumuladoMensual = [];
					mi.datoAcumuladoAnual = [];
					
					var acumulado = 0;
					
					for(var i=0; i < datoMensual.length; i++){
						if(mi.mes == datoMensual[i].mes && mi.anio == datoMensual[i].ejercicio)
							break;
						
						if(datoMensual[i].mes == 1)
							acumulado = 0;
						else
							acumulado = mi.datoAcumuladoMensual.length != 0 ? mi.datoAcumuladoMensual[i-1].p_ejecucion + datoMensual[i].p_ejecucion : datoMensual[i].p_ejecucion;
						
						mi.datoAcumuladoMensual.push({mes: datoMensual[i].mes, ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumulado});
						
						if(datoMensual[i].mes == 12)
							mi.datoAcumuladoAnual.push({ejercicio: datoMensual[i].ejercicio, p_ejecucion: acumulado});
					}
					
					for(var i=0; i<mi.datoAcumuladoMensual.length; i++){
						mi.labels.push(mi.meses[mi.datoAcumuladoMensual[i].mes - 1] + mi.datoAcumuladoMensual[i].ejercicio);
						cantidades.push(Number(mi.datoAcumuladoMensual[i].p_ejecucion).toFixed(2));
					}
					
					mi.data.push(cantidades);
				}
			});
				
			$http.post('/SProducto', 
				{
					accion: 'getVectoresValores', 
					entidad: mi.entidad,
					unidadEjecutora: mi.unidadEjecutora,
					programa: mi.programa,
					subProbrama: mi.subPrograma,
					actividad: mi.actividad,
					codigo_meta: row.codigo_meta,
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
						
						for(var i=0; i < datoMensual.length; i++){
							if(mi.mes == datoMensual[i].mes && mi.anio == datoMensual[i].ejercicio)
								break;
							
							acumuladoEje = mi.datosAcumulado.length != 0 ? mi.datosAcumulado[i-1].ejecucion + datoMensual[i].ejecucion: datoMensual[i].ejecucion;	
							acumuladoMod = mi.datosAcumulado.length != 0 ? mi.datosAcumulado[i-1].modificacion + datoMensual[i].modificacion : datoMensual[i].modificacion;	
							
							if(datoMensual[i].mes == 1){
								acumuladoCant = datoMensual[i].cantidad + acumuladoCant; 
							}
							
							var p_ejecucion = acumuladoEje / (acumuladoMod + acumuladoCant);
							mi.datosAcumulado.push({mes: datoMensual[i].mes, ejercicio: datoMensual[i].ejercicio, p_ejecucion: p_ejecucion *100, ejecucion: acumuladoEje, modificacion: acumuladoMod});	
						}
					}
				});
	}
	
	mi.limpiarData = function(){
		mi.labels = [];
		mi.data = [];
	}
	
	mi.cambioAcumuladoMensual = function(){
		mi.labels = [];
		var cantidades = [];
		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoMensual.length; i++){
			mi.labels.push(mi.meses[mi.datoAcumuladoMensual[i].mes - 1] + mi.datoAcumuladoMensual[i].ejercicio);
			cantidades.push(Number(mi.datoAcumuladoMensual[i].p_ejecucion).toFixed(2));
		}
		
		mi.data.push(cantidades);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Meses";
	}	
		
	mi.cambioAcumuladoAnual = function(){
		mi.labels = [];
		var cantidades = [];
		mi.data = [];
		
		for(var i=0; i<mi.datoAcumuladoAnual.length; i++){
			mi.labels.push(mi.datoAcumuladoAnual[i].ejercicio);
			cantidades.push(Number(mi.datoAcumuladoAnual[i].p_ejecucion).toFixed(2));
		}
		
		mi.data.push(cantidades);
		mi.options.scales.xAxes["0"].scaleLabel.labelString = "Años";
	}
	
	mi.cambioAcumuladoMensualNormal = function(){
		mi.labels = [];
		var cantidades = [];
		mi.data = [];
		
		for(var i=0; i<mi.datosAcumulado.length; i++){
			mi.labels.push(mi.meses[mi.datosAcumulado[i].mes - 1] + mi.datosAcumulado[i].ejercicio);
			cantidades.push(Number(mi.datosAcumulado[i].p_ejecucion).toFixed(2));
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
		}
	};
}])