function findProfileInfo() {
   var tableProfile =  $('#table-profile').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/personnel-info/profile/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
            }
        },
        columns: [
            { data: "staffId" },
            { data: "name"},
            { data: "structure2" },
            { data: "emailOrg" },
            { data: "tel" },
            { data: "mobile" }
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var fullName = row["prefix"]+' '+row["name"] + ' ' + row["surname"];
                       return fullName;
                    },
               targets: 1,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-profile tbody').on('click', 'tr', function () {
            if(!$('#table-profile tbody tr td').hasClass("dataTables_empty")){
               var data = tableProfile.row( this ).data();
                window.location.href = "/poba/personnel-info/profile/"+data.staffId;
            }
        } );
}

function findEducationInfo() {
   var tableEducation =  $('#table-education').DataTable({
        ajax: {
            type: "GET",
            url: "../../poba/api/personnel-info/education",
            dataSrc: "",
            data: function(d){
                d.name = $('#staff-name').val();
                d.startDate = $('#start-date').val();
                d.endDate = $('#end-date').val();
            }
        },
        columns: [
            { data: "staffId" },
            { data: "name"},
            { data: "location" },
            { data: "country" },
            { data: "startDate" },
            { data: "endDate" }
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var fullName = row["prefix"]+' '+row["name"] + ' ' + row["surname"];
                       return fullName;
                    },
               targets: 1,
            },
        ],
        searching: false,
        "bDestroy": true
    });
    $('#table-education tbody').on('click', 'tr', function () {
            if(!$('#table-education tbody tr td').hasClass("dataTables_empty")){
               var data = tableEducation.row( this ).data();
                window.location.href = "/poba/personnel-info/education/"+data.staffId;
            }
        } );
}

function calDiffDays(date1, date2){
    const diffTime = Math.abs(date2 - date1);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
}


function startDateChange(){
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();

    $("#endDate").attr('min',startDate);

    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
             const date1 = new Date(startDate);
             const date2 = new Date(endDate);
             const diffTime = date2 - date1;
             if (diffTime < 0 ){
                $("#endDate").val("");
             }else{
                diffDays = calDiffDays(date1, date2);
                $("#totalDate").val(diffDays);
             }
    }
}
function endDateChange(){
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
             const date1 = new Date(startDate);
             const date2 = new Date(endDate);
             const diffTime = date2 - date1;
             diffDays = calDiffDays(date1, date2);
             $("#totalDate").val(diffDays);
    }
}

function submitEducationInfo(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/api/personnel-info/education/save",
         data: $("#form-education").serialize(),
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";
                    window.scrollTo(0, 0);
                setTimeout(function(){
                    window.location.href = "/poba/personnel-info/education";
                },2000);
         }
    });
}

function findResearcherInfo() {
    if ($.fn.DataTable.isDataTable('#table-researcher')) {
        $('#table-researcher').DataTable().clear().destroy();
    }
    var tableResearcher = $('#table-researcher').DataTable({
        ajax: {
            type: 'GET',
            url: '/poba/personnel-info/researchers/search',
            dataSrc: '',
            data: function(d) {
                d.name = $('#name').val();
                d.workStartDate = $('#work-start-date').val();
                d.workEndDate = $('#work-end-date').val();
            }
        },
        columns: [
            { data: 'staffId' },
            { data: 'name' },
            { data: 'status' },
            { data: 'type' },
            { data: 'teacher1' },
            { data: 'teacher2' },
            { data: 'subSegment' },
            { data: 'workStartDate' },
            { data: 'workEndDate' }
        ],
        columnDefs: [{
            render: function(data, type, row) {
                var fullName = row['prefix'] + ' ' + row['name'] + ' ' + row['surname'];
                return fullName;
            },
            targets: 1,
        }],
        searching: false,
        'bDestroy': true
    });
    // TODO: Check onClick after search again
    $('#table-researcher tbody').on('click', 'tr', function() {
        if (!$('#table-researcher tbody tr td').hasClass("dataTables_empty")) {
            var data = tableResearcher.row(this).data();
            loadView('/poba/personnel-info/researchers/' + data.staffId);
        }
    });
}
