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
                window.location.href = ""+data.staffId;
            }
        } );
}