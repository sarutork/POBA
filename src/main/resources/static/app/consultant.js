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
            { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
            { data: "name"},
            { data: "studentName" },
            { data: "yearOfStudy" },
            { data: "studentsLevel" },
            { data: "admissionStatus"},
            { data: "course"},
            { data: "department" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                    return  row["prefix"]+' '+row["name"];
               },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                return row["studentPrefix"]+' '+row["studentName"];
               },
               targets: 2,
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
    $('#table-consultant-student tbody').on('click', 'tr', function () {
            if(!$('#table-consultant-student tbody tr td').hasClass("dataTables_empty")){
               var data = tableConsultantStudent.row( this ).data();
                loadView('/poba/consultant/students/'+data.consultantStudentId);
            }
        } );
}

function submitConsultantStudent(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/consultant/students/save",
         data: $("#form-consultant-student").serialize(),
         success: function(data) {
                 setTimeout(function(){
                     loadView('/poba/consultant/students');
             },3000);
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
                error: function (error) {
                 $('.content-wrapper').html(error.responseText);
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

    $(".btn-search").prop("disabled", false);

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
        $('#table-sum-consultant tbody').on('click', 'tr', function () {
                if(!$('#table-sum-consultant tbody tr td').hasClass("dataTables_empty")){
                   var data = tableSumConsultant.row( this ).data();

                    loadView('/poba/consultant/students/search/sum/consultant/detail/'+data.name+'/'+data.surname);

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
}

function findYearlyReport(){
    var yearStart = $('#yearStart').val();
    var yearEnd = $('#yearEnd').val();

    if(yearStart == "" || yearEnd == ""){
        $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษา");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }

    var yearStartInt = parseInt(yearStart);
    var yearEndInt = parseInt(yearEnd);

    if(yearEndInt - yearStartInt < 0){
        $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษาเริ่มต้นน้อยกว่าปีที่เข้าศึกษาสิ้นสุด");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }else if(yearEndInt - yearStartInt > 9){
        $("#msgAlert").text("เงื่อนไขในการค้นหาต้องไม่เกิน 10 ปี");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }

    $("#msgAlertDiv").removeClass("d-block");
    $("#msgAlertDiv").addClass("d-none");

    var tableYearlyReport = $('#table-yearly-report').DataTable({
            ajax: {
                type: "GET",
                url: "/poba/consultant/students/search/yearly-report",
                dataSrc: "",
                data: function(d){
                    d.name = $('#name').val();
                    d.yearStart = $('#yearStart').val();
                    d.yearEnd = $('#yearEnd').val();
                    d.admissionStatus = $('#admissionStatus').val();
                    d.studentsLevel = $('#studentsLevel').val();
                }
            },
            columns: [
                { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
                { data: "name"},
                { data: "studentsLevel" },
                { data: "admissionStatus" },
                { data: "sumYear1" },
                { data: "sumYear2" },
                { data: "sumYear3" },
                { data: "sumYear4" },
                { data: "sumYear5" },
                { data: "sumYear6" },
                { data: "sumYear7" },
                { data: "sumYear8" },
                { data: "sumYear9" },
                { data: "sumYear10" },
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
                       let admissionStatus = row["admissionStatus"];

                       if(admissionStatus != "" && admissionStatus != null){
                         const adStatusArray = admissionStatus.split(":");
                          return adStatusArray[1];
                       }
                       return admissionStatus;
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
            ],
            language: {
                "emptyTable": "ไม่พบผลการค้นหา"
            }
        });

        for(var i = 0 ;i < 10 ;i++ ){
            $(tableYearlyReport.column(4+i).header()).text("-");
        }

        for(var i = 0 ;i <= (yearEndInt - yearStartInt) ;i++ ){
            $(tableYearlyReport.column(4+i).header()).text(""+(yearStartInt+i));
        }
}

//ConsultantThesis
function findConsultantThesis() {
   var tableConsultantThesis=  $('#table-consultant-thesis').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/consultant/thesis/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.thesisType = $('#thesisType').val();
                d.studentsLevel = $('#studentsLevel').val();
            }
        },
        columns: [
           { data:  null,"sortable": false,
                    render: function (data, type, row, meta) {
                       return meta.row + meta.settings._iDisplayStart + 1;
                    }},
            { data: "name"},
            { data: "consultantPosition"},
            { data: "studentsId"},
            { data: "studentName" },
            { data: "studentsLevel" },
            { data: "course"},
            { data: "thesisType" },
            { data: "thesisTopic"},
            { data: "thesisConsider"},
            { data: "thesisStartdate"},
            { data: "thesisApprove"},
            { data: "thesisSuccessDate"},
            { data: "thesisSuccess"},
            { data: "thesisSuccessDate"},
            { data: "thesisAssessment" },
            { data: "publishedTopic"},
            { data: "publishedJournal"},
            { data: "publishedYear"},
            { data: "publishedIssue"},
            { data: "publishedPage"},
            { data: "publishedMonth"},
            { data: "publishedYear2"},
            { data: "publishedBase"},
            { data: "publishedLevel"},
            { data: "conferenceTopic"},
            { data: "conferenceName"},
            { data: "conferenceInstitution"},
            { data: "conferenceLevel"},
            { data: "conferenceDateFrom"},
            { data: "conferenceDateTo"},
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
                    return row["studentPrefix"]+' '+row["studentName"];
               },
               targets: 4,
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
    $('#table-consultant-thesis tbody').on('click', 'tr', function () {
            if(!$('#table-consultant-thesis tbody tr td').hasClass("dataTables_empty")){
               var data = tableConsultantThesis.row( this ).data();
                loadView('/poba/consultant/thesis/'+data.thesisId);
            }
        } );
}

function editThesisInfo(){
    $(":input").prop("disabled", false);
    $("#studentsLevel").trigger("change");

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    $(".btn-search").prop("disabled", false);
}

function findConsultantStudent2() {
   var tableConsultantStudent=  $('#table-consultant-student').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/consultant/students2/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#staffName').val();
                d.yearOfStudy = $('#yearOfStudy').val();
                d.studentsLevel = $('#studentsLevel').val();
            }
        },
        columns: [
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name"},
            { data: "studentName" },
            { data: "yearOfStudy" },
            { data: "studentsLevel" },
            { data: "admissionStatus"},
            { data: "course"},
            { data: "department" },

        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                    return  row["prefix"]+' '+row["name"];
               },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                return row["studentPrefix"]+' '+row["studentName"];
               },
               targets: 2,
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
    $('#table-consultant-student tbody').on('click', 'tr', function () {
            if(!$('#table-consultant-student tbody tr td').hasClass("dataTables_empty")){
               var data = tableConsultantStudent.row( this ).data();
                loadView('/poba/consultant/students2/'+data.consultantStudentId);
            }
        } );
}
function submitConsultantStudent2(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/consultant/students2/save",
         data: $("#form-consultant-student").serialize(),
         success: function(data) {
                 setTimeout(function(){
                     loadView('/poba/consultant/students2');
             },3000);
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
                error: function (error) {
                 $('.content-wrapper').html(error.responseText);
         }
    });
}
function findYearlyReport2(){
    var yearStart = $('#yearStart').val();
    var yearEnd = $('#yearEnd').val();

    if(yearStart == "" || yearEnd == ""){
        $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษา");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }

    var yearStartInt = parseInt(yearStart);
    var yearEndInt = parseInt(yearEnd);

    if(yearEndInt - yearStartInt < 0){
        $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษาเริ่มต้นน้อยกว่าปีที่เข้าศึกษาสิ้นสุด");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }else if(yearEndInt - yearStartInt > 9){
        $("#msgAlert").text("เงื่อนไขในการค้นหาต้องไม่เกิน 10 ปี");
        $("#msgAlertDiv").removeClass("d-none");
        $("#msgAlertDiv").addClass("d-block");
        return;
    }

    $("#msgAlertDiv").removeClass("d-block");
    $("#msgAlertDiv").addClass("d-none");

    var tableYearlyReport = $('#table-yearly-report').DataTable({
            ajax: {
                type: "GET",
                url: "/poba/consultant/students2/search/yearly-report",
                dataSrc: "",
                data: function(d){
                    d.name = $('#name').val();
                    d.yearStart = $('#yearStart').val();
                    d.yearEnd = $('#yearEnd').val();
                    d.admissionStatus = $('#admissionStatus').val();
                    d.studentsLevel = $('#studentsLevel').val();
                }
            },
            columns: [
                { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
                { data: "name"},
                { data: "studentsLevel" },
                { data: "admissionStatus" },
                { data: "sumYear1" },
                { data: "sumYear2" },
                { data: "sumYear3" },
                { data: "sumYear4" },
                { data: "sumYear5" },
                { data: "sumYear6" },
                { data: "sumYear7" },
                { data: "sumYear8" },
                { data: "sumYear9" },
                { data: "sumYear10" },
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
                       let admissionStatus = row["admissionStatus"];

                       if(admissionStatus != "" && admissionStatus != null){
                         const adStatusArray = admissionStatus.split(":");
                          return adStatusArray[1];
                       }
                       return admissionStatus;
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
            ],
            language: {
                "emptyTable": "ไม่พบผลการค้นหา"
            }
        });

        for(var i = 0 ;i < 10 ;i++ ){
            $(tableYearlyReport.column(4+i).header()).text("-");
        }

        for(var i = 0 ;i <= (yearEndInt - yearStartInt) ;i++ ){
            $(tableYearlyReport.column(4+i).header()).text(""+(yearStartInt+i));
        }
}