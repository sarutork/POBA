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
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
            { data: "name"},
            { data: "structureNameLevel2" },
            { data: "emailOrg" },
            { data: "tel" },
            { data: "mobile" }
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
    $('#table-profile tbody').on('click', 'tr', function () {
            if(!$('#table-profile tbody tr td').hasClass("dataTables_empty")){
               var data = tableProfile.row( this ).data();
                loadView('/poba/personnel-info/profile/'+data.staffId);
            }
     } );
}

function submitProfileInfo(){
    var type = "POST";
    $.ajax({
         type: type,
         url: "/poba/personnel-info/profile/save",
         data: $("#form-profile").serialize(),
         success: function(data) {
                setTimeout(function(){
                     loadView('/poba/personnel-info/profile');
                 },3000);
                 window.scrollTo(0, 0);
                 $('.content-wrapper').html(data);
            },
         error: function (error) {
            $('.content-wrapper').html(error.responseText);
         }
    });
}

function editProfileInfo(){
    $(":input").prop("disabled", false);

    $("#submit").removeClass("d-none");
    $("#submit").addClass("d-block");

    $("#edit").removeClass("d-block");
    $("#edit").addClass("d-none");

    $("#viewName").text("แก้ไข")

    window.scrollTo(0, 0);
}

function startWorkDateChange(){
    var startDate = $("#startWorkDate").val();
    var endDate = $("#startCountWorkDate").val();
    $("#startCountWorkDate").attr('min',startDate);
    startCountWorkOHECDateChange();
}

function startCountWorkDateChange(){
    var startDate = $("#startWorkDate").val();
    var endDate = $("#startCountWorkDate").val();
    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
             const date1 = new Date(startDate);
             const date2 = new Date(endDate);
             const diffTime = date2 - date1;
             if (diffTime < 0 ){
                $("#toWorkDate").val("");
             }else{
               setTotalDayMonthYear(startDate,endDate,"totalWorkYear","totalWorkMonth","totalWorkDay");
             }
    }

    startCountWorkOHECDateChange();
}

function startCountWorkOHECDateChange(){
    var startDate = $("#startWorkDate").val();
    var endDate = $("#startCountWorkDate").val();
    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
             const date1 = new Date(startDate);
             const date2 = new Date(endDate);
             const diffTime = date2 - date1;
             if (diffTime < 0 ){
                $("#toWorkDate").val("");
             }else{
                 let ohecCount = calDiffDays(date1, date2)/30;

                 let totalOhec = 0;
                 if(ohecCount > 5 && ohecCount <= 9){
                    totalOhec = 0.5;
                 }else if(ohecCount > 9){
                    totalOhec = 1;
                 }
                $("#totalWorkOHEC").val(totalOhec);
             }
    }
}
function toWorkDateChange(){
    startWorkDateChange();
    startCountWorkDateChange();
}

function findStudyInfo() {
   var tableStudy =  $('#table-study').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/personnel-info/study/search",
            dataSrc: "",
            data: function(d){
                d.name = $('#staff-name').val();
                d.startDate = $('#start-date').val();
                d.endDate = $('#end-date').val();
            }
        },
        columns: [
            { data:  null,"sortable": false,
                         render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1;
                         }},
            { data: "name"},
            { data: "location" },
            { data: "country" },
            { data: "startDate" },
            { data: "endDate" }
        ],
        columnDefs: [
            {
               render: function (data, type, row) {
                   var fullName = row["prefix"]+' '+row["name"];
                       return fullName;
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
    $('#table-study tbody').on('click', 'tr', function () {
            if(!$('#table-study tbody tr td').hasClass("dataTables_empty")){
               var data = tableStudy.row( this ).data();
                loadView('/poba/personnel-info/study/' + data.staffId);
            }
        } );
}

function calDiffDays(date1, date2){
    const diffTime = Math.abs(date2 - date1);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return diffDays;
}

function setTotalDayMonthYear(startingDate, endingDate, setFieldYear, setFieldMonth, setFieldDay) {
    var startDate = new Date(new Date(startingDate).toISOString().substr(0, 10));
    if (!endingDate) {
        endingDate = new Date().toISOString().substr(0, 10);    // need date in YYYY-MM-DD format
    }
    var endDate = new Date(endingDate);
    if (startDate > endDate) {
        var swap = startDate;
        startDate = endDate;
        endDate = swap;
    }
    var startYear = startDate.getFullYear();
    var february = (startYear % 4 === 0 && startYear % 100 !== 0) || startYear % 400 === 0 ? 29 : 28;
    var daysInMonth = [31, february, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];

    var yearDiff = endDate.getFullYear() - startYear;
    var monthDiff = endDate.getMonth() - startDate.getMonth();
    if (monthDiff < 0) {
        yearDiff--;
        monthDiff += 12;
    }
    var dayDiff = endDate.getDate() - startDate.getDate();
    if (dayDiff < 0) {
        if (monthDiff > 0) {
            monthDiff--;
        } else {
            yearDiff--;
            monthDiff = 11;
        }
        dayDiff += daysInMonth[startDate.getMonth()];
    }
        $("#"+setFieldYear).val(yearDiff);
        $("#"+setFieldMonth).val(monthDiff);
        $("#"+setFieldDay).val(dayDiff);
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
               setTotalDayMonthYear(startDate,endDate,"totalYear","totalMonth","totalDay");
             }
    }
}
function endDateChange(){
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
        setTotalDayMonthYear(startDate,endDate,"totalYear","totalMonth","totalDay");
    }
}

function submitStudyInfo(){
    var type = "POST";
    var staffId = $("#staffId").val();
    if (staffId != null &&  staffId != 0 ){
        type = "PUT"
    }
    $.ajax({
         type: type,
         url: "/poba/personnel-info/study/save",
         data: $("#form-study").serialize(),
         success: function(data) {
            $('.content-wrapper').html(data);
         },
         error: function (error) {
             $('.content-wrapper').html(error.responseText);
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
            { data:  null,"sortable": false,
                     render: function (data, type, row, meta) {
                        return meta.row + meta.settings._iDisplayStart + 1;
                     }},
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
                var fullName = row['prefix'] + ' ' + row['name'];
                return fullName;
            },
            targets: 1,
        }],
        searching: false,
        'bDestroy': true,
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
    $('#table-researcher tbody').on('click', 'tr', function() {
        if (!$('#table-researcher tbody tr td').hasClass("dataTables_empty")) {
            var data = tableResearcher.row(this).data();
            loadView('/poba/personnel-info/researchers/' + data.staffId);
        }
    });
}
