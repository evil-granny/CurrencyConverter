package com.example.denys.currencyconverter.model.curse_value;

import android.util.Log;

import org.simpleframework.xml.core.Persister;

import java.io.InputStream;
import java.util.List;

public class CurseParser {
    private ValuteCurrency curs;

    public CurseParser(InputStream inputStream) {
        Persister serializer = new Persister();
        try {
            curs = serializer.read(ValuteCurrency.class, inputStream, false);
        } catch (Exception e) {
            Log.e("SimpleXML::", e.getMessage());
        }
    }

    public float getVal(String code) {
        return Float.parseFloat(curs.getValute(code).value.replace(",", "."));
    }

    public List<Valute> getValute() {
        return curs.getValute();
    }

}
