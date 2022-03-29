
function findTextbookInfo() {
   var tableTextbook =  $('#table-textbook').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/textbook/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.textbookLevel = $('#textbookLevel').val();
                d.textbookType = $('#textbookType').val();
            }
        },
        columns: [
            { data: "textbookId" },
            { data: "name"},
            { data: "textbookType" },
            { data: "textbookTopic" },
            { data: "textbookLevel" },

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
    $('#table-textbook tbody').on('click', 'tr', function () {
            if(!$('#table-textbook tbody tr td').hasClass("dataTables_empty")){
               var data = tableTextbook.row( this ).data();
                loadView('/poba/textbook/'+data.textbookId);
            }
    } );
}

function submitTextbookInfo(){

    removeComma();

    var type = "POST";
    var textbookId = $("#textbookId").val();
    if (textbookId != null &&  textbookId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/textbook/save",
         data: $("#form-textbook").serialize(),
         success: function(data) {
            setTimeout(function(){
                loadView('/poba/textbook');
            },3000);
            window.scrollTo(0, 0);
            $('.content-wrapper').html(data);
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function addPhase(){
    removeComma();
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/textbook/addPhase",
         data: $("#form-textbook").serialize(),
         success: function(data) {
            $('.content-wrapper').html(data);
            amtFormat();
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function removePhase(phase){
    removeComma();
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/textbook/removePhase/"+phase,
         data: $("#form-textbook").serialize(),
         success: function(data) {
            $('.content-wrapper').html(data);
            amtFormat();
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editTextbookInfo(){
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
    calSum();
}

function calSum(){
    var sum = 0.00;
    $( ".textbookAmount" ).each( function( i, el ) {
             var elem = $( el );
             sum += parseFloat(elem.val().replace(',', ''));
         });
    $("#textbookAmountTotal").val(new Intl.NumberFormat('th-TH', { style: 'currency', currency: 'THB' }).format(sum).replace('฿', ''));
}