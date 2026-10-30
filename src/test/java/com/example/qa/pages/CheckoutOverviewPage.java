
package com.example.qa.pages;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutOverviewPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By precosProdutos =
        By.className("inventory_item_price");

    private final By subtotal =
        By.className("summary_subtotal_label");

    private final By imposto =
        By.className("summary_tax_label");

    private final By total =
        By.className("summary_total_label");

    private final By finalizar =
        By.id("finish");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(
            ExpectedConditions.urlContains(
                "/checkout-step-two.html"
            )
        );

        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                total
            )
        );
    }

    private BigDecimal converterPreco(String texto) {
        String valor = texto
            .replaceAll("[^0-9.,]", "")
            .replace(",", ".");

        return new BigDecimal(valor);
    }

    public List<BigDecimal> obterPrecosProdutos() {
        return driver.findElements(precosProdutos)
            .stream()
            .map(WebElement::getText)
            .map(this::converterPreco)
            .toList();
    }

    public BigDecimal obterSubtotal() {
        return converterPreco(
            driver.findElement(subtotal).getText()
        );
    }

    public BigDecimal obterImposto() {
        return converterPreco(
            driver.findElement(imposto).getText()
        );
    }

    public BigDecimal obterTotal() {
        return converterPreco(
            driver.findElement(total).getText()
        );
    }

    public CheckoutCompletePage finalizarCompra() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                finalizar
            )
        ).click();

        return new CheckoutCompletePage(driver);
    }
}
