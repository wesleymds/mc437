(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserProfileController', UserProfileController);

    UserProfileController.$inject = ['$stateParams', 'User', '$http'];

    function UserProfileController ($stateParams, User, $http) {
        var vm = this;

        vm.data = {};

        $http.get("/api/user-data/"+ $stateParams.id).then(function (result) {
            vm.data = result.data;
            console.log(vm.data);
        });
    }
})();
