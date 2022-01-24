function findConsultantStudent() {
   var tableTeaching =  $('#table-consultant-student').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/consultant/students/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#staffName').val();
                d.yearOfStudy = $('#yearOfStudy').val();
                d.studentsLevel = $('#studentsLevel').val();
            }
        },
        columns: [
            { data: "staffId" },
            { data: "name"},
            { data: "studentName" },
            { data: "yearOfStudy" },
            { data: "studentsLevel" },
            { data: "course" },

        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var fullName = row["prefix"]+' '+row["name"] + ' ' + row["surname"];
                       return fullName;
                    },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                   var fullName = row["studentPrefix"]+' '+row["studentName"] + ' ' + row["studentSurname"];
                       return fullName;
                    },
               targets: 2,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-consultant-student tbody').on('click', 'tr', function () {
            if(!$('#table-consultant-student tbody tr td').hasClass("dataTables_empty")){
               var data = tableTeaching.row( this ).data();
                window.location.href = "/poba/consultant/students/"+data.staffId;
            }
        } );
}

function submitConsultantStudent(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/consultant/students/save",
         data: $("#form-consultant-student").serialize(),
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";
                setTimeout(function(){
                    window.location.href = "/poba/consultant/students";
                },2000);
         }
    });
}

function editConsultantStudent(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")
}