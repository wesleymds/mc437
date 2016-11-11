(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('FeedbackController', FeedbackController);

    FeedbackController.$inject = ['$scope', '$state', 'Feedback', 'FeedbackSearch'];

    function FeedbackController ($scope, $state, Feedback, FeedbackSearch) {
        var vm = this;
        
        vm.feedbacks = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Feedback.query(function(result) {
                vm.feedbacks = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            FeedbackSearch.query({query: vm.searchQuery}, function(result) {
                vm.feedbacks = result;
            });
        }    }
})();
