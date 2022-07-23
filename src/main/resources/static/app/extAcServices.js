function findExtAcademicServices() {
   var tableExtAcademicService =  $('#table-ext-academic-services').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/ext-academic-services/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.level = $('#level').val();
            }
        },
        columns: [
            { data: "id" },
            { data: "name"},
            { data: "title" },
            { data: "level" },
            { data: "institution" },
            { data: "year" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                    return row["prefix"]+' '+row["name"];
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
    $('#table-ext-academic-services tbody').on('click', 'tr', function () {
            if(!$('#table-ext-academic-services tbody tr td').hasClass("dataTables_empty")){
               var data = tableExtAcademicService.row( this ).data();
                loadView('/poba/ext-academic-services/'+data.id);
            }
    } );
}

function submitExtAcServices(){

    var type = "POST";

    $.ajax({
         type: type,
         url: "/poba/ext-academic-services/save",
         data: $("#form-ext-academic-services").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/ext-academic-services');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editExtAcServices(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}