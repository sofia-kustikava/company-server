package com.example.companyserver.entity;

import com.example.companyserver.entity.UserEntity;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> owners = new ArrayList<>();
}
