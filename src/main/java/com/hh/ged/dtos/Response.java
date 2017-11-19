package com.hh.ged.dtos;

import org.codehaus.jackson.annotate.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({
        "isError",
        "totalElements",
        "size",
        "page",
        "one",
        "oneMessage",
        "resultList",
        "resultMessages"

})
public class Response<T> {

    private boolean isError = false;
    private int totalElements = 0;
    private int size = 0;
    private int page = 0;
    private T one;
    private String oneMessage = "";
    private List<T> resultList = new ArrayList<>();
    private List<String> resultMessages;


    public Response(List<T> resultList, int totalElements) {
        this.resultList = resultList;
        this.totalElements = totalElements;
    }

    public Response(boolean isError, String oneMessage) {
        this.isError = isError;
        this.oneMessage = oneMessage;
    }

    public T getOne() {
        return one;
    }

    public void setOne(T one) {
        this.one = one;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public String getOneMessage() {
        return oneMessage;
    }

    public void setOneMessage(String oneMessage) {
        this.oneMessage = oneMessage;
    }

    public List<String> getResultMessages() {
        return resultMessages;
    }

    public void setResultMessages(List<String> resultMessages) {
        this.resultMessages = resultMessages;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
