package com.example.tictactoe.model;

import lombok.Data;

@Data
public class Move {
    private String gameId;
    private String playerName;
    private int row;
    private int col;
}
