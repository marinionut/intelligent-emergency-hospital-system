/**
 * Master Controller
 */

angular.module('RDash')
    .controller('MasterCtrl', ['$scope', '$cookieStore', '$rootScope', '$http', '$state', '$location', 'AuthService', 'AUTH', MasterCtrl]);

function MasterCtrl($scope, $cookieStore, $rootScope, $http, $state, $location, AuthService, AUTH) {
    /**
     * Sidebar Toggle & Cookie Control
     */
    var mobileView = 992;

    $scope.getWidth = function() {
        return window.innerWidth;
    };

    $scope.$watch($scope.getWidth, function(newValue, oldValue) {
        if (newValue >= mobileView) {
            if (angular.isDefined($cookieStore.get('toggle'))) {
                $scope.toggle = ! $cookieStore.get('toggle') ? false : true;
            } else {
                $scope.toggle = true;
            }
        } else {
            $scope.toggle = false;
        }

    });

    $scope.toggleSidebar = function() {
        $scope.toggle = !$scope.toggle;
        $cookieStore.put('toggle', $scope.toggle);
    };

    window.onresize = function() {
        $scope.$apply();
    };

    $scope.logout = function(){
        AuthService.logout();
        $rootScope.memberinfo = undefined;
        $state.go('login');
    };

    $scope.$on(AUTH.notAuthenticated, function(event){
        AuthService.logout();
        $state.go('login');
    });

    $scope.$on(AUTH.authenticated, function(){
        $http.get('http://localhost:8081/api/memberinfo').then(function(result){
            if(result.data.success){
                $rootScope.memberinfo = angular.fromJson(result.data.msg);
                console.log("memberinfo  " + $rootScope.memberinfo);
            }
        });
    });

    $scope.isLogged = function(){
        if (AuthService.isAuthenticated()){
            $http.get('http://localhost:8081/api/memberinfo').then(function(result){
                if(result.data.success){
                    $rootScope.memberinfo = angular.fromJson(result.data.msg);
                    console.log("memberinfo  " + $rootScope.memberinfo);
                }
            });
        }
    };
    $scope.isLogged();

    $scope.pageName = "";
    $rootScope.$on('$stateChangeStart', function (event, next) {
        if(next.name === 'index') {
            $scope.pageName = "HospitAlert";
        }  else if(next.name === 'users') {
            $scope.pageName = "Utilizatori";
        }  else if(next.name === 'alerts') {
            $scope.pageName = "Alerte";
        }  else if(next.name === 'doctor') {
            $scope.pageName = "Doctori";
        }  else if(next.name === 'patient') {
            $scope.pageName = "Pacienti";
        }  else if(next.name === 'room') {
            $scope.pageName = "Camere";
        } else if(next.name === 'appointment') {
            $scope.pageName = "Internari";
        } else if(next.name === 'charts') {
            $scope.pageName = "Rapoarte";
        } else if(next.name === 'emergencyMap') {
            $scope.pageName = "Harta urgente";
        } else if(next.name === 'medicalEquipment') {
            $scope.pageName = "Echipament";
        } else {
            $scope.pageName = "";
        }
    });

    $scope.hideForMedicalEquipmentConsole = function() {
        console.log($location.path());
        if ($location.path() == '/medicalEquipmentConsole')
            return true;
        return false;
    }
}