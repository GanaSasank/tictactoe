package com.example.tictactoe.model;

import lombok.Data;
import java.util.UUID;

@Data
public class Game {

    private String id = UUID.randomUUID().toString();
    private Player playerX;
    private Player playerO;
    private String[][] board = new String[3][3];
    private String nextTurn;
    private boolean finished = false;
    private String winner = null;

    public Game(Player player1, Player player2) {
        this.playerX = player1;
        this.playerO = player2;
        this.nextTurn = playerX.getName();
    }

    public boolean makeMove(String playerName, int row, int col) {
        if (finished || !playerName.equals(nextTurn) || board[row][col] != null) return false;

        board[row][col] = playerName.equals(playerX.getName()) ? "X" : "O";
        nextTurn = playerName.equals(playerX.getName()) ? playerO.getName() : playerX.getName();
        return true;
    }

public String checkWinner() {
    // Rows & columns
    for (int i = 0; i < 3; i++) {
        if (board[i][0] != null && board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]))
            return getPlayerNameBySymbol(board[i][0]);
        if (board[0][i] != null && board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]))
            return getPlayerNameBySymbol(board[0][i]);
    }
    // Diagonals
    if (board[0][0] != null && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
        return getPlayerNameBySymbol(board[0][0]);
    if (board[0][2] != null && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
        return getPlayerNameBySymbol(board[0][2]);

    // Draw
    boolean draw = true;
    for (int r = 0; r < 3; r++)
        for (int c = 0; c < 3; c++)
            if (board[r][c] == null) draw = false;
    if (draw) { finished = true; return "Draw"; }

    return null;
}

// Helper to get player name from symbol
private String getPlayerNameBySymbol(String symbol) {
    return symbol.equals("X") ? playerX.getName() : playerO.getName();
}


    public Player[] getPlayers() {
        return new Player[]{playerX, playerO};
    }
}
