function fill_table(){
    ajax("table",function(json) {
        var rowTemplate;
        var table = document.getElementById("userTable");
        table.children[0].innerHTML = json.table;
        var body = table.children[2];
        rowTemplate = body.children[0].cloneNode(true);
        rowTemplate.id =  json.table_id + "-" ;
        body.removeChild(body.children[0]);
        for(var i = 0; i < json.rows.length;) {
            var row = json.rows[i++];
            var el = rowTemplate.cloneNode(true);
            el.id += i;
            el.children[0].innerHTML = i.toString();
            el.children[1].innerHTML = row.login;
            el.children[2].innerHTML = row.name;
            el.children[3].innerHTML = row.email;
            el.children[4].innerHTML = row.role;
            var l = row.location;
            el.children[5].innerHTML = l.city + "/" + l.country;
            if (!(use_user.edit === "all" || (use_user.edit === "user" && use_user.login === row.login))) {
                el.children[6].innerHTML = "";
            }
            body.appendChild(el);
        }
        table.style.display = 'table';
    },{})}

var inject;

function toggle_edit(el) {
    var parent_id = el.parentElement.parentElement.id;
    if (inject !== undefined) {
        var it_is = inject.toggle_id === parent_id;
        inject.parentNode.removeChild(inject);
        inject = undefined;
        if (!it_is) toggle_edit(el);
    } else {
        var edit = document.getElementById("edit_form");
        inject = edit.cloneNode(true);
        inject.style = undefined;
        inject.toggle_id = parent_id;
        var sister = document.getElementById(parent_id);
        var parent = sister.parentNode;
        var index = $(parent).children().index(sister);
        parent.insertBefore(inject, parent.children[index + 1]);

        var form = document.getElementById("update_form");
        insertLabel("edit_login", "login", sister.children[1].innerHTML);
        form.elements.name.value = sister.children[2].innerHTML;
        form.elements.email.value = sister.children[3].innerHTML;
        insertRoles(sister.children[4].innerHTML);
        var location = sister.children[5].innerHTML.split('/');
        insertCountries(location[1]);
        insertCities(location[0],location[1]);
    }
}

function edit_row() {
    var edit_form = document.getElementById("update_form");
    var update = {};
    var valid = true;
    for(var i = 0; i < 3; i++) {
        var el = edit_form.children[i].children[0];
        var validate = el.validity;
        if (validate !== undefined) {
            if (validate.valid) {
                el.classList.remove('is-invalid');
                el.classList.add('is-valid');
                switch (el.name) {
                    case 'email' : update.email = el.value; break;
                    case 'name' : update.name = el.value; break;
                }
            } else {
                el.classList.remove('is-valid');
                el.classList.add('is-invalid');
                valid = false;
            }
        }
    }
    if (valid) {
        update.login = edit_form.elements.login.value;
        update.role = edit_form.elements.role.value;
        update.country = edit_form.elements.country.value;
        update.city = edit_form.elements.city.value;
        ajax("update", function(msg) {inject = undefined; console.log(msg); loadPage({useUser : use_user})}, update);
    }
}

function insertRoles(selected){
    if(use_user.edit === "all")
        ajax('roles', function (data) {
            insertOption("edit_role", "role", data.roles, selected)
        },{});
    else insertLabel("edit_role", "role", selected)
}

function insertCities(selected, country){
    ajax('location', function (data) {
        insertCountries(data.country);
        insertOption("edit_city", "city", data.cities, selected)
    },{type:'city',country: country})
}

function insertCountries(selected){
    ajax('location', function (data) {
        console.log(data,selected);
        insertOption("edit_country", "country", data.countries, selected);
        document.getElementById("edit_country").addEventListener("change", function (ev) {
            console.log(ev,ev.path[0].value); insertCities(undefined, ev.path[0].value)})
    },{type:'country'})
}

function insertOption(selector, name, data, select) {
    var element = $('<select/>', {id: selector, name: name});
    $.each(data, function () {
        $('<option/>', {
            val: this,
            text: this
        }).appendTo(element);
    });
    if (select !== undefined && select !== '') {
        console.log(select, "selected");
        $('option[value=' + select + ']', element).attr('selected', 'selected');
    } else {
        console.log(select, "no selected");
    }
    $(('#' + selector)).replaceWith(element);
}

function insertLabel(selector, name, value) {
    var div = $('<div/>', {id:selector});
    $('<label/>',{class:"label", text:value}).appendTo(div);
    $('<input/>',{type:"hidden", name:name, value:value}).appendTo(div);
    $(('#' + selector)).replaceWith(div);
}




