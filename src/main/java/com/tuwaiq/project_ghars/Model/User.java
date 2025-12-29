package com.tuwaiq.project_ghars.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min = 2, max = 20)
    @Column(columnDefinition = "varchar(20) not null")
    private String name;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 10)
    @Column(columnDefinition = "varchar(10) not null unique")
    private String username;

    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @NotEmpty
    @Pattern(regexp = "^05\\d{8}$")
    @Column(columnDefinition = "varchar(10) not null")
    private String phoneNumber;

    @NotEmpty
    @Pattern(regexp = "^(FARMER|CUSTOMER|DRIVER|ADMIN)$")
    @Column(columnDefinition = "varchar(10) not null")
    private String role;

    @Column(columnDefinition = "datetime")
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Farmer farmer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Customer customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Driver driver;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address;

    @ManyToMany(mappedBy = "users")
    private Set<Event> events ;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
