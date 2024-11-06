package org.gameshop.service;


import org.gameshop.service.dtos.GameAddDTO;

import java.util.Map;

public interface GameService {
    String addGame(GameAddDTO gameAddDTO);

    String editGame(long id, Map<String, String> map);

    String deleteGame(long id);

    String getAllGames();

    String getDetailsForGameWithGivenTitle(String title);

    String getOwnedGames();
}
