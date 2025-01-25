package com.e_commerce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.e_commerce.models.User;

public interface UserRepo extends JpaRepository<User, Long>{

}
