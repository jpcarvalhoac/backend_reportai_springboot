package br.com.reportai.reportai_api.model.enums.user;

import lombok.Getter;

@Getter
public enum LevelRankUser
{
    BRONZE("terceiro maior raking"),
    SILVER ("Segundo maior raking"),
    GOLD("Maior ranking");

    private final String description;

    LevelRankUser(String description){
        this.description = description;
    }

}
