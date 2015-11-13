(function(angular){
	'use strict'
	angular.module('firebaseapp').controller('logout',function($scope,$firebaseauth,FBURL,$window){
		var fberf=new Firebase(FBURL);
		$scope.simplelogin=$firebaseauth(fbref);
		$scope.simplelogin
	}
}