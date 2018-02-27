var app = angular.module('productoController', ['ui.grid','ui.grid.pinning','ngUtilidades']);
app.controller('productoController',['$rootScope','$scope','$http', 
	function($rootScope,$scope,$http){
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
	
	mi.entidad = 11130009;
	mi.unidadEjecutora = 219;
	mi.programa = 15;
	mi.subPrograma = 0;
	mi.actividad = 3;
	mi.obra = 0;
	
	$http.post('/SEjecucionFisica',
		{
			accion: 'getEjecucionFisica',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subProbrama: mi.subPrograma,
			actividad: mi.actividad,
			obra: mi.obra,
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
					
					mi.getGraficaProducto({metaDescripcion:'Todos los productos y sub productos', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa, subPrograma: mi.subPrograma, actividad: mi.actividad, obra: mi.obra});
				}
			}
		});
	
	mi.getGraficaProducto = function(row){
		mi.tituloGrafica = row.metaDescripcion;
		mi.getInfoGraficas(row);
	}
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
		$http.post('/SEjecucionFisica', 
			{
				accion : 'getEjecucionFisicaMensual',
				entidad: row.entidad,
				unidadEjecutora: row.unidadEjecutora,
				programa: row.programa,
				subProbrama: row.subPrograma,
				actividad: row.actividad,
				obra: row.obra,
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
				
			$http.post('/SEjecucionFisica', 
				{
					accion: 'getVectoresValores', 
					entidad: row.entidad,
					unidadEjecutora: row.unidadEjecutora,
					programa: row.programa,
					subProbrama: row.subPrograma,
					actividad: row.actividad,
					obra: row.obra,
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
		line : { tension : 1 },
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
	
	mi.datasetOverride = [{
        yAxisID: 'y-axis-1',
        tension: 0
    }]
}])