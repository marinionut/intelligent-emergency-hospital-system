angular.module('RDash')
    .controller('DoctorCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', 'leafletData', 'FileSaver', DoctorCtrl]);

function DoctorCtrl($scope, $rootScope, $window, $http, $q, $interval, leafletData, FileSaver) {
    $scope.doctors = [];
    $scope.failMessage = "";

    $scope.hideAllActions = function() {
        if ($rootScope.memberinfo.role == "read")
            return true;
        return false;
    };

    $scope.getDoctors = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/doctor/all'}).then(function (response) {
            $scope.doctors = response.data;
        });

    };
    $scope.getDoctors();


    $scope.addDoctor = function () {

        var newDoctor = {
            id: 0,
            firstName: $scope.inputDoctorFirstName,
            lastName: $scope.inputDoctorLastName,
            email: $scope.inputDoctorEmail,
            phoneNumber: $scope.inputDoctorTelefon,
            specialization: $scope.inputDoctorSpecializare
        };

        // alert(newVM.vmName + " " + newVM.zone + " " + newVM.image);
        if (newDoctor.firstName == null || newDoctor.firstName == ""
            || newDoctor.lastName == null || newDoctor.lastName == ""
            || newDoctor.email == null || newDoctor.email == ""
            || newDoctor.phoneNumber == null || newDoctor.phoneNumber == ""
            || newDoctor.specialization == null || newDoctor.specialization == "") {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newDoctor, url: 'http://localhost:8081/api/doctor/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getDoctors();
                $scope.failMessage = "";
                $scope.inputDoctorFirstName = "";
                $scope.inputDoctorLastName = "";
                $scope.inputDoctorEmail = "";
                $scope.inputDoctorTelefon = "";
                $scope.inputDoctorSpecializare = "";
            } else if (response.status == 500) {
                $scope.failMessage = response.data;
                window.alert($scope.failMessage);
                window.alert("Error!")
            }
        });

        $scope.getDoctors();
    };

    $scope.deleteDoctor = function (id) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8081/api/doctor/delete?id=' + id
        }).then(function (response) {
            $scope.getDoctors();
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
