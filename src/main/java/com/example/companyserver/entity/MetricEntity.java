package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
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
    private float weekHigh;

    @Column(name = "week_high_date")
    private LocalDate weekHighDate;

    @Column(name = "week_low")
    private float weekLow;

    @Column(name = "week_low_date")
    private LocalDate weekLowDate;

    @Column(name = "week_price_daily")
    private float weekPriceDaily;
}
