(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ExternalResourceDeleteController',ExternalResourceDeleteController);

    ExternalResourceDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExternalResource'];

    function ExternalResourceDeleteController($uibModalInstance, entity, ExternalResource) {
        var vm = this;

        vm.externalResource = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExternalResource.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
