function findTeachingInfo() {
   var tableTeaching =  $('#table-teaching').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/teaching/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#staffName').val();
                d.studyYear = $('#studyYear').val();
                d.semester = $('#semester').val();
            }
        },
        columns: [
            { data: "staffId" },
            { data: "name"},
            { data: "studyYear" },
            { data: "semester" },
            { data: "subjectName" },
            { data: "studyType" },

        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var fullName = row["prefix"]+' '+row["name"] + ' ' + row["surname"];
                       return fullName;
                    },
               targets: 1,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-teaching tbody').on('click', 'tr', function () {
            if(!$('#table-teaching tbody tr td').hasClass("dataTables_empty")){
               var data = tableTeaching.row( this ).data();
                window.location.href = "/poba/teaching/"+data.staffId;
            }
        } );
}

function submitTeachingInfo(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/teaching/save",
         data: $("#form-teaching").serialize(),
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";

                window.scrollTo(0, 0);

                setTimeout(function(){
                    window.location.href = "/poba/teaching";
                },2000);
         }
    });
}

function editTeachingInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}