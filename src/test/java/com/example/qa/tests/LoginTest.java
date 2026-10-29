
package com.example.qa.tests;

import com.example.qa.pages.LoginPage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    void configurar() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @Test
    void deveRealizarLoginValido() {
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(ExpectedConditions.urlContains(
            "/inventory.html"
        ));

        assertTrue(
            driver.getCurrentUrl().contains("/inventory.html")
        );
    }

    @Test
    void deveRejeitarUsuarioBloqueado() {
        loginPage.preencherUsuario("locked_out_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        String erro = loginPage.obterMensagemErro();

        assertTrue(erro.contains("locked out"));
    }

    @AfterEach
    void encerrar() {
        if (driver != null) {
            driver.quit();
        }
    }
}