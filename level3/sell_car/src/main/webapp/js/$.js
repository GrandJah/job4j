const _debug = msg => console.log(msg)

const _search = (selector, element) => {
    const el = element !== undefined ? element : document
    return el.querySelector(selector)
}

const _stub = () => {
    if (event !== null) {
        event.preventDefault()
    }
}

const _create = (tag, id) => {
    const el = document.createElement(tag)
    if (id !== undefined) {
        el.id = id;
    }
    return el
}

const _app = title => {
    const el = _create("title")
    el.innerText = title
    document.head.appendChild(el);
}

const state_app = {
    data: {},
    modules: [],
    renders: [],
    render: () => {
        state_app.renders.forEach(render => render())
    }
}

const _render = () => state_app.render()

const _loadUrlTpl = (url, selector) => {
    const module = []
    const wrapper = document.createElement("div")
    fetch(url)
        .then(resp => resp.text())
        .then(html => {
            wrapper.innerHTML = html;
        })
        .then(() => wrapper.childNodes.forEach(
            node => {
                const tag = node.nodeName
                if (tag === "SCRIPT" || tag === "STYLE") {
                    if (node.src !== undefined && node.src.endsWith("/$.js")) {
                        return
                    }
                    const el = _create(tag)
                    if (node.src !== "") {
                        el.src = node.src
                    }
                    el.innerHTML = node.innerHTML
                    module.push(el)
                    document.head.appendChild(el)
                } else if (tag === "TEMPLATE") {
                    const el = _search(selector)
                    el.innerHTML = node.innerHTML
                    module.push(...el.childNodes)
                }
            }
        ))
        .then(() => _render())
        .catch(error => _debug(error))
    state_app.modules.push(module)
    return module
}

const _add_render = callBack => {
    state_app.renders.push(callBack)
}

const main = _create("script")
main.src = "js/main.js"
document.head.appendChild(main);
