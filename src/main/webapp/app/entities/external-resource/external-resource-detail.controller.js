(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ExternalResourceDetailController', ExternalResourceDetailController);

    ExternalResourceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ExternalResource', 'Project'];

    function ExternalResourceDetailController($scope, $rootScope, $stateParams, previousState, entity, ExternalResource, Project) {
        var vm = this;

        vm.externalResource = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mc437App:externalResourceUpdate', function(event, result) {
            vm.externalResource = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
