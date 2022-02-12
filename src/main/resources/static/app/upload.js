var uploaded = $('input.file-id').toArray().map(i => Number($(i).val()));
var selected = new DataTransfer();

$('#button-upload').click(function(event) { // TODO: Check file type, size
    event.preventDefault();
    let form = $('#form-upload')[0];
    let formData = new FormData(form);
    formData.append('filesToKeep', uploaded);
    $.each(selected.files, function(i, file) {
        formData.append('newFiles', file);
    });
    $.ajax({
        method: "POST",
        url: $(form).attr('action'),
        enctype: 'multipart/form-data',
        data: formData,
        processData: false,
        contentType: false,
        cache: false,
        success: function (data) {
            $('.content-wrapper').html(data);
        },
        error: function (error) {
            $('.content-wrapper').html(error.responseText);
        }
    });
});

$('#input-file').on('change', function() {
    for (let i = 0; i < this.files.length; i++) {
        let fileDiv = $('<div/>', { class: 'col-sm-6 mb-2' }),
            fileName = $('<span/>', { class: 'file-name', text: this.files.item(i).name }),
            fileRemove = $('<span/>', { class: 'file-remove close mdi mdi-close-circle' });
        fileDiv.append(fileName).append(fileRemove);
        $('#file-divs').append(fileDiv);
    }
    for (let file of this.files) {
        selected.items.add(file);
    }
    this.files = selected.files;
    $('span.file-remove').click(function() {
        removeFile(this);
    });
});

function removeFile(fileRemove) {
    let name = $(fileRemove).prev('span.file-name').text();
    for (let i = 0; i < selected.items.length; i++) {
        if (name === selected.items[i].getAsFile().name) {
            selected.items.remove(i);
            continue;
        }
    }
    $('#input-file')[0].files = selected.files;
    $(fileRemove).parent().remove();
}

function removeUploadedFile(fileRemove) {
    let id = $(fileRemove).next('input.file-id').val();
    for (let i = 0; i < uploaded.length; i++) {
        if (uploaded[i] == id) {
            uploaded.splice(i, 1);
            continue;
        }
    }
    $(fileRemove).parent().remove();
}
