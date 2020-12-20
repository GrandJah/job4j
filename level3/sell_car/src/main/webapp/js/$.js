const root_path = "tpl"
const ext_tpl = "tpl"

const _debug = (...msg) => msg.forEach(msg => console.log(msg))

const _search = (selector, element) => {
    const el = element !== undefined ? element : document
    return el.querySelector(selector)
}

function _hide(selector) {
    _search(selector).setAttribute('hidden', '')
}

function _visible(selector) {
    _search(selector).removeAttribute('hidden')
}

const _stub = () => {
    if (event !== undefined) {
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
    modules: {},
    renders: [],
    render: () => {
        state_app.renders.forEach(render => render())
    }
}

const _add_module = module => {
    state_app.data[module.id] = module
    module.on = (slot, callback) => {
        if (module.hasOwnProperty("slots") && module.slots[slot] !== undefined) {
            module.callback = {
                ...module.callback,
                [slot]: [
                    ...module.callback[slot],
                    callback
                ]
            }
        } else {
            _debug(`error on slot ${slot} in module ${module.id}`)
        }
    }
    module.emit = (slot, event) => {
        if (!module.hasOwnProperty("callback")) {
            return
        }
        if (module.callback[slot] !== undefined) {
            [...module.callback[slot]].forEach(f => f(event))
        } else {
            _debug(`error emit slot ${slot} in module ${module.id}`)
        }
    }
}

const _find_module = id => state_app.data[id]

const _get_prop = (moduleId,prop) => {
    const module = _find_module(moduleId)
    return module === undefined ? undefined : module.property[prop]
}

const _render = () => state_app.render()

window.onresize = () => _render()

const _loadUrlTpl = (id, selector, callback) => {
    const module = []
    const wrapper = document.createElement("div")
    fetch(`${root_path}/${id}.${ext_tpl}`)
        .then(resp => resp.text())
        .then(html => {
            wrapper.innerHTML = html;
        })
        // .then(() => new Promise((resolve, reject) => setTimeout(resolve, 500)))//todo debug slow net
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
        .then(() => {
            state_app.modules[id] = module
            _render()
            if (callback) {
                callback()
            }
        })
        .catch(error => _debug(error))
}

const _add_render = callBack => {
    state_app.renders.push(callBack)
}

const _cookies = () => {
    return document.cookie.split(";").map(cookieString => {
        const cookieEntry = cookieString.trim().split("=")
        return {[cookieEntry[0]]: cookieEntry[1]}
    }).reduce((arr, cookie) => {
        return {...cookie, ...arr}
    }, {})
}

const _set_cookie = (key, value) => document.cookie = `${key}=${value};path=/;`

const _del_cookie = (key) => {
    document.cookie = name + `${key}=;expires=Thu, 01 Jan 1970 00:00:00 GMT;`;
    document.cookie = name + `${key}=; path=/; expires=Thu, 01 Jan 1970 00:00:01 GMT;`;
}

const main = _create("script")
main.src = "js/main.js"
document.head.appendChild(main);
