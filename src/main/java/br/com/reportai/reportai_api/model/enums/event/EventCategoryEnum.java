package br.com.reportai.reportai_api.model.enums.event;

import lombok.Getter;

@Getter
public enum EventCategoryEnum {

    BURACO_VIA("Buraco ou irregularidade no asfalto"),
    ILUMINACAO_PUBLICA("Poste apagado ou fiação exposta"),
    SANEAMENTO_ESGOTO("Vazamento de água ou esgoto a céu aberto"),
    LIXO_ENTULHO("Descarte irregular de resíduos"),
    VANDALISMO_PATRIMONIO("Pichação ou dano a monumento histórico"),
    ARVORE_RISCO("Árvore com risco de queda ou obstruindo via"),
    SINALIZACAO_TRANSITO("Placa danificada ou semáforo estragado"),
    SEGURANCA_PREVENTIVA("Atividade suspeita ou falta de policiamento"),
    ACESSIBILIDADE_RAMPA("Calçada danificada ou falta de rampa de acesso"),
    OBSTRUCAO_CALCADA("Calçada bloqueada por materiais ou veículos");

    private final String drescription;

    EventCategoryEnum(String description) {
        this.drescription = description;
    }
}



