package com.angular.spring.demo.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Component
public class Games {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "created_at")
    private Date createdAt;

    @JsonProperty(value = "description")
    private String description;

    @JsonProperty(value = "name")
    private String name;


    public Games() {

    }

    public Games(Long id, Date createdAt, String description, String name) {
        this.id = id;
        this.createdAt = createdAt;
        this.description = description;
        this.name = name;
    }
}
