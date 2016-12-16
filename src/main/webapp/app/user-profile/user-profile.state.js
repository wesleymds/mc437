(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('user-profile', {
            parent: 'app',
            url: '/user-profile/:id',
            data: {
                authorities: ["ROLE_ADMIN"]
            },
            views: {
                'content@': {
                    templateUrl: 'app/user-profile/user-profile.html',
                    controller: 'UserProfileController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
