function findAcademicService() {
   var tableAcademicService =  $('#table-academic-service').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/academic-service/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.serviceLevel = $('#serviceLevel').val();
            }
        },
        columns: [
            { data: "serviceId" },
            { data: "name"},
            { data: "serviceStatus" },
            { data: "serviceLevel" },
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
    $('#table-academic-service tbody').on('click', 'tr', function () {
            if(!$('#table-academic-service tbody tr td').hasClass("dataTables_empty")){
               var data = tableAcademicService.row( this ).data();
                loadView('/poba/academic-service/'+data.serviceId);
            }
    } );
}

function submitAcademicService(){

    var type = "POST";
    var serviceId = $("#serviceId").val();
    if (serviceId != null &&  serviceId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/academic-service/save",
         data: $("#form-academic-service").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/academic-service');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editAcademicService(){
    $(":input").prop("disabled", false);

    $("#studyType").trigger("change");

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("???????????????")

    window.scrollTo(0, 0);
}