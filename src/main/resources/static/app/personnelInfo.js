function initHistoryInfo() {
    var dataSet = [
        [ 1, 'ผศ.ดร. วินัยบุศนินท์', 'ภาควิชาการปกครองรศ', 'CHAIYAN.C@CHULA.AC.TH', '87220, 87221', '0837853335' ],
        [ 2, 'ผศ.ดร. วินัยบุศนินท์', 'ภาควิชาการปกครองรศ', 'CHAIYAN.C@CHULA.AC.TH', '87220', '0837853335' ],
        [ 3, 'ผศ.ดร. วินัยบุศนินท์', 'ภาควิชาการปกครองรศ', 'CHAIYAN.C@CHULA.AC.TH', '87220', '0837853335' ],
        [ 4, 'ผศ.ดร. วินัยบุศนินท์', 'ภาควิชาการปกครองรศ', 'CHAIYAN.C@CHULA.AC.TH', '87220', '0837853335' ],
        [ 5, 'ผศ.ดร. วินัยบุศนินท์', 'ภาควิชาการปกครองรศ', 'CHAIYAN.C@CHULA.AC.TH', '87220', '0837853335' ],
        [ 6, 'John', 'Doe 6', 'STAFF.6@CHULA.AC.TH', '00006', '0000000006' ],
        [ 7, 'John', 'Doe 7', 'STAFF.7@CHULA.AC.TH', '00007', '0000000007' ],
        [ 8, 'John', 'Doe 8', 'STAFF.8@CHULA.AC.TH', '00008', '0000000008' ],
        [ 9, 'John', 'Doe 9', 'STAFF.9@CHULA.AC.TH', '00009', '0000000009' ],
        [ 10, 'John', 'Doe 10', 'STAFF.10@CHULA.AC.TH', '00010', '0000000010' ],
        [ 11, 'John', 'Doe 11', 'STAFF.11@CHULA.AC.TH', '00011', '0000000011' ],
        [ 12, 'John', 'Doe 12', 'STAFF.12@CHULA.AC.TH', '00012', '0000000012' ],
        [ 13, 'John', 'Doe 13', 'STAFF.13@CHULA.AC.TH', '00013', '0000000013' ],
        [ 14, 'John', 'Doe 14', 'STAFF.14@CHULA.AC.TH', '00014', '0000000014' ],
        [ 15, 'John', 'Doe 15', 'STAFF.15@CHULA.AC.TH', '00015', '0000000015' ],
        [ 16, 'John', 'Doe 16', 'STAFF.16@CHULA.AC.TH', '00016', '0000000016' ],
        [ 17, 'John', 'Doe 17', 'STAFF.17@CHULA.AC.TH', '00017', '0000000017' ],
        [ 18, 'John', 'Doe 18', 'STAFF.18@CHULA.AC.TH', '00018', '0000000018' ],
        [ 19, 'John', 'Doe 19', 'STAFF.19@CHULA.AC.TH', '00019', '0000000019' ],
        [ 20, 'John', 'Doe 20', 'STAFF.20@CHULA.AC.TH', '00020', '0000000020' ],
        [ 21, 'John', 'Doe 21', 'STAFF.21@CHULA.AC.TH', '00021', '0000000021' ],
        [ 22, 'John', 'Doe 22', 'STAFF.22@CHULA.AC.TH', '00022', '0000000022' ],
        [ 23, 'John', 'Doe 23', 'STAFF.23@CHULA.AC.TH', '00023', '0000000023' ],
        [ 24, 'John', 'Doe 24', 'STAFF.24@CHULA.AC.TH', '00024', '0000000024' ],
        [ 25, 'John', 'Doe 25', 'STAFF.25@CHULA.AC.TH', '00025', '0000000025' ]
    ];

    $('#table-history').DataTable({
        data: dataSet,
        columns: [
            { title: '#' },
            { title: 'ชื่อ-นามสกุล' },
            { title: 'ชื่อโครงสร้างระดับ 2' },
            { title: 'อีเมลจุฬาฯ' },
            { title: 'เบอร์ห้องทํางาน' },
            { title: 'เบอร์มือถือ' }
        ],
        searching: false
    });
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
   var tableResearcher =  $('#table-researcher').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/personnel-info/researchers/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#name').val();
                d.workStartDate = $('#work-start-date').val();
                d.workEndDate = $('#work-end-date').val();
            }
        },
        columns: [
            { data: "staffId" },
            { data: "name"},
            { data: "status" },
            { data: "type" },
            { data: "workStartDate" },
            { data: "workEndDate" }
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
    $('#table-researcher tbody').on('click', 'tr', function () {
            if(!$('#table-researcher tbody tr td').hasClass("dataTables_empty")){
               var data = tableResearcher.row( this ).data();
                window.location.href = "/poba/personnel-info/researchers/"+data.staffId;
            }
        } );
}

function submitResearcherInfo(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/personnel-info/researchers/add",
         data: $("#form-researcher").serialize(),
         success: function() {
                //$("#noti-msg").text("บันทึกสำเร็จ");
                var x = document.getElementById("noti-msg");
                    x.style.display = "block";
                    window.scrollTo(0, 0);
                setTimeout(function(){
                    window.location.href = "/poba/personnel-info/researchers";
                },2000);
         }
    });
}