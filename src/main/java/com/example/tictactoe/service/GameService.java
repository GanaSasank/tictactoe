package com.example.tictactoe.service;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.Move;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public Game createGame(Game game) {
        games.put(game.getId(), game);
        return game;
    }

    public Game processMove(Move move) {
        Game game = games.get(move.getGameId());
        if (game == null || game.isFinished()) return null;

        synchronized (game) {
            boolean valid = game.makeMove(move.getPlayerName(), move.getRow(), move.getCol());
            if (!valid) return game;

            String winner = game.checkWinner();
            if (winner != null) {
                game.setWinner(winner);
                game.setFinished(true);
            }
        }
        return game;
    }

    public Game getGame(String id) {
        return games.get(id);
    }
}
