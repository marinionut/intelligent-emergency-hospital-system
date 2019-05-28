'use strict';

/**
 * Route configuration for the RDash module.
 */
angular.module('RDash')

.constant("AUTH", {
    "notAuthenticated" : "auth-not-authenticated",
    "authenticated" : "auth-authenticated"
})

.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        // For unmatched routes
        $urlRouterProvider.otherwise('/');

        // Application routes
        $stateProvider
            .state('index', {
                url: '/',
                templateUrl: 'templates/welcome.html'
            })
            .state('login', {
                url: '/login',
                templateUrl: 'templates/login.html'
            })
            .state('users', {
                url: '/users',
                templateUrl: 'templates/users.html'
            })
            .state('alerts', {
                url: '/alerts',
                templateUrl: 'templates/alerts.html'
            })
            .state('room', {
                url: '/room',
                templateUrl: 'templates/room.html'
            })
            .state('patient', {
                url: '/patient',
                templateUrl: 'templates/patient.html'
            })
            .state('appointment', {
                url: '/appointment',
                templateUrl: 'templates/appointment.html'
            })
            .state('charts', {
                url: '/charts',
                templateUrl: 'templates/charts.html'
            })
            .state('doctor', {
                url: '/doctor',
                templateUrl: 'templates/doctor.html'
            });
    }
])

.run(['$rootScope', '$state', '$http', 'AuthService', 
    function($rootScope, $state, $http, AuthService) {
        $rootScope.$on('$stateChangeStart', function (event, next) {
            if (!AuthService.isAuthenticated()) {
              if (next.name !== 'login' && next.name !== 'register') {
                event.preventDefault();
                $state.go('login');
              }
            } else {
            	if(!angular.isDefined($rootScope.memberinfo) && next.name !== 'register') {
            		$http({method: 'GET', url: '/api/memberinfo'}).
            		then(function(response) {
            			if(response.data.success)
            				$rootScope.memberinfo = response.data.msg;
            		});
            	}
            }
        });
    }
]);
