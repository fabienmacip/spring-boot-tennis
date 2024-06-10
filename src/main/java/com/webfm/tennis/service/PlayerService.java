package com.webfm.tennis.service;

import com.webfm.tennis.Player;
import com.webfm.tennis.web.PlayerList;
import com.webfm.tennis.web.PlayerToRegister;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    public List<Player> getAllPlayers(){
        return PlayerList.ALL.stream()
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .collect(Collectors.toList());
    }

    public Player getByLastName(String lastName){
        return PlayerList.ALL.stream()
                .filter(player -> player.lastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException(lastName));
    }

    public Player create(PlayerToRegister playerToRegister) {
        RankingCalculator rankingCalculator = new RankingCalculator(PlayerList.ALL, playerToRegister);

        List<Player> players = rankingCalculator.getNewPlayersRanking();
        return players.stream()
                .filter(player -> player.lastName().equals(playerToRegister.lastName()))
                .findFirst().get();
    }
}
