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

    function connect() {
        var socket = new SockJS('http://localhost:8081/gs-guide-websocket');
        $rootScope.stompClient = Stomp.over(socket);
        //stompClient.connect("","",function (frame) {
        $rootScope.stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            $rootScope.stompClient.subscribe('/topic/alerts', function (greeting) {
                $scope.getAllAlerts();
                alertify
                    .alert('Alerta', JSON.parse(greeting.body).name);
                showGreeting(JSON.parse(greeting.body).name);

                $scope.getAllAlerts();
                //showGreeting("2");
            });
            console.log("subscribing to " + '/topic/alerts/' + $rootScope.memberinfo.username);
            $rootScope.stompClient.subscribe('/topic/alerts/' + $rootScope.memberinfo.username, function (greeting) {
                alertify
                    .alert('Alerta', JSON.parse(greeting.body).name);
                showGreeting(JSON.parse(greeting.body).name);

                $scope.getAllAlerts();
                //showGreeting("2");
            });
            console.log('Subscribed');
        });
    }

    function disconnect() {
        if ($rootScope.stompClient !== null) {
            $rootScope.stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    function sendName() {
        alert($("#name").val());
        $rootScope.stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
        $rootScope.stompClient.send("/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }

    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $("#connect").click(function () {
            connect();
        });
        $("#disconnect").click(function () {
            disconnect();
        });
        $("#send").click(function () {
            sendName();
        });
    });
}