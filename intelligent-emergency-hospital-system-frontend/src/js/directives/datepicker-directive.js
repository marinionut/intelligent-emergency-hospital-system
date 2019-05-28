// angular
//     .module('RDash')
//     .directive('datepicker', function() {
//
//     return {
//         restrict: 'E',
//         transclude: true,
//         scope: {
//             date: '='
//         },
//         link: function(scope, element, attrs) {
//             element.datepicker({
//                 dateFormat: 'dd-mm-yy',
//                 onSelect: function(dateText, datepicker) {
//                     scope.date = dateText;
//                     scope.$apply();
//                 }
//             });
//         },
//         template: '<input type="text" class="span2" ng-model="date"/>',
//         replace: true
//     }
//
// });