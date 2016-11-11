(function() {
    'use strict';
    angular
        .module('mc437App')
        .factory('UserData', UserData);

    UserData.$inject = ['$resource'];

    function UserData ($resource) {
        var resourceUrl =  'api/user-data/:id';

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
