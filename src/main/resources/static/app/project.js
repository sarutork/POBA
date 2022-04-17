function findProjectInfo() {
   var tableProject =  $('#table-project').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/project/search",
            dataSrc: "",
            data: function(d){
                d.projectName = $('#projectName').val();
                d.projectYear = $('#projectYear').val();
                d.projectDateFrom = $('#projectDateFrom').val();
            }
        },
        columns: [
            { data: "projectId" },
            { data: "projectYear"},
            { data: "projectName" },
            { data: "projectType" },
            { data: "projectDateFrom" },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-project tbody').on('click', 'tr', function () {
            if(!$('#table-project tbody tr td').hasClass("dataTables_empty")){
               var data = tableProject.row( this ).data();
                loadView('/poba/project/'+data.projectId);
            }
    } );
}

function submitProjectInfo(){

    removeComma();

    var type = "POST";

    $.ajax({
         type: type,
         url: "/poba/project/save",
         data: $("#form-project").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/project');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editProjectInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}

function amtFormat(){
    numberFormat();
}