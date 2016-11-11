(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('UserDataController', UserDataController);

    UserDataController.$inject = ['$scope', '$state', 'UserData', 'UserDataSearch'];

    function UserDataController ($scope, $state, UserData, UserDataSearch) {
        var vm = this;
        
        vm.userData = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            UserData.query(function(result) {
                vm.userData = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            UserDataSearch.query({query: vm.searchQuery}, function(result) {
                vm.userData = result;
            });
        }    }
})();
