package com.example.demo.models;

import com.example.demo.enums.TossAction;
import lombok.Data;

@Data
public class Toss {
    private String tossedBy;
    private String askedBy;
    private String wonByTeam;
    private TossAction tossAction;
}
