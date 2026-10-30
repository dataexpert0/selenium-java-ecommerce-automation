
package com.example.qa.tests;

import com.example.qa.pages.LoginPage;
import com.example.qa.pages.InventoryPage;
import com.example.qa.base.BaseTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeEach
    void configurarInventario() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        inventoryPage = new InventoryPage(driver);
    }

    @Test
    void deveExibirProdutosNoCatalogo() {
        int quantidade = inventoryPage.contarProdutos();

        assertTrue(
                quantidade > 0,
                "O catálogo deveria conter produtos.");
    }

    @Test
    void deveOrdenarProdutosPorMenorPreco() {
        inventoryPage.ordenarPorMenorPreco();

        List<Double> precos = inventoryPage.obterPrecos();

        List<Double> precosOrdenados = new ArrayList<>(precos);
        precosOrdenados.sort(Comparator.naturalOrder());

        assertFalse(precos.isEmpty());

        assertEquals(
                precosOrdenados,
                precos,
                "Os produtos deveriam estar ordenados por preço crescente.");
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() {
        inventoryPage.adicionarMochilaAoCarrinho();

        String quantidade = inventoryPage.obterQuantidadeCarrinho();

        assertEquals("1", quantidade);
    }
}
