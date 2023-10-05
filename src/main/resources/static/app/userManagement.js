function findUser() {
   var tableUserManagementInfo =  $('#table-user-management').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/user-management/search",
            dataSrc: "",
        },
        columns: [
            { data: "id"},
            { data: "username" },
            { data: "role" },
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
        ],
        language: {
            "emptyTable": "ไม่พบผลการค้นหา"
        }
    });
    $('#table-user-management tbody').on('click', 'tr', function () {
            if(!$('#table-user-management tbody tr td').hasClass("dataTables_empty")){
               var data = tableUserManagementInfo.row( this ).data();
                loadView('/poba/user-management/'+data.id);
            }
        } );
}

function submitUserInfo(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/user-management/save",
         data: $("#form-user").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/user-management');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editUser(){
    $(":input").prop("disabled", false);
    $("select").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข");

    window.scrollTo(0, 0);
}

