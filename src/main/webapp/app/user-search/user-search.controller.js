(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserSearchController', UserSearchController);

    UserSearchController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'UserSearch', '$http'];

    function UserSearchController ($scope, Principal, LoginService, $state, UserSearch, $http) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.result = "";
        vm.login = LoginService.open;

        $http.get('/api/skills').then(function(result){
            if(result.status === 200) {
                vm.skills = result.data;
            } else {
                alert("ERRO");
            }
        });

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
            console.log('chamei a funcao');
            UserSearch.search(vm.searchQuery).then(function (response) {
                console.log(vm.searchQuery);
                console.log(response);
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
