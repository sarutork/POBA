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
            { data: "studentsId" },
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