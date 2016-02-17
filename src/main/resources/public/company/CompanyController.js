(function () {

    angular
        .module('companies')
        .controller('CompanyController', [
            'companyService',
            'formModal',
            CompanyController
        ]);

    /**
     * Main Controller for the Angular Material Starter App
     * @constructor
     */
    function CompanyController(companyService, formModal) {
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

        // *********************************
        // Internal methods
        // *********************************
        function init() {
            companyService
                .loadAllCompanies()
                .then(function (companies) {
                    vm.companies = [].concat(companies.content);
                });
        }

        function Edit(comp) {
            vm.newComp = comp;
            formModal.show();
        }

        function Remove(comp) {
            if (!confirm('Do you really want to remove ' + comp.name)) {
                return;
            }
            companyService.remove(comp).then(function (response) {
                vm.newComp = {};
                init();
            });
        }

        function Save() {
            companyService.save(vm.newComp).then(function (response) {
                vm.newComp = {};
                init();
                formModal.hide();
            });
        }

        function Update() {
            companyService.update(vm.newComp).then(function (response) {
                vm.newComp = {};
                init();
                formModal.hide();
            });
        }

        function ShowModalDialog() {
            vm.newComp = {};
            formModal.show();
        }
    }

})();
