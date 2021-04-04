function checkPass() {
    const pw = document.getElementById('password').value;
    const conf_pw = document.getElementById('c_password').value;
    document.getElementById('submitButton').disabled =
        pw.empty ||
        pw.length < 6 ||
        pw !== conf_pw;
}
