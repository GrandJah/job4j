function login_action() {
    var form_login = document.getElementById("form_login");
    var login = {};
    login.login = form_login.elements.login.value;
    login.password = form_login.elements.password.value;
    ajax("login", loadPage, login);
}