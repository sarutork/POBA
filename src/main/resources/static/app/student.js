function findStudent() {
   var tableStudents =  $('#table-students').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/students/search",
            dataSrc: "",
            data: function(d){
                d.studentsName = $('#studentsName').val();
                d.studentsYear = $('#studentsYear').val();
                d.studentsLevel = $('#studentsLevel').val();
            }
        },
        columns: [
            { data:"id"},
            { data: "studentsId"},
            { data: "studentsName" },
            { data: "studentsYear" },
            { data: "studentsLevel" },
            { data: "studentsStatus" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var prefix = row["studentsPrefix"];
                      if(prefix == "อื่นๆ"){
                           prefix = row["studentsPrefixOther"]
                      }
                      var fullName = prefix+' '+row["studentsName"] + ' ' + row["studentsSurname"];
                          return fullName;
                       },
               targets: 2,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-students tbody').on('click', 'tr', function () {
            if(!$('#table-students tbody tr td').hasClass("dataTables_empty")){
               var data = tableStudents.row( this ).data();
                 loadView('/poba/students/'+data.id);
            }
        } );
}

function submitStudentInfo(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/students/save",
         data: $("#form-student").serialize(),
         success: function(data) {
                setTimeout(function(){
                     loadView('/poba/students');
                 },3000);
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editStudentInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข");

    $("#viewName").text("แก้ไข");

    $("#studentsLevel").trigger("change");

    window.scrollTo(0, 0);
}