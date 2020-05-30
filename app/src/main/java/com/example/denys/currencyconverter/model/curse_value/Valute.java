package com.example.denys.currencyconverter.model.curse_value;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root(name = "Valute")
public class Valute {
    @Element(name = "Value")
    public String value;

    @Element(name = "CharCode")
    public String code;

    @Element(name = "Nominal")
    public float nominal;
}