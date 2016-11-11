(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserDataDetailController', UserDataDetailController);

    UserDataDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserData', 'User', 'Feedback', 'Project', 'Interview', 'Skill'];

    function UserDataDetailController($scope, $rootScope, $stateParams, previousState, entity, UserData, User, Feedback, Project, Interview, Skill) {
        var vm = this;

        vm.userData = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('mc437App:userDataUpdate', function(event, result) {
            vm.userData = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
