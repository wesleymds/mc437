(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ExternalResourceDialogController', ExternalResourceDialogController);

    ExternalResourceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExternalResource', 'Project'];

    function ExternalResourceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExternalResource, Project) {
        var vm = this;

        vm.externalResource = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.externalResource.id !== null) {
                ExternalResource.update(vm.externalResource, onSaveSuccess, onSaveError);
            } else {
                ExternalResource.save(vm.externalResource, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mc437App:externalResourceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
