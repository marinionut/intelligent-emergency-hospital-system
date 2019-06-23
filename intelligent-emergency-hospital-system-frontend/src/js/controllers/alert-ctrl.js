angular.module('RDash')
    .controller('AlertsCtrl', ['$scope', '$rootScope', '$http', '$filter', 'NgTableParams', AlertsCtrl]);

function AlertsCtrl($scope, $rootScope, $http, $filter, NgTableParams) {

    console.log($rootScope.memberinfo);

    $scope.alerts = [];

    $scope.getAllAlerts = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/alert/all'})
            .then(function (response) {
                $scope.alerts = response.data;

                $scope.alertsTable = new NgTableParams({
                    page: 1,
                    count: 12
                }, {
                    total: $scope.alerts.length,
                    getData: function (params) {
                        params.total($scope.alerts.length);
                        $scope.alerts = params.sorting() ? $filter('orderBy')($scope.alerts, params.orderBy()) : $scope.alerts;
                        return $scope.alerts.slice((params.page() - 1) * params.count(), params.page() * params.count());
                    }
                });
            });

    };
    $scope.getAllAlerts();

    $scope.getDatetime = function time(s) {
         return new Date(s).toUTCString();
    };

    $rootScope.stompClient = null;

    // function setConnected(connected) {
    //     $("#connect").prop("disabled", connected);
    //     $("#disconnect").prop("disabled", !connected);
    //     if (connected) {
    //         $("#conversation").show();
    //     } else {
    //         $("#conversation").hide();
    //     }
    //     $("#greetings").html("");
    // }

    $rootScope.connectSocket = function connect() {
        console.log("trying to coonect to socket");
        var socket = new SockJS('http://localhost:8081/gs-guide-websocket');
        $rootScope.stompClient = Stomp.over(socket);

        $rootScope.stompClient.connect({}, function (frame) {
            // setConnected(true);
            console.log('Connected: ' + frame);

            console.log("subscribing to " + '/topic/alerts/' + $rootScope.memberinfo.username);
            $rootScope.stompClient.subscribe('/topic/alerts/' + $rootScope.memberinfo.username, function (alert) {
                alertify.confirm( JSON.parse(alert.body).message,
                    function(){
                        alertify.success('Accepted');
                        $scope.sendAlertId(JSON.parse(alert.body).username, JSON.parse(alert.body).uid);
                    },function(){
                        alertify.error('Declined');
                    }).set({title:"Alerta"}, {labels:{ok:'Accept', cancel: 'Decline'}, padding: false});
                // showGreeting(JSON.parse(alert.body).message);
                //$scope.getAllAlerts();
            });
            console.log('Subscribed');
        });
    };

    $scope.connectSocket();

    $rootScope.disconnectSocket = function disconnect() {
        try {
            // if ($rootScope.stompClient != null) {
                $rootScope.stompClient.disconnect();
            // }
            console.log("Disconnected");
        }
        catch(err) {
            console.log("Error on socket disconnecting " + err.message);
        }
    }

    $scope.sendAlertId = function sendName(username, uid) {

        $rootScope.stompClient.send("/app/alert/ack", {},
            JSON.stringify({
                'username' : username,
                'alertUid' : uid
            }));
    };

    // $(function () {
    //     $("form").on('submit', function (e) {
    //         e.preventDefault();
    //     });
    //     $("#connect").click(function () {
    //         $scope.connectSocket();
    //     });
    //     $("#disconnect").click(function () {
    //         disconnect();
    //     });
    // });
}