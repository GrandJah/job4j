<template>
    <div hidden id="create_form">
        <div class="space"></div>
        <form id="create">
            <input type="text" name="model" value="Модель" title="model">
            <button>Опубликовать</button>
        </form>
        <div id="image_area"></div>
        <form id="upload">
            Загрузите фото: <input name="fileUpload" required multiple type="file"/>
            <button>Загрузить фото</button>
        </form>
    </div>
</template>
<script>
    (function () {
        function upload() {
            _stub()
            fetch("upload", {
                method: 'post',
                body: new FormData(document.querySelector("#upload"))
            })
        }

        _search("#upload button").onclick = upload
        _search("#create button").onclick = () => {
            _stub()
            _debug("click create")
        }


        // const switchForm = (visible, ...hide) => {
        //     _stub()
        //     if (visible) {
        //         _visible(visible)
        //     }
        //     [...hide, "#error_user", "#error_name", "#error_pass", "#error_email"].forEach(el => _hide(el))
        // }
        //
        // const action = _find_module("action")
        //
        // const set_user = (token, username) => {
        //     _set_cookie("token", token)
        //     _set_cookie("username", username)
        //     m.method.change_login(true, username)
        // }
        //
        // const login = () => {
        //     _stub();
        //     const form = _search("#login");
        //     const username = form.elements.username.value
        //     const pass = form.elements.password.value;
        //
        //     action.login({username, pass})
        //         .then(data => set_user(data.token, username))
        //         .catch(error => {
        //             if (error === 'errorUser') {
        //                 switchForm('#login', '#register')
        //                 _visible('#error_user')
        //             } else {
        //                 _debug(`error login - ${error}`);
        //             }
        //         })
        // }
        //
        // const register = () => {
        //     _stub();
        //     const form = _search("#register");
        //     const username = form.elements.usernamesignup.value
        //     const email = form.elements.emailsignup.value
        //     const pass = form.elements.passwordsignup.value;
        //
        //     if (pass !== form.elements.passwordsignup_confirm.value) {
        //         _visible('#error_pass')
        //         return
        //     }
        //
        //     action.register({username, email, pass})
        //         .then(data => set_user(data.token, username))
        //         .catch(error => {
        //             if (error === 'errorName') {
        //                 switchForm('#register', '#login')
        //                 _visible('#')
        //             }
        //             if (error === 'errorEmail') {
        //                 switchForm('#register', '#login')
        //                 _visible('#error_email')
        //             } else {
        //                 _debug(`error register - ${error}`);
        //             }
        //         })
        // }
        //
        const m = {
            id: "create_form",
            //     data: {},
            property: {
                hidden: true,
                //         login: _cookies().token !== undefined,
                //         username: _cookies().username !== undefined ? _cookies().username : "unknown"
            },
            method: {
                closeDialog: () => { //todo
                    _hide("#create_form")
                    m.property.hidden = true
                    //             _render()
                },
                //         emit_status: () =>
                //             m.emit("change_login", {
                //                 login: m.property.login,
                //                 username: m.property.username
                //             }),
                openDialog: () => { //todo
                    //             switchForm('#login', '#register')
                    _visible("#create_form")
                    m.property.hidden = false
                    //             _render()
                },
                //         logout: () => {
                //             _del_cookie("token", token)
                //             _del_cookie("username", username)
                //             m.method.change_login(false, "unknown")
                //         },
                //         change_login: (login, username) => {
                //             m.property.login = login
                //             m.property.username = username
                //             m.method.closeDialog();
                //             m.emit("change_login", {login, username})
                //         },
                switch_form: () => {
                    if (m.property.hidden) {
                        m.method.openDialog()
                    } else {
                        m.method.closeDialog()
                    }
                }
            },
            slots: [
                //         "change_login" // login, username
            ]
        }
        _add_module(m)
    })()
</script>
<style>
    #create_form {
        padding: 1em;
        background: azure;
        border-radius: 0 0 20px 20px;
        border: lightslategrey 1px solid;
    }

    .space {
        height: 20pt;
    }
</style>
