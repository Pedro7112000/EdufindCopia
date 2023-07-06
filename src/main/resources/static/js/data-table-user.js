$(function() {
  'use strict';

  $(function() {
    var myCourseDataTable = $('#dataTableExample').DataTable({
      rowReorder: {
          selector: 'td:nth-child(2)'
      },
      responsive: true,
      "lengthMenu": [
        [10, 30, 50, -1],
        [10, 30, 50, "All"]
      ],
      pageLength: 10,
      language: {
        "processing":    "Procesando...",
        "lengthMenu":    "Mostrar _MENU_ usuarios",
        "zeroRecords":   "No se encontraron resultados",
        "emptyTable":    "No tiene usuarios registrados",
        "info":          "Mostrando del _START_ al _END_ de _TOTAL_ usuarios",
        "infoEmpty":     "Ningún usuario coincide con su busqueda",
        "infoFiltered":  "(filtrado de un total de _MAX_ registros)",
        "infoPostFix":   "",
        "search":        "Buscar:",
        "thousands":  ",",
        "loadingRecords": "Cargando...",
        "paginate": {
            "first":    "Primero",
            "last":     "Último",
            "next":     "Siguiente",
            "previous": "Anterior"
        },
        aria: {
            "sortAscending":  ": Activar para ordenar la columna de manera ascendente",
            "sortDescending": ": Activar para ordenar la columna de manera descendente"
        }
      }
    });
    $('#dataTableExample').each(function() {
      var datatable = $(this);
      // SEARCH - Add the placeholder for Search and Turn this into in-line form control
      var search_input = datatable.closest('.dataTables_wrapper').find('div[id$=_filter] input');
      search_input.attr('placeholder', 'Search');
      search_input.removeClass('form-control-sm');
      // LENGTH - Inline-Form control
      var length_sel = datatable.closest('.dataTables_wrapper').find('div[id$=_length] select');
      length_sel.removeClass('form-control-sm');
    });

    // Cambio de searchbox
    $("#mySearchInput").keyup(function () {
        myCourseDataTable.search($(this).val()).draw();
    });
  });

});