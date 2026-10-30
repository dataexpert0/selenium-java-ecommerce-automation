package com.example.qa.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import com.example.qa.extensions.ScreenshotOnFailureExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.example.qa.pages.LoginPage;

import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ExtendWith(ScreenshotOnFailureExtension.class)
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

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeEach
    protected void configurarNavegador() {

        ChromeOptions options = new ChromeOptions();

        // Evita alertas de senhas vazadas no ambiente de testes
        options.setExperimentalOption(
                "prefs",
                java.util.Map.of(
                        "profile.password_manager_leak_detection", false,
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false));

        // Configuração específica para integração contínua
        if ("true".equalsIgnoreCase(System.getenv("CI"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

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
