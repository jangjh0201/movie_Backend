package com.dodatabase.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.dodatabase.demo.domain.wish.WishRequest;
import com.dodatabase.demo.domain.wish.WishResponse;
import com.dodatabase.demo.service.WishService;
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

@WebMvcTest(WishController.class)
public class WishControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private WishService wishService;

  @MockBean
  private ModelMapper modelMapper;

  private ObjectMapper objectMapper = new ObjectMapper();

  private WishRequest wishRequest;

  @BeforeEach
  void initialize() {
    wishRequest = WishRequest.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build();
  }

  @Test
  void createWishTest_Success() throws Exception {

    when(wishService.findById(any())).thenReturn(Optional.empty());

    mockMvc.perform(post("/v1/wish")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(wishRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  void createWishTest_AlreadyExists() throws Exception {
    when(wishService.findById("F10538")).thenReturn(Optional.of(WishResponse.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build()));

    mockMvc.perform(post("/v1/wish")
        .contentType("application/json")
        .content(objectMapper.writeValueAsString(wishRequest)))
        .andExpect(status().isConflict());
  }

  @Test
  void removeWishTest_Success() throws Exception {
    when(wishService.findById("F10538")).thenReturn(Optional.of(WishResponse.builder()
        .id("F10538")
        .title("스타워즈 에피소드 3 : 시스의 복수")
        .prodYear(2005)
        .genre("액션,SF,어드벤처,판타지")
        .nation("미국")
        .runtime(139)
        .director("조지 루카스")
        .actor("이완 맥그리거")
        .build()));

    mockMvc.perform(delete("/v1/wish/F10538")
        .contentType("application/json"))
        .andExpect(status().isOk());
  }
}
