package br.com.sysmap;

import br.com.sysmap.driver.Api;
import br.com.sysmap.driver.Browser;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProjetoOpenWeatherTest {

    static String site = "https://openweathermap.org/";
    String cidade = "barueri";
    @BeforeAll
    static void inicio() {
        Browser.abrirChrome(site);
    }

    @Test
    @Order(2)
    void consultarCidade() {
        Browser.digitar(By.xpath("//input[@placeholder=\"Search city\"]"), cidade);
        Browser.aguardar(4);
        Browser.clicar(By.xpath("//button[@type=\"submit\"]"));
        Browser.aguardar(10);
        Browser.clicar(By.xpath("//ul[@class=\"search-dropdown-menu\"]"));
        Browser.aguardar(12);
        String texto = (Browser.lerTexto(By.xpath("//div[@class=\"current-container mobile-padding\"]//h2")));
        String[] nomeCidade = texto.split(",");
        System.out.printf("Validado que cidade mostrada no site é a mesma cidade %s pesquisada.\n", nomeCidade[0]);
        assertEquals(nomeCidade[0].toLowerCase(), cidade.toLowerCase());
    }

    @Test
    @Order(3)
    void validarTemperaturaCelsius() {
        Browser.clicar(By.xpath("//*[@id=\"weather-widget\"]/div[1]/div/div/div[1]/div[2]/div[2]"));
        Browser.aguardar(3);
        String texto = (Browser.lerTexto(By.xpath("//span[@class=\"heading\"]")));
        String tempSite = texto.substring(0,2);
        String tempApi = Api.currentWeatherC(cidade);
        assertEquals(tempApi, tempSite);
        System.out.printf("Validado que a temperatura em graus Celsius do site é %s ºC e a da API igualmente é %s ºC.\n", tempSite, tempApi);


    }

    @Test
    @Order(4)
    void validarTemperaturaFahrenheit() {
        Browser.clicar(By.xpath("//*[@id=\"weather-widget\"]/div[1]/div/div/div[1]/div[2]/div[3]"));
        Browser.aguardar(3);
        String texto = (Browser.lerTexto(By.xpath("//span[@class=\"heading\"]")));
        String tempSite = texto.substring(0,2);
        String tempApi = Api.currentWeatherF(cidade);
        assertEquals(tempApi, tempSite);
        System.out.printf("Validado que a temperatura em graus Fahrenheit do site é %s ºF e a da API igualmente é %s ºF.\n", tempSite, tempApi);

    }

    @AfterAll
    static void fim() {
        Browser.fecharChrome();
    }


}
