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
                <button id="btn_reg">Зарегистрироватся</button>
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
        const switchForm = (visible, ...hide) => () => {
            _stub()
            console.log(visible)
            _visible(visible)
            hide.forEach(el => _hide(el))
        }

        const login = () => {
            _stub();
            let form = _search("#login");
            username = form.elements.username.value
            let pass = form.elements.password.value;
            let data = {
                action: "login",
                username,
                pass
            };

            _ajax(data, data => {
                if (data.success === true) {
                    _set_cookie("token", data.token)
                    _search("#user").innerText = username
                    switchForm('create', 'register', 'login') //todo заменить показ форма на действие
                } else if (data.success === false && data.error === 'errorUser') {
                    switchForm('#login', '#register')
                    _visible('#error_user')
                } else {
                    console.log("error login - " + data.error);
                }
            });
        }

        const register = () => {
            _stub();
            let form = _search("#register");
            username = form.elements.usernamesignup.value
            let pass = form.elements.passwordsignup.value;
            if (pass !== form.elements.passwordsignup_confirm.value) {
                _visible('#error_pass')
                return
            }
            let data = {
                action: "register",
                username,
                pass
            };
            _ajax(data, data => {
                if (data.success === true) {
                    _set_cookie("token", data.token)
                    _search("#user").innerText = username
                    switchForm('create', 'register', 'login')//todo заменить показ форма на действие
                } else if (data.success === false && data.error === 'errorName') {
                    switchForm('#register', '#login')
                    _visible('error_name')
                } else {
                    console.log("error register - " + data.error);
                }
            });
        }

        _search("#btn_login_switch").onclick = switchForm('#login', '#register')
        _search("#btn_reg_switch").onclick = switchForm('#register', '#login')
        _search("#btn_login").onclick = login
        _search("#btn_reg").onclick = register

        const m = {
            id: "login",
            data: {},
            property: {
                hidden: true,
                login: _cookies().token !== undefined
            },
            method: {
                closeDialog: () => {
                    _search("#login_form").setAttribute("hidden", "")
                    console.log(_find_module("login"))
                    m.property.hidden = true
                },

                openDialog: () => {
                    _search("#login_form").removeAttribute("hidden")
                    m.property.hidden = false
                }
            }
        }
        _add_module(m)
    })()
</script>
<style>
    #login_form {
        width: 100%;
        padding: 1em;
        background: lightslategrey;
    }

    .error {
        color: red;
    }
</style>
