package com.angular.spring.demo.api.controller;

import com.angular.spring.demo.api.models.Games;
import com.angular.spring.demo.api.service.DataServiceImp;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.angular.spring.demo.globals.GlobalServices.dataService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/app")
public class GameController {
    private static final Logger logger = LogManager.getLogger(GameController.class);

    @GetMapping(value = {"games"})
    public List<Games> getGames() {
        logger.info("GETTING GAMES ");
        return dataService.getAllGamesFirestore();
    }

    @GetMapping(value = {"getGame/{id}"})
    public Games getAGame(@PathVariable(name = "id") String id) throws ExecutionException, InterruptedException {
        logger.info("GETTING " + id);
        return dataService.getGame(Long.valueOf(id)).orElse(null);
    }

    @PostMapping(value = {"addGame"})
    public Games addGame(@RequestBody Games games) throws ParseException {
        logger.info("ADDING GAME " + games.getName());
        games.setCreatedAt(Calendar.getInstance().getTime());
        return dataService.saveGameFirestore(games);
        // return null;
    }

    @DeleteMapping(value = {"deleteGame/{id}"})
    public boolean deleteGame(@PathVariable(name = "id") String id) throws ExecutionException, InterruptedException {
        logger.info("DELETING " + id);
        return dataService.deleteGame(Long.valueOf(id));
    }



}
