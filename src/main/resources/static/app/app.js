function loadView(url) {
    $.get(url, function(data) {
        $('.content-wrapper').html(data);
    }).fail(function() {
      window.location = "login";
    });
}

function alertSuccess() {

}

function alertError() {

}

function numberFormat(){
   $( ".amount" ).each( function( i, el ) {
        var elem = $( el );
        var num = elem.val().replace(/,/g, '');
        var numFormat = new Intl.NumberFormat('th-TH', { style: 'currency', currency: 'THB' }).format(num).replace('฿', '');
        elem.val(numFormat);
   });
}

function removeComma(){
     $( ".amount" ).each( function( i, el ) {
         var elem = $( el );
         elem.val( elem.val().replace(/,/g, ''));
     });
}

function findPersonnel() {
  var searchTxt = $('#searchTxt').val();
  var tablePersonnelModal =  $('#table-personnel-modal').DataTable({
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
        "bDestroy": true
    });
    $('#table-personnel-modal tbody').on('click', 'tr', function () {
            if(!$('#table-personnel-modal tbody tr td').hasClass("dataTables_empty")){
               var data = tablePersonnelModal.row( this ).data();
               var prefix = data.prefix;
                     if(prefix == "อื่นๆ"){
                          prefix = data.prefixOther;
                     }
               $('#persNo').val(data.persNo);
               $('#prefix').val(prefix);
               $('#name').val(data.name+ " "+data.surname);
               $('#personnelModal').modal('toggle');
            }
    } );
}

function findStudentModal() {
  var searchTxt = $('#searchStTxt').val();
  var tableStModal =  $('#table-student-modal').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/students/search-txt-level1",
            dataSrc: "",
            data:{'searchTxt' : searchTxt},
        },
        columns: [
            { data: "studentsId"},
            { data: null}
        ],
        columnDefs: [
                    {
                        render: function (data, type, row) {
                           var prefix = row["studentsPrefix"];
                              if(prefix == "อื่นๆ"){
                                   prefix = row["studentsPrefixOther"]
                              }
                              var fullName = prefix+' '+row["studentsName"] + ' ' + row["studentsSurname"];
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
        "bDestroy": true
    });
    $('#table-student-modal tbody').on('click', 'tr', function () {
            if(!$('#table-student-modal tbody tr td').hasClass("dataTables_empty")){
               var data = tableStModal.row( this ).data();
               var prefix = data.studentsPrefix;
                     if(prefix == "อื่นๆ"){
                          prefix = data.studentsPrefixOther;
                     }
               $('#studentsId').val(data.studentsId);
               $('#studentPrefix').val(prefix);
               $('#studentName').val(data.studentsName+" "+data.studentsSurname);
               $('#yearOfStudy').val(data.studentsYear);
               $('#studentsLevel').val(data.studentsLevel);
               $('#admissionStatus').val(data.studentsHow);
               $('#course').val(data.studentsCourse);
               $('#studentModal').modal('toggle');
            }
    } );
}

function findStudent2Modal() {
  var searchTxt = $('#searchSt2Txt').val();
  var tableSt2Modal =  $('#table-student-modal2').DataTable({
        ajax: {
            type: "GET",
            url: "/poba/students/search-txt-level23",
            dataSrc: "",
            data:{'searchTxt' : searchTxt},
        },
        columns: [
            { data: "studentsId"},
            { data: null}
        ],
        columnDefs: [
                    {
                        render: function (data, type, row) {
                           var prefix = row["studentsPrefix"];
                              if(prefix == "อื่นๆ"){
                                   prefix = row["studentsPrefixOther"]
                              }
                              var fullName = prefix+' '+row["studentsName"] + ' ' + row["studentsSurname"];
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
        "bDestroy": true
    });
    $('#table-student-modal2 tbody').on('click', 'tr', function () {
            if(!$('#table-student-modal2 tbody tr td').hasClass("dataTables_empty")){
               var data = tableSt2Modal.row( this ).data();
               var prefix = data.studentsPrefix;
                     if(prefix == "อื่นๆ"){
                          prefix = data.studentsPrefixOther;
                     }
               $('#studentsId').val(data.studentsId);
               $('#studentPrefix').val(prefix);
               $('#studentName').val(data.studentsName+" "+data.studentsSurname);
               $('#yearOfStudy').val(data.studentsYear);
               $('#studentsLevel').val(data.studentsLevel);
               $('#admissionStatus').val(data.studentsHow);
               $('#course').val(data.studentsCourse);
               $('#student2Modal').modal('toggle');
            }
    } );
}