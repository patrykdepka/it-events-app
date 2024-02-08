package pl.patrykdepka.iteventsapp.appuser;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.iteventsapp.appuser.domain.AppUserService;
import pl.patrykdepka.iteventsapp.appuser.domain.CurrentUserFacade;
import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserPasswordEditDTO;
import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserProfileEditDTO;
import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserRegistrationDTO;
import pl.patrykdepka.iteventsapp.appuser.domain.dto.AppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.domain.exception.IncorrectCurrentPasswordException;

import javax.validation.Valid;
import java.util.Locale;

@Controller
class AppUserController {
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
    String showRegistrationForm(Model model) {
        model.addAttribute("newUserData", new AppUserRegistrationDTO(null, null, null, null, null, null));
        return "registration-form";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("newUserData") AppUserRegistrationDTO newUserData, BindingResult bindingResult) {
        if (appUserService.checkIfUserExists(newUserData.getEmail())) {
            bindingResult.addError(new FieldError("newUserData", "email", messageSource.getMessage("form.field.email.error.emailIsInUse.message", null, Locale.getDefault())));
        }
        if (bindingResult.hasErrors()) {
            return "registration-form";
        } else {
            appUserService.createUser(newUserData);
            return "redirect:/confirmation";
        }
    }

    @GetMapping("/confirmation")
    String registrationConfirmation() {
        return "registration-confirmation";
    }

    @GetMapping("/profile")
    String getUserProfile(Model model) {
        model.addAttribute("userProfile", appUserService.findUserProfile(currentUserFacade.getCurrentUser()));
        return "user/app-user-profile";
    }

    @GetMapping("/users")
    String getAllUsers(
            @RequestParam(name = "page", required = false) Integer pageNumber,
            @RequestParam(name = "sort_by", required = false) String sortProperty,
            @RequestParam(name = "order_by", required = false) String sortDirection,
            Model model
    ) {
        int page = pageNumber != null ? pageNumber : 1;
        String property = sortProperty != null && !sortProperty.isEmpty() ? sortProperty : "lastName";
        String direction = sortDirection != null && !sortDirection.isEmpty() ? sortDirection : "ASC";
        if (page > 0) {
            PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
            Page<AppUserTableDTO> users = appUserService.findAllUsers(pageRequest);
            if (page <= users.getTotalPages()) {
                model.addAttribute("users", users);
                model.addAttribute("prefixUrl", "/users?");
                if (sortProperty != null) {
                    String sortParams = "sort_by=" + sortProperty + "&order_by=" + sortDirection;
                    model.addAttribute("sortParams", sortParams);
                }
                return "user/app-user-table";
            } else {
                if ((sortProperty != null && !sortProperty.isEmpty()) && (sortDirection != null && !sortDirection.isEmpty())) {
                    return "redirect:/users?page=" + users.getTotalPages() + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
                }
                return "redirect:/users?page=" + users.getTotalPages();
            }
        } else {
            if ((sortProperty != null && !sortProperty.isEmpty()) && (sortDirection != null && !sortDirection.isEmpty())) {
                return "redirect:/users?page=1" + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
            }
            return "redirect:/users?page=1";
        }
    }

    @GetMapping("/users/results")
    String getUsersBySearch(
            @RequestParam(name = "search_query", required = false) String searchQuery,
            @RequestParam(name = "page", required = false) Integer pageNumber,
            @RequestParam(name = "sort_by", required = false) String sortProperty,
            @RequestParam(name = "order_by", required = false) String sortDirection,
            Model model
    ) {
        if (searchQuery != null) {
            int page = pageNumber != null ? pageNumber : 1;
            String property = sortProperty != null && !sortProperty.isEmpty() ? sortProperty : "lastName";
            String direction = sortDirection != null && !sortDirection.isEmpty() ? sortDirection : "ASC";
            if (page > 0) {
                PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
                Page<AppUserTableDTO> users = appUserService.findUsersBySearch(searchQuery, pageRequest);
                if (users.getNumberOfElements() == 0) {
                    model.addAttribute("users", users);
                    if (page > 1) {
                        if (sortProperty != null) {
                            return "redirect:/users/results?search_query=" + searchQuery + "&sort_by=" + sortProperty;
                        }
                        return "redirect:/users/results?search_query=" + searchQuery;
                    } else {
                        model.addAttribute("prefixUrl", "/users/results?search_query=" + searchQuery + "&");
                        return "user/app-user-table";
                    }
                } else if (page <= users.getTotalPages()) {
                    model.addAttribute("users", users);
                    searchQuery = searchQuery.replace(" ", "+");
                    model.addAttribute("searchQuery", searchQuery);
                    model.addAttribute("prefixUrl", "/users/results?search_query=" + searchQuery + "&");
                    return "user/app-user-table";
                } else {
                    searchQuery = searchQuery.replace(" ", "+");
                    if (sortProperty != null) {
                        return "redirect:/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages() + "&sort_by=" + sortProperty;
                    }
                    return "redirect:/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages();
                }
            } else {
                searchQuery = searchQuery.replace(" ", "+");
                if (sortProperty != null) {
                    return "redirect:/users/results?search_query=" + searchQuery + "&page=1" + "&sort_by=" + sortProperty;
                }
                return "redirect:/users/results?search_query=" + searchQuery + "&page=1";
            }
        } else {
            return "redirect:/users/results?search_query=";
        }
    }

    @GetMapping("/users/{id}")
    String getUserProfile(@PathVariable Long id, Model model) {
        model.addAttribute("userProfile", appUserService.findUserProfileByUserId(id));
        return "user/app-user-profile";
    }

    @GetMapping("/settings/profile")
    String showUserProfileEditForm(Model model) {
        model.addAttribute("profileUpdated", false);
        model.addAttribute("userProfile", appUserService.findUserProfileToEdit(currentUserFacade.getCurrentUser()));
        return "user/forms/app-user-profile-edit-form";
    }

    @PatchMapping("/settings/profile")
    String updateUserProfile(
            @Valid @ModelAttribute(name = "userProfile") AppUserProfileEditDTO userProfile,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profileUpdated", false);
        } else {
            model.addAttribute("userProfile", appUserService.updateUserProfile(currentUserFacade.getCurrentUser(), userProfile));
            model.addAttribute("profileUpdated", true);
        }
        return "user/forms/app-user-profile-edit-form";
    }

    @GetMapping("/settings/password")
    String showUserPasswordEditForm(Model model) {
        model.addAttribute("newUserPassword", null);
        return "user/forms/app-user-password-edit-form";
    }

    @PatchMapping("/settings/password")
    String updateUserPassword(
            @Valid @ModelAttribute(name = "newUserPassword") AppUserPasswordEditDTO newUserPassword,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordUpdated", false);
            return "user/forms/app-user-password-edit-form";
        } else {
            try {
                model.addAttribute("newUserPassword", appUserService.updateUserPassword(currentUserFacade.getCurrentUser(), newUserPassword));
                model.addAttribute("passwordUpdated", true);
                return "user/forms/app-user-password-edit-form";
            } catch (IncorrectCurrentPasswordException e) {
                bindingResult.addError(new FieldError("newUserPassword", "currentPassword", messageSource.getMessage("form.field.currentPassword.error.invalidCurrentPassword.message", null, Locale.getDefault())));
                model.addAttribute("passwordUpdated", false);
                return "user/forms/app-user-password-edit-form";
            }
        }
    }
}
