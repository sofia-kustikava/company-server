package com.example.companyserver.entity;

import com.example.companyserver.entity.CompanyEntity;
import com.example.companyserver.entity.UserStatus;
import com.example.companyserver.entity.UserSubscriptionEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_name")
    private  String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "date_updated")
    private LocalDate updated;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @Column(name = "password")
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSubscriptionEntity subscription;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private List<RoleEntity> roles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_companies",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "companies_id")
    )
    private List<CompanyEntity> companies = new ArrayList<>();

}
