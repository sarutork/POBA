function findProjectInfo() {
   var tableProject =  $('#table-project').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/project/search",
            dataSrc: "",
            data: function(d){
                d.projectName = $('#projectName').val();
                d.projectYear = $('#projectYear').val();
                d.projectDateFrom = $('#projectDateFrom').val();
            }
        },
        columns: [
            { data: "projectId" },
            { data: "projectYear"},
            { data: "projectName" },
            { data: "projectType" },
            { data: "projectDateFrom" },
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
    $('#table-project tbody').on('click', 'tr', function () {
            if(!$('#table-project tbody tr td').hasClass("dataTables_empty")){
               var data = tableProject.row( this ).data();
                loadView('/poba/project/'+data.projectId);
            }
    } );
}

function submitProjectInfo(){

    removeComma();

    var type = "POST";

    $.ajax({
         type: type,
         url: "/poba/project/save",
         data: $("#form-project").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/project');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editProjectInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข");

    $(".btn-add").prop("disabled", false);
    $(".btn-del").prop("disabled", false);

    window.scrollTo(0, 0);
}

function amtFormat(){
    numberFormat();
}

function findParticipant() {

  $("#table-participant").on("draw.dt", function () {
      $(this).find(".dataTables_empty").parents('tbody').empty();
  }).DataTable();

  var index = $('#table-participant').dataTable().fnGetData().length;
  //var index = $('#table-participant tr').length;;
  var indexId = index;
  var indexName = index;
  var indexStatus = index;

  var searchTxt = $('#searchTxt').val();
  var tableParticipantModal =  $('#table-participant-modal').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/project/search-participant",
            dataSrc: "",
            data:{'searchTxt' : searchTxt},
        },
        columns: [
            { data: null},
            { data: null},
            { data: null },
            { data: null}
        ],
        columnDefs: [
                    {
                        render: function (data, type, row) {
                           return '<input type="checkbox" value="' + $('<div/>').text(data).html() + '">';
                        },
                        targets: 0,
                    },
                    {
                        render: function (data, type, row) {
                           var participantId = row["participantId"];
                           var inputStr = '<input type="text" name="participants['+(indexId++)+'].participantId" value="'+participantId+'" style="border: none;"  readonly/>';

                           return inputStr;
                        },
                        targets: 1,
                    },
                    {
                       render: function (data, type, row) {
                              var fullName = row["name"];
                              var inputStr = '<input type="text" name="participants['+(indexName++)+'].name" value="'+fullName+'" style="border: none;"  readonly/>';
                                  return inputStr;
                               },
                       targets: 2,
                    },
                    {
                        render: function (data, type, row) {
                            var str1 = "<select type='text' class='form-control' name='participants["+(indexStatus++)+"].status'>";
                            var str2 = "<option value=''>กรุณาเลือก</option>";
                            var str3 = "<option value='ที่ปรึกษาโครงการ'>ที่ปรึกษาโครงการ</option>";
                            var str4 = "<option value='ที่ปรึกษา/เข้าร่วมโครงการ'>ที่ปรึกษา/เข้าร่วมโครงการ</option>";
                            var str5 = "<option value='ผู้รับผิดชอบโครงการ'>ผู้รับผิดชอบโครงการ</option>";
                            var str6 = "<option value='ผู้ปฏิบัติงาน'>ผู้ปฏิบัติงาน</option>";
                            var str7 = "<option value='เข้าร่วมโครงการ'>เข้าร่วมโครงการ</option>";
;                           var str8 = "<option value='สัมภาษณ์ทุน'>สัมภาษณ์ทุน</option>";
                            var str9 = "</select>";
                           return str1.concat(str2,str3,str4,str5,str6,str7,str8,str9);
                        },
                        targets: 3,
                    },
                    {
                        "defaultContent": "",
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
        ]
    });
    $('#table-participant-modal tbody').on('click', 'tr', function () {
            if(!$('#table-participant-modal tbody tr td').hasClass("dataTables_empty")){
               var data = tableParticipantModal.row( this ).data();
            }
    } );
}

function addParticipant(){
    var getSelectedRows = $("#table-participant-modal input:checked").parents("tr").clone();
    $("#table-participant tbody").append(getSelectedRows);
}

function removeParticipant(){
    //var length = $("#table-participant input:checked").parents("tr");

    $("#table-participant input:checked").parents("tr").remove();


}