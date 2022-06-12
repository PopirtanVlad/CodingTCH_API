package com.example.licenta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "users")
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "ENABLED")
    private Boolean enabled;

    private String provider;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<Submission> submissions;
}
