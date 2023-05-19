package pl.patrykdepka.iteventsapp.appuser.controller;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.facade.CurrentUserFacade;
import pl.patrykdepka.iteventsapp.appuser.service.AppUserService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
public class AppUserController {
    private final AppUserService appUserService;
    private final MessageSource messageSource;
    private final CurrentUserFacade currentUserFacade;

    public AppUserController(
            AppUserService appUserService,
            MessageSource messageSource,
            CurrentUserFacade currentUserFacade
    ) {
        this.appUserService = appUserService;
        this.messageSource = messageSource;
        this.currentUserFacade = currentUserFacade;
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

    @GetMapping("/profile")
    public String getUserProfile(Model model) {
        model.addAttribute("userProfile", appUserService.findUserProfile(currentUserFacade.getCurrentUser()));
        return "app-user-profile";
    }

    @GetMapping("/users")
    public String getAllUsers(@RequestParam(name = "page", required = false) Integer pageNumber, Model model) {
        int page = pageNumber != null ? pageNumber : 1;
        if (page > 0) {
            PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.ASC, "lastName"));
            Page<AppUserTableDTO> users = appUserService.findAllUsers(pageRequest);
            if (page <= users.getTotalPages()) {
                model.addAttribute("users", users);
                model.addAttribute("prefixUrl", "/users?");
                return "app-user-table";
            } else {
                return "redirect:/users?page=" + users.getTotalPages();
            }
        } else {
            return "redirect:/users?page=1";
        }
    }

    @GetMapping("/users/{id}")
    public String getUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", appUserService.findUserProfileByUserId(id));
        return "app-user-profile";
    }
}
