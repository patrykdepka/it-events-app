function validateLoginForm() {
    const usernameValue = document.loginForm.username.value;
    const isNotUsernameError = checkIfNotEmpty('username', usernameValue);
    const passwordValue = document.loginForm.password.value;
    const isNotPasswordError = checkIfNotEmpty('password', passwordValue);

    return isNotUsernameError && isNotPasswordError;
}

function validateRegistrationForm() {
    const firstNameValue = document.registrationForm.firstName.value;
    const isNotFirstNameError = checkIfNotEmpty('firstName', firstNameValue);
    const lastNameValue = document.registrationForm.lastName.value;
    const isNotLastNameError = checkIfNotEmpty('lastName', lastNameValue);
    const dateOfBirthValue = document.registrationForm.dateOfBirth.value;
    const isNotDateOfBirthError = checkIfNotEmpty('dateOfBirth', dateOfBirthValue);
    const emailValue = document.registrationForm.email.value;
    const isNotEmailError = checkIfNotEmpty('email', emailValue);
    const passwordValue = document.registrationForm.password.value;
    const isNotPasswordError = checkIfNotEmpty('password', passwordValue);
    const confirmPasswordValue = document.registrationForm.confirmPassword.value;
    const isNotConfirmPasswordError = checkIfNotEmpty('confirmPassword', confirmPasswordValue);

    return isNotFirstNameError && isNotLastNameError && isNotDateOfBirthError && isNotEmailError && isNotPasswordError && isNotConfirmPasswordError;
}

function validateAdminUserProfileEditForm() {
    const firstNameValue = document.adminUserProfileEditForm.firstName.value;
    const isNotFirstNameError = checkIfNotEmpty('firstName', firstNameValue);
    const lastNameValue = document.adminUserProfileEditForm.lastName.value;
    const isNotLastNameError = checkIfNotEmpty('lastName', lastNameValue);
    const dateOfBirthValue = document.adminUserProfileEditForm.dateOfBirth.value;
    const isNotDateOfBirthError = checkIfNotEmpty('dateOfBirth', dateOfBirthValue);

    return isNotFirstNameError && isNotLastNameError && isNotDateOfBirthError;
}

function validateUserPasswordEditForm() {
    const currentPasswordValue = document.userPasswordEditForm.currentPassword.value;
    const isNotCurrentPasswordError = checkIfNotEmpty('currentPassword', currentPasswordValue);
    const newPasswordValue = document.userPasswordEditForm.newPassword.value;
    const isNotNewPasswordError = checkIfNotEmpty('newPassword', newPasswordValue);
    const confirmNewPasswordValue = document.userPasswordEditForm.confirmNewPassword.value;
    const isNotConfirmNewPasswordError = checkIfNotEmpty('confirmNewPassword', confirmNewPasswordValue);

    return isNotCurrentPasswordError && isNotNewPasswordError && isNotConfirmNewPasswordError;
}

function validateAdminUserPasswordEditForm() {
    const adminPasswordValue = document.adminUserPasswordEditForm.adminPassword.value;
    const isNotAdminPasswordError = checkIfNotEmpty('adminPassword', adminPasswordValue);
    const newPasswordValue = document.adminUserPasswordEditForm.newPassword.value;
    const isNotNewPasswordError = checkIfNotEmpty('newPassword', newPasswordValue);
    const confirmNewPasswordValue = document.adminUserPasswordEditForm.confirmNewPassword.value;
    const isNotConfirmNewPasswordError = checkIfNotEmpty('confirmNewPassword', confirmNewPasswordValue);

    return isNotAdminPasswordError && isNotNewPasswordError && isNotConfirmNewPasswordError;
}

function checkIfNotEmpty(fieldName, fieldValue) {
    if (!fieldValue) {
        document.getElementById(`${fieldName}`).className='form-control is-invalid';
        if (document.getElementById(`${fieldName}ErrorMessage`).style.display === 'none') { // check if error message is not displaying
            document.getElementById(`${fieldName}ErrorMessage`).style.display = ''; // display error message
            document.getElementById(`${fieldName}`).value = '';
        }

        return false;
    }

    document.getElementById(`${fieldName}`).className='form-control';
    if (document.getElementById(`${fieldName}ErrorMessage`).style.display === '') {
        document.getElementById(`${fieldName}ErrorMessage`).style.display = 'none';
    }

    return true;
}

function validateSearchForm() {
    const searchQueryValue = document.searchForm.search_query.value;
    if (!searchQueryValue) {
        return false;
    }

    for (let i = 0; i < searchQueryValue.length; i++) {
        if (searchQueryValue[i] !== ' ') {
            return true;
        }
    }

    return false;
}