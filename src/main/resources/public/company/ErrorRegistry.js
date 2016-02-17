(function () {
    'use strict';

    angular.module('companies')
        .service('errorRegistry', ErrorRegistry);

    /**
     * Error Registry Service
     * Holds an error registry object that maps the object graph path to an error message.
     *
     * @returns {{reset: reset, addAll: addAll, hasError: hasError, getMessage: getMessage}}
     */
    function ErrorRegistry() {
        var registry = {};

        // Promise-based API
        return {
            reset: function () {
                registry = {};
            },
            addAll: function (errors) {
                var that = this;
                that.reset();

                errors.filter(function(error) {
                    return error.path;
                }).forEach(function (error) {
                    registry[error.path] = error.message;
                });
            },
            hasError: function (path) {
                var that = this;
                return that.getMessage(path);
            },
            getMessage: function (path) {
                return registry[path];
            }
        };
    }

})();
