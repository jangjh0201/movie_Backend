package com.dodatabase.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.dodatabase.demo.domain.movie.Movie;
import com.dodatabase.demo.domain.movie.MovieResponse;
import com.dodatabase.demo.repository.MovieCacheMemory;
import com.dodatabase.demo.service.FavoriteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(FavoriteController.class)
public class FavoriteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FavoriteService favoriteService;

  @MockBean
  private MovieCacheMemory movieCacheMemory;

  @MockBean
  private ModelMapper modelMapper;

  private ObjectMapper objectMapper = new ObjectMapper();

  private Movie movie;
  private MovieResponse movieResponse;

  @BeforeEach
  void setUp() {
    movie = Movie.builder(1L)
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();

    movieResponse = MovieResponse.builder()
        .title("스타워즈")
        .prodYear(1977)
        .genre("SF")
        .nation("미국")
        .runtime(121)
        .director("조지 루카스")
        .actor("한 솔로")
        .build();
  }

  @Test
  public void movieListTest() throws Exception {
    mockMvc.perform(get("/v1/favorites"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
        .andExpect(view().name("html/favorite/list"))
        .andDo(print());
  }

  @Test
  void createMovieTest_Success() throws Exception {
    when(movieCacheMemory.getMovieById(any(Long.class))).thenReturn(movieResponse);
    when(favoriteService.findByTitle(any())).thenReturn(Optional.empty());
    when(modelMapper.map(any(MovieResponse.class), eq(Movie.class))).thenReturn(movie);

    mockMvc.perform(post("/v1/favorites")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(1L)))
        .andExpect(status().isCreated());
  }

  @Test
  void createMovieTest_AlreadyExists() throws Exception {
    when(movieCacheMemory.getMovieById(any(Long.class))).thenReturn(movieResponse);
    when(favoriteService.findByTitle(any())).thenReturn(Optional.of(movie));

    mockMvc.perform(post("/v1/favorites")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(1L)))
        .andExpect(status().isConflict());
  }

  @Test
  void createMovieTest_NotFound() throws Exception {
    when(movieCacheMemory.getMovieById(any(Long.class))).thenReturn(null);

    mockMvc.perform(post("/v1/favorites")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(1L)))
        .andExpect(status().isNotFound());
  }
}