(function() {
    'use strict';
    angular
        .module('mc437App')
        .factory('ExternalResource', ExternalResource);

    ExternalResource.$inject = ['$resource'];

    function ExternalResource ($resource) {
        var resourceUrl =  'api/external-resources/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
