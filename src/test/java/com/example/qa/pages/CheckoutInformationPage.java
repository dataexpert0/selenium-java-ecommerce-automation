
package com.example.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutInformationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By primeiroNome = By.id("first-name");
    private final By sobrenome = By.id("last-name");
    private final By codigoPostal = By.id("postal-code");
    private final By continuar = By.id("continue");
    private final By mensagemErro = By.cssSelector("[data-test='error']");

    public CheckoutInformationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.urlContains(
                "/checkout-step-one.html"));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(primeiroNome));
    }

    public CheckoutOverviewPage avancarParaResumo() {
        clicarContinuar();
        return new CheckoutOverviewPage(driver);
    }

    public void preencherDados(
            String nome,
            String ultimoNome,
            String cep) {

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(primeiroNome)).sendKeys(nome);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(sobrenome)).sendKeys(ultimoNome);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(codigoPostal)).sendKeys(cep);
    }

    public void clicarContinuar() {
        driver.findElement(continuar).click();
    }

    public String obterMensagemErro() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        mensagemErro))
                .getText();
    }
}