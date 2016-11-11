(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ExternalResourceController', ExternalResourceController);

    ExternalResourceController.$inject = ['$scope', '$state', 'ExternalResource', 'ExternalResourceSearch'];

    function ExternalResourceController ($scope, $state, ExternalResource, ExternalResourceSearch) {
        var vm = this;
        
        vm.externalResources = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ExternalResource.query(function(result) {
                vm.externalResources = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ExternalResourceSearch.query({query: vm.searchQuery}, function(result) {
                vm.externalResources = result;
            });
        }    }
})();
