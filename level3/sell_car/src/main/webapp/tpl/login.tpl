<template>
    <div id="login_form" hidden>
        <form id="login">
            <h1>Войти</h1>
            <p>
                <label for="username"> Ваш логин </label>
                <input id="username" name="username" required="required" type="text"
                       placeholder="myusername"
                       oninput="_hide('#error_user')"/>
            </p>
            <p>
                <label for="password"> Ваш пароль </label>
                <input id="password" name="password" required="required" type="password"
                       placeholder="eg. X8df!90EO"
                       oninput="_hide('#error_user')"/>
            </p>
            <p class="login button">
                <button id="btn_login">Войти</button>
                <label hidden id='error_user' class="error">Пользователь не найден</label>
            </p>
            <p class="change_link">
                Не зарегестрированы?
                <button id="btn_reg_switch">Регистрация</button>
            </p>
        </form>
        <form hidden id="register">
            <h1> Sign up </h1>
            <p>
                <label for="usernamesignup">Ваше имя</label>
                <input id="usernamesignup" name="usernamesignup" required="required" type="text"
                       placeholder="mysuperusername"
                       oninput="_hide('#error_name')"/>
                <label hidden id='error_name' class="error">Данное имя уже существует</label>
            </p>
            <p>
                <label for="emailsignup">Ваше имя</label>
                <input id="emailsignup" name="emailsignup" required="required" type="email"
                       placeholder="myemail@com"
                       oninput="_hide('#error_email')"/>
                <label hidden id='error_email' class="error">Данная почта уже
                    зарегистрирована</label>
            </p>
            <p>
                <label for="passwordsignup">Ваш пароль</label>
                <input id="passwordsignup" name="passwordsignup" required="required"
                       type="password" placeholder="eg. X8df!90EO"
                       oninput="_hide('#error_pass')"/>
            </p>
            <p>
                <label for="passwordsignup_confirm">Подтвердите пароль</label>
                <input id="passwordsignup_confirm" name="passwordsignup_confirm"
                       required="required" type="password" placeholder="eg. X8df!90EO"
                       oninput="_hide('#error_pass')"/>
                <label hidden id='error_pass' class="error">Пароли не совпадают</label>
            </p>
            <p class="signin button">
                <button id="btn_reg">Зарегистрироваться</button>
            </p>
            <p class="change_link">
                Уже зарегистрированы ?
                <button id="btn_login_switch">Войти</button>
            </p>
        </form>
    </div>
</template>
<script>
    (function () {
        const switchForm = (visible, ...hide) => {
            _stub()
            if (visible) {
                _visible(visible)
            }
            [...hide, "#error_user", "#error_name", "#error_pass", "#error_email"].forEach(el => _hide(el))
        }

        const action = _find_module("action")

        const set_user = (token, username) => {
            _set_cookie("token", token)
            _set_cookie("username", username)
            m.method.change_login(true, username)
        }

        const login = () => {
            _stub();
            const form = _search("#login");
            const username = form.elements.username.value
            const pass = form.elements.password.value;

            action.login({username, pass})
                .then(data => set_user(data.token, username))
                .catch(error => {
                    if (error === 'errorUser') {
                        switchForm('#login', '#register')
                        _visible('#error_user')
                    } else {
                        _debug(`error login - ${error}`);
                    }
                })
        }

        const register = () => {
            _stub();
            const form = _search("#register");
            const username = form.elements.usernamesignup.value
            const email = form.elements.emailsignup.value
            const pass = form.elements.passwordsignup.value;

            if (pass !== form.elements.passwordsignup_confirm.value) {
                _visible('#error_pass')
                return
            }

            action.register({username, email, pass})
                .then(data => set_user(data.token, username))
                .catch(error => {
                    if (error === 'errorName') {
                        switchForm('#register', '#login')
                        _visible('#')
                    }
                    if (error === 'errorEmail') {
                        switchForm('#register', '#login')
                        _visible('#error_email')
                    } else {
                        _debug(`error register - ${error}`);
                    }
                })
        }

        _search("#btn_login_switch").onclick = () => switchForm('#login', '#register')
        _search("#btn_reg_switch").onclick = () => switchForm('#register', '#login')
        _search("#btn_login").onclick = login
        _search("#btn_reg").onclick = register

        const m = {
            id: "login",
            data: {},
            property: {
                hidden: true,
                login: _cookies().token !== undefined,
                username: _cookies().username !== undefined ? _cookies().username : "unknown"
            },
            method: {
                closeDialog: () => {
                    _search("#login_form").setAttribute("hidden", "")
                    m.property.hidden = true
                    _render()
                },

                openDialog: () => {
                    switchForm('#login', '#register')
                    _search("#login_form").removeAttribute("hidden")
                    m.property.hidden = false
                    _render()
                },
                logout: () => {
                    _del_cookie("token", token)
                    _del_cookie("username", username)
                    m.method.change_login(false, "unknown")
                },
                change_login: (login, username) => {
                    m.property.login = login
                    m.property.username = username
                    m.method.closeDialog();
                    m.emit("change_login",{login, username})
                }
            },
            slots: [
                "change_login" // login, username
            ]
        }
        _add_module(m)
    })()
</script>
<style>
    #login_form {
        padding: 1em;
        background: lightslategrey;
        border-radius: 0 0 20px 20px;
    }

    .error {
        color: red;
    }
</style>
