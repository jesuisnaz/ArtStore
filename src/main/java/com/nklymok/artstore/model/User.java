package com.nklymok.artstore.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Length(min = 3, max = 30)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 30)
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 6)
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}
