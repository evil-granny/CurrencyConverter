package com.example.denys.currencyconverter.repos;
import java.util.List;

public interface IRepoCallback <T>{
    void update(List<T> list);
    void onFinish();
    void log(String str);
    void log(int str);
}
