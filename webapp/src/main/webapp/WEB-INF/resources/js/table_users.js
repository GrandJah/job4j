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
        insertRoles();
        insertCountries();
        insertCities();
    }
}

function edit_row() {
    var edit_form = document.getElementById("update_form");
    var update = {};
    update.login = edit_form.elements.login.value;
    update.name = edit_form.elements.name.value;
    update.email = edit_form.elements.email.value;
    update.role = edit_form.elements.role.value;
    update.country = edit_form.elements.country.value;
    update.city = edit_form.elements.city.value;
    ajax("update", function(msg) {inject = undefined; console.log(msg); loadPage({useUser : use_user})}, update);
}

function insertRoles(selected){
    ajax('roles', function (data) {
        insertOption("edit_role", "role", data.roles, selected)
    },{})
}

function insertCities(selected, country){
    ajax('location', function (data) {
        insertCountries(data.country);
        insertOption("edit_city", "city", data.cities, selected)
    },{type:'city',country: country})
}

function insertCountries(selected){
    ajax('location', function (data) {
        insertOption("edit_country", "country", data.countries, selected);
        document.getElementById("edit_country").addEventListener("change", function (ev) {
            console.log(ev,ev.path[0].value); insertCities(undefined, ev.path[0].value)})
    },{type:'country'})
}

function insertOption(selector, name, data, select) {
    var element = $('<select/>', {id:selector, name:name});
    $.each(data,function() {
        $('<option/>', {
            val:  this,
            text: this
        }).appendTo(element);
    });
    if (select !== undefined && select !== '')
        $('option[value=' + select + ']', element).attr('selected', 'selected');

    $(('#' + selector)).replaceWith(element);
}


