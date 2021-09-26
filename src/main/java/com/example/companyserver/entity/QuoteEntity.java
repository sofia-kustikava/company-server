package com.example.companyserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quote")
public class QuoteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "companies_id")
    private CompaniesEntity companies;

    @Column(name = "current_price")
    private float currentPrice;

    @Column(name = "change")
    private float change;

    @Column(name = "percent_change")
    private float percentChange;

    @Column(name = "high_price")
    private float highPrice;

    @Column(name = "low_price")
    private float lowPrice;

    @Column(name = "open_price")
    private float openPrice;

    @Column(name = "close_price")
    private float closePrice;
}
