'use strict';

var app = angular.module('ngUtilidades', []);

app.filter('formatoMillonesSinTipo', function() {
    return function(numero, millones) {
    	if(numero != null){
	        if(millones){
	        	var res = ((numero/1000000).toFixed(2));
	        	return (res.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	        }
	        return ((Number(numero).toFixed(2)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    	}
    };
})

app.filter('formatoMillones', function() {
    return function(numero, millones) {
    	if(numero != null){
	        if(millones){
	        	var res = ((numero/1000000).toFixed(2));
	        	return ('Q '+res.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	        }
	        return ('Q '+ (Number(numero).toFixed(2)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    	}
    };
});