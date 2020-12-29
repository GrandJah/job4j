_app("Продажа машин")
_loadUrlTpl("top_bar", "body")
let pathApi = ''//todo const stub for debugModule
let _ajax = async data =>  //todo const stub for debugModule
    fetch(`${pathApi}ajax`, {
        method: 'post',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(data => data.json())

const upload = (formData, callback) => {
    _stub()
    fetch(`${pathApi}upload`, {
        method: 'post',
        body: formData
    }).then(data => data.json())
        .then(data => callback(data))
}

const _ajax_action = async (data, action) => {
    let answer = {}
    try {
        answer = await _ajax({action, ...data, token: _cookies().token})
    } catch (err) {
        throw `ajax - error: <|${err}|>`
    }
    if (answer.success === true) {
        delete answer.success
        return {...answer}
    }
    throw answer.error !== undefined ? answer.error : "Unknown error"
}

_add_module({
    id: "action",
    login: data => _ajax_action(data, "login"),
    register: data => _ajax_action(data, "register"),
    getCategories: callback => _ajax_action({}, "getCategories").then(callback),
    change_status: id_adv => _ajax_action({id: id_adv}, "changeStatus")
        .then(data => data.status),
    checkUser: name => _get_prop("login", "username") === name,
    getAdverts: (data, callback) => _ajax_action(data, "list_ad").then(data => callback(data.data)),
    create_advert: (data, callback) => _ajax_action(data, "create").then(data => callback(data)),
    upload
})

_loadUrlTpl("debugModule", undefined, () => {
    _ajax = _ajax_stub(_ajax)
})
