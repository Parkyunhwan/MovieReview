package org.yunhwan.moviereview.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.util.QueryDslUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.yunhwan.moviereview.entity.QMovie.movie;
import static org.yunhwan.moviereview.entity.QMovieImage.movieImage;
import static org.yunhwan.moviereview.entity.QReview.review;

@Log4j2
public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {

    private final JPAQueryFactory queryFactory;

    public SearchMovieRepositoryImpl(EntityManager em) {
        super(Movie.class);
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<MovieSearchResponseDTO> searchPageBy(Long cursorId, String type, String keyword, Pageable pageable) {
        List<MovieSearchResponseDTO> result = queryFactory
                .select(Projections.constructor(MovieSearchResponseDTO.class,
                        movie, movieImage, review.grade.avg().coalesce(0.0).as("avg"), review.count().as("reviewCnt"))
                )
                .from(movie)
                .leftJoin(movieImage)
                .on(movie.eq(movieImage.movie))
                .leftJoin(review)
                .on(movie.eq(review.movie))
                .where(
                        idGt(cursorId),
                        searchCondition(type, keyword)
                )
                .groupBy(movie)
                .orderBy(getOrderSpecifier(pageable.getSort()))
                //.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(
                result
                , pageable
                , pageable.getPageSize()
        );
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {
        return sort.stream()
                .map(order ->
                        QueryDslUtil.getOrderSpecifier(direction(order), movie, order.getProperty())
                ).toArray(OrderSpecifier[]::new);
    }

    private static Order direction(Sort.Order order) {
        return order.isAscending() ? Order.ASC : Order.DESC;
    }

    private static BooleanBuilder searchCondition(String type, String keyword) {
        BooleanBuilder conditonBuilder = new BooleanBuilder();
        List<String> types = SearchMovieCondition.splitTypes(type);
        types.stream()
                .map(SearchMovieCondition::getType)
                .map(searchMovieCondition -> searchMovieCondition.checkCondition(keyword))
                .forEach(conditonBuilder::or);
        return conditonBuilder;
    }

    private static BooleanExpression idGt(Long id) {
        Long movieId = Optional.ofNullable(id)
                .orElseThrow(() -> new IllegalArgumentException("비교할 movie Id값이 필요합니다."));
        return movie.id.gt(movieId);
    }
}
