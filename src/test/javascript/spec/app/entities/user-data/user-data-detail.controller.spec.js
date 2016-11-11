'use strict';

describe('Controller Tests', function() {

    describe('UserData Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUserData, MockUser, MockFeedback, MockProject, MockInterview, MockSkill;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUserData = jasmine.createSpy('MockUserData');
            MockUser = jasmine.createSpy('MockUser');
            MockFeedback = jasmine.createSpy('MockFeedback');
            MockProject = jasmine.createSpy('MockProject');
            MockInterview = jasmine.createSpy('MockInterview');
            MockSkill = jasmine.createSpy('MockSkill');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UserData': MockUserData,
                'User': MockUser,
                'Feedback': MockFeedback,
                'Project': MockProject,
                'Interview': MockInterview,
                'Skill': MockSkill
            };
            createController = function() {
                $injector.get('$controller')("UserDataDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mc437App:userDataUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
