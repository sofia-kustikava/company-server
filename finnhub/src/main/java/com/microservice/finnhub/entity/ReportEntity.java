package com.microservice.finnhub.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reports")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "companies_id")
    private CompanyEntity companies;

    @Column(name = "unit")
    private String unit;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private Long value;

    @Column(name = "concept")
    private String concept;
}
