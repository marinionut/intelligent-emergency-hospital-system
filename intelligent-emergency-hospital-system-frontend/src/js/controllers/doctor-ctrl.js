angular.module('RDash')
    .controller('DoctorCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', '$filter', 'NgTableParams', DoctorCtrl]);

function DoctorCtrl($scope, $rootScope, $window, $http, $q, $interval, $filter, NgTableParams) {
    $scope.doctors = [];
    $scope.failMessage = "";

    $scope.hideAllActions = function() {
        if ($rootScope.memberinfo.role != "admin")
            return true;
        return false;
    };


    $scope.getDoctors = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/doctor/all'}).then(function (response) {
            $scope.doctors = response.data;

            $scope.doctorsTable = new NgTableParams({
                page: 1,
                count: 12
            }, {
                total: $scope.doctors.length,
                getData: function (params) {
                    params.total($scope.doctors.length);
                    $scope.doctors = params.sorting() ? $filter('orderBy')($scope.doctors, params.orderBy()) : $scope.doctors;
                    return $scope.doctors.slice((params.page() - 1) * params.count(), params.page() * params.count());
                }
            });
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
            specialization: $scope.inputDoctorSpecializare,
            userId: $scope.inputDoctorUser
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
                $scope.inputUserId = "";
                $scope.inputDoctorUser ="";

                $scope.getAvailableUsers();
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

    $scope.availableUsers  =[];

    $scope.getAvailableUsers = function () {
        $http({
            method: 'GET',
            url: 'http://localhost:8081/api/doctor/available-users'
        }).then(function (response) {
            console.log(response.data);
            $scope.availableUsers =response.data;
        });
    };
    $scope.getAvailableUsers();

    $scope.getUserById = function (id) {
        if (id === -1) {
            return "-nedefinit-";
        } else {
            $http({
                method: 'GET',
                url: 'http://localhost:8081/api/user?id=' + id
            }).then(function (response) {
                console.log("raspuns getUser by id:");
                console.log(response.data);
                return response.data.username;
            });
        }
    }


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
