package com.example.tictactoe.model;

import lombok.Data;
import java.util.UUID;

@Data
public class Player {
    private String id = UUID.randomUUID().toString();
    private String name;

    public Player(String name) {
        this.name = name;
    }
}
