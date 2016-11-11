(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('FeedbackDetailController', FeedbackDetailController);

    FeedbackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Feedback', 'Project', 'UserData'];

    function FeedbackDetailController($scope, $rootScope, $stateParams, previousState, entity, Feedback, Project, UserData) {
        var vm = this;

        vm.feedback = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mc437App:feedbackUpdate', function(event, result) {
            vm.feedback = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
