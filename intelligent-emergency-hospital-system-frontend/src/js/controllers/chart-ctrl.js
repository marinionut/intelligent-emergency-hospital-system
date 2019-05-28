angular.module('RDash')
    .controller('ChartCtrl', ['$scope', '$rootScope', '$window', '$http', '$q', '$interval', '$filter', 'leafletData', ChartCtrl]);

function ChartCtrl($scope, $rootScope, $window, $http, $q, $interval, $filter, leafletData, FileSaver ) {
    $scope.doctorBySpecialization = {};

    $scope.chartList = [{
        "chartid": 1,
        "name": "Medici in functie de specializare",
        "show": false
    }, {
        "chartid": 2,
        "name": "Rohini",
        "show": false
    }, {
        "chartid": 3,
        "name": "Praveen",
        "show": false
    }];

    $scope.showChart = function () {
        for(var chart in chartlist)
            chart.show = false;

        $scope.chartList[$scope.userselected.userid].show = true;
    };

    $scope.getDoctorBySpecialization = function () {
        $http({method: 'GET', url: 'http://localhost:8081/chart/doctorBySpecialization'}).then(function (response) {

            $scope.doctorBySpecJson = {
                globals: {
                    shadow: false,
                    fontFamily: "Verdana",
                    fontWeight: "100"
                },
                type: "pie",
                backgroundColor: "#fff",

                title:{
                    backgroundColor : "transparent",
                    fontColor :"black",
                    text : "Doctori in functie de specializare"
                },

                legend: {
                    layout: "x5",
                    position: "50%",
                    borderColor: "transparent",
                    marker: {
                        borderRadius: 10,
                        borderColor: "transparent"
                    }
                },
                tooltip: {
                    text: "%v medici"
                },
                plot: {
                    refAngle: "-90",
                    borderWidth: "0px",
                    valueBox: {
                        placement: "in",
                        text: "%npv %",
                        fontSize: "15px",
                        textAlpha: 1
                    }
                },
                series: []
            };

            console.log(response.data);
            $scope.doctorBySpecialization = response.data;

            angular.forEach(response.data, function (value, key) {
                $scope.doctorBySpecJson.series.push({text: key, values:[value]});
            });
            return response.data;
        });
    };
    $scope.getDoctorBySpecialization();


    //CHART 2
    $scope.getAppointmentsCalendar = function () {
        $http({method: 'GET', url: 'http://localhost:8081/chart/appointmentCalendar'}).then(function (response) {

            console.log("test");

            $scope.appointmentCalendarJson = response.data;

            return response.data;
        });
    };

    $scope.getAppointmentsCalendar();

    //CHART 3
    $scope.getAppointmentsCalendarZwing = function () {
        $http({method: 'GET', url: 'http://localhost:8081/chart/appointmentCalendar2'}).then(function (response) {
            console.log("testtt2");

            console.log(Object.entries(response.data));

            $scope.appointmentCalendarZwingJson = {
                type: 'calendar',
                plot: {
                    valueBox: { // Use this object to configure the value boxes.
                        fontColor: 'black',
                        fontFamily: 'Courier New',
                        fontSize: 12,
                        fontWeight: 'normal'
                    },
                },
                options: {
                    year: {
                        text: '2019', //Set the calendar year.
                        backgroundColor: '#ffe6e6',
                        borderColor: 'blue',
                        borderRadius: 3,
                        borderWidth: 1,
                        fontColor: 'blue',
                        fontFamily: 'Georgia',
                        visible: true
                    },

                    values:
                        Object.entries(response.data)
                },
                plotarea: {
                    marginTop: '5%',
                    marginBottom: '5%'
                }
            };

            console.log("test22");
            return response.data;
        });
    };

    $scope.getAppointmentsCalendarZwing();
}
