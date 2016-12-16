(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserProfileController', UserProfileController);

    UserProfileController.$inject = ['$stateParams', 'User', '$http'];

    function UserProfileController ($stateParams, User, $http) {
        var vm = this;

        vm.data = {};

        vm.evaluation = {};

        $http.get("/api/user-data/"+ $stateParams.id).then(function (result) {
            vm.data = result.data;
        });


        $http.get("/api/projects").then(function(result) {
            vm.projects = result.data;
        });

        vm.evaluateUser = function() {
            vm.evaluation.developerId = $stateParams.id;
            $http.post('/api/feedbacks', vm.evaluation).then(function(result) {
                $('#evaluate-modal').modal('hide');
                $('#success-modal').modal('show');
            });
        }
    }
})();
