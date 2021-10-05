package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class CompanyEntity {
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

    @ManyToMany(mappedBy = "companies")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "companies")
    private List<MetricEntity> metrics;

    @OneToMany(mappedBy = "companies")
    private List<QuoteEntity> quotes;

    @OneToMany(mappedBy = "companies")
    private List<ReportEntity> reports;
}
