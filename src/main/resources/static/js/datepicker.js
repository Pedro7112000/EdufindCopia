$(function() {
  'use strict';

  if($('#datePickerExample').length) {
    // var date = new Date();
    // var today = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    $('#datePickerExample').datepicker({
      format: "dd/mm/yyyy",
      todayHighlight: true,
      autoclose: true,
      language: "es",
      orientation: "bottom auto"
    });
    // $('#datePickerExample').datepicker('setDate', today);
  }
});