package org.zerock.w2.controller;

import lombok.extern.log4j.Log4j2;
import org.zerock.w2.dto.TodoDTO;
import org.zerock.w2.service.TodoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Log4j2
@WebServlet(name = "TodoReadController", urlPatterns = "/todo/read")
public class TodoReadController extends HttpServlet {
  TodoService todoService = TodoService.INSTANCE;
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try{
      Long tno = Long.parseLong(req.getParameter("tno"));

      TodoDTO todoDTO = todoService.get(tno);
      //모델 담기
      req.setAttribute("dto",todoDTO);

      //쿠키 찾기
      Cookie viewTodoCookie = findCookie(req.getCookies(), "viewTodos");
      String todoListStr = viewTodoCookie.getValue();
      boolean exist = false;

      //해당 tno가 todoListStr에 존재하는지 check
      if(todoListStr != null && todoListStr.indexOf(tno+"-") >= 0)
        //indexOf는 해당문자열이 몇번째 인덱스에서 시작되는지 return한다. 없으면 -1 return
        exist = true;

      log.info("exist: " + exist);

      //해당 tno가 존재하지 않는다면 기존의 문자열에 더해서 새로운 쿠키를 생성하여 담아준다.
      if(!exist){
        todoListStr += tno+"-";
        viewTodoCookie.setValue(todoListStr);
        viewTodoCookie.setMaxAge(60*60*24);
        viewTodoCookie.setPath("/");
        resp.addCookie(viewTodoCookie);
      }

      req.getRequestDispatcher("/WEB-INF/todo/read.jsp").forward(req,resp);
    }catch (Exception e){
      log.error(e.getMessage());
      throw new ServletException("read error");
    }
  }

  private Cookie findCookie(Cookie[] cookies, String cookieName){
    Cookie targetCookie = null;

    //Request에 쿠키가 있다면 해당 쿠키 찾기
    if(cookies != null && cookies.length > 0){
      for(Cookie ck : cookies){
        if(ck.getName().equals(cookieName)){
          targetCookie = ck;
          break;
        }
      }
    }
    //Request에 쿠키가 없다면 생성, 이떄 유효 기간은 24시간으로 설정
    if(targetCookie == null){
      targetCookie = new Cookie(cookieName,"");
      targetCookie.setPath("/");
      targetCookie.setMaxAge(60*60*24); // 초단위 이므로 24시간으로 설정
    }
    return targetCookie;
  }

}
