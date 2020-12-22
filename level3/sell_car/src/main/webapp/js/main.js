_app("Продажа машин")
_loadUrlTpl("top_bar", "body")

let _ajax = async data => //todo const stub for debugModule
    fetch("ajax", {
        method: 'post',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(data => data.json())



const _ajax_action = async (data, action) => {
    let answer = {}
    try {
        answer = await _ajax({action, ...data, token: _cookies().token})
    } catch (err) {
        throw `ajax - error: <|${err}|>`
    }
    if (answer.success === true) {
        return {...answer, success: undefined}
    }
    throw answer.error !== undefined ? answer.error : "Unknown error"
}

_add_module({
    id: "action",
    login: data => _ajax_action(data, "login"),
    register: data => _ajax_action(data, "register"),
    change_status: id_adv => _ajax_action({id: id_adv}, "changeStatus")
        .then(data => data.status),
    checkUser: name => _get_prop("login", "username") === name,
    getAdverts: (data,callback) => _ajax_action(data,"list_ad").then(data => callback(data.data))
})

_loadUrlTpl("debugModule", undefined, () => {
    _ajax = _ajax_stub(_ajax)
})
