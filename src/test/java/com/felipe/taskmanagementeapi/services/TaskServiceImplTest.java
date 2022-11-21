package com.felipe.taskmanagementeapi.services;

import com.felipe.taskmanagementeapi.Repositories.TaskRepository;
import com.felipe.taskmanagementeapi.Repositories.TeamRepository;
import com.felipe.taskmanagementeapi.dtos.TaskDto;
import com.felipe.taskmanagementeapi.entities.TaskEntity;
import com.felipe.taskmanagementeapi.entities.TeamEntity;
import com.felipe.taskmanagementeapi.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskServiceImplTest {

    private static final Integer ID_TASK = 1;
    private static final String TITLE_TASK = "Nome da terefa teste 1";
    private static final String DESCRIPTION_TASK = "Descrição da tarefa teste 1";
    private static final Boolean DONE_TASK = false;
    private static final LocalDateTime CREATION_DATE_TASK = LocalDateTime.now();
    private static final LocalDateTime FINALIZATION_DATE_TASK = LocalDateTime.of(LocalDateTime.now().getYear(), 12, 31, 0, 0);

    private static final String NAME_TEAM = "Departamento de TI";
    private static final Integer ID_TEAM = 1;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TeamRepository teamRepository;

    private TaskEntity savedTask;
    private TaskDto taskDto;
    private TeamEntity savedTeam;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        savedTeam = new TeamEntity();
        savedTeam.setId(ID_TEAM);
        savedTeam.setName(NAME_TEAM);

        savedTask = new TaskEntity();
        savedTask.setId(ID_TASK);
        savedTask.setTitle(TITLE_TASK);
        savedTask.setDescription(DESCRIPTION_TASK);
        savedTask.setCreationDate(CREATION_DATE_TASK);
        savedTask.setFinalizationDate(FINALIZATION_DATE_TASK);
        savedTask.setDone(DONE_TASK);
        savedTask.setTeam(savedTeam);

        taskDto = new TaskDto();
        taskDto.setFinalizationDate(FINALIZATION_DATE_TASK);
        taskDto.setTitle(TITLE_TASK);
        taskDto.setDescription(DESCRIPTION_TASK);
        taskDto.setTeamId(ID_TEAM);
    }

    @DisplayName("Unit test for createTask method")
    @Test
    void givenTaskDto_whenCreateTask_thenReturnSavedTask() {
        // given
        Mockito.when(teamRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(savedTeam));
        // As this save return is not being stored in the createTask method, it is okay to let Mockito return the same savedTask field.
        Mockito.when(taskRepository.save(Mockito.any(TaskEntity.class))).thenReturn(savedTask);

        // when
        TaskDto response = taskService.createTask(taskDto);

        // then
        assertEquals(ID_TASK, response.getId());
        assertEquals(TITLE_TASK, response.getTitle());
        assertEquals(DESCRIPTION_TASK, response.getDescription());
        assertEquals(CREATION_DATE_TASK, response.getCreationDate());
        assertEquals(FINALIZATION_DATE_TASK, response.getFinalizationDate());
        assertEquals(DONE_TASK, response.getDone());
        assertEquals(ID_TEAM, response.getTeamId());
    }
}