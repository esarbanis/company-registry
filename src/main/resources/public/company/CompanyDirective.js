(function () {
    'use strict';

    // Prepare the 'users' module for subsequent registration of controllers and delegates
    angular.module('companies')
        .directive('companyForm', CompanyDirective);

    /**
     * The company form directive, will render a form for managing a company's data.
     * @returns {{restrict: string, scope: {onsave: string, onupdate: string, comp: string}, templateUrl: string}}
     */
    function CompanyDirective() {
        return {
            restrict: 'E',
            scope: {
                onsave: '&',
                onupdate: '&',
                comp: "="
            },
            templateUrl: 'company/view/companyForm.tpl.html'
        };
    };

})();
