function findConsultantStudent() {
   var tableConsultantStudent=  $('#table-consultant-student').DataTable({
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
                   var prefix = row["studentPrefix"];
                      if(prefix == "อื่นๆ"){
                           prefix = row["studentPrefixOther"]
                      }
                   var fullName = prefix+' '+row["studentName"] + ' ' + row["studentSurname"];
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
               var data = tableConsultantStudent.row( this ).data();
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
    $("#studentsLevel").trigger("change");

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")
}

function findSumConsultant(){
     var tableSumConsultant =  $('#table-sum-consultant').DataTable({
            ajax: {
                type: "GET",
                url: "/poba/consultant/students/search/sum/consultant",
                dataSrc: "",
                data: function(d){
                    d.name = $('#name').val();
                    d.yearOfStudy = $('#yearOfStudy').val();
                    d.studentsLevel = $('#studentsLevel').val();
                    d.course = $('#course').val();
                }
            },
            columns: [
                { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
                { data: "name"},
                { data: "yearOfStudy" },
                { data: "studentsLevel" },
                { data: "course" },
                { data: "countStudent" },
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
                    "defaultContent": "-",
                    "targets": "_all"
                }
            ],
            searching: false,
            "bDestroy": true
        });
        $('#table-sum-consultant tbody').on('click', 'tr', function () {
                if(!$('#table-sum-consultant tbody tr td').hasClass("dataTables_empty")){
                   var data = tableSumConsultant.row( this ).data();
                    $("#prefix").val(data.prefix !=null ? data.prefix : data.prefixOther);
                    $("#name").val(data.name);
                    $("#surname").val(data.surname);
                    $("#countStudent").val(data.countStudent);
                    $("#department").val(data.department);
                    $("#yearOfStudy").val(data.yearOfStudy);
                    $("#studentsLevel").val(data.studentsLevel);
                    $("#course").val(data.course);

                    $("#consult-std-sum-cst").attr("action","/poba/consultant/students/search/sum/consultant/detail");
                    $("#consult-std-sum-cst").submit();
                }
            } );
}

function findStudentByConsultant(){
     var tableSumConsultant =  $('#table-student').DataTable({
            ajax: {
                type: "GET",
                url: "/poba/consultant/students/search/student-by-consultant",
                dataSrc: "",
            },
            columns: [
                { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
                { data: "studentsId"},
                { data: "studentName" },
                { data: "admissionStatus" },
            ],
            columnDefs: [
                {
                   render: function (data, type, row) {
                       var prefix = row["studentPrefix"];
                       if(prefix == "อื่นๆ"){
                            prefix = row["studentPrefixOther"]
                       }
                       var fullName = prefix+' '+row["studentName"] + ' ' + row["studentSurname"];
                           return fullName;
                   },
                   targets: 2,
                },
                {
                    "defaultContent": "-",
                    "targets": "_all"
                }
            ],
            searching: false,
            "bDestroy": true
        });
}