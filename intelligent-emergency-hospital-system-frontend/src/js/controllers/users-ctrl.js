angular.module('RDash')
    .controller('UsersCtrl', ['$q', '$scope', '$rootScope', '$http', '$window', '$filter', 'NgTableParams', UsersCtrl]);

function UsersCtrl($q, $scope, $rootScope, $http, $window, $filter, NgTableParams) {

    console.log($rootScope.memberinfo);

    $scope.failMessage = "";
    $scope.users = [];
    $scope.roles = ["admin", "doctor", "guest"];
    $scope.role = $scope.roles[0];

    $scope.getUsers = function () {
        $http({method: 'GET', url: 'http://localhost:8081/api/user/all'}).then(function (response) {
            $scope.users = response.data;

            $scope.usersTable = new NgTableParams({
                page: 1,
                count: 12
            }, {
                total: $scope.users.length,
                getData: function (params) {
                    params.total($scope.users.length);
                    $scope.users = params.sorting() ? $filter('orderBy')($scope.users, params.orderBy()) : $scope.users;
                    return $scope.users.slice((params.page() - 1) * params.count(), params.page() * params.count());
                }
            });
        });

    }
    $scope.getUsers();

    $scope.addUser = function () {
        var newUser = {
            username: $scope.username,
            password: $scope.password,
            role: $scope.role
        };
        console.log("adding " + newUser.username + " " + newUser.password + " " + newUser.role);

        if (newUser.username == null || newUser.username == ""
            || newUser.password == null || newUser.password == ""
            || newUser.role == null || newUser.role == ""
        ) {
            window.alert("Va rugam completati toate campurile!")
            return;
        }

        $http({method: 'POST', data: newUser, url: 'http://localhost:8081/api/user/add'}).then(function (response) {
            if (response.status == 200) {
                $scope.failMessage = "";
                $scope.getUsers();
                $scope.username = "";
                $scope.password = "";
                $scope.role = "";
            } else if (response.status == 500) {
                $scope.failMessage = response.message;
                window.alert($scope.failMessage);
                $window.alert("Utilizatorul nu a putut fi adaugat");
            }
        });
    }

    $scope.deleteUser = function (id) {
        $http({method: 'DELETE', url: 'http://localhost:8081/api/user/delete?id=' + id}).then(function (response) {
            $scope.getUsers();
        });
    }

    //
// }$scope.tableParams = new NgTableParams({
//     page: 1,
//     count: 12
// }, {
//     getData: function ($defer, params) {
//         console.log('params ' + params)
//         // when landing on the page, get all todos and show them
//         $scope.getUsers().then(
//             function (data) {
//                 var users = params.sorting() ? $filter('orderBy')(data, params.orderBy()) : data;
//                 users = params.filter() ? $filter('filter')(users, params.filter()) : users;
//                 users = users.slice((params.page() - 1) * params.count(), params.page() * params.count());
//                 $defer.resolve(users);
//             }
//         )
//     }});
}