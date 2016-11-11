(function() {
    'use strict';

    angular
        .module('mc437App')
        .factory('UserDataSearch', UserDataSearch);

    UserDataSearch.$inject = ['$resource'];

    function UserDataSearch($resource) {
        var resourceUrl =  'api/_search/user-data/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
