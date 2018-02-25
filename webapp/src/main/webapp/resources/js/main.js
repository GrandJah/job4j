var tableUser;
var UseUser;

function onload() {
    // ajax("dcsc", fillTable);
    stubAjax("us", function (data) {
        UseUser = data,
            setCreateForm(data.create);
    });
    stubAjax("ut", fillTable);

}

function stubAjax(url, func) {
    if(url === "ut")
        func({
            table : 'Таблица пользователей',
            table_id : 'table_user',
            rows : [
                {login: 'login' , name: 'name' , email: 'email', role: 'user', location: 'MegaCity' },
                {login: 'logqwin' , name: 'name' , email: 'email', role: 'user', location: 'MetroCity' },
                {login: 'loqwgin' , name: 'name' , email: 'email', role: 'admin', location: 'MegaCity' },
                {login: 'logdqin' , name: 'name' , email: 'email', role: 'user', location: 'MegaCity' },
                {login: 'logdin' , name: 'name' , email: 'email', role: 'admin', location: 'OldCity' },
                {login: 'logwdin' , name: 'name' , email: 'email', role: 'user', location: 'MegaCity' },
                {login: 'logwqqin' , name: 'name' , email: 'email', role: 'user', location: 'MegaCity' },
                {login: 'login' , name: 'name' , email: 'email', role: 'user', location: 'MegaCity' }
            ]
        });
    if(url === "us")
        func({
            login: 'login',
            create: true,
            edit: "user"
        })
}

function setCreateForm(flag) {
    document.getElementById("newUserForm").style.display = flag ? 'block' : 'none';
}

function openCreateForm() {
    var button = document.getElementById("createFormButton");
    button.children[0].style.display = 'none';
    button.children[1].style.display = 'block';
    button.setAttribute('onclick', 'closeCreateForm()');

    var form = document.getElementById("createForm");
    form.style.display = "block";
}

function closeCreateForm() {
    var button = document.getElementById("createFormButton");
    button.children[0].style.display = 'block';
    button.children[1].style.display = 'none';
    button.setAttribute('onclick', 'openCreateForm()');

    var form = document.getElementById("createForm");
    form.style.display = "none";
}

var rowTemplate;

function fillTable(json) {
    var table = document.getElementById("userTable");
    table.style.display = 'none';
    table.children[0].innerHTML = json.table;
    var body = table.children[2];
    rowTemplate = body.children[0].cloneNode(true);
    rowTemplate.id =  json.table_id + "-" ;
    body.removeChild(body.children[0]);
    tableUser = [];
    while (tableUser.length < json.rows.length)
        addUserTable(json.rows[tableUser.length]);
    table.style.display = 'table';
}

function addUserTable(user) {
    var body = document.getElementById("userTable").children[2];
    var el = rowTemplate.cloneNode(true);
    el.id +=  tableUser.length;
    el.children[0].innerHTML = tableUser.length + 1;
    el.children[1].innerHTML = user.login;
    el.children[2].innerHTML = user.name;
    el.children[3].innerHTML = user.email;
    el.children[4].innerHTML = user.role !== undefined ? user.role : 'user';
    el.children[5].innerHTML = user.location !== undefined ? user.location : "MetroCity";
    body.appendChild(el);
    tableUser[tableUser.length] = user;
}

function ajax(url, json, func) {
    $.post(url,json,func);
}

function createUser(){
    event.preventDefault();
    var form = document.getElementById("createForm");
    var newUser = {};
    var valid = true;
    for(var i = 0; i < 3; i++) {
        var el = form.children[i].children[1];
        if (el.validity.valid) {
            el.classList.remove('is-invalid')
            el.classList.add('is-valid')
            switch (el.name) {
                case 'login' : newUser.login = el.value; break;
                case 'email' : newUser.email = el.value; break;
                case 'name' : newUser.name = el.value; break;
            }
        } else {
            el.classList.remove('is-valid')
            el.classList.add('is-invalid')
            valid = false;
        }
    }
    if (valid) {
        // stubAjax("create", newUser, addUserTable);
        addUserTable(newUser);
    }
}

