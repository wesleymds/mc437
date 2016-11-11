(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ProjectDetailController', ProjectDetailController);

    ProjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'ExternalResource', 'Feedback', 'UserData'];

    function ProjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, ExternalResource, Feedback, UserData) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mc437App:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
