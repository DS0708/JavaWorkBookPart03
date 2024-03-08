package org.zerock.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jdbcex.dao.TodoDAO;
import org.zerock.jdbcex.domain.TodoVO;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {
  private TodoDAO todoDAO;

  @BeforeEach
  public void ready(){
    todoDAO = new TodoDAO();
  }

  @Test
  public void testTime() throws Exception{
    System.out.println(todoDAO.getTime());
    System.out.println(todoDAO.getTime2());
  }

  @Test
  public void testInsert() throws Exception{
    TodoVO todoVO = TodoVO.builder()
            .title("Sample Title")
            .dueDate(LocalDate.of(2024,2,22))
            .build();

    todoDAO.insert(todoVO);
  }

  @Test
  public void testList() throws Exception{
    List<TodoVO> list = todoDAO.selectAll();
    list.forEach(vo -> System.out.println(vo));
  }

  @Test
  public void testSelectOne() throws Exception{
    long tno = 7;

    TodoVO vo = todoDAO.selectOne(tno);

    System.out.println(vo);
  }

  @Test
  public void testDelete() throws Exception{
    todoDAO.deleteOne(7);
  }

  @Test
  public void testUpdate() throws Exception{
    TodoVO vo = TodoVO.builder()
            .tno(3l)
            .title("Sample Title....")
            .dueDate(LocalDate.of(2024,02,23))
            .finished(true)
            .build();
    todoDAO.updateOne(vo);
  }
}