(function() {
    'use strict';

    angular
        .module('mc437App')
        .factory('InterviewSearch', InterviewSearch);

    InterviewSearch.$inject = ['$resource'];

    function InterviewSearch($resource) {
        var resourceUrl =  'api/_search/interviews/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
