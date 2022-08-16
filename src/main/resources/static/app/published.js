function findPublished() {
   var tablePublished=  $('#table-published').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/published/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.publishedLevel = $('#publishedLevel').val();
                d.publishedYear2 = $('#publishedYear2').val();
            }
        },
        columns: [
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name"},
            { data: "publishedStatus" },
            { data: "publishedTopic" },
            { data: "" },
            { data: "publishedLevel" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                return row["prefix"]+' '+row["name"];
               },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                   var prefix = row["publishedJoinPrefix"];
                   if(prefix == "อื่นๆ"){
                        prefix = row["publishedJoinPrefixOther"]
                   }
                   var fullName = prefix+' '+row["publishedJoinName"] + ' ' + row["publishedJoinSurname"];

                    if (!row["publishedJoinName"]){
                        fullName = "-";
                    }
                       return fullName;
                    },
               targets: 4,
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
    $('#table-published tbody').on('click', 'tr', function () {
            if(!$('#table-published tbody tr td').hasClass("dataTables_empty")){
               var data = tablePublished.row( this ).data();
               loadView('/poba/published/'+data.publishedId);
            }
        } );
}
function submitPublishedInfo(){
    removeComma();

    var type = "POST";
    var staffId = $("#publishedId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/published/save",
         data: $("#form-published").serialize(),
         success: function(data) {
             setTimeout(function(){
                 loadView('/poba/published');
             },3000);
             amtFormat();
             window.scrollTo(0, 0);
             $('.content-wrapper').html(data);
         },
         error: function (error) {
             $('.content-wrapper').html(error.responseText);
         }
    });
}

function editPublishedInfo(){
        $(":input").prop("disabled", false);

        $("#submit").removeClass("d-none");
        $("#submit").addClass("d-block");

        $("#edit").removeClass("d-block");
        $("#edit").addClass("d-none");

        $("#viewName").text("แก้ไข");

        $(".btn-add").prop("disabled", false);
        $(".btn-del").prop("disabled", false);

        window.scrollTo(0, 0);
}

function addFiscalYear(){
    var table = $('#table-fiscal-year').DataTable();

    var rowCount = table.rows().count();
    var index = rowCount

    var id = '<input type="checkbox"/>';

    var year = '<input type="text" name="fiscalYears['+(index)+'].year" style="border: none;"/>';

    var quarter0 = '<select name="fiscalYears['+(index)+'].quarter" style="border: none;">';
    var quarter1 = '<option value="">กรุณาเลือก</option>';
    var quarter2 = '<option value="ไตรมาส 1">ไตรมาส 1</option>';
    var quarter3 = '<option value="ไตรมาส 2">ไตรมาส 2</option>';
    var quarter4 = '<option value="ไตรมาส 3">ไตรมาส 3</option>';
    var quarter5 = '<option value="ไตรมาส 4">ไตรมาส 4</option>';
    var quarter6 = '</select>';

    var countRef = '<input type="text" name="fiscalYears['+(index)+'].countRef" style="border: none;"/>';

    table.row.add([id,year,quarter0+quarter1+quarter2+quarter3+quarter4+quarter5+quarter6,countRef]).draw();

}

function removeFiscalYear(){
    $("#table-fiscal-year input:checked").parents("tr").remove();
}