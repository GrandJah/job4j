<template>
    <div id="listing-card">
        <h1>Тут заголовок листинга</h1>
        <div>
            <div hidden id="card-item">
                <div class="card_adv">
                    <div class="ratio ratio_card">
                        <div class="ratio-content border_1">
                            <div class="content">
                                <div id="image_container">
                                    <div class="img ratio ratio_img border_2">
                                        <div class="ratio-content">
                                            <div class="image_container"></div>
                                            <button class="left">&lt;</button>
                                            <button class="right">&gt;</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="info">
                                    <div class="ratio ratio_inf_bar header_desc">
                                        <div class="ratio-content bar out">
                                            <div class="align_price">
                                                <div class="price">

                                                </div>
                                            </div>
                                            <div class="align_status">
                                                <div class="status">

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="ratio ratio_description">
                                        <div class="ratio-content">
                                            <div class="description out">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script src="js/$.js"></script>
<script>
    (() => {
        const card = _search("#card-item")
        const list = card.parentElement
        list.removeChild(card)

        const switch_image = (direction) => {
            const images = [..._search(".image_container", event.target.parentElement).childNodes]
            for (let i in images) {
                if (!images[i].hidden) {
                    const new_i = direction ? (+i + 1) : (+i + images.length - 1)
                    images[i].hidden = true
                    images[new_i % images.length].hidden = false
                    return
                }
            }
        }

        const add_image = (container, filepath) => {
            const image = _create("img")
            if (image.src !== filepath) {
                image.src = filepath;
            }
            image.setAttribute("hidden", "")
            container.appendChild(image)
        }

        const action = _find_module("action")

        //todo смена статуса объявления
        const click_status = (item, button) => () => {
            const target = _search(".status", event.target.parentElement)
            button.onclick = ""
            button.style.backgroundColor = "blue"
            action.change_status(item.id)
                .then(status => {
                        target.style.color = status ? "pink" : "lightgreen";
                        target.innerText = status ? "Не продали? Жми сюда?" : "Уже продали? Жми сюда?";
                        setTimeout(() => {
                            button.onclick = click_status(item, button)
                            button.style.backgroundColor = "black"
                        }, 5000)

                    }
                )

        }

        const getCard = item => {
            let el = _create("div", item.id)
            el.innerHTML = card.innerHTML
            _search("button.left", el).onclick = () => switch_image(false)
            _search("button.right", el).onclick = () => switch_image(true)
            const image_container = _search(".image_container", el)
            if (item.car.images.length === 0) {
                add_image(image_container, "image/NoPhoto1.png")
            }
            for (let img of item.car.images) {
                add_image(image_container, img.filepath)
            }
            image_container.firstChild.removeAttribute("hidden")
            _search(".price", el).innerText = item.price;
            _search(".status", el).innerText = item.status ? "Продано" : "Продаётся";
            const status = _search(".align_status", el);

            if (action.checkUser(item.user.name)) {
                status.style.backgroundColor = "black";
                _search(".align_status", el).style.width = "65%"
                _search(".status", el).innerText = item.status ? "Не продали? Жми сюда?" : "Уже продали? Жми сюда?";
                _search(".status", el).style.color = item.status ? "pink" : "lightgreen";
                status.onclick = click_status(item, status)
            } else {
                status.style.backgroundColor = item.status ? "pink" : "lightgreen";
            }
            _search(".description", el).innerText = item.description

            //todo загрузка параметров автомобиля
            return el
        }

        _add_render(() => {
            if (state_app.data.itemsChange) { //todo state_app!? o_O
                state_app.data.itemsChange = false
                list.innerText = ''
                state_app.data.items.forEach((item) => {
                    list.appendChild(getCard(item))
                })
            }
        })
    })()
</script>
<style>
    .bar {
        text-align: center;
    }

    .status {
        font-size: small;
        color: white;
        position: relative;
        top: 50%;
        transform: translateY(-50%);
        text-align: center;
    }

    .align_status {
        width: 30%;
        position: relative;
        float: right;
        right: 5%;
        height: 100%;
        border-radius: 5px;
    }

    .align_price {
        width: 25%;
        position: relative;
        float: right;
        height: 100%;
    }

    .price {
        color: blue;
        font-size: large;
        position: relative;
        top: 50%;
        transform: translateY(-50%);
        text-align: center;
    }

    h1 {
        text-align: center;
    }

    .header_desc {
        width: 100%;
        left: -1%;
    }

    .info {
        width: 70%;
        position: relative;
        float: right;
        left: -1%;
    }

    .content {
        margin: 1%;
    }

    .img {
        width: 26%;
        float: left;
        background: lightgrey;
        padding: 0;
    }

    .image_container {
        position: relative;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }

    img {
        max-width: 100%;
        max-height: 100%;
        border-radius: 3px;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        position: absolute;
    }

    .img button {
        position: absolute;
        top: 50%;
        transform: translate(0, -50%);
        background: grey;
        color: white;
        font-size: 8pt;
        padding: 3px 3px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        outline: none;
    }

    .img button:hover {
        background-color: lightgrey;
    }

    .img .left {
        left: 5%;
    }

    .img .right {
        right: 5%;
    }

    .card_adv {
        width: 90%;
        margin: auto;
    }

    .ratio {
        margin: 1%;
        position: relative;
        height: 0;
        border: none;
    }

    .ratio_card {
        padding-top: 30%;
    }

    .ratio_img {
        padding-top: 26%
    }

    .ratio_description {
        width: 100%;
        padding-top: 29%;
        left: -1%;
    }

    .ratio_inf_bar {
        padding-top: 8%
    }

    .ratio-content {
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }

    .border_1 {
        border: 3px solid lightgrey;
        border-radius: 20px;
    }

    .border_2 {
        border: 2px solid grey;
        border-radius: 5px;
    }

    .description {
        left: -2%;
        overflow-y: scroll;
        overflow-x: hidden;
        max-height: 100%;
        font-size: small;
    }

    .out {
        outline: red 1px dashed;
    }
</style>
