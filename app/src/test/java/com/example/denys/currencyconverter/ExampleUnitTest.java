package com.example.denys.currencyconverter;

import com.example.denys.currencyconverter.model.curse_value.CurseParser;
import com.example.denys.currencyconverter.interactors.Converter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String xml =
            "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                    "<ValCurs>\n" +
                    "    <Valute>\n" +
                    "        <NumCode>978</NumCode>\n" +
                    "        <CharCode>EUR</CharCode>\n" +
                    "        <Nominal>1</Nominal>\n" +
                    "        <Name>Евро</Name>\n" +
                    "        <Value>70</Value>\n" +
                    "    </Valute>\n" +
                    "    <Valute>\n" +
                    "        <NumCode>840</NumCode>\n" +
                    "        <CharCode>USD</CharCode>\n" +
                    "        <Nominal>1</Nominal>\n" +
                    "        <Name>Доллар США</Name>\n" +
                    "        <Value>58</Value>\n" +
                    "    </Valute>\n" +
                    "</ValCurs>";


    @Test
    public void addition_isCorrect() throws Exception {
        //создать парсер
        CurseParser curseParser = new CurseParser(xml);

        //вытащить объект
        float eur = curseParser.getVal("EUR");
        float usd = curseParser.getVal("USD");


        assertEquals(eur, 70, 0.001);
        assertEquals(usd, 58, 0.001);
    }


    @Test
    public void addition_currencyCalculate() throws  Exception {
        Converter converter = new Converter();

        // выставляем текущие значения курса
        converter.put("EUR", 70);
        converter.put("USD", 60);

         // проверяем рассчеты
        float eur_rub = converter.convert("EUR", "RUB", 1);
        assertEquals(eur_rub, 70, 0.001);

        float usd_rub = converter.convert("USD", "RUB", 1);
        assertEquals(usd_rub, 60, 0.001);
    }
}