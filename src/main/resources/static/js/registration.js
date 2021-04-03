function check_pass() {
    document.getElementById('submitButton').disabled =
        document.getElementById('password').value !== document.getElementById('c_password').value;

}
