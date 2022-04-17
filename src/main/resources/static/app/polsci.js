function findPolsciInfo() {
   var tablePolsci =  $('#table-polsci').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/polsci/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.polsciYear = $('#polsciYear').val();
                d.polsciDateFrom = $('#polsciDateFrom').val();
            }
        },
        columns: [
            { data: "polsciId" },
            { data: "name"},
            { data: "polsciName" },
            { data: "polsciYear" },
            { data: "polsciDateFrom" },
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
    $('#table-polsci tbody').on('click', 'tr', function () {
            if(!$('#table-polsci tbody tr td').hasClass("dataTables_empty")){
               var data = tablePolsci.row( this ).data();
                loadView('/poba/polsci/'+data.polsciId);
            }
    } );
}

function submitPolsciInfo(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/polsci/save",
         data: $("#form-polsci").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/polsci');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editPolsciInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}