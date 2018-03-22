var app = angular.module('nivelesController', []).controller('centrosController',['$rootScope','$scope','$http','$routeParams', 
	function($rootScope,$scope,$http,$routeParams){
	
	mi = this;
	mi.rubros_nivel_1 = [];
	mi.rubros_nivel_2 = [];
	mi.rubros_nivel_3 = [];
	mi.rubros_totales = [];
	mi.totales_1 = [];
	mi.totales_2 = [];
	mi.totales_3 = [];
	
	mi.today = moment();
	
	mi.grafica_series=[mi.today.year()-2, mi.today.year()-1, mi.today.year()];
	mi.grafica_labels=['Med','MMQ','Ins','APP','EM','RH','GA','GO','M'];
	mi.grafica_dataset=[];
	
	mi.chart_colors =[{
        backgroundColor: '#992038',
        borderColor: '#992038',
        hoverBackgroundColor: '#992038',
        hoverBorderColor: '#992038'
    },{
        backgroundColor: '#F1A944',
        borderColor: '#F1A944',
        hoverBackgroundColor: '#F1A944',
        hoverBorderColor: '#F1A944'
    },{
        backgroundColor: '#4169E1',
        borderColor: '#4169E1',
        hoverBackgroundColor: '#4169E1',
        hoverBorderColor: '#4169E1'
    }]
	
	mi.grafica_nivel1=new Array([],[],[]);
	mi.grafica_nivel2=new Array([],[],[]);
	mi.grafica_nivel3=new Array([],[],[]);
	mi.grafica_total=new Array([],[],[]);
	
	mi.titulo_grafica_nivel1 = 'Comparativo de EJECUCIÓN. En millones de quetzales';
	mi.titulo_grafica_nivel2 = 'Comparativo de EJECUCIÓN. En millones de quetzales';
	mi.titulo_grafica_nivel3 = 'Comparativo de EJECUCIÓN. En millones de quetzales';
	mi.titulo_grafica_total = 'Comparativo de EJECUCIÓN. En millones de quetzales';
	
	mi.grafica_opciones={
			layout: {
	            padding: {
	                left: 0,
	                right: 0,
	                top: 30,
	                bottom: 0
	            }
	        },
			scales:
		        {	xAxes: [{
		                gridLines: {
		                    display:false
		                },
		                ticks: {
	                        autoSkip: false,
	                        maxRotation: 90,
	                        minRotation: 90,
	                        fontSize: 10
	                    }
	            	}],
		            yAxes: [{
		                display: false
		            }]
		        },
		        tooltips:{
					enabled:false
				},
		        animation: {
	                onComplete: function() {
	                	if(this.config.type=='bar' || this.config.type=='line'){
		                	var ctx = this.chart.ctx;
		                	  ctx.font = Chart.helpers.fontString(10, 'normal', Chart.defaults.global.defaultFontFamily);
		                	  ctx.fillStyle = this.chart.config.options.defaultFontColor;
		                	  ctx.textAlign = 'center';
		                	  ctx.textBaseline = 'bottom';
		                	  this.data.datasets.forEach(function (dataset) {
		                	    for (var i = 0; i < dataset.data.length; i++) {
		                	      if(dataset._meta[Object.keys(dataset._meta)[0]].hidden){ 
		                	    	  continue; 
		                	      }
		                	      var model = dataset._meta[Object.keys(dataset._meta)[0]].data[i]._model;
		                	      if(dataset.data[i] !== null){
		                	    	  ctx.save();
		                	    	  ctx.beginPath();
		                	    	  ctx.translate( model.x + 7, model.y - 20)
		                	    	  ctx.rotate(270*Math.PI/180);
		                	    	  ctx.fillText(formatoNumero(dataset.data[i], 0), 0, 0);
		                	    	  ctx.restore();
		                	      }
		                	    }
		                	  });
	                	}
	               }
	            }
	}
	
	$http.post('/SSalud',{
		accion: 'getNiveles',
		t: new Date().getTime()
	}).then(function(response){
		if(response.data.success){
			mi.rubros_nivel_1 = response.data.nivel1;
			mi.rubros_nivel_2 = response.data.nivel2;
			mi.rubros_nivel_3 = response.data.nivel3;
			for(var i=0; i< mi.rubros_nivel_1.length; i++){
				for(var j=0; j<mi.rubros_nivel_1[i].ejercicios.length; j++){
					if(mi.totales_1[j]===undefined)
						mi.totales_1[j]=new Array(0.0,0.0,0.0);
					mi.totales_1[j][0]+=mi.rubros_nivel_1[i].data_ejercicio[j][0];
					mi.totales_1[j][1]+=mi.rubros_nivel_1[i].data_ejercicio[j][1];
					mi.totales_1[j][2]+=mi.rubros_nivel_1[i].data_ejercicio[j][2];
					if(j>1 && i>0){
						mi.grafica_nivel1[j-2].push(mi.rubros_nivel_1[i].data_ejercicio[j][2]/1000000);
					}
				}
			}
			console.log(mi.grafica_nivel1);
			for(var i=0; i< mi.rubros_nivel_2.length; i++){
				for(var j=0; j<mi.rubros_nivel_2[i].ejercicios.length; j++){
					if(mi.totales_2[j]===undefined)
						mi.totales_2[j]=new Array(0.0,0.0,0.0);
					mi.totales_2[j][0]+=mi.rubros_nivel_2[i].data_ejercicio[j][0];
					mi.totales_2[j][1]+=mi.rubros_nivel_2[i].data_ejercicio[j][1];
					mi.totales_2[j][2]+=mi.rubros_nivel_2[i].data_ejercicio[j][2];
					if(j>1 && i>0){
						mi.grafica_nivel2[j-2].push(mi.rubros_nivel_2[i].data_ejercicio[j][2]/1000000);
					}
				}
			}
			for(var i=0; i< mi.rubros_nivel_3.length; i++){
				for(var j=0; j<mi.rubros_nivel_3[i].ejercicios.length; j++){
					if(mi.totales_3[j]===undefined)
						mi.totales_3[j]=new Array(0.0,0.0,0.0);
					mi.totales_3[j][0]+=mi.rubros_nivel_3[i].data_ejercicio[j][0];
					mi.totales_3[j][1]+=mi.rubros_nivel_3[i].data_ejercicio[j][1];
					mi.totales_3[j][2]+=mi.rubros_nivel_3[i].data_ejercicio[j][2];
					if(j>1 && i>0){
						mi.grafica_nivel3[j-2].push(mi.rubros_nivel_3[i].data_ejercicio[j][2]/1000000);
					}
				}
			}
			mi.rubros_totales = new Array();
			for(var i=0; i< mi.rubros_nivel_1.length; i++){
				mi.rubros_totales.push({});
				mi.rubros_totales[i]={
					data_ejercicio: [[0,0,0],[0,0,0],[0,0,0],[0,0,0],[0,0,0]]
				};
				for(var j=0; j<mi.rubros_nivel_1[i].ejercicios.length; j++){
					mi.rubros_totales[i].data_ejercicio[j][0]+=mi.rubros_nivel_1[i].data_ejercicio[j][0] + mi.rubros_nivel_2[i].data_ejercicio[j][0] + mi.rubros_nivel_3[i].data_ejercicio[j][0];
					mi.rubros_totales[i].data_ejercicio[j][1]+=mi.rubros_nivel_1[i].data_ejercicio[j][1] + mi.rubros_nivel_2[i].data_ejercicio[j][1] + mi.rubros_nivel_3[i].data_ejercicio[j][1];
					mi.rubros_totales[i].data_ejercicio[j][2]+=mi.rubros_nivel_1[i].data_ejercicio[j][2] + mi.rubros_nivel_2[i].data_ejercicio[j][2] + mi.rubros_nivel_3[i].data_ejercicio[j][2];
					if(j>1 && i>0){
						mi.grafica_total[j-2].push(mi.rubros_totales[i].data_ejercicio[j][2]/1000000);
					}
				}
			}
		}
	});
	
	mi.changeGrafica = function(grafica, tipo){
		var datos = [];
		var grafica_datos=[[],[],[]];
		switch(grafica){
			case 1: datos = mi.rubros_nivel_1; break;
			case 2: datos = mi.rubros_nivel_2; break;
			case 3: datos = mi.rubros_nivel_3; break;
			case 4: datos = mi.rubros_totales; break;
		}
		for(var i=1; i< mi.rubros_nivel_1.length; i++){
			grafica_datos[0].push(datos[i].data_ejercicio[2][tipo-1]/1000000);
			grafica_datos[1].push(datos[i].data_ejercicio[3][tipo-1]/1000000);
			grafica_datos[2].push(datos[i].data_ejercicio[4][tipo-1]/1000000);
		}
		var texto = '';
		switch(tipo){
			case 1: texto = 'ASIGNACIÓN'; break;
			case 2: texto = 'VIGENTE'; break;
			case 3: texto = 'EJECUCIÓN'; break;
		}
		switch(grafica){
			case 1: mi.grafica_nivel1 = grafica_datos.slice(); 
				mi.titulo_grafica_nivel1 = 'Comparativo de '+texto+'. En millones de quetzales';
				break;
			case 2: mi.grafica_nivel2 = grafica_datos.slice();
				mi.titulo_grafica_nivel2 = 'Comparativo de '+texto+'. En millones de quetzales';
				break;
			case 3: mi.grafica_nivel3 = grafica_datos.slice(); 
				mi.titulo_grafica_nivel3 = 'Comparativo de '+texto+'. En millones de quetzales';
				break;
			case 4: mi.grafica_total = grafica_datos.slice(); 
				mi.titulo_grafica_total = 'Comparativo de '+texto+'. En millones de quetzales';
				break;
		}
	}
	
	mi.go=function(nivel){
		switch(nivel){
			case 1: window.location.href='#!/salud/nivel1'; break;
			case 2: window.location.href='#!/salud/nivel2'; break;
			case 3: window.location.href='#!/salud/nivel3'; break;
		}
	}
}]);