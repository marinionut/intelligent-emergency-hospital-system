angular.module('RDash')
    .controller('AppointmentCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', '$filter', 'NgTableParams', AppointmentCtrl]);

function AppointmentCtrl($scope, $rootScope, $window, $http, $q, $interval, $filter, NgTableParams) {
    $scope.appointments = [];
    $scope.full = [];
    $scope.doctors = [];
    $scope.rooms = [];
    $scope.patients = [];
    $scope.failMessage = "";

    $scope.getDoctor = function (id) {
        $http({
            method: 'GET',
            url: 'http://35.234.123.144:8081/api/doctor?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getDoctors = function () {
        $http({method: 'GET', url: 'http://35.234.123.144:8081/api/doctor/all'}).then(function (response) {
            $scope.doctors = response.data;
        });

    };
    $scope.getDoctors();

    $scope.freeBedsByRoomId = [];

    $scope.getFreeBedsByRoomId = function (id) {
        console.log("trying to get free beds by room with roomId" + id);
        if (id !== undefined && id != null && id !== "")
            $http({
                method: 'GET',
                url: 'http://35.234.123.144:8081/api/room/bed/available?roomId=' + id
            }).then(function (response) {
                $scope.freeBedsByRoomId = response.data;
            });
    };

    $scope.getPatient = function (id) {
        $http({
            method: 'GET',
            url: 'http://35.234.123.144:8081/api/patient?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getPatients = function () {
        $http({method: 'GET', url: 'http://35.234.123.144:8081/api/patient/all'}).then(function (response) {
            $scope.patients = response.data;
        });

    };
    $scope.getPatients();


    $scope.getRoom = function (id) {
        $http({
            method: 'GET',
            url: 'http://35.234.123.144:8081/api/room?id=' + id
        }).then(function (response) {
            return response.data;
        });
    };
    $scope.getRooms = function () {
        $http({method: 'GET', url: 'http://35.234.123.144:8081/api/room/all'}).then(function (response) {
            $scope.rooms = response.data;
        });

    };
    $scope.getRooms();

    $scope.getAppointments = function () {
        $http({method: 'GET', url: 'http://35.234.123.144:8081/api/appointment/all'}).then(function (response) {
            $scope.appointments = response.data;

            $scope.appointmentTable = new NgTableParams({
                page: 1,
                count: 12
            }, {
                total: $scope.appointments.length,
                getData: function (params) {
                    params.total($scope.appointments.length);
                    $scope.appointments = params.sorting() ? $filter('orderBy')($scope.appointments, params.orderBy()) : $scope.appointments;
                    return $scope.appointments.slice((params.page() - 1) * params.count(), params.page() * params.count());
                }
            });
        });
    };

    $scope.getAppointments();

    $scope.addAppointment = function () {


        console.log("doctor " + $scope.inputDoctor);
        console.log("patient " + $scope.inputPatient);
        console.log("room " + $scope.inputRoom);
        console.log("room " + $scope.inputBedNumber);

        var newAppointment = {
            id: 0,
            doctorId: $scope.inputDoctor,
            patientId: $scope.inputPatient,
            roomId: $scope.inputRoom,
            bedNumber: $scope.inputBedNumber
        };

        // alert(newVM.vmName + " " + newVM.zone + " " + newVM.image);
        if (newAppointment.doctorId == null || newAppointment.doctorId == ""
            || newAppointment.patientId == null || newAppointment.patientId == ""
            || newAppointment.roomId == null || newAppointment.roomId == ""
            || newAppointment.bedNumber == null || newAppointment.bedNumber == "") {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newAppointment, url: 'http://35.234.123.144:8081/api/appointment/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getDoctors();
                $scope.failMessage = "";
                $scope.inputDoctor = "";
                $scope.inputPatient = "";
                $scope.inputRoom = "";
                $scope.inputBedNumber = "";
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
            url: 'http://35.234.123.144:8081/api/appointment/delete?id=' + id
        }).then(function (response) {
            $scope.getAppointments();
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
