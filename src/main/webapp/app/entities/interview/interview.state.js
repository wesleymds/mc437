(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('interview', {
            parent: 'entity',
            url: '/interview',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Interviews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/interview/interviews.html',
                    controller: 'InterviewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('interview-detail', {
            parent: 'entity',
            url: '/interview/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Interview'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/interview/interview-detail.html',
                    controller: 'InterviewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Interview', function($stateParams, Interview) {
                    return Interview.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'interview',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('interview-detail.edit', {
            parent: 'interview-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/interview/interview-dialog.html',
                    controller: 'InterviewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Interview', function(Interview) {
                            return Interview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('interview.new', {
            parent: 'interview',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/interview/interview-dialog.html',
                    controller: 'InterviewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('interview', null, { reload: 'interview' });
                }, function() {
                    $state.go('interview');
                });
            }]
        })
        .state('interview.edit', {
            parent: 'interview',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/interview/interview-dialog.html',
                    controller: 'InterviewDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Interview', function(Interview) {
                            return Interview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('interview', null, { reload: 'interview' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('interview.delete', {
            parent: 'interview',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/interview/interview-delete-dialog.html',
                    controller: 'InterviewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Interview', function(Interview) {
                            return Interview.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('interview', null, { reload: 'interview' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
