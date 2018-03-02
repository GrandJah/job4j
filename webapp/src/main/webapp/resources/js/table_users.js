function fill_table(){
    ajax("{\"action\":\"table\"}",function(json) {
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
            el.children[4].innerHTML = row.role !== undefined ? row.role : 'user';
            el.children[5].innerHTML = row.location !== undefined ? row.location : "---";
            body.appendChild(el);
        }
        table.style.display = 'table';
    })}