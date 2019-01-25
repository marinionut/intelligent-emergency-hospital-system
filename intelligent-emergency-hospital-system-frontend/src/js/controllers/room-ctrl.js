angular.module('RDash')
    .controller('RoomCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', 'leafletData', 'FileSaver', RoomCtrl]);

function RoomCtrl($scope, $rootScope, $window, $http, $q, $interval, leafletData, FileSaver) {
    $scope.rooms = [];
    $scope.failMessage = "";

    $scope.hideAllActions = function() {
        if ($rootScope.memberinfo.role == "read")
            return true;
        return false;
    };

    $scope.getRooms = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/room/all'}).then(function (response) {
            $scope.rooms = response.data;
        });

    };
    $scope.getRooms();


    $scope.addRoom = function () {

        var newRoom = {
            id: 0,
            number: $scope.inputRoomNumber,
            description: $scope.inputRoomDescription,
            bedNumber: $scope.inputRoomBedNumber
        };

        if (newRoom.number == null || newRoom.number == ""
            || newRoom.description == null || newRoom.description == "") {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newRoom, url: 'http://localhost:8081/api/room/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getRooms();
                $scope.failMessage = "";
                $scope.inputRoomNumber = "";
                $scope.inputRoomDescription = "";
                $scope.inputRoomBedNumber = "";
            } else if (response.status == 500) {
                $scope.failMessage = response.data;
                window.alert($scope.failMessage);
                window.alert("Error!")
            }
        });

        $scope.getRooms();
    };

    $scope.deleteRoom = function (id) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8081/api/room/delete?id=' + id
        }).then(function (response) {
            $scope.getRooms();
        });

    };

    $scope.hideDelete = function () {
        if ($rootScope.memberinfo.role == "doctor")
            return true;
        if ($rootScope.memberinfo.role == "guest")
            return true;
        if ($rootScope.memberinfo.role == "admin")
            return false;
        return false;
    };
}