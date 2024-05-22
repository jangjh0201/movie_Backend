package com.dodatabase.movie_backend.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.dodatabase.movie_backend.domain.Movie.MovieResponse;

public class JsonParserTest {

    @Test
    public void parseResponse() {
        // given
        String jsonResponse = """
                {
                    "Data": [
                        {
                            "Result": [
                                {
                                    "title": "스타워즈",
                                    "prodYear": 1977,
                                    "genre": "SF",
                                    "nation": "미국",
                                    "runtime": 121,
                                    "directors": {
                                        "director": [
                                            { "directorNm": "조지 루카스" }
                                        ]
                                    },
                                    "actors": {
                                        "actor": [
                                            { "actorNm": "한 솔로" }
                                        ]
                                    }
                                }
                            ]
                        }
                    ]
                }
                """;

        // when
        List<MovieResponse> movieResponses = JsonParser.parseResponse(jsonResponse);

        // then
        assertNotNull(movieResponses);
        assertEquals(1, movieResponses.size());
        MovieResponse movieResponse = movieResponses.get(0);
        assertEquals("스타워즈", movieResponse.getTitle());
        assertEquals(1977, movieResponse.getProdYear());
        assertEquals("SF", movieResponse.getGenre());
        assertEquals("미국", movieResponse.getNation());
        assertEquals(121, movieResponse.getRuntime());
        assertEquals("조지 루카스", movieResponse.getDirector());
        assertEquals("한 솔로", movieResponse.getActor());
    }
}