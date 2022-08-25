function findTraining() {
   var tableTraining =  $('#table-training').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/training/search",
            dataSrc: "",
            data: function(d){
                d.name1 = $('#name1').val();
                d.trainingLevel = $('#trainingLevel').val();
                d.trainingDateFrom = $('#trainingDateFrom').val();
                d.trainingDateTo = $('#trainingDateTo').val();
            }
        },
        columns: [
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name1"},
            { data: "trainingStatus1" },
            { data: "name2"},
            { data: "trainingStatus2" },
            { data: "name3"},
            { data: "trainingStatus3" },
            { data: "trainingName" },
            { data: "trainingDateFrom" },
            { data: "trainingTimeFrom" },
            { data: "trainingDateTo" },
            { data: "trainingTimeTo" },
            { data: "trainingTotalDay" },
            { data: "trainingLocation" },
            { data: "trainingType" },
            { data: "trainingAnnounce" },
            { data: "trainingJoin" },
            { data: "trainingBudget" },
            { data: "trainingAmountTotal" },
            { data: "trainingThai" },
            { data: "trainingForeign" },
            { data: "trainingTotalPerson" },
            { data: "trainingLevel" },
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                    return row["prefix1"]+' '+row["name1"];
               },
               targets: 1,
            },
            {
               render: function (data, type, row) {
                    return row["prefix2"]+' '+row["name2"];
               },
               targets: 3,
            },
            {
               render: function (data, type, row) {
                    return row["prefix3"]+' '+row["name3"];
               },
               targets: 5,
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

function sumTrainee(){
    var thaiTrainee = $("#trainingThai").val();
    var foreignTrainee = $("#trainingForeign").val();
    $("#trainingTotalPerson").val(parseInt(thaiTrainee) + parseInt(foreignTrainee));
}

function addTrainingPhase(){
    removeComma();
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/training/addPhase",
         data: $("#form-training").serialize(),
         success: function(data) {
            $('.content-wrapper').html(data);
            trainingAmtFormat();
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function removeTrainingPhase(phase){
    removeComma();
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/training/removePhase/"+phase,
         data: $("#form-training").serialize(),
         success: function(data) {
            $('.content-wrapper').html(data);
            trainingAmtFormat();
         },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function trainingAmtFormat(){
    numberFormat();
    calTrainingSum();
}
function calTrainingSum(){
    var sum = 0.00;
    $( ".trainingAmount" ).each( function( i, el ) {
             var elem = $( el );
             sum += parseFloat(elem.val().replace(/,/g, ''));
     });
    $("#trainingAmountTotal").val(new Intl.NumberFormat('th-TH', { style: 'currency', currency: 'THB' }).format(sum).replace('฿', ''));
}

function findPersonnelTraining(index) {
  var searchTxt = $('#searchTxt'+index).val();
  var tablePersonnelModal =  $('#table-personnel-modal'+index).DataTable({
        ajax: {
            type: "GET",
            url: "/poba/personnel-info/profile/search-txt",
            dataSrc: "",
            data:{'searchTxt' : searchTxt},
        },
        columns: [
            { data: "persNo"},
            { data: null}
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

    if(index == 1){
        $('#table-personnel-modal1 tbody').on('click', 'tr', function () {
                if(!$('#table-personnel-modal1 tbody tr td').hasClass("dataTables_empty")){
                   var data = tablePersonnelModal.row( this ).data();
                   var prefix = data.prefix;
                         if(prefix == "อื่นๆ"){
                              prefix = data.prefixOther;
                         }
                   $('#persNo1').val(data.persNo);
                   $('#prefix1').val(prefix);
                   $('#name1').val(data.name+ " "+data.surname);
                   $('#personnelModal1').modal('toggle');
                }
        } );
  }else if(index ==2 ){
        $('#table-personnel-modal2 tbody').on('click', 'tr', function () {
                        if(!$('#table-personnel-modal2 tbody tr td').hasClass("dataTables_empty")){
                           var data = tablePersonnelModal.row( this ).data();
                           var prefix = data.prefix;
                                 if(prefix == "อื่นๆ"){
                                      prefix = data.prefixOther;
                                 }
                           $('#persNo2').val(data.persNo);
                           $('#prefix2').val(prefix);
                           $('#name2').val(data.name+ " "+data.surname);
                           $('#personnelModal2').modal('toggle');
                        }
        } );
  }else if(index == 3){
        $('#table-personnel-modal3 tbody').on('click', 'tr', function () {
            if(!$('#table-personnel-modal3 tbody tr td').hasClass("dataTables_empty")){
               var data = tablePersonnelModal.row( this ).data();
               var prefix = data.prefix;
                     if(prefix == "อื่นๆ"){
                          prefix = data.prefixOther;
                     }
               $('#persNo3').val(data.persNo);
               $('#prefix3').val(prefix);
               $('#name3').val(data.name+ " "+data.surname);
               $('#personnelModal3').modal('toggle');
            }
        } );
  }
}