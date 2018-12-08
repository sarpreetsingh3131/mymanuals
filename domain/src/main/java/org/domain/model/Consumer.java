package org.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Consumer {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String name;

    @NonNull
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    @Column(nullable = false)
    private String password;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    @Column(nullable = false)
    private Boolean subscription;

    @NonNull
    @ManyToMany
    @JsonIgnore
    private List<Product> products;
}
