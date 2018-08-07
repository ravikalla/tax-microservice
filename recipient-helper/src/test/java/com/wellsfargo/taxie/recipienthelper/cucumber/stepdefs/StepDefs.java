package com.wellsfargo.taxie.recipienthelper.cucumber.stepdefs;

import com.wellsfargo.taxie.recipienthelper.RecipienthelperApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = RecipienthelperApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
