function checkConfirmPassword() {
    const password = document.querySelector('input[name=password]');
    const confirmation = document.querySelector('input[name=confirm]');

    if (confirmation.value === password.value) {
        confirmation.setCustomValidity('');
    } else {
        confirmation.setCustomValidity(`Passwords don't match!`);
    }
}