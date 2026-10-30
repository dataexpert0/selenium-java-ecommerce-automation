package com.example.qa.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.example.qa.pages.LoginPage;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BaseTest {

    protected WebDriver driver;

    protected void autenticarUsuarioPadrao() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.realizarLogin(
                "standard_user",
                "secret_sauce");

        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.urlContains("/inventory.html"));
    }

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
