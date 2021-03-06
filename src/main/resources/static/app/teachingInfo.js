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
               targets: 3,
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

    $("#viewName").text("???????????????");

    $(".uploadDiv").removeClass("d-none");
    $(".uploadDiv").addClass("d-block");

    $(".downloadDiv").removeClass("d-block");
    $(".downloadDiv").addClass("d-none");

    window.scrollTo(0, 0);
}