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

function setTotalDayMonthYear(startingDate, endingDate) {
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
    $("#totalYear").val(yearDiff);
    $("#totalMonth").val(monthDiff);
    $("#totalDay").val(dayDiff);
    //return yearDiff + 'Y ' + monthDiff + 'M ' + dayDiff + 'D';
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
               setTotalDayMonthYear(startDate,endDate);
             }
    }
}
function endDateChange(){
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(startDate != null && endDate != null && startDate != "" && endDate != ""){
        setTotalDayMonthYear(startDate,endDate);
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
