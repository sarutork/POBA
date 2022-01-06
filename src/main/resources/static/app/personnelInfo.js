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

function initEducationInfo() {
/*
    $.ajaxSetup({
        beforeSend: (xhr) => {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("access_token"));
        }
    });

    var educationInfo = $('#educationInfo').DataTable({
        ajax: {
            type: "GET",
            url: "../../poba/api/personnel-info/education",
            dataSrc: ""
        },
        columns: [
            { data: "staff_id" },
            { data: "name" },
            { data: "location" },
            { data: "country" },
            { data: "start_date" },
            { data: "end_date" }
        ],
        searching: false,
        bInfo: true,
        scrollX: true
    });
*/
    var dataSet = [
        [ 1, 'Mr. Ekamon M.', 'Kasikorn Business-Technology Group', 'Thailand', '1/1/2564', '31/12/2565' ],
        [ 2, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 3, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 4, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 5, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 6, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 7, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 8, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 9, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 10, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 11, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 12, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ],
        [ 13, 'ผศ.ดร. วินัยบุศนินท์', 'University of Wisconsin-Madison', 'สหรัฐอเมริกา', '1/8/2561', '31/7/2562' ]
    ];

    $('#table-education').DataTable({
        data: dataSet,
        columns: [
            { title: '#' },
            { title: 'ชื่อ-นามสกุล' },
            { title: 'มหาวิทยาลัย/สถาบันที่ไป' },
            { title: 'ประเทศ' },
            { title: 'วันที่ไป' },
            { title: 'วันที่กลับ' }
        ],
        searching: false
    });
}