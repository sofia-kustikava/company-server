package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "metrics")
public class MetricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "companies_id")
    private CompanyEntity companies;

    @Column(name = "week_high")
    private String weekHigh;

    @Column(name = "week_high_date")
    private String weekHighDate;

    @Column(name = "week_low")
    private String weekLow;

    @Column(name = "week_low_date")
    private String weekLowDate;

    @Column(name = "week_price_daily")
    private String weekPriceDaily;
}
