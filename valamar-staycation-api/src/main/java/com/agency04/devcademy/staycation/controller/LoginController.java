package com.agency04.devcademy.staycation.controller;

import com.agency04.devcademy.staycation.dto.LoginDto;
import com.agency04.devcademy.staycation.exception.IdAlreadyExists;
import com.agency04.devcademy.staycation.form.LoginForm;
import com.agency04.devcademy.staycation.form.RegistrationForm;
import com.agency04.devcademy.staycation.jwt.JwtUtils;
import com.agency04.devcademy.staycation.model.Location;
import com.agency04.devcademy.staycation.model.User;
import com.agency04.devcademy.staycation.service.LocationService;
import com.agency04.devcademy.staycation.service.MyUserDetailsService;
import com.agency04.devcademy.staycation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final MyUserDetailsService userDetailsService;
    private final LocationService locationService;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public LoginController(AuthenticationManager authenticationManager, UserService userService, MyUserDetailsService userDetailsService, LocationService locationService, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.locationService = locationService;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new LoginDto(jwt));
    }

    @PostMapping("/user/register")
    public ResponseEntity<LoginDto> login(@Valid @RequestBody RegistrationForm form) {
        if (userService.findByUsername(form.getEmail()).isPresent())
            throw new IdAlreadyExists("Username already exists");

        Location location = new Location(form.getLocation().getTitle(), form.getLocation().getPostalCode(), form.getLocation().getCountry());
        location = locationService.addLocation(location);
        User user = new User(form.getFirstName(), form.getLastName(), form.getEmail(), encoder.encode(form.getPassword()), form.getDob(), location);
        userService.addUser(user);

        ResponseEntity<LoginDto> response = login(new LoginForm(user.getEmail(), form.getPassword()));
        return new ResponseEntity(response.getBody(), HttpStatus.CREATED);
    }
}
