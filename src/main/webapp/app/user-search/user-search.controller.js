(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserSearchController', UserSearchController);

    UserSearchController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'UserSearch'];

    function UserSearchController ($scope, Principal, LoginService, $state, UserSearch) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.result = "";
        vm.login = LoginService.open;
        vm.searchQuery = {
            minAvailableHours: 10,
            maxCostPerHour: 20
        };
        vm.disponibilities = [
            {
                value: "true",
                text: "Disponível",
            },
            {
                value: "false",
                text: "Indisponível",
            }
        ];
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        $scope.searchDevs = function () {
            UserSearch.search(vm.searchQuery).then(function (response) {
                vm.result = response.data;
            });
        };

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
    }
})();
