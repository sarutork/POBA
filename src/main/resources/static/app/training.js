function findTraining() {
   var tableTraining =  $('#table-training').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/training/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.trainingLevel = $('#trainingLevel').val();
            }
        },
        columns: [
            { data:"trainingId"},
            { data: "name"},
            { data: "trainingStatus" },
            { data: "trainingLevel" },
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
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-training tbody').on('click', 'tr', function () {
            if(!$('#table-training tbody tr td').hasClass("dataTables_empty")){
               var data = tableTraining.row( this ).data();
                 loadView('/poba/training/'+data.trainingId);
            }
        } );
}

function submitTrainingInfo(){
    removeComma();
    var type = "POST";
    var staffId = $("#trainingId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/training/save",
         data: $("#form-training").serialize(),
         success: function(data) {
                setTimeout(function(){
                     loadView('/poba/training');
                 },3000);
                 amtFormat();
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editTrainingInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}