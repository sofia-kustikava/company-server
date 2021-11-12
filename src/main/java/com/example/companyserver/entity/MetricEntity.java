package com.example.companyserver.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "metrics")
public class MetricEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companies_id")
    private CompanyEntity companies;

    @Column(name = "week_high")
    private Double weekHigh;

    @Column(name = "week_high_date")
    private LocalDate weekHighDate;

    @Column(name = "week_low")
    private Double weekLow;

    @Column(name = "week_low_date")
    private LocalDate weekLowDate;

    @Column(name = "week_price_daily")
    private Double weekPriceDaily;
}
