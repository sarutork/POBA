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
    $('#table-reward tbody').on('click', 'tr', function () {
            if(!$('#table-reward tbody tr td').hasClass("dataTables_empty")){
               var data = tableReward.row( this ).data();
                window.location.href = "/poba/rewards/"+data.staffId;
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
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";

                window.scrollTo(0, 0);

                setTimeout(function(){
                    window.location.href = "/poba/rewards";
                },2000);
         }
    });
}