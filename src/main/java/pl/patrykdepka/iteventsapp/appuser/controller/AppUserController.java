package pl.patrykdepka.iteventsapp.appuser.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.service.AppUserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class AppUserController {
    private final AppUserService appUserService;
    private final MessageSource messageSource;

    public AppUserController(AppUserService appUserService, MessageSource messageSource) {
        this.appUserService = appUserService;
        this.messageSource = messageSource;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("newUserData", new AppUserRegistrationDTO());
        return "forms/registration-form";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUserData") AppUserRegistrationDTO newUserData,
                           BindingResult bindingResult) {
        if (appUserService.checkIfUserExists(newUserData.getEmail())) {
            bindingResult.addError(new FieldError("newUserData", "email", messageSource.getMessage("form.field.email.error.emailIsInUse.message", null, Locale.getDefault())));
        }
        if (bindingResult.hasErrors()) {
            return "forms/registration-form";
        } else {
            appUserService.createUser(newUserData);
            return "redirect:/confirmation";
        }
    }

    @GetMapping("/confirmation")
    public String registrationConfirmation() {
        return "registration-confirmation";
    }
}
