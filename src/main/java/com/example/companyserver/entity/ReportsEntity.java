package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
public class ReportsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "companies_id")
    private CompaniesEntity companies;

    @Column(name = "unit")
    private String unit;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private Long value;

    @Column(name = "concept")
    private String concept;
}
