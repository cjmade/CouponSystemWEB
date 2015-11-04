var app=angular.module('coupon',['ngRoute']);
app.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl: 'templates/home.html',
			controller: 'loginController'
		})
		.when('/adminPage', {
		templateUrl: "templates/Adminlogin.html",
			controller: 'loginController'
	})
		.when('/CustomerPage', {
		templateUrl: "templates/Customerlogin.html",
		controller: 'loginController'
	})
		.when('/CompanyPage', {
		templateUrl: "templates/Companylogin.html",
		controller: 'loginController'
	})
		.when('/newcompany', {
		templateUrl: "templates/newcompany.html",
		controller: 'adminController'
	})
		.when('/updatecompany', {
		templateUrl: "templates/updatecompany.html",
		controller: 'adminController'
	})
	.when('/managecompany', {
		templateUrl: "templates/deleteshowcomp.html",
		controller: 'adminController'
	})
	.when('/custcoupons', {
		templateUrl: "templates/custcoupons.html",
		controller: 'customerController'
	})
	.when('/custcouponsprice', {
		templateUrl: "templates/custcouponsprice.html",
		controller: 'customerController'
	})
	.when('/custcouponstype', {
		templateUrl: "templates/custcouponstype.html",
		controller: 'customerController'
	})
	.when('/purchasecoupons', {
		templateUrl: "templates/purchasecoupons.html",
		controller: 'customerController'
	})
	.when('/newcoupon', {
		templateUrl: "templates/newcoupon.html",
		controller: 'companyController'
	})
		.otherwise({
			redirectTo: '/'
		});
});
// login controller for admin, company and customer//
app.controller(
		'loginController',
		function($scope, $http,$location,$rootScope) {
			// on load
			$scope.loginResult = "no body";
var log=$scope.type;	
			// admin login
			$scope.login = function() {
				$http.post(
						"rest/"+$scope.type+"/login/" + $scope.user + "/" + $scope.pass)
						.success(function(response) {
							if (response == "success"){
								$rootScope.username = $scope.user;
								switch ($scope.type) 
								{
								case "customer":
									$location.path("/CustomerPage");
									break; 				
								case "company":
									$location.path("/CompanyPage");
									break;
								case "admin":
									$location.path("/adminPage");
									break;
								default:
									$location.path("/");
									break;
								}			
							}
							else {
								$scope.loginlog = response;
							}
						});
			};
			
		});

app.controller(
		'adminController',
		function($scope, $http,$location,$rootScope) {
		///////company/////////
			// get all companies
			$scope.viewAll = function() {
				$http.get("rest/admin/getAllCompanies").success(
						function(response) {
							$scope.companies = response;
						});
			};
			// get company
			$scope.viewcompany = function() {
				$http.get("rest/admin/GetCompany" + $scope.id).success(
						function(response) {
							$scope.company = response;
						});
			};
			// new company
			$scope.getnew = function() {
				$http.post(
						"rest/admin/AddCompany/" + $scope.user + "/"
								+ $scope.mail + "/" + $scope.pass).success(
						function(response) {
							$scope.newcompany = response;
							
						});
			};
			// DeleteCompany
			$scope.erase = function() {
				$http.delete("rest/admin/DeleteCompany/" + $scope.id).success(
						function(response) {
							$scope.deletecompany = response;
						});
			};
			// updateCompany
			$scope.update = function() {
				$http.put(
						"rest/admin/UpdateCompany/" + $scope.id + "/"
								+ $scope.pass + "/" + $scope.mail).success(
						function(response) {
							$scope.updatecompany = response;
						});
			};
///////customer/////////
			// get all customers
			$scope.viewAllcust = function() {
				$http.get("rest/admin/getAllCustomers").success(
						function(response) {
							$scope.customers = response;
						});
			};
			// get customer
			$scope.viewcustomer = function() {
				$http.get("rest/admin/GetCustomer" + $scope.id).success(
						function(response) {
							$scope.customer = response;
						});
			};
			// new customer
			$scope.getnewcust = function() {
				$http.post(
						"rest/admin/AddCustomer/" + $scope.CustName + "/"
								+ $scope.pass).success(
						function(response) {
							$scope.newcustomer= response;
						});
			};
			// DeleteCustomer
			$scope.erasecustomer = function() {
				$http.post("rest/admin/DeleteCustomer/" + $scope.id).success(
						function(response) {
							$scope.deletecustomer= response;
						});
			};
			// updateCustomer
			$scope.updatecustomer = function() {
				$http.put(
						"rest/admin/UpdateCustomer/" + $scope.id + "/"
								+ $scope.pass ).success(
						function(response) {
							$scope.updatecustomer = response;
						});
			};
		});

/////////company service////////
app.controller(
		'companyController',
		function($scope, $http,$location,$rootScope) {
		///////company/////////
			// get all coupons
			$scope.viewAllcoupons = function() {
				$http.get("rest/company/getAllCoupons").success(
						function(response) {
							$scope.coupons = response;
						});
			};
			// new coupon
			$scope.getnewcoup = function() {
				$http.post(
						"rest/company/createCoupon/" + $scope.coupon).success(
						function(response) {
							$scope.newcoup= response;
						});
			};
			// DeleteCoupon
			$scope.erasecoupon = function() {
				$http.post("rest/company/DeleteCoupon/" + $scope.coupon).success(
						function(response) {
							$scope.deletecoupon= response;
						});
			};
			// updateCoupon
			$scope.updatecoupon = function() {
				$http.put(
						"rest/company/updateCoupon/" + $scope.coupon).success(
						function(response) {
							$scope.updatecoup = response;
						});
			};
		});
/////////customer service////////
app.controller(
		'customerController',
		function($scope, $http,$location,$rootScope) {
		///////company/////////
			// get all coupons
			$scope.viewAllcustomercoupons = function() {
				$http.get("rest/customer/getAllCoupons").success(
						function(response) {
							$scope.custcoupons = response;
						});
			};
			// get coupon by price
			$scope.getcoupbyprice = function() {
				$http.post(
						"rest/customer/ByPrice/" + $scope.price).success(
						function(response) {
							$scope.getcoup= response;
						});
			};
			// get coupon by type
			$scope.getcoupbyTYPE = function() {
				$http.post(
						"rest/customer/ByType/" + $scope.type).success(
						function(response) {
							$scope.getcoupTYPE= response;
						});
			};
			// purchaseCoupon
			$scope.purchaseCoupon = function() {
				$http.put(
						"rest/customer/purchaseCoupon/" + $scope.coupon).success(
						function(response) {
							$scope.purchcoupon = response;
						});
			};
		});