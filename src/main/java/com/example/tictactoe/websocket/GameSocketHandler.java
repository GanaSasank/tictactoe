package com.example.tictactoe.websocket;

import com.example.tictactoe.model.Game;
import com.example.tictactoe.model.Move;
import com.example.tictactoe.model.Player;
import com.example.tictactoe.service.GameService;
import com.example.tictactoe.service.MatchmakingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Component
public class GameSocketHandler extends TextWebSocketHandler {

    private final MatchmakingService matchmakingService;
    private final GameService gameService;
    private final ObjectMapper mapper = new ObjectMapper();

    // Map session -> player name
    private final Map<WebSocketSession, String> sessionPlayerMap = new ConcurrentHashMap<>();
    // Map player name -> session (for broadcasting)
    private final Map<String, WebSocketSession> playerSessionMap = new ConcurrentHashMap<>();

    public GameSocketHandler(MatchmakingService matchmakingService, GameService gameService) {
        this.matchmakingService = matchmakingService;
        this.gameService = gameService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> payload = mapper.readValue(message.getPayload(), Map.class);
        String type = (String) payload.get("type");

        switch (type) {
            case "JOIN":
                String name = (String) payload.get("playerName");
                Player player = new Player(name);

                sessionPlayerMap.put(session, name);
                playerSessionMap.put(name, session);

                Game game = matchmakingService.findOrCreateGame(player);

                if (game != null) {
                    // Broadcast START to both players
                    for (Player p : game.getPlayers()) {
                        WebSocketSession s = playerSessionMap.get(p.getName());
                        if (s != null && s.isOpen()) {
                            s.sendMessage(new TextMessage(mapper.writeValueAsString(Map.of(
                                    "type", "START",
                                    "game", game
                            ))));
                        }
                    }
                }
                break;

            case "MOVE":
                Move move = mapper.convertValue(payload.get("move"), Move.class);
                Game updatedGame = gameService.processMove(move);

                if (updatedGame != null) {
                    // Broadcast updated game state to both players
                    for (Player p : updatedGame.getPlayers()) {
                        WebSocketSession s = playerSessionMap.get(p.getName());
                        if (s != null && s.isOpen()) {
                            s.sendMessage(new TextMessage(mapper.writeValueAsString(Map.of(
                                    "type", "UPDATE",
                                    "game", updatedGame
                            ))));
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String playerName = sessionPlayerMap.remove(session);
        if (playerName != null) {
            playerSessionMap.remove(playerName);
        }
    }
}
