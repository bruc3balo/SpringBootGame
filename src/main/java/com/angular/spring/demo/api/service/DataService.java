package com.angular.spring.demo.api.service;

import com.angular.spring.demo.api.models.Games;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface DataService {
    Games saveGameFirestore(Games games) throws ParseException;

    Optional<Games> getGame(Long id) throws ExecutionException, InterruptedException;

    Games updateGame(Games games);

    boolean deleteGame (Long id) throws ExecutionException, InterruptedException;

    List<Games> getAllGamesFirestore();
}
