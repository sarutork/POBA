function findLecturerInfo() {
   var tableLecturer =  $('#table-lecturer').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/lecturer/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#staffName').val();
                d.studyYear = $('#studyYear').val();
                d.semester = $('#semester').val();
            }
        },
        columns: [
            { data: "lecturerId" },
            { data: "name"},
            { data: "studyYear" },
            { data: "semester" },
            { data: "subjectName" },
            { data: "studyType" },

        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var prefix = row["prefix"];
                      if(prefix == "อื่นๆ"){
                           prefix = row["prefixOther"]
                      }
                      var fullName = prefix+' '+row["name"] + ' ' + row["surname"];
                          return fullName;
                       },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                   var semester = row["semester"];
                   const semesterArray = semester.split(":");
                   return semesterArray[1]
                   },
               targets: 3,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-lecturer tbody').on('click', 'tr', function () {
            if(!$('#table-lecturer tbody tr td').hasClass("dataTables_empty")){
               var data = tableLecturer.row( this ).data();
                loadView('/poba/lecturer/'+data.lecturerId);
            }
    } );
}

function editLecturerInfo(){
    $(":input").prop("disabled", false);

    $("#studyType").trigger("change");

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข");

    $(".uploadDiv").removeClass("d-none");
    $(".uploadDiv").addClass("d-block");

    $(".downloadDiv").removeClass("d-block");
    $(".downloadDiv").addClass("d-none");

    window.scrollTo(0, 0);
}