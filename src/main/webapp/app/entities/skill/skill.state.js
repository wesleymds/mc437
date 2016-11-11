(function() {
    'use strict';

    angular
        .module('mc437App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('skill', {
            parent: 'entity',
            url: '/skill',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Skills'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skill/skills.html',
                    controller: 'SkillController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('skill-detail', {
            parent: 'entity',
            url: '/skill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Skill'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/skill/skill-detail.html',
                    controller: 'SkillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Skill', function($stateParams, Skill) {
                    return Skill.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'skill',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('skill-detail.edit', {
            parent: 'skill-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill/skill-dialog.html',
                    controller: 'SkillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Skill', function(Skill) {
                            return Skill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skill.new', {
            parent: 'skill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill/skill-dialog.html',
                    controller: 'SkillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                verified: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('skill', null, { reload: 'skill' });
                }, function() {
                    $state.go('skill');
                });
            }]
        })
        .state('skill.edit', {
            parent: 'skill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill/skill-dialog.html',
                    controller: 'SkillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Skill', function(Skill) {
                            return Skill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skill', null, { reload: 'skill' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('skill.delete', {
            parent: 'skill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/skill/skill-delete-dialog.html',
                    controller: 'SkillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Skill', function(Skill) {
                            return Skill.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('skill', null, { reload: 'skill' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
