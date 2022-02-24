function findPublished() {
   var tablePublished=  $('#table-published').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/published/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.publishedLevel = $('#publishedLevel').val();
            }
        },
        columns: [
            { data: "publishedId" },
            { data: "name"},
            { data: "publishedStatus" },
            { data: "publishedTopic" },
            { data: "publishedLevel" },
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
    $('#table-published tbody').on('click', 'tr', function () {
            if(!$('#table-published tbody tr td').hasClass("dataTables_empty")){
               var data = tablePublished.row( this ).data();
                window.location.href = "/poba/published/"+data.publishedId;
            }
        } );
}
function submitPublishedInfo(){
    var type = "POST";
    var staffId = $("#publishedId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/published/save",
         data: $("#form-published").serialize(),
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";

                window.scrollTo(0, 0);

                setTimeout(function(){
                    window.location.href = "/poba/published";
                },2000);
         }
    });
}

function editPublishedInfo(){
        $(":input").prop("disabled", false);

        $("#submit").removeClass("d-none");
        $("#submit").addClass("d-block");

        $("#edit").removeClass("d-block");
        $("#edit").addClass("d-none");

        $("#viewName").text("แก้ไข")

        window.scrollTo(0, 0);
}