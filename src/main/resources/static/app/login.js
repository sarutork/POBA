function authenticate(){
    $.ajax({
        url:"authenticate",
        method:"POST",
        data:JSON.stringify({"username":$("#username").val(),"password":$("#passwd").val()}),
        contentType:"application/json",
        dataType:"json",
        success:(data)=>{
            $(".er-lg-in").removeClass("error-show");
            window.location = "home";
        },
        error:(a,b,c)=>{
            $(".er-lg-in").addClass("error-show");
            console.log(a,b,c);
        }
    });
}