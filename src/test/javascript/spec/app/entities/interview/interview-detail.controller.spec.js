'use strict';

describe('Controller Tests', function() {

    describe('Interview Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInterview, MockExternalResource, MockSkill, MockUserData;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInterview = jasmine.createSpy('MockInterview');
            MockExternalResource = jasmine.createSpy('MockExternalResource');
            MockSkill = jasmine.createSpy('MockSkill');
            MockUserData = jasmine.createSpy('MockUserData');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Interview': MockInterview,
                'ExternalResource': MockExternalResource,
                'Skill': MockSkill,
                'UserData': MockUserData
            };
            createController = function() {
                $injector.get('$controller')("InterviewDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'mc437App:interviewUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
