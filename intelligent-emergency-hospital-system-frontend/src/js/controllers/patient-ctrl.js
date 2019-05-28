angular.module('RDash')
    .controller('PatientCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$filter','NgTableParams', PatientCtrl]);

function PatientCtrl($scope, $rootScope, $window, $http, $q, $filter, NgTableParams) {
    $scope.patients = [];
    $scope.failMessage = "";

    $scope.hideAllActions = function() {
        if ($rootScope.memberinfo.role == "read")
            return true;
        return false;
    };

    $scope.getPatients = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/patient/all'}).then(function (response) {
            $scope.patients = response.data;

            $scope.patientsTable = new NgTableParams({
                page: 1,
                count: 12
            }, {
                total: $scope.patients.length,
                getData: function (params) {
                    params.total($scope.patients.length);
                    $scope.patients = params.sorting() ? $filter('orderBy')($scope.patients, params.orderBy()) : $scope.patients;
                    return $scope.patients.slice((params.page() - 1) * params.count(), params.page() * params.count());
                }
            });
        });
    };
    $scope.getPatients();

    $scope.addPatient = function () {

        var newPatient = {
            id: 0,
            firstName: $scope.inputPatientFirstName,
            lastName: $scope.inputPatientLastName,
            address: $scope.inputPatientAddress,
            phoneNumber: $scope.inputPatientPhoneNumber,
            age: $scope.inputPatientAge,
            gender: $scope.inputPatientGender,
            cnp: $scope.inputPatientCnp
        };

        // alert(newVM.vmName + " " + newVM.zone + " " + newVM.image);
        if (newPatient.firstName == null || newPatient.firstName == ""
            || newPatient.lastName == null || newPatient.lastName == ""
            || newPatient.address == null || newPatient.address == ""
            || newPatient.phoneNumber == null || newPatient.phoneNumber == ""
            || newPatient.age == null || newPatient.age == ""
            || newPatient.gender == null || newPatient.gender == ""
            || newPatient.cnp == null || newPatient.cnp == "") {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newPatient, url: 'http://localhost:8081/api/patient/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getPatients();
                $scope.failMessage = "";
                $scope.inputPatientFirstName = "";
                $scope.inputPatientLastName = "";
                $scope.inputPatientAddress = "";
                $scope.inputPatientPhoneNumber = "";
                $scope.inputPatientAge = "";
                $scope.inputPatientGender = "";
                $scope.inputPatientCnp = "";
            } else if (response.status == 500) {
                $scope.failMessage = response.data;
                window.alert($scope.failMessage);
                window.alert("Error!")
            }
        });
    };

    $scope.deletePatient = function (id) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8081/api/patient/delete?id=' + id
        }).then(function (response) {
            $scope.getPatients();
        });

    };

    $scope.hideDelete = function () {
        if ($rootScope.memberinfo.role == "doctor")
            return true;
        if ($rootScope.memberinfo.role == "doctorstuff")
            return true;
        if ($rootScope.memberinfo.role == "admin")
            return false;
        return false;
    };
}
