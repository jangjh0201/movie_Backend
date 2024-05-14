package com.dodatabase.movie_backend.domain.Movie;

import lombok.Getter;

@Getter
public enum GenreType {
    드라마("드라마"), 첩보("첩보"), 옴니버스("옴니버스"), 뮤직("뮤직"),
    동성애("동성애"), 로드무비("로드무비"), 아동("아동"), 하이틴("하이틴(고교)"),
    전기("전기"), 청춘영화("청춘영화"), 재난("재난"), 문예("문예"),
    연쇄극("연쇄극"), 신파("신파"), 활극("활극"), 반공("반공/분단"),
    군사("군사"), 계몽("계몽"), 사회물("사회물(경향)"), 스포츠("스포츠"),
    합작("합작(번안물)"), 종교("종교"), 무협("무협"), 미스터리("미스터리"),
    SF("SF"), 코메디("코메디"), 느와르("느와르"), 액션("액션"),
    범죄("범죄"), 어드벤처("어드벤처"), 가족("가족"), 에로("에로"),
    멜로("멜로/로맨스"), 공포("공포"), 뮤지컬("뮤지컬"), 시대극("시대극/사극"),
    실험("실험"), 스릴러("스릴러"), 서부("서부"), 전쟁("전쟁"),
    갱스터("갱스터"), 판타지("판타지"), 해양액션("해양액션");

    private String code;

    GenreType(String code) {
        this.code = code;
    }
}
