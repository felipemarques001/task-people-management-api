package com.felipe.taskmanagementeapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_ENTITY")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private Set<TaskEntity> tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
    private Set<EmployeeEntity> employees;
}
