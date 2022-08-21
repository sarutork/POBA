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
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name"},
            { data: "institutionInfo" },
            { data: "teachTopic" },
            { data: "teachTimes" },
            { data: "teachDate"},
            { data: "noteOfTeach" },
            { data: "studyType" },
            { data: "semester" },
            { data: "studyYear" },
            { data: "subjectId"},
            { data: "subjectName" },
            { data: "subjectCredit" },
            { data: "currentCredit" },
            { data: "teachType" },
            { data: "teachDay"},
            { data: "studyStart" },
            { data: "studyEnd" },
            { data: "teachLocation" },
            { data: "teachRoom"},
            { data: "teachStyle" },
            { data: "teachStyleDetail" },
            { data: "totalOfStudents" },
            { data: "totalStudentsRegister" }
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                    return row["prefix"]+' '+row["name"];
               },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                   var semester = row["semester"];
                   const semesterArray = semester.split(":");
                   return semesterArray[1]
                   },
               targets: 8,
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