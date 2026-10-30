
package com.example.qa.tests;

import com.example.qa.pages.LoginPage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import com.example.qa.base.BaseTest;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    // Teste parametrizado para validar diferentes cenários de login inválido.
    // Utiliza o JUnit 5 e Selenium WebDriver para verificar se a aplicação rejeita corretamente credenciais inválidas.
    // Inclui casos de usuários bloqueados, usuários inexistentes e combinações incorretas de nome de usuário e senha.
    
@ParameterizedTest(name = "[{index}] Usuário: {0}")
@CsvSource({
    "locked_out_user, secret_sauce, locked out",
    "standard_user, senha123, Username and password do not match",
    "usuario_inexistente, secret_sauce, Username and password do not match",
    // Adição extra: usuário com senha vazia
    "visual_user, '', Password is required" 
})

void deveRejeitarCredenciaisInvalidas(
    // Dados pareados com o CSVSource, representando diferentes combinações de usuário, senha e a mensagem de erro esperada.
        String usuario,
        String senha,
        String mensagemEsperada) {

    loginPage.preencherUsuario(usuario);
    loginPage.preencherSenha(senha);
    loginPage.clicarLogin();

    String mensagemObtida = loginPage.obterMensagemErro();

    assertTrue(
        mensagemObtida.contains(mensagemEsperada),
        "A mensagem de erro não corresponde ao esperado."
    );
}

    private LoginPage loginPage;

    @BeforeEach
    void configurar() {
        loginPage = new LoginPage(driver);
    }

    @Test
    void deveRealizarLoginValido() {
        loginPage.preencherUsuario("standard_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        wait.until(ExpectedConditions.urlContains(
            "/inventory.html"
        ));

        assertTrue(
            driver.getCurrentUrl().contains("/inventory.html")
        );
    }

    @Test
    void deveRejeitarUsuarioBloqueado() {
        loginPage.preencherUsuario("locked_out_user");
        loginPage.preencherSenha("secret_sauce");
        loginPage.clicarLogin();

        String erro = loginPage.obterMensagemErro();

        assertTrue(erro.contains("locked out"));
    }
}