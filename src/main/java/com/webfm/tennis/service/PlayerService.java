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
        Optional<PlayerEntity> player = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if (player.isPresent()) {
            throw new PlayerAlreadyExistsException(playerToSave.lastName());
        }

        PlayerEntity playerToRegister = new PlayerEntity(
                playerToSave.lastName(),
                playerToSave.firstName(),
                playerToSave.birthDate(),
                playerToSave.points(),
                999999999);

        PlayerEntity registeredPlayer = playerRepository.save(playerToRegister);

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);

        return getByLastName(registeredPlayer.getLastName());
    }

    public Player update(PlayerToSave playerToSave) {
        Optional<PlayerEntity> playerToUpdate = playerRepository.findOneByLastNameIgnoreCase(playerToSave.lastName());
        if (playerToUpdate.isEmpty()) {
            throw new PlayerNotFoundException(playerToSave.lastName());
        }

        playerToUpdate.get().setFirstName(playerToSave.firstName());
        playerToUpdate.get().setBirthDate(playerToSave.birthDate());
        playerToUpdate.get().setPoints(playerToSave.points());

        PlayerEntity updatedPlayer = playerRepository.save(playerToUpdate.get());

        RankingCalculator rankingCalculator = new RankingCalculator(playerRepository.findAll());
        List<PlayerEntity> newRanking = rankingCalculator.getNewPlayersRanking();
        playerRepository.saveAll(newRanking);

        return getByLastName(updatedPlayer.getLastName());
    }
    public void delete(String lastName) {
        /*Player playerToDelete = getByLastName(lastName);

        PlayerList.ALL = PlayerList.ALL.stream()
                .filter(player -> !player.lastName().equals(lastName))
                .toList();

        RankingCalculator rankingCalculator = new RankingCalculator(PlayerList.ALL);
        rankingCalculator.getNewPlayersRanking();*/
    }

/*    private Player getPlayerNewRanking(List<Player> existingPlayers, PlayerToSave playerToSave) {
        RankingCalculator rankingCalculator = new RankingCalculator(existingPlayers);
        List<Player> players = rankingCalculator.getNewPlayersRanking();

        return players.stream()
                .filter(player -> player.lastName().equals(playerToSave.lastName()))
                .findFirst().get();
    }*/

}
