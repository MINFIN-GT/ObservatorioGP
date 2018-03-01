var app = angular.module('main', ['chart.js','smart-table','ui.bootstrap','ngCookies',
    'ngMessages',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'circularMenu-directive']);
app.controller('mainController',['$rootScope','$scope','$http', function($rootScope,$scope,$http){
	var mi = this;
	mi.etiqueta = 'Bienvenido';
	
	$http.post('/STest',{accion: 'getData'}).then(
		function(response){
			if (response.data.success){
				mi.dato = [];
				mi.dato = response.data.valores;
				mi.gridOptions.data = mi.dato;
				
				mi.rowCollection = [];
				mi.rowCollection = mi.dato;
				
				mi.displayedCollection = [].concat(mi.rowCollection);
				
				mi.labels = [];
				mi.series = ['Cantidad de personas'];
				
				mi.data = [];
				mi.cantidades = [];
				mi.totales = 0;
				for(var i=0; i<mi.dato.length; i++){
					mi.cantidades.push(mi.dato[i].cantidad);
					mi.labels.push(mi.dato[i].lugar);
					mi.totales += mi.dato[i].cantidad;
				}
				
				mi.data.push(mi.cantidades);
			}
		});
	
	mi.options = {
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
				scaleLabel: {
                    display: true,
                    labelString: 'Cantidad'
                }
			}
			],
			xAxes: [{
		    	  scaleLabel: {
                     display: true,
                     labelString: 'Municipios'
                   }
		      }
		      ]
		}
	};
	
	mi.gridOptions = {
		enableFiltering: true,
		enablePinning: true,
	    showColumnFooter: true,
		columnDefs : [
			{ name: 'lugar', displayName: 'Lugares', cellClass: 'grid-align-right'},
		    { name: 'cantidad', displayName: 'Cantidad de personas', type: 'number', cellClass: 'grid-align-left' },
			{ name: 'zona', displayName: 'Zonas', cellClass: 'grid-align-right'}
		]
	};
	
	/*-----------------------CAROUSEL-----------------------------------*/
	
	mi.interval = 5000;
	mi.wrap = false;
	mi.active = 0;
	mi.slides = [];
	var currIndex = 0;
	
	for (var i = 0; i < 4; i++) {
		mi.slides.push({
		      image: '/assets/img/slider/' + i + '.jpg',
		      text: '',
		      id: i
		 });
	 }
	
	/*-----------------------MENU REDONDO-----------------------------------*/
	
	$scope.menuConfig = {
			  "buttonWidth": 60,
			  "menuRadius": 160,
			  "color": "#ff7f7f",
			  "offset":25,
			  "textColor": "#ffffff",
			  "showIcons":false,
			  "gutter": {
			    "top": 130,
			    "right": 30,
			    "bottom": 30,
			    "left": 30
			  },
			  "angles": {
			    "topLeft": 0,
			    "topRight": 90,
			    "bottomRight": 180,
			    "bottomLeft": 270
			  }
			};


			$scope.menuItems = [{
			  "title": "iPad",
			  "color": "#ea2a29",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-tablet","size": 35}
			}, {
			  "title": "iMac",
			  "color": "#f16729",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-laptop","size": 30}
			}, {
			  "title": "iPhone",
			  "color": "#f89322",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-mobile","size": 30}
			}, {
			  "title": "iWatch",
			  "color": "#ffcf14",
			  "rotate": 0,
			  "show": 0,
			  "titleColor": "#fff",
			  "icon":{"color":"#fff","name":"fa fa-clock-o","size": 30}
			}];
			

	
}])
