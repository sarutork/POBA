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
        "bDestroy": true
    });
    $('#table-project tbody').on('click', 'tr', function () {
            if(!$('#table-project tbody tr td').hasClass("dataTables_empty")){
               var data = tableProject.row( this ).data();
                loadView('/poba/project/'+data.projectId);
            }
    } );
}

function submitProjectInfo(){

    var table = $('#table-participant').DataTable();

    var plainArray = table.column(1).data().toArray();

    for (const element of plainArray) { // You can use `let` instead of `const` if you like
        console.log(element);
    }

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

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}

function amtFormat(){
    numberFormat();
}

function findParticipant() {
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
            { data: "studentsId"},
            { data: "studentsName" },
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
                           var prefix = row["studentsPrefix"];
                              if(prefix == "อื่นๆ"){
                                   prefix = row["studentsPrefixOther"]
                              }
                              var fullName = prefix+' '+row["studentsName"] + ' ' + row["studentsSurname"];
                                  return fullName;
                               },
                       targets: 2,
                    },
                    {
                        render: function (data, type, row) {
//                            let str1 = '<select type="text" class="form-control">';
//                            let str2 = '<option value="">กรุณาเลือก</option>';
//                            let str3 = '<option value="X">X</option>';
//                            let str4 = '<option value="Y">Y</option>';
//                            let str5 = '</select>';

                            var str1 = "<select type='text' class='form-control'>";
                            var str2 = "<option value=''>กรุณาเลือก</option>";
                            var str3 = "<option value='X'>X</option>";
                            var str4 = "<option value='Y'>Y</option>";
                            var str5 = "</select>";
                           return str1.concat(str2,str3,str4,str5);
                        },
                        targets: 3,
                    },
                    {
                        "defaultContent": "",
                        "targets": "_all"
                    }
        ],
        searching: false,
        "bDestroy": true
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