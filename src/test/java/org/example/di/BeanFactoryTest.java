package org.example.di;

import org.example.mvc.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeanFactoryTest {
    private BeanFactory beanFactory;

    @BeforeEach
    void setUp() {
        this.beanFactory = new BeanFactory();
    }

    @Test
    void createTest() {
        UserController userController = beanFactory.getBean(UserController.class);
        assertThat(userController).isNotNull();
        assertThat(userController.getUserRepository()).isNotNull();
    }
}