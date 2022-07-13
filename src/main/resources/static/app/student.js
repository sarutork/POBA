function findStudent() {
   var tableStudents =  $('#table-students').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/students/search",
            dataSrc: "",
            data: function(d){
                d.studentsName = $('#studentsName').val();
                d.studentsYear = $('#studentsYear').val();
                d.studentsLevel = $('#studentsLevel').val();
            }
        },
        columns: [
            { data:"id"},
            { data: "studentsId"},
            { data: "studentsName" },
            { data: "studentsYear" },
            { data: "studentsLevel" },
            { data: "studentsStatus" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var prefix = row["studentsPrefix"];
                      if(prefix == "อื่นๆ"){
                           prefix = row["studentsPrefixOther"]
                      }
                      var fullName = prefix+' '+row["studentsName"] + ' ' + row["studentsSurname"];
                          return fullName;
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
        ]
    });
    $('#table-students tbody').on('click', 'tr', function () {
            if(!$('#table-students tbody tr td').hasClass("dataTables_empty")){
               var data = tableStudents.row( this ).data();
                 loadView('/poba/students/'+data.id);
            }
        } );
}

function submitStudentInfo(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/students/save",
         data: $("#form-student").serialize(),
         success: function(data) {
                setTimeout(function(){
                     loadView('/poba/students');
                 },3000);
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editStudentInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข");

    $("#viewName").text("แก้ไข");

    $("#studentsLevel").trigger("change");

    window.scrollTo(0, 0);
}
function searchSummary(){
    if(window.tableStudents2 != undefined){
        window.tableStudents2.fnDestroy();
    }
    $.when(validateSummarySearch()).then(function(data){
        if(data == true){
            $.when(summaryStudentSearch()).then(function(data){
                if(data.body.length > 0){
                    window.tableStudents2 = $("#table-students2").dataTable();
                }else{
                    if(window.tableStudents2 != undefined){
                        window.tableStudents2.fnDestroy();
                    }
                }
            });
        }
    });
}
function validateSummarySearch(){
    var yearStart = $('#fromYear').val();
    var yearEnd = $('#toYear').val();

        if($("#studentsLevel").val() == ""){
            $("#msgAlert").text("กรุณาเลือกประเภทรายงาน");
            $("#msgAlertDiv").removeClass("d-none");
            $("#msgAlertDiv").addClass("d-block");
            return false;
        }

        if(yearStart == "" || yearEnd == ""){
            $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษา");
            $("#msgAlertDiv").removeClass("d-none");
            $("#msgAlertDiv").addClass("d-block");
            return false;
        }

        var yearStartInt = parseInt(yearStart);
        var yearEndInt = parseInt(yearEnd);

        if(yearEndInt - yearStartInt < 0){
            $("#msgAlert").text("กรุณาเลือกปีที่เข้าศึกษาเริ่มต้นน้อยกว่าปีที่เข้าศึกษาสิ้นสุด");
            $("#msgAlertDiv").removeClass("d-none");
            $("#msgAlertDiv").addClass("d-block");
            return false;
        }else if(yearEndInt - yearStartInt > 9){
            $("#msgAlert").text("เงื่อนไขในการค้นหาต้องไม่เกิน 10 ปี");
            $("#msgAlertDiv").removeClass("d-none");
            $("#msgAlertDiv").addClass("d-block");
            return false;
        }

        $("#msgAlertDiv").removeClass("d-block");
        $("#msgAlertDiv").addClass("d-none");
    return true;
}
function summaryStudentSearch(){
    return $.ajax({
         type: "GET",
         url: "/poba/students/summary/search",
         data: {"fromYear" : $("#fromYear").val(),"toYear" : $("#toYear").val(),level:$("#studentsLevel").val()},
         success: function(data) {
            $("#table-students2 tbody").html("");
            var thtr = "<tr><th>#</th>";
            for(var i =0 ;i < data.header.length ;i++){
                if(data.header[i] == 0){
                    thtr += "<th>อาจารย์ที่ปรึกษา</th>";
                }else{
                    if(data.header[i]!= "-"){
                        thtr += "<th>"+data.header[i].substring(2,4)+"4X</th>";
                    }else{
                        thtr += "<th>"+data.header[i]+"</th>";
                    }

                }
            }
            thtr += "</tr>";
            $("#table-students2 thead").html(thtr);

            if(data.body.length > 0){
                for(var i =0 ;i < data.body.length ;i++){
                    var trtd = "<tr><td>"+(i+1)+"</td>";
                    for(var j =0 ;j < data.header.length ;j++){
                        if(j == 0){
                            trtd += "<td>"+data.body[i].name+"</td>";
                        }else{
                            if(data.body[i][data.header[j]] != undefined){
                                trtd += "<td>"+data.body[i][data.header[j]]+"</td>";
                            }else{
                                trtd += "<td> - </td>";
                            }

                        }

                    }
                    trtd += "</tr>";
                    $("#table-students2 tbody").append(trtd);
                }
            }else{
                $("#table-students2 tbody").html('<tr><td colspan="12" class="dataTables_empty text-center bg-secondary">รายงานจำนวนนิสิต</td></tr>');
            }

         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}