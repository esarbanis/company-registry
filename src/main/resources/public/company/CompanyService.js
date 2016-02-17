(function () {
    'use strict';

    angular.module('companies')
        .service('companyService', ['$http', '$q', 'errorRegistry', CompanyService]);

    /**
     * Companies DataService
     * <p>
     *     <code>CompanyService</code> is responsible to interface with the backend api.
     * </p>
     * @param $http the actual service to use for the rest calls
     * @param $q used to reject the promise in case of errors
     * @returns {{loadAllCompanies: loadAllCompanies, save: save, update: update, remove: remove}}
     */
    function CompanyService($http, $q, errorRegistry) {

        var path = '/company';

        function ErrorHandler(response) {
            errorRegistry.addAll(response.data);
            return $q.reject();
        }

        // Promise-based API
        return {
            loadAllCompanies: function () {
                var req = {
                    method: 'GET',
                    url: path,
                    headers: {'Content-Type': 'application/json'}
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            },
            save: function (comp) {
                var req = {
                    method: 'POST',
                    url: path,
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
                    url: [path,comp.id].join('/'),
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
                    url: [path,comp.id].join('/'),
                    headers: {'Content-Type': 'application/json'}
                };
                return $http(req).then(function (response) {
                    return response.data
                }, ErrorHandler);
            }
        };
    }

})();
