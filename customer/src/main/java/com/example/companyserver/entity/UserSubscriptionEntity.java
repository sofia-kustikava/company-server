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
@Table(name = "users_subscriptions")
public class UserSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "date_start")
    private LocalDate dateStart;

    @Column(name = "date_end")
    private LocalDate dateEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_status")
    private SubscriptionStatus subscriptionStatus;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "subscriptions_id", referencedColumnName = "id")
    private SubscriptionEntity subscription;

}
