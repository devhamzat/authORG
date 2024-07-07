package org.devhamzat.authorg.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String orgId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    private String description;

    @ManyToMany(mappedBy = "organization")
    private Set<User> users;


}
