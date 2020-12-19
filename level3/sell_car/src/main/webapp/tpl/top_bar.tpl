<template>
    <div id="col">
        <div class="top_bar">
            <div class="container">
                <div>
                    <button id="user_button">OK!</button>
                </div>
                <div id="user_form"></div>
            </div>
        </div>
        <div class="opacity"></div>
        <div class="list"></div>
    </div>
</template>
<script src="js/$.js"></script>
<script>
    const user_button = _search("#user_button")

    _loadUrlTpl("listing_card", ".list")
    _loadUrlTpl("login", "#user_form", () => {
        const login = _find_module("login")

        user_button.onclick = () => {
            if (login.property.hidden) {
                login.method.openDialog()
            } else {
                login.method.closeDialog()
            }
        }

        user_button.innerText = login.property.login
            ? user_button.innerText = "Выйти"
            : user_button.innerText = "Войти"
    })


    _add_render(() =>
        _search(".list").style.height =
            (document.documentElement.clientHeight
                - _search(".top_bar").clientHeight) + "px"
    )

    window.onresize = function () {
        _render();
    };
</script>
<style>
    body {
        margin: 0;
        overflow: hidden;
    }

    #user_form {
        width: 80%;
        margin: 0 auto;
        top: 80pt;
        position: relative;
    }

    #col {
        max-width: 800px;
        margin: auto;
    }

    #user_button {
        float: right;
        right: 5pt;
        position: absolute;
        top: 50%;
        transform: translate(0, -50%);
    }

    .opacity {
        top: -1pt;
        position: relative;
        height: 16pt;
        z-index: 100;
        outline: blue 1px dotted;
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
        outline: blue 1px dotted;
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
