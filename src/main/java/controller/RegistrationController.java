package controller;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.logging.Log;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import aspect.ControllerLogs;
import dao.abstracts.AccountDao;
import enums.RegisterResult;
import jdo.RegistrationForm;

import javax.validation.Valid;

/**
 * Controller for registration page
 * */
@Slf4j
@Controller
@ControllerLogs
@RequestMapping("/register")
public class RegistrationController {
    private static final String DAO_RESULT = "daoResult";

    private final AccountDao accountDao;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(
            AccountDao accountDao, PasswordEncoder passwordEncoder) {
        this.accountDao = accountDao;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("registrationForm")
    public RegistrationForm registrationForm(){
        return new RegistrationForm();
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(@Valid RegistrationForm form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            log.warn("Registration errors: {}", errors.getAllErrors());
            return registerForm();
        }
        RegisterResult registerResult = accountDao.register(form.toAccount(passwordEncoder));
        switch (registerResult){
            case OK:
                return "redirect:/login";
            case USERNAME_EXISTS:
                model.addAttribute(DAO_RESULT, "User with username " + form.getUsername() +
                        " is already registered. Choose a different username.");
                return registerForm();
            default:
                model.addAttribute(DAO_RESULT, "Unknown error, contact the developer");
                return registerForm();
        }

    }
}