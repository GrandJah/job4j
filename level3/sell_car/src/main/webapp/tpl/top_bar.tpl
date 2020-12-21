<template>
    <div id="col">
        <div class="top_bar">
            <div class="container">
                <div id="create_button" hidden>
                    <button>Новое Объявление</button>
                </div>
                <div>
                    <label id="user_name"></label>
                </div>
                <div>
                    <button id="user_button">OK!</button>
                </div>
            </div>
            <div id="user_form"></div>
            <div id="create_form_site"></div>
        </div>
        <div class="opacity"></div>
        <div id="card_list_site" class="list"></div>
    </div>
</template>
<script src="js/$.js"></script>
<script>
    (() => {
        const create_button = _search("#create_button")

        const user_button = _search("#user_button")

        const user_name = _search("#user_name")

        _loadUrlTpl("login", "#user_form", login => {
            user_button.onclick = () => {
                if (login.property.login) {
                    login.method.logout()
                } else {
                    if (login.property.hidden) {
                        login.method.openDialog()
                    } else {
                        login.method.closeDialog()
                    }
                }
            }

            login.on("change_visible", isVisible => {
                if (isVisible) _pipe.go("loginForm")
            })

            login.on("change_login", e => {
                const {login, username} = e
                user_name.innerText = username
                user_button.innerText = login ? "Выйти" : "Войти"
                create_button.hidden = !login
                if (!login) {
                    _pipe.go("loginForm")
                }
            })

            login.method.emit_status();
        })

        _loadUrlTpl("listing_card", "#card_list_site")

        _loadUrlTpl("create_form", "#create_form_site", create => {
            _pipe.on("loginForm", create.method.closeDialog)
            create_button.onclick = create.method.switch_form
        })

        _add_render(() =>
            _search(".list").style.height =
                (document.documentElement.clientHeight
                    - _search(".top_bar").clientHeight) + "px"
        )
    })()
</script>
<style>
    body {
        margin: 0;
        overflow: hidden;
    }

    #user_form, #create_form_site {
        width: 80%;
        margin: 0 auto;
        top: 60pt;
        position: relative;
    }

    #col {
        max-width: 800px;
        min-width: 450px;
        margin: auto;
    }

    #user_button {
        float: right;
        right: 5pt;
        width: 15%;
    }

    .container button {
        right: 20%;
        position: absolute;
        top: 50%;
        transform: translate(0, -50%);
    }

    #user_name {
        color: blue;
        float: left;
        left: 2%;
        font-size: x-large;
        font-weight: bold;
        font-family: Verdana, sans-serif;
        position: absolute;
        top: 50%;
        transform: translate(0, -50%);
    }

    .opacity {
        top: -1pt;
        position: relative;
        height: 16pt;
        z-index: 100;
        background-image: linear-gradient(rgba(255, 255, 255, 1.0), rgba(255, 255, 255, 0.8), rgba(255, 255, 255, 0));
    }


    button {
        display: inline-block;
        color: white;
        text-decoration: none;
        padding: .5em 2em;
        outline: none;
        border-width: 2px 0;
        border-style: solid none;
        border-color: skyblue black lightskyblue;
        border-radius: 6px;
        background: linear-gradient(lightskyblue, skyblue) lightblue;
        transition: 0.2s;
    }

    button:hover {
        background: linear-gradient(skyblue, deepskyblue) skyblue;
    }

    button:active {
        background: linear-gradient(deepskyblue, skyblue) deepskyblue;
    }

    .top_bar {
        width: inherit;
        position: relative;
        height: 60pt;
        z-index: 100;
    }

    .top_bar .container {
        height: 0;
    }

    .list {
        top: -16pt;
        position: relative;
        overflow-x: hidden;
        overflow-y: scroll;
    }
</style>
