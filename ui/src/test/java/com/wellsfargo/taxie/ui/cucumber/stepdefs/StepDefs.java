package com.wellsfargo.taxie.ui.cucumber.stepdefs;

import com.wellsfargo.taxie.ui.UiApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = UiApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
