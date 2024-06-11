package com.webfm.tennis.service;

import com.webfm.tennis.data.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class RankingCalculator {

    private final List<PlayerEntity> currentPlayersRanking;

    public RankingCalculator(List<PlayerEntity> currentPlayersRanking) {
        this.currentPlayersRanking = currentPlayersRanking;
    }

    public List<PlayerEntity> getNewPlayersRanking() {
        currentPlayersRanking.sort((player1, player2) -> Integer.compare(player2.getPoints(), player1.getPoints()));

        List<PlayerEntity> updatedPlayers = new ArrayList<>();
        for (int i = 0; i < currentPlayersRanking.size(); i++) {
            PlayerEntity updatedPlayer = currentPlayersRanking.get(i);
            updatedPlayer.setRank(i + 1);
            updatedPlayers.add(updatedPlayer);
        }
        return updatedPlayers;
    }
}

/*
public class RankingCalculator {

    private final List<Player> currentPlayersRanking;
    private final PlayerToSave playerToSave;

    public RankingCalculator(List<Player> currentPlayersRanking, PlayerToSave playerToSave) {
        this.currentPlayersRanking = currentPlayersRanking;
        this.playerToSave = playerToSave;
    }

    public RankingCalculator(List<Player> currentPlayersRanking) {
        this.currentPlayersRanking = currentPlayersRanking;
        this.playerToSave = null;
    }

    public List<Player> getNewPlayersRanking() {
        List<Player> newRankingList = new ArrayList<>(currentPlayersRanking);
        if (playerToSave != null) {
            newRankingList.add(new Player(
                    playerToSave.firstName(),
                    playerToSave.lastName(),
                    playerToSave.birthDate(),
                    new Rank(999999999, playerToSave.points())
            ));
        }

        newRankingList.sort((player1, player2) -> Integer.compare(player2.rank().points(), player1.rank().points()));

        List<Player> updatedPlayers = new ArrayList<>();

        for (int i = 0; i < newRankingList.size(); i++) {
            Player player = newRankingList.get(i);
            Player updatedPlayer = new Player(
                    player.firstName(),
                    player.lastName(),
                    player.birthDate(),
                    new Rank(i + 1, player.rank().points())
            );
            updatedPlayers.add(updatedPlayer);
        }

        PlayerList.ALL = updatedPlayers;

        return updatedPlayers;
    }
}
*/