package com.epam.taxvicespring.repository;

import com.epam.taxvicespring.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

}
