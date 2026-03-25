package br.com.reportai.reportai_api.model.enums.user;


import lombok.Getter;

@Getter
public enum RoleUserEnum
{
    ROLE_ADMIN("Usuário com permissão de admistrador"),
    ROLE_USER ("Usuário com permissão comum");

    private final String description;

    RoleUserEnum(String description){
        this.description = description;
    }

}
