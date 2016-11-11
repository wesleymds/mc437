(function() {
    'use strict';

    angular
        .module('mc437App')
        .controller('SkillController', SkillController);

    SkillController.$inject = ['$scope', '$state', 'Skill', 'SkillSearch'];

    function SkillController ($scope, $state, Skill, SkillSearch) {
        var vm = this;
        
        vm.skills = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Skill.query(function(result) {
                vm.skills = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            SkillSearch.query({query: vm.searchQuery}, function(result) {
                vm.skills = result;
            });
        }    }
})();
