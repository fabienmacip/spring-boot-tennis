package com.webfm.tennis.service;

import com.webfm.tennis.Player;
import com.webfm.tennis.Rank;
import com.webfm.tennis.data.PlayerEntity;
import com.webfm.tennis.data.PlayerRepository;
import com.webfm.tennis.web.PlayerList;
import com.webfm.tennis.web.PlayerToSave;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public List<Player> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> new Player(
                        player.getFirstName(),
                        player.getLastName(),
                        player.getBirthDate(),
                        new Rank(player.getRank(), player.getPoints())
                ))
                .sorted(Comparator.comparing(player -> player.rank().position()))
                .collect(Collectors.toList());
    }

    public Player getByLastName(String lastName) {
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(lastName);
        if(player.isEmpty()) {
            throw new PlayerNotFoundException(lastName);
        }
        return new Player(
                player.get().getFirstName(),
                player.get().getLastName(),
                player.get().getBirthDate(),
                new Rank(player.get().getRank(), player.get().getPoints())
        );
    }

    public Player create(PlayerToSave playerToSave) {
        return getPlayerNewRanking(PlayerList.ALL, playerToSave);
    }

    public Player update(PlayerToSave playerToSave) {
        getByLastName(playerToSave.lastName());

        List<Player> playersWithoutPlayerToUpdate = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(playerToSave.lastName()))
                .toList();

        RankingCalculator rankingCalculator = new RankingCalculator(playersWithoutPlayerToUpdate, playerToSave);
        List<Player> players = rankingCalculator.getNewPlayersRanking();

        return players.stream()
                .filter(player -> player.lastName().equals(playerToSave.lastName()))
                .findFirst().get();
    }

    public void delete(String lastName) {
        Player playerToDelete = getByLastName(lastName);

        PlayerList.ALL = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(lastName))
                .toList();

        RankingCalculator rankingCalculator = new RankingCalculator(PlayerList.ALL);
        rankingCalculator.getNewPlayersRanking();
    }

    private Player getPlayerNewRanking(List<Player> existingPlayers, PlayerToSave playerToSave) {
        RankingCalculator rankingCalculator = new RankingCalculator(existingPlayers, playerToSave);
        List<Player> players = rankingCalculator.getNewPlayersRanking();

        return players.stream()
                .filter(player -> player.lastName().equals(playerToSave.lastName()))
                .findFirst().get();
    }

}
