(function () {
    'use strict';

    angular
        .module('mc437App')
        .factory('UserSearch', UserSearch);

    UserSearch.$inject = ['$http'];

    function UserSearch ($http) {
        var service = {
            search: function search(data) {
               return $http.get('/api/user-data/search', {
                params: data,
                });
            }
        };
        return service;
    }
})();
