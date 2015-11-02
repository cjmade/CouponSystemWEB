angular.module('coupon', []);

// login controller for admin, company and customer//
angular.module('coupon').controller(
		'loginController',
		function($scope, $http) {
			// on load
			$scope.loginResult = "no body";

			// admin login
			$scope.login = function() {
				$http.post(
						"rest/admin/login/" + $scope.user + "/" + $scope.pass)
						.success(function(response) {
							$scope.loginResult = response;
						});
			};
			// admin customer
			$scope.logincust = function() {
				$http.post(
						"rest/customer/login/" + $scope.user + "/" + $scope.pass)
						.success(function(response) {
							$scope.loginResult = response;
						});
			};
			// admin company
			$scope.logincomp = function() {
				$http.post(
						"rest/company/login/" + $scope.user + "/" + $scope.pass)
						.success(function(response) {
							$scope.loginResult = response;
						});
			};
		});

angular.module('coupon').controller(
		'adminController',
		function($scope, $http, $location) {
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
				$http.post("rest/admin/DeleteCompany/" + $scope.id).success(
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
			$scope.viewAll = function() {
				$http.get("rest/admin/getAllCustomers").success(
						function(response) {
							$scope.companies = response;
						});
			};
			// get company
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
angular.module('coupon').controller(
		'companyController',
		function($scope, $http, $location) {
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
				$http.post("rest/company/DeleteCustomer/" + $scope.coupon).success(
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
angular.module('coupon').controller(
		'customerController',
		function($scope, $http, $location) {
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