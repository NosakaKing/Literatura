package com.nosakaking.books.service;

public interface IDataConverter {
    <T> T gettingData(String json, Class<T> clase);
}
