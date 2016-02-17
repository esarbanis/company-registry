(function () {
    'use strict';

    // Prepare the 'users' module for subsequent registration of controllers and delegates
    angular.module('companies')
        .directive('companyForm', CompanyDirective);

    function CompanyDirective() {
        var directive = {
            restrict: 'E',
            scope: {
                onsave: '&',
                onupdate: '&',
                comp: "="
            },
            templateUrl: 'company/view/companyForm.tpl.html'
        };
        return directive;
    };

})();
