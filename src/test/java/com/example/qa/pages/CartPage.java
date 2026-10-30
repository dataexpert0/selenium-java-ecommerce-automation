
package com.example.qa.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By itens = By.className("cart_item");
    private final By nomes = By.className("inventory_item_name");
    private final By precos = By.className("inventory_item_price");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(
            ExpectedConditions.urlContains("/cart.html")
        );
    }

    public int contarItens() {
        return driver.findElements(itens).size();
    }

    public List<String> obterNomesProdutos() {
        return driver.findElements(nomes)
            .stream()
            .map(WebElement::getText)
            .toList();
    }

    public double obterPrecoProduto(String nome) {
        WebElement item = driver.findElements(itens)
            .stream()
            .filter(elemento ->
                elemento.findElement(nomes)
                    .getText().equals(nome)
            )
            .findFirst()
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Produto não encontrado: " + nome
                )
            );

        String preco = item.findElement(precos)
            .getText()
            .replace("$", "");

        return Double.parseDouble(preco);
    }

    public void removerMochila() {
        driver.findElement(
            By.id("remove-sauce-labs-backpack")
        ).click();
    }

    public void aguardarQuantidadeItens(int quantidade) {
        wait.until(driver ->
            driver.findElements(itens).size() == quantidade
        );
    }
}
