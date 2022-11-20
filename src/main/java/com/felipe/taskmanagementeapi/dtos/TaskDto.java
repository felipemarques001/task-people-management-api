package com.felipe.taskmanagementeapi.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felipe.taskmanagementeapi.entities.TaskEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TaskDto {

    private Integer id;

    private LocalDateTime creationDate;

    @NotNull(message = "Finalization date cannot be empty!")
    private LocalDateTime finalizationDate;

    @NotBlank(message = "Title cannot be empty!")
    private String title;

    @NotBlank(message = "Description cannot be empty!")
    private String description;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Inform the team id in which this employee is working")
    private Integer teamId;

    private Boolean done;

    public TaskDto(TaskEntity taskEntity) {
        id = taskEntity.getId();
        creationDate = taskEntity.getCreationDate();
        finalizationDate = taskEntity.getFinalizationDate();
        title = taskEntity.getTitle();
        description = taskEntity.getDescription();
        done = taskEntity.getDone();
    }
}
