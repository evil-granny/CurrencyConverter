package com.example.denys.currencyconverter.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.denys.currencyconverter.R;
import com.example.denys.currencyconverter.interactors.Converter;
import com.example.denys.currencyconverter.repos.IRepoCallbackData;
import com.example.denys.currencyconverter.repos.IRepoCurrency;
import com.example.denys.currencyconverter.repos.RepoCurrency;
import com.example.denys.currencyconverter.view.MainView;

public class MainPresenter implements IMainPresenter {
    private static MainPresenter instance = new MainPresenter();
    private static boolean updated = false;
    private MainView mainView;
    private Converter converter;
    private IRepoCurrency repo;

    private MainPresenter() {
    }

    public static MainPresenter getInstance() {
        return instance;
    }

    @Override
    public void setView(MainView mainView) {
        this.mainView = mainView;
        converter = new Converter(this.getContext());
        repo = new RepoCurrency(this.getContext());
        initActions();

        if (updated == true) {
            return;
        }
        update();
        updated = true;
    }

    public void update() {
        progress(true);
        repo.update(new IRepoCallbackData() {

            public void onFinish() {
                Activity activity = (Activity) mainView;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initActions();
                        progress(false);
                    }
                });
            }

        });
    }

    public void onDestroy() {
        repo.onDestroy();
    }

    public Context getContext() {
        return (Context) mainView;
    }


    @Override
    public void convert() {
        String from = mainView.getSpinner1().getSelectedItem().toString();
        String to = mainView.getSpinner2().getSelectedItem().toString();
        String v = mainView.getMoneyIn().getText().toString();
        String value;

        if ("".equals(v)) {
            value = "";
        } else {
            float val = Float.parseFloat(v);
            float res = converter.convert(from, to, val);
            value = String.format("%4.2f", res);
        }
        mainView.setValue(value);
    }

    private void initActions() {
        makeSpinner(mainView.getSpinner1(), 0);
        makeSpinner(mainView.getSpinner2(), 1);

        mainView.getMoneyIn().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    convert();
                }
                return false;
            }
        });
        mainView.getUpdateBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.update_btn) {
                    update();
                }
            }
        });
    }

    private void makeSpinner(Spinner spinner, int position) {
        String[] data = repo.getCurrencies();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, data);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (data.length <= position) {
            position = 0;
        }
        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                convert();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void progress(boolean show) {
        if (show) {
            mainView.getProgressBar().setVisibility(View.VISIBLE);
        } else {
            mainView.getProgressBar().setVisibility(View.GONE);
        }
    }
}
