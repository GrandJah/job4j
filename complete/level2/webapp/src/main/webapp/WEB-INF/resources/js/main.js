var use_user = undefined;

function onload() {
    ajax("login", loadPage, {})
}

function ajax(action, fun, data) {
    $.ajax({
        method: "POST",
        cache: false,
        url: "../../ajax",
        data: JSON.stringify({
            action: action,
            data: data
        }),
        success: fun,
        error: function (a,s,d) {
            console.log("ajax - error")
        },
        statusCode:{
            200: function () {
                console.log("ajax - OK")
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