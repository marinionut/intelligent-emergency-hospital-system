angular.module('RDash')
    .controller('MedicalEquipmentCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$filter','NgTableParams', MedicalEquipmentCtrl]);

function MedicalEquipmentCtrl($scope, $rootScope, $window, $http, $q, $filter, NgTableParams) {
    $scope.medical_equipment = [];
    $scope.failMessage = "";

    $scope.hideAllActions = function() {
        if ($rootScope.memberinfo.role == "read")
            return true;
        return false;
    };

    $scope.getMedicalEquipment = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/medicalEquipment/all'}).then(function (response) {
            $scope.medical_equipment = response.data;

            $scope.medicalEquipmentTable = new NgTableParams({
                page: 1,
                count: 6
            }, {
                total: $scope.medical_equipment.length,
                getData: function (params) {
                    params.total($scope.medical_equipment.length);
                    $scope.medical_equipment = params.sorting() ? $filter('orderBy')($scope.medical_equipment, params.orderBy()) : $scope.medical_equipment;
                    return $scope.medical_equipment.slice((params.page() - 1) * params.count(), params.page() * params.count());
                }
            });
        });
    };
    $scope.getMedicalEquipment();

    $scope.addMedicalEquipment = function () {

        var newMedicalEquipment = {
            id: 0,
            name: $scope.inputMedicalEquipmentName,
            roomNumber: $scope.inputMedicalEquipmentRoomNumber,
            floor: $scope.inputMedicalEquipmentFloor,
        };

        // alert(newVM.vmName + " " + newVM.zone + " " + newVM.image);
        if (newMedicalEquipment.name == null || newMedicalEquipment.name == ""
            || newMedicalEquipment.roomNumber == null || newMedicalEquipment.roomNumber == ""
            || newMedicalEquipment.floor == null || newMedicalEquipment.floor == "") {
            window.alert("Va rugam completati toate campurile!");
            return;
        }

        $http({method: 'POST', data: newMedicalEquipment, url: 'http://localhost:8081/api/medicalEquipment/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.getMedicalEquipment();
                $scope.failMessage = "";
                $scope.inputMedicalEquipmentName = "";
                $scope.inputMedicalEquipmentRoomNumber = "";
                $scope.inputMedicalEquipmentFloor = "";
            } else if (response.status == 500) {
                $scope.failMessage = response.data;
                window.alert($scope.failMessage);
                window.alert("Error!")
            }
        });
    };

    $scope.deleteMedicalEquipment = function (id) {
        $http({
            method: 'DELETE',
            url: 'http://localhost:8081/api/medicalEquipment/delete?id=' + id
        }).then(function (response) {
            $scope.getMedicalEquipment();
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


    $scope.changeStatus =  function (id, status) {
        console.log("id" + id + ";" + 'status' + status);

        $http({
            method: 'GET',
            url: 'http://localhost:8081/api/medicalEquipment/update?id=' + id + "&status=" + status
        })
            .then(function (response) {
                if (response.status == 200) {
                    $scope.getMedicalEquipment();
                    $scope.failMessage = "";
                } else if (response.status == 500) {
                    $scope.failMessage = response.data;
                    window.alert($scope.failMessage);
                    window.alert("Error!")
                }
        });
    }
}
