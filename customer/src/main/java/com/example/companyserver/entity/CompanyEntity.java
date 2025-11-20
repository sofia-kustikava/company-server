package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @ManyToMany(mappedBy = "companies")
    private List<UserEntity> users;

}
