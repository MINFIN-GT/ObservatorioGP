'use strict';

var app = angular.module('ngUtilidades', []);

function toFixed(num, dec) {
    var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec),    
        $arr = (result+[]).split('.'),
        $int = $arr[0] + '.',
        $dec = $arr[1] || '0';
    
    return $int + $dec + (Math.pow(10,(dec - $dec.length))+[]).substr(1);
}

app.filter('formatoMillonesSinTipo', function() {
    return function(numero, millones) {
    	if(numero != null){
	        if(millones){
	        	var res = toFixed((numero/1000000),2);
	        	return (res.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	        }
	        return (toFixed(numero,2)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    	}
    };
})

app.filter('formatoMillones', function() {
    return function(numero, millones) {
    	if(numero != null){
	        if(millones){
	        	var res = toFixed((numero/1000000),2);
	        	return ('Q '+res.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
	        }
	        return ('Q '+ toFixed(numero,2).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","));
    	}
    };
});