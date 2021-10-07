package com.example.companyserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String subscription;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @OneToMany(mappedBy = "subscription")
    private List<UserSubscriptionEntity> userSubscriptionEntities;
}
