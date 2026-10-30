
package com.example.qa.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InventoryPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By produtos = By.className("inventory_item");
    private final By precos = By.className("inventory_item_price");
    private final By filtro = By.className("product_sort_container");
    private final By indicadorCarrinho = By.className("shopping_cart_badge");

    public void adicionarLuzBicicletaAoCarrinho() {
        driver.findElement(
                By.id("add-to-cart-sauce-labs-bike-light")).click();
    }

    public double obterPrecoMochila() {
        WebElement produto = driver.findElement(
                By.id("add-to-cart-sauce-labs-backpack")).findElement(
                        By.xpath("./ancestor::div[contains(@class,'inventory_item')]"));

        String preco = produto.findElement(
                By.className("inventory_item_price")).getText().replace("$", "");

        return Double.parseDouble(preco);
    }

    public CartPage abrirCarrinho() {
        driver.findElement(
                By.className("shopping_cart_link")).click();

        return new CartPage(driver);
    }

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10));

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(produtos));
    }

    public int contarProdutos() {
        return driver.findElements(produtos).size();
    }

    public void ordenarPorMenorPreco() {
        WebElement dropdown = driver.findElement(filtro);

        Select selecao = new Select(dropdown);
        selecao.selectByValue("lohi");
    }

    public List<Double> obterPrecos() {
        return driver.findElements(precos)
                .stream()
                .map(elemento -> elemento.getText())
                .map(texto -> texto.replace("$", ""))
                .map(Double::parseDouble)
                .toList();
    }

    public void adicionarMochilaAoCarrinho() {
        driver.findElement(
                By.id("add-to-cart-sauce-labs-backpack")).click();
    }

    public String obterQuantidadeCarrinho() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        indicadorCarrinho))
                .getText();
    }
}
