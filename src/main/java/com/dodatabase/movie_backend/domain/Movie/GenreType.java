package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;

@Getter
public enum GenreType {
    드라마("1"),
    판타지("2"),
    서부("3"),
    공포("4"),
    로맨스("5"),
    모험("6"),
    스릴러("7"),
    느와르("8"),
    컬트("9"),
    다큐멘터리("10"),
    코미디("11"),
    가족("12"),
    미스터리("13"),
    전쟁("14"),
    애니메이션("15"),
    범죄("16"),
    뮤지컬("17"),
    SF("18"),
    액션("19"),
    무협("20"),
    에로("21"),
    서스펜스("22"),
    서사("23"),
    블랙코미디("24"),
    실험("25"),
    영화카툰("26"),
    영화음악("27"),
    영화패러디포스터("28");

    private String code;

    GenreType(String code) {
        this.code = code;
    }
}
