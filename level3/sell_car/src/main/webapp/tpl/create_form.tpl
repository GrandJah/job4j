<template>
    <div hidden id="create_form">
        <div class="space"></div>
        <form id="create">
            <input type="text" name="model" value="Модель" title="model">
            <input type="text" name="price" value="Стоимость" title="price">
            <div id="image_area">
                <div id="categories"></div>
                <div class="img ratio ratio_img border_2">
                    <div class="ratio-content">
                        <div class="image_container"></div>
                        <button class="left">&lt;</button>
                        <button class="right">&gt;</button>
                    </div>
                </div>
            </div>
            <div class="comment">
                <label>Комментарий продавца:
                    <textarea rows="5" wrap="soft" name="description"></textarea>
                </label>
            </div>
            <div class="submit">
                <button class="right">Опубликовать</button>
            </div>
        </form>
        <form id="upload">
            Загрузите фото: <input name="fileUpload" required multiple type="file"/>
            <button>Загрузить фото</button>
        </form>
    </div>
</template>
<script>
    (function () {
        const action = _find_module("action")
        const image_container = _search("#image_area .image_container")
        const switch_image = (direction) => {
            _stub()
            const images = [...image_container.childNodes]
            for (let i in images) {
                if (!images[i].hidden) {
                    const new_i = direction ? (+i + 1) : (+i + images.length - 1)
                    images[i].hidden = true
                    images[new_i % images.length].hidden = false
                    return
                }
            }
        }
        _search("button.left", image_container.parentNode).onclick = () => switch_image(false)
        _search("button.right", image_container.parentNode).onclick = () => switch_image(true)
        const submit = _search("#create .submit")
        submit.hidden = true;

        function upload() {
            action.upload(
                new FormData(document.querySelector("#upload")),
                data => {
                    if (data.success) {
                        m.method.addPhoto(...data.files)
                    }
                    submit.hidden = !m.method.hasPhoto()
                    _search("#upload").reset();
                }
            )
        }

        _search("#upload button").onclick = upload


        submit.onclick = () => {
            _stub()
            const form = _search("#create");
            const model = form.elements.model.value
            const price = form.elements.price.value
            const description = form.elements.description.value
            const photo = [...m.data.photos]
            const categories = {}
            for (let category in m.property.categories) {
                categories[category] = form.elements[category].value
            }
            const advert = {model, price, photo, description, categories}
            action.create_advert({advert}, () => {
                document.querySelector("#upload").reset()
                form.reset()
                image_container.innerText = ""
                m.data.photos = []
                m.method.closeDialog()
                action.updateAdvList();
                _render();
            })
        }

        const m = {
            id: "create_form",
            data: {
                photos: []
            },
            property: {
                hidden: true,
                categories: {},
            },
            method: {
                closeDialog: () => { //todo
                    _hide("#create_form")
                    m.property.hidden = true
                    //             _render()
                },
                openDialog: () => { //todo
                    _visible("#create_form")
                    m.property.hidden = false
                    submit.hidden = !m.method.hasPhoto()
                    //             _render()
                },
                setCategory: data => {
                    m.property.categories = {...data.categories}
                    m.method.fillCategories();
                },
                fillCategories: () => {
                    const container = _create("div")
                    for (let category in m.property.categories) {
                        const cat = m.property.categories[category]
                        const sel = _create("select")
                        const wrap = _create("div")
                        const label = _create("label")
                        const wrapLabel = _create("div")
                        wrapLabel.classList.add("label")
                        wrapLabel.appendChild(label)
                        label.innerText = _(cat.text)
                        wrap.appendChild(wrapLabel)
                        wrap.appendChild(sel)
                        sel.name = category
                        const opt = _create("option")
                        opt.select = true
                        sel.appendChild(opt)
                        for (let type in cat.values) {
                            const opt = _create("option")
                            opt.value = type
                            opt.innerText = _(cat.values[type])
                            sel.appendChild(opt)
                        }
                        container.appendChild(wrap)
                    }
                    _search("#categories").innerHTML = container.innerHTML;
                },
                addPhoto: (...photos) => {
                    for (let i in photos) {
                        const filepath = `${pathApi}img/` + photos[i];
                        const image = _create("img")
                        if (image.src !== filepath) {
                            image.src = filepath;
                            m.data.photos.push(photos[i])
                            image.hidden = true
                        }
                        image_container.appendChild(image)
                    }
                    const firstImage = image_container.childNodes[0]
                    if (firstImage !== undefined) {
                        firstImage.hidden = false
                    }
                    for (let i = 0; i < m.data.photos.length; i += 1) {
                        switch_image(true);
                    }
                },
                hasPhoto: () => m.data.photos.length > 0,
                switch_form: () => {
                    if (m.property.hidden) {
                        m.method.openDialog()
                    } else {
                        m.method.closeDialog()
                    }
                }
            }
        }
        _add_module(m)
    })()
</script>
<style>
    #upload {
        width: 60%;
        padding: 5px;
    }

    .submit button {
        margin-top: 2em;
    }

    #image_area {
        margin-top: 1em;
        height: 12em;
    }

    #create_form label, .comment {
        position: relative;
        align-items: center;
        width: 100%;
        padding: 5px;
    }

    #categories {
        width: 50%;
        float: left;
    }

    #create_form textarea {
        width: 80%;
        resize: vertical;
    }

    #create_form select {
        width: 50%;
        padding: 2pt;
    }

    .right {
        float: right;
    }

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
