(function(){
  'use strict';

  // Setup modal
  var formModal = $('#formModal');
  formModal.show = function() {
    this.modal('show');
  };
  formModal.hide = function() {
    this.modal('hide');
  };

  // Prepare the 'companies' module for subsequent registration of controllers and delegates
  angular
      .module('companies', ['ngTagsInput'])
      .value('formModal', formModal);

})();
