package com.example.tictactoe.service;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.Player;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MatchmakingService {

    private final Queue<Player> waitingPlayers = new ConcurrentLinkedQueue<>();
    private final GameService gameService;

    public MatchmakingService(GameService gameService) {
        this.gameService = gameService;
    }

    public Game findOrCreateGame(Player player) {
        Player opponent = waitingPlayers.poll();
        if (opponent != null) {
            Game game = new Game(player, opponent);
            gameService.createGame(game);
            return game;
        } else {
            waitingPlayers.add(player);
            return null;
        }
    }
}
