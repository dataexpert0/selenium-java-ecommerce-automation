
package com.example.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutCompletePage {
    
    private final WebDriverWait wait;

    private final By confirmacao =
        By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(
            ExpectedConditions.urlContains(
                "/checkout-complete.html"
            )
        );
    }

    public String obterMensagemConfirmacao() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                confirmacao
            )
        ).getText();
    }
}
