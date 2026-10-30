
package com.example.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usuario = By.id("user-name");
    private final By senha = By.id("password");
    private final By botaoLogin = By.id("login-button");
    private final By mensagemErro = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));
    }

    public void preencherUsuario(String nome) {
        driver.findElement(usuario).sendKeys(nome);
    }

    public void preencherSenha(String password) {
        driver.findElement(senha).sendKeys(password);
    }

    public void clicarLogin() {
        driver.findElement(botaoLogin).click();
    }

    public String obterMensagemErro() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        mensagemErro))
                .getText();
    }

    public void realizarLogin(String usuario, String senha) {
        preencherUsuario(usuario);
        preencherSenha(senha);
        clicarLogin();
    }
}
