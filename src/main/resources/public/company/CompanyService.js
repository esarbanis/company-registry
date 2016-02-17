(function () {
    'use strict';

    angular.module('companies')
        .service('companyService', ['$http', '$q', CompanyService]);

    /**
     * Companies DataService
     * <p>
     *     <code>CompanyService</code> is responsible to interface with the backend api.
     * </p>
     * @param $http the actual service to use for the rest calls
     * @param $q used to reject the promise in case of errors
     * @returns {{loadAllCompanies: loadAllCompanies, save: save, update: update, remove: remove}}
     */
    function CompanyService($http, $q) {
        function ErrorHandler() {
            return $q.reject();
        }

        // Promise-based API
        return {
            loadAllCompanies: function () {
                var req = {
                    method: 'GET',
                    url: '/company',
                    headers: {'Content-Type': 'application/json'}
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            },
            save: function (comp) {
                var req = {
                    method: 'POST',
                    url: '/company',
                    headers: {'Content-Type': 'application/json'},
                    data: comp
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            },
            update: function (comp) {
                var req = {
                    method: 'PUT',
                    url: '/company/' + comp.id,
                    headers: {'Content-Type': 'application/json'},
                    data: comp
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            },
            remove: function (comp) {
                var req = {
                    method: 'DELETE',
                    url: '/company/' + comp.id,
                    headers: {'Content-Type': 'application/json'}
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            }
        };
    }

})();
