(function() {
    'use strict';

    angular
        .module('mc437App')
        .factory('ExternalResourceSearch', ExternalResourceSearch);

    ExternalResourceSearch.$inject = ['$resource'];

    function ExternalResourceSearch($resource) {
        var resourceUrl =  'api/_search/external-resources/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
