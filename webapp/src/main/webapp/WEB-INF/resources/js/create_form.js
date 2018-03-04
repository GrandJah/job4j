var button_toggle = document.getElementById("createFormButton");
var form = document.getElementById("createForm");
var flag_toggle = false;

function openCreateForm() {
    button_toggle.children[0].style.display = flag_toggle ? 'block' : 'none';
    button_toggle.children[1].style.display = flag_toggle ? 'none' : 'block';
    form.style.display = flag_toggle ? 'none' : 'block' ;
    flag_toggle = !flag_toggle;
}

function createUser(){
    event.preventDefault();
    var newUser = {};
    var valid = true;
    for(var i = 0; i < 3; i++) {
        var el = form.children[i].children[1];
        if (el.validity.valid) {
            el.classList.remove('is-invalid');
            el.classList.add('is-valid');
            switch (el.name) {
                case 'login' : newUser.login = el.value; break;
                case 'email' : newUser.email = el.value; break;
                case 'name' : newUser.name = el.value; break;
            }
        } else {
            el.classList.remove('is-valid');
            el.classList.add('is-invalid');
            valid = false;
        }
    }
    if (valid) {
        newUser.action = "createUser";
        ajax(JSON.stringify(newUser), update)
    }
}