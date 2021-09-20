package com.angular.spring.demo.api.service;

import com.angular.spring.demo.api.models.Games;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.angular.spring.demo.globals.GlobalServices.dataService;
import static com.angular.spring.demo.globals.GlobalServices.firestore;

@Service
public class DataServiceImp implements DataService {

    public static final String COLLECTION = "Games";
    private static final Logger logger = LogManager.getLogger(DataServiceImp.class);

    @Override
    public Games saveGameFirestore(Games games) {
        List<Long> idList = dataService.getAllGamesFirestore().stream().sorted(Comparator.comparing(Games::getId).reversed()).map(Games::getId).collect(Collectors.toList());
        if (games.getId() != null) { //update
            if (idList.contains(games.getId())) {
                return updateGame(games);
            }
        } else {
            Long latestId = idList.get(0);
            games.setId(latestId != null ? latestId + 1 : 1);

            Date now = Calendar.getInstance().getTime();
            //SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            games.setCreatedAt(now);

            ApiFuture<WriteResult> gameSaveFuture = firestore.collection(COLLECTION).document(String.valueOf(games.getId())).set(games);
            try {
                logger.info(games.getName() + " SAVED AT " + gameSaveFuture.get().getUpdateTime());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("FAILED TO SAVE GAME " + e.getMessage());
            }
        }
        return games;
    }

    @Override
    public Optional<Games> getGame(Long id) throws ExecutionException, InterruptedException {
        return Optional.of(Objects.requireNonNull(firestore.collection(COLLECTION).document(String.valueOf(id)).get().get().toObject(Games.class)));
    }

    @Override
    public Games updateGame(Games games) {
        ApiFuture<WriteResult> update = firestore.collection(COLLECTION).document(String.valueOf(games.getId())).set(games);
        try {
            logger.info(games.getName() + " SAVED AT " + update.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FAILED TO SAVE GAME " + e.getMessage());
        }
        return games;
    }

    @Override
    public boolean deleteGame(Long id) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> update = firestore.collection(COLLECTION).document(String.valueOf(id)).delete();
        try {
            logger.info(id + " DELETED AT " + update.get().getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            logger.error("FAILED TO DELETE GAME " + e.getMessage());
        }
        return update.get().getUpdateTime() != null;
    }

    @Override
    public List<Games> getAllGamesFirestore() {
        List<Games> gamesList = new ArrayList<>();
        ApiFuture<QuerySnapshot> qs = firestore.collection(COLLECTION).get();
        try {
            for (DocumentSnapshot ds : qs.get()) {
                Games game = ds.toObject(Games.class);
                gamesList.add(game);

                assert game != null;
                logger.info(game.getName() + " RETRIEVED ");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return gamesList;
    }
}
