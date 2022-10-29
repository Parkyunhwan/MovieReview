package org.yunhwan.moviereview.repository.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.yunhwan.moviereview.entity.QMovie;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
public enum SearchMovieCondition {
    TITLE_CONDITION("t", QMovie.movie.title::contains),
    RATING_CONDITION("r", QMovie.movie.rating::contains),
    COUNTRY_CONDITION("c", QMovie.movie.country::contains),
    ;

    private final String type;
    private final Function<String, BooleanExpression> condition;

    public static SearchMovieCondition getType(String type) {
        return Arrays.stream(SearchMovieCondition.values())
                .filter((value) -> value.type.equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 검색 타입입니다."));
    }

    public BooleanExpression checkCondition(String keyword) {
        return this.condition.apply(keyword);
    }

    public static List<String> splitTypes(String types) {
        if (StringUtils.hasText(types)) {
            return List.of(types.split(""));
        }
        return Collections.emptyList();
    }
}
