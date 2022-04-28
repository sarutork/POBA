function loadView(url) {
    $.get(url, function(data) {
        $('.content-wrapper').html(data);
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