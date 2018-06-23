package com.seebattleserver.application.gameregistry;

import com.seebattleserver.application.user.User;
import com.seebattleserver.domain.game.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GameRegistry {

    private Map<User, Game> games;

    public GameRegistry() {
        games = new HashMap<>();
    }

    public void put(User user, Game game) {
        games.put(user, game);
    }

    public void remove(User user, Game game) {
        games.remove(user, game);
    }

    public Game getGameByUser(User user) {
        if (games.containsKey(user)) {
            return games.get(user);
        }
        throw new IllegalArgumentException("Данный игрок не ведет игру");
    }
}
