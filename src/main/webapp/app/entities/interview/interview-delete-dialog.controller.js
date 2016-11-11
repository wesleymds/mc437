(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('InterviewDeleteController',InterviewDeleteController);

    InterviewDeleteController.$inject = ['$uibModalInstance', 'entity', 'Interview'];

    function InterviewDeleteController($uibModalInstance, entity, Interview) {
        var vm = this;

        vm.interview = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Interview.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
