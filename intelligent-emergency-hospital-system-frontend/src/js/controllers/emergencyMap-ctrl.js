angular.module('RDash')
    .controller('EmergencyMapCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', '$filter', 'leafletData', 'FileSaver', 'NgTableParams', EmergencyMapCtrl]);

function EmergencyMapCtrl($scope, $rootScope, $window, $http, $q, $interval, $filter, leafletData, FileSaver, NgTableParams) {
    $scope.emergencies = [];
    $scope.failMessage = "";
    $scope.alertStartDate = moment().format('DD-MM-YYYY');

    $scope.getEmergencies = function (startDate) {
        var millis = new Date(startDate).getTime();
        console.log("get emergencies by start dates: " + startDate);
        $http({method: 'GET', url: 'http://localhost:8081/api/emergency?startDate=' + millis}).then(function (response) {
            $scope.emergencies = response.data;
            console.log($scope.emergencies);
            console.log(response.data.alertEntityList)
        });

    };
    $scope.getEmergencies(moment().format('DD-MM-YYYY'));

    $scope.getReadableDate = function(millis) {
        return new Date(millis);
    }


    $scope.filterByTimestamp = function(timestamp, minutes)
    {
        var d = new Date();
        d.setMinutes(d.getMinutes() - minutes);

        if(timestamp > d)
        {
            return true; // this will be listed in the results
        }

        return false; // otherwise it won't be within the results
    };

    $scope.getToday = function() {
        var today = moment().format('YYYY-MM-DD');
        $scope.alertStartDate = new Date(today);
        return today;
    };

    if (typeof(EventSource) !== "undefined") {
        var source = new EventSource('http://localhost:8081/emergency/stream');
        // var div = document.getElementById('demo');

        source.onopen = function (event) {
            // div.innerHTML += '<p>Connection open ...</p>';
            console.log("Connected open to sse notify service...")
        };

        source.onerror = function (event) {
            // div.innerHTML += '<p>Connection close.</p>';
            console.log("Error! Connection closed to sse notify service...")
        };

        source.addEventListener('connecttime', function (event) {
            // div.innerHTML += ('<p>Start time: ' + event.data + '</p>');
            console.log("Event listener start time: " + event.data);
        }, false);

        source.onmessage = function (event) {
            // div.innerHTML += ('<p>Ping: ' + event.data + '</p>');
            console.log("SSE message received " + event.data);
            console.log("reloading emergencies...");
            $scope.getEmergencies($scope.alertStartDate);

            // var notification = alertify.notify('sample', 'success', 5, function(){  console.log('dismissed'); });
            var notification = alertify.message('A aparut o noua alerta!', 10, function () {
                console.log('notification dismissed');
            });
        };
    } else {
        alert('SSE not supported by this browser');
    }

}
