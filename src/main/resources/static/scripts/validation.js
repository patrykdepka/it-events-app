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

function validateAdminUserDeleteForm() {
    const adminPasswordValue = document.adminUserDeleteForm.adminPassword.value;
    const isNotAdminPasswordError = checkIfNotEmpty('adminPassword', adminPasswordValue);

    return isNotAdminPasswordError;
}

function validateCreateEventForm() {
    const nameValue = document.createEventForm.name.value;
    const isNotNameError = checkIfNotEmpty('name', nameValue);
    const eventTypeValue = document.createEventForm.eventType.value;
    const isNotEventTypeError = checkIfNotEmpty('eventType', eventTypeValue);
    const dateTimeValue = document.createEventForm.dateTime.value;
    const isNotDateTimeError = checkIfNotEmpty('dateTime', dateTimeValue);
    const languageValue = document.createEventForm.language.value;
    const isNotLanguageError = checkIfNotEmpty('language', languageValue);
    const admissionValue = document.createEventForm.admission.value;
    const isNotAdmissionError = checkIfNotEmpty('admission', admissionValue);
    const cityValue = document.createEventForm.city.value;
    const isNotCityError = checkIfNotEmpty('city', cityValue);
    const locationValue = document.createEventForm.location.value;
    const isNotLocationError = checkIfNotEmpty('location', locationValue);
    const addressValue = document.createEventForm.address.value;
    const isNotAddressError = checkIfNotEmpty('address', addressValue);
    const descriptionValue = document.createEventForm.description.value;
    const isNotDescriptionError = checkIfNotEmpty('description', descriptionValue);

    return isNotNameError && isNotEventTypeError && isNotDateTimeError && isNotLanguageError && isNotAdmissionError &&
        isNotCityError && isNotLocationError && isNotAddressError && isNotDescriptionError;
}

function validateEventEditForm() {
    const nameValue = document.eventEditForm.name.value;
    const isNotNameError = checkIfNotEmpty('name', nameValue);
    const eventTypeValue = document.eventEditForm.eventType.value;
    const isNotEventTypeError = checkIfNotEmpty('eventType', eventTypeValue);
    const dateTimeValue = document.eventEditForm.dateTime.value;
    const isNotDateTimeError = checkIfNotEmpty('dateTime', dateTimeValue);
    const languageValue = document.eventEditForm.language.value;
    const isNotLanguageError = checkIfNotEmpty('language', languageValue);
    const admissionValue = document.eventEditForm.admission.value;
    const isNotAdmissionError = checkIfNotEmpty('admission', admissionValue);
    const cityValue = document.eventEditForm.city.value;
    const isNotCityError = checkIfNotEmpty('city', cityValue);
    const locationValue = document.eventEditForm.location.value;
    const isNotLocationError = checkIfNotEmpty('location', locationValue);
    const addressValue = document.eventEditForm.address.value;
    const isNotAddressError = checkIfNotEmpty('address', addressValue);
    const descriptionValue = document.eventEditForm.description.value;
    const isNotDescriptionError = checkIfNotEmpty('description', descriptionValue);

    return isNotNameError && isNotEventTypeError && isNotDateTimeError && isNotLanguageError && isNotAdmissionError &&
        isNotCityError && isNotLocationError && isNotAddressError && isNotDescriptionError;
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