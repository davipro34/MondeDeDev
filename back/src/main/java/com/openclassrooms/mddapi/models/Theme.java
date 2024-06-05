package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "themes")
@Data
@Builder
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    /**
     * The list of users subscribed to this theme.
     */
    @Builder.Default
    @ManyToMany(mappedBy = "themes")
    private List<User> users = new ArrayList<>();

    /**
     * Returns a list of user IDs that are subscribed to this theme.
     *
     * @return a list of user IDs
     */
    public List<Long> getUserds() {
    return users.stream().map(user -> user.getId()).collect(Collectors.toList());
    }
}

