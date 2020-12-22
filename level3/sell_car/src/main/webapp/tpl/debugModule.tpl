<script src="js/$.js"></script>
<script>
    const m_advert = (id, description, price, user, car, status, created) => {
        return {id, description, price, user, car, status, created}
    }

    const m_car = (id, images) => {
        return {id, images: [...images]}
    }

    const images = []

    const m_img = (id, filepath, type, created, size) => {
        const m = {id, filepath, type, created, size}
        images.push(m)
        return m
    }

    const m_user = (id, name, email, phone, registration) => {
        return {id, name, email, phone, registration}
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
                m_user("U" + id, _rnd(1, 500) > 100 ? "nameuser" + id : "q", "email-" + id, "phone-" + id, "cdscasc"),
                m_car("C" + id, img_generator(id, _rnd(1, 9))),
                _rnd(0, 100) > 50,
                _rnd(100000000, 1000000000)
                )
            )
        }
        ff()
        return adverts
    }

    $.rundomImage = false

    const ff = () => { //todo load random images
        if (!$.rundomImage) return
        const per = 50;
        let i = 0;
        let page = 1;
        do {
            const h = i
            fetch(`https://api.pexels.com/v1/search?query="cars"&page=${page}&per_page=80`, {
                method: 'get',
                headers: {
                    "Authorization": "563492ad6f91700001000001ffddfc9a0636454a88e766c2d10cf7ef",
                    "Content-type": "application/x-www-form-urlencoded; charset=UTF-8"
                },
            }).then(data => data.json())
                .then(data => {
                    const photo = data.photos
                    for (let j = 0; j < Math.min(images.length - h, per); j += 1) {
                        images[j + h].filepath = photo[j].src.medium
                    }
                    state_app.data.itemsChange = true
                    _render('updateImg')
                })
            i += per
            page += 1
        } while (i < images.length)
        __debug(true, "img", images.length)
    }

    function retError(error) {
        return {success: false, error}
    }

    function retOk(data) {
        return {success: true, ...data}
    }


    const _ajax_stub = ajax => {
        const _ajax = ajax
        const _stub = async data => {
            // await new Promise((resolve, reject) => setTimeout(resolve, 3000));//todo
            if (data.action === "register") {
                if (users[data.username] !== undefined) {
                    return retError("errorName")
                } else {
                    users[data.username] = data.pass;
                    return retOk({token: Math.random()})
                }
            }
            if (data.action === "login") {
                if (users[data.username] === undefined || users[data.username] !== data.pass) {
                    return retError("errorUser")
                } else {
                    return retOk({token: Math.random()})
                }
            }
            if (data.action === "changeStatus") {
                const item = state_app.data.items.filter(item => item.id === data.id).shift()
                item.status = !item.status
                return retOk({status: item.status})
            }
            if (data.action === "list_ad") {
                return retOk({data: [...adv_generator(25, 10)]})
            }
            throw `no stub for action : ${data.action}`
        }

        return data => {
            if ($.local) {
                return _stub(data)
            } else _ajax(data)
        }
    }

    const _updItem = () => {
        state_app.data.itemsChange = false
        _render("updItem")
    }

    $.info = 1
    $.error = 1
    $.local = true

    const users = {q: "", "": ""}
</script>
