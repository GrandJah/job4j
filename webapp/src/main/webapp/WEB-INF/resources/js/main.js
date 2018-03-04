var use_user = undefined;

function onload() {
    ajax("{\"action\":\"login\"}", login);
}


function login(json) {
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