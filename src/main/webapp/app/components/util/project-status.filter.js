(function () {
    'use strict';

    angular.module('mc437App')
        .filter('projectStatus', projectStatusFilter);

    function projectStatusFilter() {
        return function (status) {
            switch (status) {
                case 'DRAFT':
                    return 'Draft';
                case 'UNDER_DEVELOPMENT':
                    return 'Under Development';
                case 'DONE':
                    return 'Done';
                case 'CANCELLED':
                    return 'Cancelled';
                default:
                    return status;
            }
        };
    }
})();
