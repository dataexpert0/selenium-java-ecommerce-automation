
package com.example.qa.tests;

import com.example.qa.pages.LoginPage;
import com.example.qa.pages.InventoryPage;
import com.example.qa.pages.CartPage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    private WebDriver driver;
    private InventoryPage inventoryPage;

    @BeforeEach
    void configurar() {
        driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();

        assertEquals(1, cartPage.contarItens());
        assertTrue(
            cartPage.obterNomesProdutos()
                .contains("Sauce Labs Backpack")
        );
    }

    @Test
    void deveAdicionarDoisProdutosAoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();
        inventoryPage.adicionarLuzBicicletaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();

        assertEquals(2, cartPage.contarItens());
        assertTrue(
            cartPage.obterNomesProdutos()
                .contains("Sauce Labs Backpack")
        );
        assertTrue(
            cartPage.obterNomesProdutos()
                .contains("Sauce Labs Bike Light")
        );
    }

    @Test
    void deveRemoverProdutoDoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();
        cartPage.removerMochila();
        cartPage.aguardarQuantidadeItens(0);

        assertFalse(
            cartPage.obterNomesProdutos()
                .contains("Sauce Labs Backpack")
        );
    }

    @Test
    void deveManterPrecoProdutoNoCarrinho() {
        double precoCatalogo =
            inventoryPage.obterPrecoMochila();

        inventoryPage.adicionarMochilaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();

        double precoCarrinho =
            cartPage.obterPrecoProduto("Sauce Labs Backpack");

        assertEquals(precoCatalogo, precoCarrinho);
    }

    @AfterEach
    void encerrar() {
        if (driver != null) {
            driver.quit();
        }
    }
}
