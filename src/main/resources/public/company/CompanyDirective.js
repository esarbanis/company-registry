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
            controller: ['errorRegistry', DirectiveController],
            controllerAs: 'errorRegistry',
            scope: {
                onsave: '&',
                onupdate: '&',
                comp: "="
            },
            templateUrl: 'company/view/companyForm.tpl.html'
        };
    };

    /**
     * Controller for wrapping the <code>errorRegistry</code>
     *
     * @param errorRegistry the delegate
     * @returns {DirectiveController}
     */
    function DirectiveController(errorRegistry){
        var vm = this;

        vm.hasError = HasError;
        vm.getMessage = GetMessage;

        function HasError(path) {
            return errorRegistry.hasError(path);
        }

        function GetMessage(path) {
            return errorRegistry.getMessage(path);
        }

        return vm;
    };


})();
