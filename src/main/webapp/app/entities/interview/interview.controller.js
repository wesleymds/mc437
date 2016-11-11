(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('InterviewController', InterviewController);

    InterviewController.$inject = ['$scope', '$state', 'Interview', 'InterviewSearch'];

    function InterviewController ($scope, $state, Interview, InterviewSearch) {
        var vm = this;
        
        vm.interviews = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Interview.query(function(result) {
                vm.interviews = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            InterviewSearch.query({query: vm.searchQuery}, function(result) {
                vm.interviews = result;
            });
        }    }
})();
