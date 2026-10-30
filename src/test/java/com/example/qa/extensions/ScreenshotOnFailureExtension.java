
package com.example.qa.extensions;

import com.example.qa.base.BaseTest;
import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotOnFailureExtension
        implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) {

        // Executa apenas quando o método falhou.
        if (context.getExecutionException().isEmpty()) {
            return;
        }

        Object instancia = context.getRequiredTestInstance();

        if (!(instancia instanceof BaseTest teste)) {
            return;
        }

        WebDriver driver = teste.getDriver();

        if (!(driver instanceof TakesScreenshot screenshot)) {
            return;
        }

        try {
            byte[] imagem =
                screenshot.getScreenshotAs(OutputType.BYTES);

            Path diretorio = Path.of(
                "target", "screenshots"
            );

            Files.createDirectories(diretorio);

            String nome = context.getRequiredTestClass()
                .getSimpleName()
                + "-"
                + context.getRequiredTestMethod().getName()
                + "-"
                + UUID.randomUUID()
                + ".png";

            Path destino = diretorio.resolve(nome);

            Files.write(destino, imagem);

            // Anexa a imagem ao resultado Allure.
            Allure.attachment(
                nome,
                new ByteArrayInputStream(imagem)
            );

            System.out.println(
                "Screenshot salvo em: " +
                destino.toAbsolutePath()
            );

        } catch (Exception e) {
            System.err.println(
                "Não foi possível capturar screenshot: "
                + e.getMessage()
            );
        }
    }
}
