package com.example.demo.models;

import lombok.Data;

@Data
public class Wicket {

    private WicketType wicketType;
    private Player playerOut;
    private Player bowledBy;
    private Player caughtBy;
    private Player stumpedBy;
    private Player runOutBy;

    public Wicket(WicketType wicketType) {
        this.wicketType = wicketType;
    }
}
