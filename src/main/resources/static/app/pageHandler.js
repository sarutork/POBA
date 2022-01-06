'use strict';
(function(window, $) {
    var pageHandler = function(option) {
        var pData = ((option.data == undefined || option.data == "") ? {} : option.data);
        var pUrl = option.url;
        var isShowTopBar = ((option.topbar == undefined || option.topbar == "true") ? true : false);
        // console.log("pageHandler: ", $(this).html('<div class="jumping-dots-loader"><span></span><span></span><span></span></div>'));
        // console.log("pageHandler: ", $(this).html('<div class="loader-demo-box"><div class="circle-loader"></div></div>'));
        $(this).load(pUrl, pData, function(responseTxt, statusTxt, xhr) {
            if (isShowTopBar) {
                $("#content").show();
            } else {
                $("#content").hide();
            }
            if (xhr.status == 200) {
                if (option.fnCallBack) {
                    option.fnCallBack();
                }
            } else {
                window.location = "login";
            }
        });
    };
    $.fn.pageHandler = pageHandler;
    pageHandler.$ = $;
})(window, jQuery);