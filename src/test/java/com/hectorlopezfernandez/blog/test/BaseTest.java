package com.hectorlopezfernandez.blog.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hectorlopezfernandez.blog.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={TestApplicationPersistence.class,Application.class})
public abstract class BaseTest {

}