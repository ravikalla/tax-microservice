package com.wellsfargo.taxie.wftfv.cucumber.stepdefs;

import com.wellsfargo.taxie.wftfv.WftfvApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = WftfvApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
