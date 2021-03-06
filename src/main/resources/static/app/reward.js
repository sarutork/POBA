function findRewardInfo() {
   var tableReward =  $('#table-reward').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/rewards/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.rewardLevel = $('#rewardLevel').val();
                d.rewardDate = $('#rewardDate').val();
            }
        },
        columns: [
            { data:"staffId"},
            { data: "name"},
            { data: "rewardType" },
            { data: "rewardName" },
            { data: "rewardLevel" },
            { data: "rewardDate" },

        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   return row["prefix"]+' '+row["name"];
               },
               targets: 1,
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
    $('#table-reward tbody').on('click', 'tr', function () {
            if(!$('#table-reward tbody tr td').hasClass("dataTables_empty")){
               var data = tableReward.row( this ).data();
                 loadView('/poba/rewards/'+data.staffId);
            }
        } );
}

function submitRewardInfo(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/rewards/save",
         data: $("#form-reward").serialize(),
         success: function(data) {
                $('.content-wrapper').html(data);
            },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}