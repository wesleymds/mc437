(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('user-search', {
            parent: 'app',
            url: '/user-search',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/user-search/user-search.html',
                    controller: 'UserSearchController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
