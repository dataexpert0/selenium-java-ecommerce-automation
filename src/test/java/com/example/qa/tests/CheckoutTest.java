package com.example.qa.tests;

import com.example.qa.base.BaseTest;
import com.example.qa.pages.InventoryPage;
import com.example.qa.pages.CartPage;
import com.example.qa.pages.CheckoutInformationPage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class CheckoutTest extends BaseTest {

    private CheckoutInformationPage checkoutPage;

    @BeforeEach
    void configurarCheckout() {

        autenticarUsuarioPadrao();

        InventoryPage inventoryPage = new InventoryPage(driver);

        inventoryPage.adicionarMochilaAoCarrinho();
        inventoryPage.aguardarQuantidadeCarrinho(1);

        CartPage cartPage = inventoryPage.abrirCarrinho();
        cartPage.aguardarQuantidadeItens(1);

        checkoutPage = cartPage.iniciarCheckout();
    }

    @Test
    void deveAvancarComDadosValidos() {

        checkoutPage.preencherDados(
            "Joao",
            "Silva",
            "36000000"
        );

        checkoutPage.clicarContinuar();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(
            ExpectedConditions.urlContains(
                "/checkout-step-two.html"
            )
        );

        assertTrue(
            driver.getCurrentUrl()
                .contains("/checkout-step-two.html")
        );
    }

    @ParameterizedTest(name = "[{index}] {3}")
    @CsvSource({
        "'', Silva, 36000000, First Name is required",
        "Joao, '', 36000000, Last Name is required",
        "Joao, Silva, '', Postal Code is required"
    })
    void deveValidarCamposObrigatorios(
            String nome,
            String sobrenome,
            String cep,
            String mensagemEsperada) {

        checkoutPage.preencherDados(nome, sobrenome, cep);

        checkoutPage.clicarContinuar();

        String erro = checkoutPage.obterMensagemErro();

        assertEquals(
            "Error: " + mensagemEsperada,
            erro
        );

        assertTrue(
            driver.getCurrentUrl()
                .contains("/checkout-step-one.html"),
            "O checkout não deveria avançar com campos vazios."
        );
    }
}