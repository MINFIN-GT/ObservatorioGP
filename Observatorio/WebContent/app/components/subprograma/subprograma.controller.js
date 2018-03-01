angular.module('subprogramaController',[]).controller('subprogramaController', ['$rootScope','$scope','$http','$routeParams', 
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
	
	mi.entidad = $routeParams.entidad;
	mi.unidadEjecutora = $routeParams.unidadejecutora;
	mi.programa = $routeParams.programa;
	
	mi.meses = ['Ene-','Feb-','Mar-','Abr-','May-','Jun-','Jul-','Ago-','Sep-','Oct-','Nov-','Dic-'];
	
	mi.tot_asignado_4 = 0;mi.tot_asignado_3 = 0;mi.tot_asignado_2 = 0;mi.tot_asignado_1 = 0;mi.tot_asignado = 0;
	mi.tot_vigente_4 = 0;mi.tot_vigente_3 = 0;mi.tot_vigente_2 = 0;mi.tot_vigente_1 = 0;mi.tot_vigente = 0;
	mi.tot_ejecutado_4 = 0;mi.tot_ejecutado_3 = 0;mi.tot_ejecutado_2 = 0;mi.tot_ejecutado_1 = 0;mi.tot_ejecutado = 0;
	mi.tot_ep_4 = 0;mi.tot_ep_3 = 0;mi.tot_ep_2 = 0;mi.tot_ep_1 = 0;mi.tot_ep = 0;
	mi.tot_ef_4 = 0;mi.tot_ef_3 = 0;mi.tot_ef_2 = 0;mi.tot_ef_1 = 0;mi.tot_ef = 0;
	
	$http.post('/SSubprograma',{
		accion: 'getSubProgramas',
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
			
//			mi.getGraficaActividad({descripcionActividad:'Todos los sub programas', entidad: mi.entidad, unidadEjecutora: mi.unidadEjecutora, programa: mi.programa});
			
//			mi.getGraficaActividad = function(row){
//				mi.tituloGrafica = row.subprogramaNombre;
//				mi.getInfoGraficas(row);
//			}
		}
	})
	
	mi.getInfoGraficas = function(row){
		mi.limpiarData();
		
		$http.post('/SSubproducto',{
			accion: 'getInfoMensual',
			entidad: mi.entidad,
			unidadEjecutora: mi.unidadEjecutora,
			programa: mi.programa,
			subPrograma: mi.subPrograma,
			actividad: row.id,
			t: new Date().getTime()
		}).then(function(response){
			if(response.data.success){
				
			}
		});
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