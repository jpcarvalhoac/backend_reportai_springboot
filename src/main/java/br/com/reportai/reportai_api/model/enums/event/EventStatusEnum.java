package br.com.reportai.reportai_api.model.enums.event;

import lombok.Getter;


@Getter
public enum EventStatusEnum {
    OPEN("O evento foi criado e aguarda análise"),
    IN_PROGRESS("O evento está sendo resolvido pela equipe responsável"),
    RESOLVED("O evento foi concluído e o problema resolvido"); // Muito usado no lugar de FINISH

    private final String description;

    EventStatusEnum(String description) {
        this.description = description;
    }
}
