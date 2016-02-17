(function () {

    angular
        .module('companies')
        .controller('CompanyController', [
            'companyService',
            CompanyController
        ]);

    /**
     * Main Controller for the Angular Material Starter App
     * @constructor
     */
    function CompanyController(companyService) {
        var vm = this;

        vm.companies = [];
        vm.newComp = {};
        vm.edit = Edit;
        vm.remove = Remove;
        vm.update = Update;
        vm.save = Save;
        vm.remove = Remove;

        // Load all registered users

        Init();

        // *********************************
        // Internal methods
        // *********************************

        function Init() {
            companyService
                .loadAllCompanies()
                .then(function (companies) {
                    vm.companies = [].concat(companies.content);
                });
        }

        function Edit(comp) {
            vm.newComp = comp;
        }

        function Remove(comp) {
            if(!confirm('Do you really want to remove '+comp.name)) {
                return;
            }
            companyService.remove(comp).then(function (response) {
                vm.newComp = {};
                Init();
            });
        }

        function Save() {
            companyService.save(vm.newComp).then(function (response) {
                vm.newComp = {};
                Init();
            });
        }

        function Update() {
            companyService.update(vm.newComp).then(function (response) {
                vm.newComp = {};
                Init();
            });
        }
    }

})();
