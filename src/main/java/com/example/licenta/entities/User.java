package com.example.licenta.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID userId;

    @Column(name = "PROVIDER_USER_ID")
    private String providerUserId;

    @Column(name = "USER_EMAIL")
    private String email;

    @Column(name = "DISPLAY_NAME")
    private String displayName;

    @Column(name = "USER_PASSWORD")
    private String password;

    @Column(name = "ENABLED", columnDefinition = "BIT", length = 1)
    private boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "USER_ROLE", joinColumns = {@JoinColumn(name = "USER_ID")}, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;
}
