package com.lcwd.electronic.store.dtos;

import lombok.*;

import javax.persistence.Id;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleDto {


    private String roleId;
    private String roleName;

}
