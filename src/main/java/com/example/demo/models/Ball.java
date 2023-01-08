package com.example.demo.models;

import com.example.demo.enums.BallType;
import com.example.demo.enums.RunType;
import lombok.Data;

@Data
public class Ball {

    private int overNumber;
    private BallType ballType;
    private String bowledBy;
    private String facedBy;
    private RunType runType;
    private Wicket wicket;

    public Ball(int overNumber, String bowledBy, String facedBy) {
        this.overNumber = overNumber;
        this.bowledBy = bowledBy;
        this.facedBy = facedBy;
    }
}
