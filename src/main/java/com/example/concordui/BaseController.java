package com.example.concordui;

public abstract class BaseController {

    final ConcordClient client;
    final ViewFactory viewFactory;
    final String fxmlName;

    public BaseController(ConcordClient client, ViewFactory viewFactory, String fxmlName) {
        this.client = client;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
}
