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
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name"},
            { data: "teachStatus" },
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
            { data: "totalStudentsRegister" },
            { data: "midtermExamDate"},
            { data: "midtermExamTimeStart" },
            { data: "midtermExamTimeEnd" },
            { data: "finalExamDate" },
            { data: "finalExamTimeStart" },
            { data: "finalExamTimeEnd"},

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
               targets: 9,
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
        ],
        language: {
            "emptyTable": "ไม่พบผลการค้นหา"
        }
    });
    $('#table-teaching tbody').on('click', 'tr', function () {
            if(!$('#table-teaching tbody tr td').hasClass("dataTables_empty")){
               var data = tableTeaching.row( this ).data();
                loadView('/poba/teaching/'+data.staffId);
            }
    } );
}

function editTeachingInfo(){
    $(":input").prop("disabled", false);

    $(".btn-phase").prop("disabled", false);

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