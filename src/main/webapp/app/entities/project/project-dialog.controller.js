(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ProjectDialogController', ProjectDialogController);

    ProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'ExternalResource', 'Feedback', 'UserData'];

    function ProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, ExternalResource, Feedback, UserData) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.save = save;
        vm.externalresources = ExternalResource.query();
        vm.feedbacks = Feedback.query();
        vm.userdata = UserData.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                var createdProject = {
                    name: vm.project.name,
                    resources: [],  // TODO
                    assessorsIds: vm.project.assessors.map(function (assessor) { return assessor.id; }),
                    developersIds: vm.project.developers.map(function (developer) { return developer.id; }),
                    managerId: vm.project.manager.id
                };

                Project.save(createdProject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mc437App:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
