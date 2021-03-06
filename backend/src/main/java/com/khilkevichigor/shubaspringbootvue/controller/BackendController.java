package com.khilkevichigor.shubaspringbootvue.controller;

import com.khilkevichigor.shubaspringbootvue.exception.UserNotFoundException;
import com.khilkevichigor.shubaspringbootvue.model.User;
import com.khilkevichigor.shubaspringbootvue.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Привет из SpringBoot-а!";
    public static final String SECURED_TEXT = "Привет с защищенного ресурса!";

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        User user = userRepository.findUserByUsername("admin");
        return HELLO_TEXT + " Имя пользователя в БД под id = 1: " + user.getUsername();
    }

    @RequestMapping(path = "/user/{username}/{password}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser (@PathVariable("username") String username, @PathVariable("password") String password) {
        User savedUser = userRepository.save(new User(username, password));

        LOG.info(savedUser.toString() + " successfully saved into DB");

        return savedUser.getId();
    }

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable("id") long id) {

        return userRepository.findById(id).map(user -> {
            LOG.info("Reading user with id " + id + " from database.");
            return user;
        }).orElseThrow(() -> new UserNotFoundException("Пользователь с id = " + id + " не может быть найден в базе данных."));
    }

    @RequestMapping(path="/secured", method = RequestMethod.GET)
    public @ResponseBody String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }
}
