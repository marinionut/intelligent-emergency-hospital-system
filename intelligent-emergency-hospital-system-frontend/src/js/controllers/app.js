angular.module('RDash')
	.controller('AlertsCtrl', ['$scope', '$rootScope', '$http', AlertsCtrl]);

function AlertsCtrl($scope, $rootScope, $http) {

    console.log($rootScope.memberinfo);

    $scope.alerts = [];

    $scope.getAllAlerts = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/alert/all'})
            .then(function (response) {
                $scope.alerts = response.data;
            });

    };
    $scope.getAllAlerts();

    $scope.getDatetime = function time(s) {
        return new Date(s * 1e3).toDateString();
    }

    $rootScope.stompClient = null;

    function setConnected(connected) {
        $("#connect").prop("disabled", connected);
        $("#disconnect").prop("disabled", !connected);
        if (connected) {
            $("#conversation").show();
        } else {
            $("#conversation").hide();
        }
        $("#greetings").html("");
    }

    $scope.connectSocket = function connect() {
        var socket = new SockJS('http://localhost:8081/gs-guide-websocket');
        $rootScope.stompClient = Stomp.over(socket);
        //stompClient.connect("","",function (frame) {
        $rootScope.stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            // $rootScope.stompClient.subscribe('/topic/alerts', function (greeting) {
            //     $scope.getAllAlerts();
            //     alertify
            //         .alert('Alerta', JSON.parse(greeting.body).name);
            //     showGreeting(JSON.parse(greeting.body).name);
            //
            //     $scope.getAllAlerts();
            //     //showGreeting("2");
            // });
            console.log("subscribing to " + '/topic/alerts/' + $rootScope.memberinfo.username);
            $rootScope.stompClient.subscribe('/topic/alerts/' + $rootScope.memberinfo.username, function (alert) {
                // alertify
                //     .alert('Alerta',
                //         JSON.parse(alert.body).message,
                //         function(){
                //             $scope.sendAlertId(JSON.parse(alert.body).username, JSON.parse(alert.body).uid);
                //         }
                alertify.confirm( JSON.parse(alert.body).message,
                        function(){
                            $scope.sendAlertId(JSON.parse(alert.body).username, JSON.parse(alert.body).uid);
                        },function(){
                            alertify.error('Declined');
                        }).set({labels:{ok:'Accept', cancel: 'Decline'}, padding: false});
                showGreeting(JSON.parse(alert.body).message);

                $scope.getAllAlerts();
            });
            console.log('Subscribed');
        });
    }

    $scope.connectSocket();

    function disconnect() {
        if ($rootScope.stompClient !== null) {
            $rootScope.stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    $scope.sendAlertId = function sendName(username, uid) {
        //alert($("#name").val());
         //$rootScope.stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
         //$rootScope.stompClient.send("/app/hello", {}, JSON.stringify({'name': 'test'}));
         //$rootScope.stompClient.send("/hello", {}, JSON.stringify({'name': $("#name").val()}));


        var message ={
            'username' : username,
            'alertUid' : uid
        };
        $rootScope.stompClient.send("/app/alert/ack", {},
            JSON.stringify({
                    'username' : username,
                    'alertUid' : uid
                }));

        //var alertAck = {alertId: 1, username: $rootScope.memberinfo.username };
        //$rootScope.stompClient.send("/alertAck", {}, JSON.stringify(alertAck));
    };

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function () {
            $scope.connectSocket();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
        // $("#send").click(function () {
        //     sendName();
        // });
    });
}