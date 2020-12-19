_app("Продажа машин")
const m_upload = _loadUrlTpl("tpl/top_bar.tpl", "body")

let token = undefined;

const _ajax_fetch = async data =>
    fetch("ajax", {
        method: 'post',
        headers: {
            "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(data => data.json())

const _ajax = (data, func) => {
    _ajax_fetch({...data, token})
        .then(func)
        .catch(err => console.log("ajax - error:" + err))
}


const m_advert = (id, description, price, user, car, status, created) => {
    return {id, description, price, user, car, status, created}
}

const m_car = (id, images) => {
    return {id, images: [...images]}
}


const m_img = (id, filepath, type, created, size) => {
    return {id, filepath, type, created, size}
}

const m_user = (id, name, email, phone, registration) => {
    return {id, name, email, phone, registration}
}

const _ajax_stub = async data => {

}

const _rnd = (min, max) => {
    return ~~(Math.random() * 1000000000) % (max - min) + min
}

const img_generator = (id_prefix, n) => {
    const images = []
    for (let i = 0; i < n; i += 1) {
        images.push(
            m_img("I" + id_prefix + i,
                `image/NoPhoto${i + 1}.png`,
                "image/unknown",
                _rnd(100000000, 1000000000),
                _rnd(100000, 10000000)
            ))
    }
    return images
}

const bredogenerator = (n) => {
    const words = ["машина", "продажа", "ходовка", "не едет", "сальники", "двигатель", "немного",
        "три раза", "пробег", "в отличном состоянии", "не бит не крашен", "после аварии",
        "только колеса подкачать", "сел да поехал", "рыжики по кузову", "лобовое новое"]
    let phrase = ""
    do {
        phrase += words[_rnd(0, words.length)] + " "
    } while (phrase.length < n)
    return phrase
}

const adv_generator = (n, f) => {
    const adverts = []
    let id = 1
    for (let i = 0; i < n; i += 1) {
        id += _rnd(1, f)
        adverts.push(m_advert("A" + id,
            bredogenerator(_rnd(300, 1500)),
            _rnd(100000, 10000000),
            m_user("U" + id, "nameuser" + id, "email-" + id, "phone-" + id, "cdscasc"),
            m_car("C" + id, img_generator(id, _rnd(1, 9))),
            _rnd(0, 100) > 50,
            _rnd(100000000, 1000000000)
            )
        )
    }
    console.log(adverts)
    return adverts
}


state_app.data.items = [...adv_generator(25, 10)]//todo заглушка для данных
