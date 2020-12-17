<template>
    <div id="listing-card">
        <h1>Тут заголовок листинга</h1>
        <div>
            <div hidden id="card-item">
                //todo сделать шаблон для карточки объявления
            </div>
        </div>
    </div>
</template>
<script src="js/$.js"></script>
<script>
    const card = _search("#card-item")
    const list = card.parentElement
    list.removeChild(card)
    _add_render(() => {
        list.innerHTML = ""
        state_app.data.items.forEach((item, i) => {
            let el = _create("div", i)
            el.innerHTML = card.innerHTML //todo настройка шаблона для объявления
            list.appendChild(el)
        })
    })
    _render()
</script>
<style>

</style>
