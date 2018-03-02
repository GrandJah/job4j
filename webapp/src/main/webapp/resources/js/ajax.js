function ajax(json_string, func) {
    var json = JSON.parse(json_string);
    console.log("action - " + json.action);
    if(json.action === "table")
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

    if(json.action === "login")
        func({
            useUser: {
                login: 'login',
                create: true,
                edit: "user"
            }
        });

    if(json.action === "createUser")  func();
}