function findExtAcademicServices() {
   var tableExtAcademicService =  $('#table-ext-academic-services').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/ext-academic-services/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.level = $('#level').val();
            }
        },
        columns: [
            { data: "id" },
            { data: "name"},
            { data: "title" },
            { data: "type" },
            { data: "typeOther" },
            { data: "year" },
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
    $('#table-ext-academic-services tbody').on('click', 'tr', function () {
            if(!$('#table-ext-academic-services tbody tr td').hasClass("dataTables_empty")){
               var data = tableExtAcademicService.row( this ).data();
                loadView('/poba/ext-academic-services/'+data.id);
            }
    } );
}

function submitExtAcServices(){

    var type = "POST";

    $.ajax({
         type: type,
         url: "/poba/ext-academic-services/save",
         data: $("#form-ext-academic-services").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/ext-academic-services');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editExtAcServices(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}