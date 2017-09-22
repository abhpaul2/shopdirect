/**
 * 
 */

(function () {
	'use strict';
	
	angular.module('app')
			.controller('PersonController',PersonController);
	
	console.debug("Testing..1");
	PersonController.$inject = ['$http'];
	
	function PersonController ($http) {
		var vm = this;
		console.debug("Testing..2");
//		vm.persons = [{
//		              name : "Abhijit",
//		              emailId : "abhi@in"
//						}
//		              ];
		
//		
//		init();		
//		init() {	
			console.debug("Testing..3 init call");
			var url = "http://localhost:8080/persons/all"
			var allperson = $http.get(url);
			allperson.then(function(response) {
				vm.persons = response.data;
			},
			function(errResponse) {
				console.error("fail to fatch..2");;
			}
			
			);
	//	}
	}	
})();