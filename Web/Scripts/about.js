function Submit() {
    let end = document.getElementById('end');

    let name = document.getElementById('name');
    let nameError = document.getElementById('nameError');

    let email = document.getElementById('email');
    let emailError = document.getElementById('emailError');

    let message = document.getElementById('message');
    let messageError = document.getElementById('messageError');

    let valid = true;

    if (!name || name.value === "") {
        nameError.textContent = 'Please enter your name!';
        valid = false;
    } else {
        nameError.style.display = 'none';
    }

    if (!email || !email.value || !email.value.includes('@') || !email.value.includes('.')) {
        emailError.textContent = 'Please enter a valid email address!';
        valid = false;
    } else {
        emailError.style.display = 'none';
    }

    if (!message || message.value === "") {
        messageError.textContent = 'Please enter a message!';
        valid = false;
    } else {
        messageError.style.display = 'none';
    }

    if (valid) {
        end.style.display = 'block';
    }
}