function authenticate(){
    window.location = "home";
    /*$.ajax({
        url:"authenticate",
        method:"POST",
        data:JSON.stringify({"username":$("#username").val(),"password":$("#passwd").val()}),
        contentType:"application/json",
        dataType:"json",
        success:(data)=>{
            $(".er-lg-in").removeClass("error-show");
            localStorage.setItem("access_token", data.jwttoken);
            localStorage.setItem("refresh_token", data.refreshToken);
            window.location = "home";
        },
        error:(a,b,c)=>{
            $(".er-lg-in").addClass("error-show");
            console.log(a,b,c);
        }
    });*/
}