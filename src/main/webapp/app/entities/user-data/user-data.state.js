(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-data', {
            parent: 'entity',
            url: '/user-data',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-data/user-data.html',
                    controller: 'UserDataController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('user-data-detail', {
            parent: 'entity',
            url: '/user-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UserData'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-data/user-data-detail.html',
                    controller: 'UserDataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'UserData', function($stateParams, UserData) {
                    return UserData.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-data',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-data-detail.edit', {
            parent: 'user-data-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-data/user-data-dialog.html',
                    controller: 'UserDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserData', function(UserData) {
                            return UserData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-data.new', {
            parent: 'user-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-data/user-data-dialog.html',
                    controller: 'UserDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                primaryPhoneNumber: null,
                                secondaryPhoneNumber: null,
                                address: null,
                                rg: null,
                                cpf: null,
                                extra: null,
                                available: null,
                                availableHoursPerWeek: null,
                                initialCostPerHour: null,
                                bankAgency: null,
                                bankAccount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-data', null, { reload: 'user-data' });
                }, function() {
                    $state.go('user-data');
                });
            }]
        })
        .state('user-data.edit', {
            parent: 'user-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-data/user-data-dialog.html',
                    controller: 'UserDataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserData', function(UserData) {
                            return UserData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-data', null, { reload: 'user-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-data.delete', {
            parent: 'user-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-data/user-data-delete-dialog.html',
                    controller: 'UserDataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserData', function(UserData) {
                            return UserData.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-data', null, { reload: 'user-data' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
