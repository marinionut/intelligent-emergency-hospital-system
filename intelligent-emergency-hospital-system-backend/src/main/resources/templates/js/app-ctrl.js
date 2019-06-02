angular.module('HospitAlert')
    .controller('HospitAlertCtrl', ['$scope', '$rootScope', '$http' , '$window', HospitAlertCtrl]);

function HospitAlertCtrl($scope, $rootScope, $http, $window) {
    $scope.firstName = "Marin";
    $scope.lastName= "Ionut";
    $scope.inputRoomNumber = $window.localStorage.getItem('inputRoomNumber') || 0;
    $scope.inputBed = $window.localStorage.getItem('inputBed') || 0;

    $scope.registerRoomAndBedNumber = function() {
        console.log(" register room: " + $scope.inputRoomNumber + ";" + 'bed' + $scope.inputBed);
        $window.localStorage.setItem('inputRoomNumber', $scope.inputRoomNumber);
        $window.localStorage.setItem('inputBed', $scope.inputBed);

        alertify.success('Informatii salvate!');
    };

    $scope.getCachedRoom = function() {
        $scope.roomNumber = $window.localStorage.getItem('inputRoomNumber') || 0;
        console.log("from cache room: " + $scope.roomNumber);
        return $scope.roomNumber;
    };

    $scope.getCachedBed = function() {
        $scope.bed =  $window.localStorage.getItem('inputBed') || 0;
        console.log("from cache bed: " + $scope.bed);
        return $scope.bed;
    };

    console.log("room:" + $window.localStorage.getItem('inputRoomNumber'));
    console.log("bed:" + $window.localStorage.getItem('inputBed'));


    $scope.needHelp =  function () {
            console.log("resolving alert for room: " + $scope.inputRoomNumber + ";" + 'bed' + $scope.inputBed);

            $http({
                method: 'GET',
                url: "/resolveAlert?roomNumber=" + $scope.inputRoomNumber + "&bedNumber=" + $scope.inputBed
            })
                .then(function (response) {
                    console.log("RASPUNS:" + response);
                    if (response.status == 200) {
                        $scope.failMessage = "";
                        //alertify.alert('Raspuns', 'Alerta trimisa cu success!', function(){  });
                        alertify.success('Alerta a fost trimisa!');
                    } else if (response.status == 500) {
                        //var obj = JSON.parse(response.data);
                        //$scope.failResponse = obj.message;
                        //alertify.alert('Raspuns', "EROARE!", function(){ });
                        alertify.warning('Alerta nu a fost trimisa!');
                    }
                },
                function(data) {
                    $scope.failMessage = data.data.message;
                    console.log("Fail message: " + $scope.failMessage);
                    alertify.warning('Alerta nu a putut fi trimisa!');
                });
        }
}

