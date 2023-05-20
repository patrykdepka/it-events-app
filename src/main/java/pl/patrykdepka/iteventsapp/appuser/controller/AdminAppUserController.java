package pl.patrykdepka.iteventsapp.appuser.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserProfileEditDTO;
import pl.patrykdepka.iteventsapp.appuser.dto.AdminAppUserTableDTO;
import pl.patrykdepka.iteventsapp.appuser.service.AdminAppUserService;

import javax.validation.Valid;

@Controller
public class AdminAppUserController {
    private final AdminAppUserService adminAppUserService;

    public AdminAppUserController(AdminAppUserService adminAppUserService) {
        this.adminAppUserService = adminAppUserService;
    }

    @GetMapping("/admin/users")
    public String getAllUsers(@RequestParam(name = "page", required = false) Integer pageNumber,
                              @RequestParam(name = "sort_by", required = false) String sortProperty,
                              @RequestParam(name = "order_by", required = false) String sortDirection,
                              Model model) {
        int page = pageNumber != null ? pageNumber : 1;
        String property = sortProperty != null && !"".equals(sortProperty) ? sortProperty : "lastName";
        String direction = sortDirection != null && !"".equals(sortDirection) ? sortDirection : "ASC";
        if (page > 0) {
            PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
            Page<AdminAppUserTableDTO> users = adminAppUserService.findAllUsers(pageRequest);
            if (page <= users.getTotalPages()) {
                model.addAttribute("users", users);
                model.addAttribute("prefixUrl", "/admin/users?");
                if (sortProperty != null) {
                    String sortParams = "sort_by=" + sortProperty + "&order_by=" + sortDirection;
                    model.addAttribute("sortParams", sortParams);
                }
                return "admin/app-user-table";
            } else {
                if ((sortProperty != null && !"".equals(sortProperty)) && (sortDirection != null && !"".equals(sortDirection))) {
                    return "redirect:/admin/users?page=" + users.getTotalPages() + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
                }
                return "redirect:/admin/users?page=" + users.getTotalPages();
            }
        } else {
            if ((sortProperty != null && !"".equals(sortProperty)) && (sortDirection != null && !"".equals(sortDirection))) {
                return "redirect:/admin/users?page=1" + "&sort_by=" + sortProperty + "&order_by=" + sortDirection;
            }
            return "redirect:/admin/users?page=1";
        }
    }

    @GetMapping("/admin/users/results")
    public String getUsersBySearch(@RequestParam(name = "search_query", required = false) String searchQuery,
                                   @RequestParam(name = "page", required = false) Integer pageNumber,
                                   @RequestParam(name = "sort_by", required = false) String sortProperty,
                                   @RequestParam(name = "order_by", required = false) String sortDirection,
                                   Model model) {
        if (searchQuery != null) {
            int page = pageNumber != null ? pageNumber : 1;
            String property = sortProperty != null && !"".equals(sortProperty) ? sortProperty : "lastName";
            String direction = sortDirection != null && !"".equals(sortDirection) ? sortDirection : "ASC";
            if (page > 0) {
                PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by(Sort.Direction.fromString(direction), property));
                Page<AdminAppUserTableDTO> users = adminAppUserService.findUsersBySearch(searchQuery, pageRequest);
                if (users.getNumberOfElements() == 0) {
                    model.addAttribute("users", users);
                    if (page > 1) {
                        if (sortProperty != null) {
                            return "redirect:/admin/users/results?search_query=" + searchQuery + "&sort_by=" + sortProperty;
                        }
                        return "redirect:/admin/users/results?search_query=" + searchQuery;
                    } else {
                        model.addAttribute("prefixUrl", "/admin/users/results?search_query=" + searchQuery + "&");
                        return "admin/app-user-table";
                    }
                } else if (page <= users.getTotalPages()) {
                    model.addAttribute("users", users);
                    searchQuery = searchQuery.replace(" ", "+");
                    model.addAttribute("searchQuery", searchQuery);
                    model.addAttribute("prefixUrl", "/admin/users/results?search_query=" + searchQuery + "&");
                    return "admin/app-user-table";
                } else {
                    searchQuery = searchQuery.replace(" ", "+");
                    if (sortProperty != null) {
                        return "redirect:/admin/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages() + "&sort_by=" + sortProperty;
                    }
                    return "redirect:/admin/users/results?search_query=" + searchQuery + "&page=" + users.getTotalPages();
                }
            } else {
                searchQuery = searchQuery.replace(" ", "+");
                if (sortProperty != null) {
                    return "redirect:/admin/users/results?search_query=" + searchQuery + "&page=1" + "&sort_by=" + sortProperty;
                }
                return "redirect:/admin/users/results?search_query=" + searchQuery + "&page=1";
            }
        } else {
            return "redirect:/admin/users/results?search_query=";
        }
    }

    @GetMapping("/admin/users/{id}/settings/profile")
    public String showUserProfileEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("profileUpdated", false);
        model.addAttribute("userProfile", adminAppUserService.findUserProfileToEdit(id));
        return "admin/forms/app-user-profile-edit-form";
    }

    @PatchMapping("/admin/users/{id}/settings/profile")
    public String updateUserProfile(@PathVariable Long id,
                                    @Valid @ModelAttribute(name = "userProfile") AdminAppUserProfileEditDTO userProfile,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("profileUpdated", false);
        } else {
            model.addAttribute("userProfile", adminAppUserService.updateUserProfile(id, userProfile));
            model.addAttribute("profileUpdated", true);
        }
        return "admin/forms/app-user-profile-edit-form";
    }
}
