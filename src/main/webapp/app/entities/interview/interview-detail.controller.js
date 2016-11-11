(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('InterviewDetailController', InterviewDetailController);

    InterviewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Interview', 'ExternalResource', 'Skill', 'UserData'];

    function InterviewDetailController($scope, $rootScope, $stateParams, previousState, entity, Interview, ExternalResource, Skill, UserData) {
        var vm = this;

        vm.interview = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mc437App:interviewUpdate', function(event, result) {
            vm.interview = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
