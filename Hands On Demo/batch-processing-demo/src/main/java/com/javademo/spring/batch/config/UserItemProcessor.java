package com.javademo.spring.batch.config;

import com.javademo.spring.batch.entity.User;
import org.springframework.batch.item.ItemProcessor;

public class UserItemProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        // Ignore empty email records
        if (user.getEmail() == null || user.getEmail().isEmpty()){
            return null;
        }

        user.setFirstName(user.getFirstName().toUpperCase());
        return user;
    }
}
