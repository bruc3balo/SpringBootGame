package com.angular.spring.demo.globals;

import com.angular.spring.demo.api.repo.GamesRepo;
import org.springframework.stereotype.Component;

@Component
public class GlobalRepo {

    public static GamesRepo gamesRepo;

}
