package com.felipe.taskmanagementeapi.repositories;

import com.felipe.taskmanagementeapi.Repositories.TaskRepository;
import com.felipe.taskmanagementeapi.Repositories.TeamRepository;
import com.felipe.taskmanagementeapi.entities.TaskEntity;
import com.felipe.taskmanagementeapi.entities.TeamEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    private static final String TITLE_TASK1 = "Nome da terefa teste 1";
    private static final String DESCRIPTION_TASK1 = "Descrição da tarefa teste 1";
    private static final Boolean DONE_TASK1 = false;
    private static final LocalDateTime FINALIZATION_DATE_TASK1 = LocalDateTime.of(LocalDateTime.now().getYear(), 12, 12, 0, 0);

    private static final String TITLE_TASK2 = "Nome da terefa teste 2";
    private static final String DESCRIPTION_TASK2 = "Descrição da tarefa teste 2";
    private static final Boolean DONE_TASK2 = false;
    private static final LocalDateTime FINALIZATION_DATE_TASK2 = LocalDateTime.of(LocalDateTime.now().getYear(), 12, 12, 0, 0);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TeamRepository teamRepository;

    private TaskEntity taskEntity;
    private TeamEntity teamEntity;

    @BeforeEach
    void setup() {
        teamEntity = new TeamEntity();
        teamEntity.setName("Nome do time_teste");
        teamRepository.save(teamEntity);

        taskEntity = new TaskEntity();
        taskEntity.setTitle(TITLE_TASK1);
        taskEntity.setDescription(DESCRIPTION_TASK1);
        taskEntity.setDone(DONE_TASK1);
        taskEntity.setFinalizationDate(FINALIZATION_DATE_TASK1);
        taskEntity.setTeam(teamEntity);
    }

    @DisplayName("Unit test for save method")
    @Test
    public void givenTaskEntity_whenSave_thenSavedTask(){
        // given
        // code in setup method

        // when
        TaskEntity savedTask = taskRepository.save(taskEntity);

        // then
        assertNotNull(savedTask.getCreationDate());
        assertNotNull(savedTask.getId());
        assertEquals(TITLE_TASK1, savedTask.getTitle());
        assertEquals(DESCRIPTION_TASK1, savedTask.getDescription());
        assertEquals(DONE_TASK1, savedTask.getDone());
        assertEquals(FINALIZATION_DATE_TASK1, savedTask.getFinalizationDate());
        assertEquals(teamEntity, savedTask.getTeam());
    }

    @DisplayName("Unit test for findAll method")
    @Test
    public void givenSavedTasks_whenFindAll_thenReturnTaskList() {
        // given
        TaskEntity taskEntity2 = new TaskEntity();
        taskEntity2.setTitle(TITLE_TASK2);
        taskEntity2.setDescription(DESCRIPTION_TASK2);
        taskEntity2.setDone(DONE_TASK2);
        taskEntity2.setFinalizationDate(FINALIZATION_DATE_TASK2);
        taskEntity2.setTeam(teamEntity);

        taskRepository.saveAll(List.of(taskEntity, taskEntity2));

        // when
        List<TaskEntity> list = taskRepository.findAll();

        // then
        assertNotNull(list);
        assertEquals(2, list.size());

        assertNotNull(list.get(0).getId());
        assertNotNull(list.get(0).getCreationDate());
        assertEquals(TITLE_TASK1, list.get(0).getTitle());
        assertEquals(DESCRIPTION_TASK1, list.get(0).getDescription());
        assertEquals(DONE_TASK1, list.get(0).getDone());
        assertEquals(FINALIZATION_DATE_TASK1, list.get(0).getFinalizationDate());
        assertEquals(teamEntity, list.get(0).getTeam());

        assertNotNull(list.get(1).getId());
        assertNotNull(list.get(1).getCreationDate());
        assertEquals(TITLE_TASK2, list.get(1).getTitle());
        assertEquals(DESCRIPTION_TASK2, list.get(1).getDescription());
        assertEquals(DONE_TASK2, list.get(1).getDone());
        assertEquals(FINALIZATION_DATE_TASK2, list.get(1).getFinalizationDate());
        assertEquals(teamEntity, list.get(1).getTeam());
    }

    @DisplayName("Unit test for findById method")
    @Test
    public void givenTaskId_whenFindById_thenReturnOptionalTask() {
        // given
        taskRepository.save(taskEntity);

        // when
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskEntity.getId());

        // given
        assertNotNull(optionalTask);
        assertFalse(optionalTask.isEmpty());
        assertNotNull(optionalTask.get().getId());
        assertNotNull(optionalTask.get().getCreationDate());
        assertEquals(TITLE_TASK1, optionalTask.get().getTitle());
        assertEquals(DESCRIPTION_TASK1, optionalTask.get().getDescription());
        assertEquals(DONE_TASK1, optionalTask.get().getDone());
        assertEquals(FINALIZATION_DATE_TASK1, optionalTask.get().getFinalizationDate());
        assertEquals(teamEntity, optionalTask.get().getTeam());
    }

    @DisplayName("Unit test for flow to update a TaskEntity")
    @Test
    public void givenNewTask_whenUpdate_thenUpdatedTask() {
        // given
        taskRepository.save(taskEntity);
        TaskEntity savedTask = taskRepository.findById(taskEntity.getId()).get();
        savedTask.setTitle(TITLE_TASK2);
        savedTask.setDescription(DESCRIPTION_TASK2);
        savedTask.setDone(DONE_TASK2);
        savedTask.setFinalizationDate(FINALIZATION_DATE_TASK2);

        // when
        TaskEntity updatedTask = taskRepository.save(savedTask);


        // given
        assertNotNull(updatedTask);
        assertNotNull(updatedTask.getId());
        assertNotNull(updatedTask.getCreationDate());
        assertEquals(TITLE_TASK2, updatedTask.getTitle());
        assertEquals(DESCRIPTION_TASK2, updatedTask.getDescription());
        assertEquals(DONE_TASK2, updatedTask.getDone());
        assertEquals(FINALIZATION_DATE_TASK2, updatedTask.getFinalizationDate());
        assertEquals(teamEntity, updatedTask.getTeam());
    }

    @DisplayName("Unit test for delete method")
    @Test
    public void givenTaskId_whenDelete_thenDeleteTask() {
        // given
        taskRepository.save(taskEntity);

        // when
        taskRepository.deleteById(taskEntity.getId());
        Optional<TaskEntity> optionalTask = taskRepository.findById(taskEntity.getId());

        // then
        assertTrue(optionalTask.isEmpty());
    }
}
