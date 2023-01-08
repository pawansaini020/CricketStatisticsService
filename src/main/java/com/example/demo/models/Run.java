package com.example.demo.models;

import com.example.demo.enums.RunType;
import lombok.Data;

@Data
public class Run {
    private int scoredRun;
    private RunType runType;
}
