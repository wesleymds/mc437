(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserDataDeleteController',UserDataDeleteController);

    UserDataDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserData'];

    function UserDataDeleteController($uibModalInstance, entity, UserData) {
        var vm = this;

        vm.userData = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserData.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
