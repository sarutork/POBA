function authenticate(){
    $.ajax({
        url:"authenticate",
        method:"POST",
        data:JSON.stringify({"username":$("#username").val(),"password":$("#passwd").val()}),
        contentType:"application/json",
        dataType:"json",
        success:(data)=>{
            $(".er-lg-in").removeClass("error-show");
            $(".forget-lg-in").removeClass("show");
            if(data.resetPassword == 'Y'){
                $(".auth-from").css("display","none");
                $(".forgot-pwd").addClass("show");
            }else{
                window.location = "home";
            }
        },
        error:(a,b,c)=>{
            $(".er-lg-in").addClass("error-show");
            console.log(a,b,c);
        }
    });
}
function forgotPassword(){
    $.ajax({
        url:"forgot/password",
        method:"POST",
        data:JSON.stringify({"username":$("#username").val()}),
        contentType:"application/json",
        dataType:"json",
        success:(data)=>{
            $(".er-lg-in").removeClass("error-show");
            $(".forget-lg-in").addClass("show");
        },
        error:(a,b,c)=>{
            $(".er-lg-in").addClass("error-show");
            console.log(a,b,c);
        }
    });
}
function confirmNewPassword(){
    $.ajax({
        url:"reset/password",
        method:"POST",
        data:JSON.stringify({"newPassword":$("#newPassword").val(),"confirmPassword":$("#confirmPassword").val()}),
        contentType:"application/json",
        dataType:"json",
        success:(data)=>{
            $(".new-pwd-lg-err").removeClass("show");
            $(".new-pwd-lg-in").addClass("show");
        },
        error:(a,b,c)=>{
            $(".new-pwd-lg-err").find(".alert-danger").html(a.responseJSON.message);
            $(".new-pwd-lg-err").addClass("show");
            console.log(a,b,c);
        }
    });
}