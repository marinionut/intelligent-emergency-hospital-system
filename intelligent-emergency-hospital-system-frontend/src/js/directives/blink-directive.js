// angular.module('RDash', [])
//     .directive('blink', ['$interval', function($interval) {
//         return function(scope, element, attrs) {
//             var timeoutId;
//
//             var blink = function() {
//                 element.css('visibility') === 'hidden' ? element.css('visibility', 'inherit') : element.css('visibility', 'hidden');
//             }
//
//             timeoutId = $interval(function() {
//                 blink();
//             }, 1000);
//
//             element.css({
//                 'display': 'inline-block'
//             });
//
//             element.on('$destroy', function() {
//                 $interval.cancel(timeoutId);
//             });
//         };
//     }]);