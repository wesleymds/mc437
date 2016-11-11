(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserDataDialogController', UserDataDialogController);

    UserDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserData', 'User', 'Feedback', 'Project', 'Interview', 'Skill'];

    function UserDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, UserData, User, Feedback, Project, Interview, Skill) {
        var vm = this;

        vm.userData = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.feedbacks = Feedback.query();
        vm.projects = Project.query();
        vm.interviews = Interview.query();
        vm.skills = Skill.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userData.id !== null) {
                UserData.update(vm.userData, onSaveSuccess, onSaveError);
            } else {
                UserData.save(vm.userData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mc437App:userDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
