function loadView(url) {
    $.get(url, function(data) {
        $('.content-wrapper').html(data);
    });
}

function alertSuccess() {

}

function alertError() {

}