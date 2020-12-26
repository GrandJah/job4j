<template>
    <div hidden id="create_form">
        <div class="space"></div>
        <form id="create">
            <input type="text" name="model" value="Модель" title="model">
            <input type="text" name="price" value="Стоимость" title="price">
            <div id="categories"></div>
            <label>
                Комментарий продавца:
                <textarea rows="5" wrap="soft" name="description"></textarea>
            </label>
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
            const form = _search("#create");
            const model = form.elements.model.value
            const description = form.elements.description.value;
            const submit = {model, description}
            console.log(JSON.stringify(submit))
        }

        const m = {
            id: "create_form",
            //     data: {},
            property: {
                hidden: true,
                categories: [],
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
                    //             _render()
                },
                setCategory: data => {
                    m.property.categories = {...data.categories}
                    m.method.fillCategories();
                },
                fillCategories: () => {
                    const container = _create("div")
                    for (let category in m.property.categories) {
                        const sel = _create("select")
                        const wrap = _create("div")
                        const label = _create("label")
                        const wrapLabel = _create("div")
                        wrapLabel.classList.add("label")
                        wrapLabel.appendChild(label)
                        label.innerHTML = category
                        wrap.appendChild(wrapLabel)
                        wrap.appendChild(sel)
                        sel.name = category
                        const opt = _create("option")
                        opt.select = true
                        sel.appendChild(opt)
                        for (let type of m.property.categories[category]) {
                            const opt = _create("option")
                            opt.value = type
                            opt.innerText = type
                            sel.appendChild(opt)
                        }
                        container.appendChild(wrap)
                    }
                    _search("#categories").innerHTML = container.innerHTML;
                },
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
    #create_form textarea {
        width: 60%;
        resize: vertical;
    }

    #create_form select {
        width: 20%;
        padding: 2pt;
    }

    #create_form .label {
        width: 20%;
        padding: 2pt;
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
