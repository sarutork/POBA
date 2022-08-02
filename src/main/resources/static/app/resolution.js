function findResolutionInfo() {
    var bordNo = $('#bordNo').val();
    var dateStart = $('#dateStart').val();
    var dateEnd = $('#dateEnd').val();
   var tableResolutionInfo =  $('#table-resolution').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/resolution/search",
            dataSrc: "",
            data:{ 'bordNo' : bordNo, 'dateStart' : dateStart, 'dateEnd' : dateEnd },
        },
        columns: [
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "bordNo1"},
            { data: "bordDate" },
            { data: "bordType" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                       return row["bordNo1"] + '/' + row["bordNo2"];
               },
               targets: 1,
            },
        ],
        searching: false,
        "bDestroy": true,
        dom: 'Bfrtip',
        buttons: [
            {
                extend: 'excelHtml5',
                title: $(".breadcrumb-item.active span").html()
            },
            {
                extend: 'csvHtml5',
                title: $(".breadcrumb-item.active span").html()
            },
            {
                extend: 'print',
                title: $(".breadcrumb-item.active span").html()
            }
        ]
    });
    $('#table-resolution tbody').on('click', 'tr', function () {
            if(!$('#table-resolution tbody tr td').hasClass("dataTables_empty")){
               var data = tableResolutionInfo.row( this ).data();
                loadView('/poba/resolution/'+data.bordId);
            }
        } );
}

