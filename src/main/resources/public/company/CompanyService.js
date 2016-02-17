(function () {
        'use strict';

        angular.module('companies')
            .service('companyService', ['$http', '$q', CompanyService]);

        /**
         * Users DataService
         * Uses embedded, hard-coded data model; acts asynchronously to simulate
         * remote data service call(s).
         *
         * @returns {{loadAll: Function}}
         * @constructor
         */
        function CompanyService($http, $q) {
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
                    });
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
                    });
                },
                update: function (comp) {
                    var req = {
                        method: 'PUT',
                        url: '/company/'+comp.id,
                        headers: {'Content-Type': 'application/json'},
                        data: comp
                    };
                    return $http(req).then(function (response) {
                        return response.data
                    });
                },
                remove: function (comp) {
                    var req = {
                        method: 'DELETE',
                        url: '/company/'+comp.id,
                        headers: {'Content-Type': 'application/json'}
                    };
                    return $http(req).then(function (response) {
                        return response.data
                    });
                }
            };
        }

    })();
