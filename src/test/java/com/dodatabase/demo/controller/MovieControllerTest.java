package com.dodatabase.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.dodatabase.demo.domain.movie.MovieRequest;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.service.MovieApiService;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MovieApiService movieApiService;

  private MovieRequest movieRequest;
  private MovieResponse movieResponse;
  private List<MovieResponse> movieResponseList;

  @BeforeEach
  void initialize() {
    movieRequest = MovieRequest.builder()
        .nation("미국")
        .genre("SF")
        .title("스타워즈")
        .build();

    movieResponse = MovieResponse.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build();

    movieResponseList = Collections.singletonList(movieResponse);
  }

  @Test
  public void searchApiGetTest() throws Exception {
    mockMvc.perform(get("/v1/movies"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("html/movie/list"))
        .andDo(print());
  }

  @Test
  public void searchApiPostTest() throws Exception {
    // given
    given(movieApiService.findByKeyword(any(MovieRequest.class))).willReturn(movieResponseList);

    // when
    ResultActions resultActions = mockMvc.perform(post("/v1/movies")
        .param("nation", "미국")
        .param("genre", "SF")
        .param("title", "스타워즈")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED));

    System.out.println("movieRsponseList : " + movieResponseList);
    // then
    resultActions
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("movies"))
        .andExpect(model().attribute("movies", movieResponseList))
        .andExpect(view().name("html/movie/list"))
        .andDo(print());
  }
}
