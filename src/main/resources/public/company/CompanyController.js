(function () {
    'use strict';

    angular
        .module('companies')
        .controller('CompanyController', [
            'companyService',
            'formModal',
            'errorRegistry',
            CompanyController
        ]);

    /**
     * Main Controller for the Company Registry Application
     * @constructor
     * @param companyService The service to delegate all calls to the backend api
     * @param formModal The modal where the company form is placed
     */
    function CompanyController(companyService, formModal, errorRegistry) {
        var vm = this;

        vm.companies = [];
        vm.newComp = {};
        vm.edit = Edit;
        vm.remove = Remove;
        vm.update = Update;
        vm.save = Save;
        vm.remove = Remove;
        vm.showAddDialog = ShowModalDialog;

        // Load all registered users
        init();

        formModal.on('hide.bs.modal', function() {
           errorRegistry.reset();
        });

        /**
         * Initialize the company list
         */
        function init() {
            companyService
                .loadAllCompanies()
                .then(function (companies) {
                    vm.companies = [].concat(companies.content);
                });
        }

        /**
         * Edits the selected company by opening up a prefilled modal form.
         * @param comp the company to edit
         */
        function Edit(comp) {
            vm.newComp = angular.extend({}, comp);
            formModal.show();
        }

        /**
         * Removes the selected company after the user confirms his/hers action.
         * <p>
         * Should call the <code>companyService</code> to send the request to the backed.
         * </p>
         * @param comp the company to remove
         */
        function Remove(comp) {
            if (!confirm('Do you really want to remove ' + comp.name)) {
                return;
            }
            companyService.remove(comp).then(function () {
                vm.newComp = {};
                init();
            });
        }

        /**
         * Callback to be associated with the <code>formModal</code>'s save action.
         * <p>
         * Should call the backend api through <code>companyService</code> to persist the contents
         * of the modal's form.
         * </p>
         */
        function Save() {
            companyService.save(vm.newComp).then(function () {
                vm.newComp = {};
                init();
                formModal.hide();
            });
        }

        /**
         * Callback to be associated with the <code>formModal</code>'s update action.
         * <p>
         * Should call the backend api through <code>companyService</code> to update the contents
         * of the selected company according to the modal's form values.
         * </p>
         */
        function Update() {
            companyService.update(vm.newComp).then(function () {
                vm.newComp = {};
                init();
                formModal.hide();
            });
        }

        /**
         * Should be called to open up the <code>formModal</code>.
         * <p>
         *     Calling this method will result to an empty modal form.
         * </p>
         */
        function ShowModalDialog() {
            vm.newComp = {};
            formModal.show();
        }
    }

})();
