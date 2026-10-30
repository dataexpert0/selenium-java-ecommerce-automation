
package com.example.qa.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    protected void configurarNavegador() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    protected void encerrarNavegador() {
        if (driver != null) {
            driver.quit();
        }
    }
}
