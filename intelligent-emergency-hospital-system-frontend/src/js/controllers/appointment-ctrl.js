angular.module('RDash')
    .controller('AppointmentCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', 'leafletData', 'FileSaver', AppointmentCtrl]);

function AppointmentCtrl($scope, $rootScope, $window, $http, $q, $interval, leafletData, FileSaver) {
    $scope.appointments = [];
    $scope.full = [];
    $scope.doctors = [];
    $scope.rooms = [];
    $scope.patients = [];
    $scope.failMessage = "";

    $scope.getDoctor = function (id) {
        $http({
            method: 'GET',
            url: 'http://localhost:8081/api/doctor?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getDoctors = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/doctor/all'}).then(function (response) {
            $scope.doctors = response.data;
        });

    };
    $scope.getDoctors();


    $scope.getPatient = function (id) {
        $http({
            method: 'GET',
            url: 'http://localhost:8081/api/patient?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getPatients = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/patient/all'}).then(function (response) {
            $scope.patients = response.data;
        });

    };
    $scope.getPatients();


    $scope.getRoom = function (id) {
        $http({
            method: 'GET',
            url: 'http://localhost:8081/api/room?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getRooms = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/room/all'}).then(function (response) {
            $scope.rooms = response.data;
        });

    };
    $scope.getRooms();

    $scope.getAppointments = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/appointment/all'}).then(function (response) {
            $scope.appointments = response.data;
        });
    };

    $scope.getAppointments();



    $scope.addAppointment = function () {


        console.log("doctor " + $scope.inputDoctor);
        console.log("patient " + $scope.inputPatient);
        console.log("room " + $scope.inputRoom);

        var newAppointment = {
            id: 0,
            doctorId: $scope.inputDoctor,
            patientId: $scope.inputPatient,
            roomId: $scope.inputRoom
        };

        // alert(newVM.vmName + " " + newVM.zone + " " + newVM.image);
        if (newAppointment.doctorId == null || newAppointment.doctorId == ""
            || newAppointment.patientId == null || newAppointment.patientId == ""
            || newAppointment.roomId == null || newAppointment.roomId == "") {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newAppointment, url: 'http://localhost:8081/api/appointment/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getDoctors();
                $scope.failMessage = "";
                $scope.inputDoctor = "";
                $scope.inputPatient = "";
                $scope.inputRoom = "";
                $scope.getAppointments();
            } else if (response.status == 500) {
                $scope.failMessage = response.data;
                window.alert($scope.failMessage);
                window.alert("Error!")
            }
        });

        $scope.getAppointments();
    };

    $scope.deleteAppointment = function (id) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8081/api/appointment/delete?id=' + id
        }).then(function (response) {
            $scope.getAppointments();
        });

    };
}
