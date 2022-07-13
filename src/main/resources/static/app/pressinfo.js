function findPressInfo() {
   var tablePress =  $('#table-press').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/press/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.pressDate = $('#pressDate').val();
            }
        },
        columns: [
            { data: "pressId" },
            { data: "name"},
            { data: "pressDate" },
            { data: "pressName" },

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
    $('#table-press tbody').on('click', 'tr', function () {
            if(!$('#table-press tbody tr td').hasClass("dataTables_empty")){
               var data = tablePress.row( this ).data();
                loadView('/poba/press/'+data.pressId);
            }
    } );
}

function submitPressInfo(){

    var type = "POST";
    var pressId = $("#pressId").val();
    if (pressId != null &&  pressId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/press/save",
         data: $("#form-press").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/press');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editPressInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $(".btn-search").prop("disabled", false);

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}