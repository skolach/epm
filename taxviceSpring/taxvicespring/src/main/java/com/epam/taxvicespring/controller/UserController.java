package com.epam.taxvicespring.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.epam.taxvicespring.entity.User;
import com.epam.taxvicespring.entity.modelassembler.UserModelAssembler;
import com.epam.taxvicespring.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserModelAssembler userModelAssembler;

    UserController(UserRepository repo, UserModelAssembler asem){
        this.userRepository = repo;
        this.userModelAssembler = asem;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<User>> getAllUsers() {
        List<EntityModel<User>> users = userRepository.findAll().stream().
            map(userModelAssembler::toModel).collect(Collectors.toList());
        
        return CollectionModel.of(
            users,
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );
    }

    @GetMapping("/{id}")
    public EntityModel<User> getOneUser(@PathVariable Integer id) {
        
        User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));

        return userModelAssembler.toModel(user);

    }

    @PostMapping("")
    public EntityModel<User> newUser(@RequestBody User newUser){
        return userModelAssembler.toModel(userRepository.save(newUser));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userRepository.deleteById(id);
    }

}