(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('ProjectController', ProjectController);

    ProjectController.$inject = ['$scope', '$state', 'Project', 'ProjectSearch'];

    function ProjectController ($scope, $state, Project, ProjectSearch) {
        var vm = this;
        
        vm.projects = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Project.query(function(result) {
                vm.projects = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProjectSearch.query({query: vm.searchQuery}, function(result) {
                vm.projects = result;
            });
        }    }
})();
