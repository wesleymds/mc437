(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('external-resource', {
            parent: 'entity',
            url: '/external-resource',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExternalResources'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-resource/external-resources.html',
                    controller: 'ExternalResourceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('external-resource-detail', {
            parent: 'entity',
            url: '/external-resource/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ExternalResource'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/external-resource/external-resource-detail.html',
                    controller: 'ExternalResourceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ExternalResource', function($stateParams, ExternalResource) {
                    return ExternalResource.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'external-resource',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('external-resource-detail.edit', {
            parent: 'external-resource-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-resource/external-resource-dialog.html',
                    controller: 'ExternalResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExternalResource', function(ExternalResource) {
                            return ExternalResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('external-resource.new', {
            parent: 'external-resource',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-resource/external-resource-dialog.html',
                    controller: 'ExternalResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                url: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('external-resource', null, { reload: 'external-resource' });
                }, function() {
                    $state.go('external-resource');
                });
            }]
        })
        .state('external-resource.edit', {
            parent: 'external-resource',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-resource/external-resource-dialog.html',
                    controller: 'ExternalResourceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ExternalResource', function(ExternalResource) {
                            return ExternalResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('external-resource', null, { reload: 'external-resource' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('external-resource.delete', {
            parent: 'external-resource',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/external-resource/external-resource-delete-dialog.html',
                    controller: 'ExternalResourceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ExternalResource', function(ExternalResource) {
                            return ExternalResource.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('external-resource', null, { reload: 'external-resource' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
