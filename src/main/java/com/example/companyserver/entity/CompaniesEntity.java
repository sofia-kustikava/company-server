package com.example.companyserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class CompaniesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "description")
    private String description;

    @Column(name = "display_symbol")
    private String displaySymbol;

    @Column(name = "figi")
    private String figi;

    @Column(name = "mic")
    private String mic;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "type")
    private String type;
}
