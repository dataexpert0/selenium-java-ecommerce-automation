
package com.example.qa.tests;

import com.example.qa.pages.LoginPage;
import com.example.qa.pages.InventoryPage;
import com.example.qa.pages.CartPage;
import com.example.qa.base.BaseTest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeEach
    void configurarCarrinho() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();

        // Confirma que o catálogo registrou a adição.
        assertEquals(
                "1",
                inventoryPage.obterQuantidadeCarrinho());

        CartPage cartPage = inventoryPage.abrirCarrinho();

        // Aguarda o carrinho apresentar o item esperado.
        cartPage.aguardarQuantidadeItens(1);

        assertEquals(1, cartPage.contarItens());

        assertTrue(
                cartPage.obterNomesProdutos()
                        .contains("Sauce Labs Backpack"));
    }

    @Test
    void deveAdicionarDoisProdutosAoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();
        inventoryPage.adicionarLuzBicicletaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();

        assertEquals(2, cartPage.contarItens());
        assertTrue(
                cartPage.obterNomesProdutos()
                        .contains("Sauce Labs Backpack"));
        assertTrue(
                cartPage.obterNomesProdutos()
                        .contains("Sauce Labs Bike Light"));
    }

    @Test
    void deveRemoverProdutoDoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();
        cartPage.removerMochila();
        cartPage.aguardarQuantidadeItens(0);

        assertFalse(
                cartPage.obterNomesProdutos()
                        .contains("Sauce Labs Backpack"));
    }

    @Test
    void deveManterPrecoProdutoNoCarrinho() {
        double precoCatalogo = inventoryPage.obterPrecoMochila();

        inventoryPage.adicionarMochilaAoCarrinho();

        CartPage cartPage = inventoryPage.abrirCarrinho();

        double precoCarrinho = cartPage.obterPrecoProduto("Sauce Labs Backpack");

        assertEquals(precoCatalogo, precoCarrinho);
    }
}
