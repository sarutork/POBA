
function findPresentingInfo() {
   var tablePresenting =  $('#table-presenting').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/presenting/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.presentLevel = $('#presentLevel').val();
            }
        },
        columns: [
            { data: "presentId" },
            { data: "name"},
            { data: "presentTopic" },
            { data: "presentLevel" },

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
    $('#table-presenting tbody').on('click', 'tr', function () {
            if(!$('#table-presenting tbody tr td').hasClass("dataTables_empty")){
               var data = tablePresenting.row( this ).data();
                loadView('/poba/presenting/'+data.presentId);
            }
    } );
}

function submitPresentingInfo(){
    removeComma();

    var type = "POST";
    var presentId = $("#presentId").val();
    if (presentId != null &&  presentId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/presenting/save",
         data: $("#form-presenting").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/presenting');
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

function editPresentingInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}