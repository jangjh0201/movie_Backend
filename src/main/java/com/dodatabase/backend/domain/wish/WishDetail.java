package com.dodatabase.backend.domain.wish;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishDetail {
  private List<String> posters;
  private String plot;

  @Builder(builderClassName = "WishDetailBuilder", builderMethodName = "wishDetailBuilder")
  public WishDetail(List<String> posters, String plot) {
    this.posters = posters;
    this.plot = plot;
  }

  public static WishDetailBuilder builder() {
    return wishDetailBuilder();
  }
}
