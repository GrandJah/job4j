var use_user = undefined;

function onload() {
    ajax("login", loadPage, {})
}

function ajax(action, fun, data) {
    console.log(action, fun, data)
    $.ajax({
        method: "POST",
        cache: false,
        url: "../../ajax",
        data: JSON.stringify({
            action: action,
            data: data
        }),
        success: [
            function () {
                console.log("complete")
                console.log(fun)
            },
            function (a,s,d) {
                console.log("ajax a - " + a)
                console.log("ajax s - " + s)
                console.log("ajax d - " + d)
            },
            fun],
        error: function (a,s,d) {
            console.log("err a - " + a)
            console.log("err s - " + s)
            console.log("err d - " + d)
        },
        statusCode:{
            200: function () {
                console.log("200 OK")
            }
        }
    });
}

function logout() {
    ajax("login", loadPage, {action:"logout"});
}

function loadPage(json) {
    use_user = json.useUser;
    if(use_user !== undefined) $("#main_container").load("user_table.htm","",user_table_load);
    else $("#main_container").load("login.htm")
}

function user_table_load() {

    update();
    if (use_user.create) {
        $("#newUserForm").load("create_form.htm")
    }
}

function update(){
    $("#table_container").load("table_users.htm");
}