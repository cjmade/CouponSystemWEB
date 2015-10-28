angular.module('coupons',[]);

angular.module('coupons').controller('mainPanel', function($rootScope,$scope){
	$rootScope.adminFlag=false;
	$rootScope.customerFlag=false;
	$rootScope.companyFlag=false;
	$scope.showAdmin = function(){
		if($rootScope.adminFlag==false){$rootScope.adminFlag=true;}else{$rootScope.adminFlag=false;}
	};
	$scope.showCustomer = function(){
		if($rootScope.customerFlag==false){$rootScope.customerFlag=true;}else{$rootScope.customerFlag=false;}
	};
	$scope.showCompany = function(){
		if($rootScope.companyFlag==false){$rootScope.companyFlag=true;}else{$rootScope.companyFlag=false;}
	};
});
angular.module('coupons').controller('itemController', function($scope, $http){
	
		
	
	//on click - all items table
	$scope.viewAll = function(){
		$http.get("rest/srvAdmin/getAllCustomers").success(function(response){
			$scope.allItemsResult=response.item;
		});
	};	
});
