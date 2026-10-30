
package com.example.qa.tests;

import com.example.qa.base.BaseTest;
import com.example.qa.pages.InventoryPage;
import com.example.qa.pages.CartPage;
import com.example.qa.pages.CheckoutInformationPage;
import com.example.qa.pages.CheckoutOverviewPage;
import com.example.qa.pages.CheckoutCompletePage;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutOverviewTest extends BaseTest {

    private CheckoutOverviewPage overviewPage;

    @BeforeEach
    void configurarResumo() {
        autenticarUsuarioPadrao();

        InventoryPage inventoryPage =
            new InventoryPage(driver);

        inventoryPage.adicionarMochilaAoCarrinho();
        inventoryPage.adicionarLuzBicicletaAoCarrinho();

        inventoryPage.aguardarQuantidadeCarrinho(2);

        CartPage cartPage = inventoryPage.abrirCarrinho();
        cartPage.aguardarQuantidadeItens(2);

        CheckoutInformationPage informationPage =
            cartPage.iniciarCheckout();

        informationPage.preencherDados(
            "Joao",
            "Silva",
            "36000000"
        );

        overviewPage = informationPage.avancarParaResumo();
    }

    @Test
    void deveValidarSubtotalDosProdutos() {
        List<BigDecimal> precos =
            overviewPage.obterPrecosProdutos();

        assertEquals(2, precos.size());

        BigDecimal soma = precos.stream()
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(
            0,
            soma.compareTo(overviewPage.obterSubtotal()),
            "O subtotal deve corresponder à soma dos produtos."
        );
    }

    @Test
    void deveValidarTotalComImpostos() {
        BigDecimal subtotal = overviewPage.obterSubtotal();
        BigDecimal imposto = overviewPage.obterImposto();
        BigDecimal total = overviewPage.obterTotal();

        BigDecimal totalCalculado = subtotal.add(imposto);

        assertEquals(
            0,
            totalCalculado.compareTo(total),
            "O total deve corresponder ao subtotal mais impostos."
        );
    }

    @Test
    void deveFinalizarCompraComSucesso() {
        CheckoutCompletePage completePage =
            overviewPage.finalizarCompra();

        assertEquals(
            "Thank you for your order!",
            completePage.obterMensagemConfirmacao()
        );
    }
}
