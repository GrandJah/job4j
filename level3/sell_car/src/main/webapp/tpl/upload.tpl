<template>
    <form id="upload">
        Загрузите фото: <input name="fileUpload" required multiple type="file"/>
        <button onclick="upload()">Загрузить</button>
    </form>
</template>
<script src="js/$.js"></script>
<script>
    function upload() {
        _stub()
        fetch("upload", {
            method: 'post',
            body: new FormData(document.querySelector("#upload"))
        })
    }
</script>
<style>
    table {
        display: block;
    }
</style>
