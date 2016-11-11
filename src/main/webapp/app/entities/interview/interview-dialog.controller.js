(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('InterviewDialogController', InterviewDialogController);

    InterviewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Interview', 'ExternalResource', 'Skill', 'UserData'];

    function InterviewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Interview, ExternalResource, Skill, UserData) {
        var vm = this;

        vm.interview = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.reports = ExternalResource.query({filter: 'interview-is-null'});
        $q.all([vm.interview.$promise, vm.reports.$promise]).then(function() {
            if (!vm.interview.report || !vm.interview.report.id) {
                return $q.reject();
            }
            return ExternalResource.get({id : vm.interview.report.id}).$promise;
        }).then(function(report) {
            vm.reports.push(report);
        });
        vm.skills = Skill.query();
        vm.userdata = UserData.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.interview.id !== null) {
                Interview.update(vm.interview, onSaveSuccess, onSaveError);
            } else {
                Interview.save(vm.interview, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('mc437App:interviewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
