package org.yunhwan.moviereview.repository.search;

import static org.yunhwan.moviereview.entity.QMovie.*;
import static org.yunhwan.moviereview.entity.QMovieImage.*;
import static org.yunhwan.moviereview.entity.QReview.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.yunhwan.moviereview.dto.MovieSearchResponseDTO;
import org.yunhwan.moviereview.entity.*;

import java.util.List;

@Log4j2
public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {

    private final JPAQueryFactory queryFactory;

    public SearchMovieRepositoryImpl(EntityManager em) {
        super(Movie.class);
        queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Movie Search1() {
        log.info("SEARCH 1");

        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMember member = QMember.member;

        JPQLQuery<Movie> jpqlQuery = from(movie);
        jpqlQuery.leftJoin(review).on(review.movie.eq(movie));
        jpqlQuery.leftJoin(member).on(review.member.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(movie, review.grade.avg(), review.count());

        tuple.groupBy(movie);

        List<Tuple> result = tuple.fetch();
        log.info("result" + result);
        return null;
    }

    @Override
    public Page<MovieSearchResponseDTO> searchPage(String type, String keyword, Pageable pageable) {
        List<MovieSearchResponseDTO> result = queryFactory
                .select(Projections.constructor(MovieSearchResponseDTO.class,
                        movie, movieImage, review.grade.avg().coalesce(0.0).as("avg"), review.count().as("reviewCnt"))
                )
                .from(movie)
                .leftJoin(movieImage)
                .on(movie.eq(movieImage.movie))
                .leftJoin(review)
                .on(movie.eq(review.movie))
                .where(mnoGreatherThanZero()
                        .and(isSearchable(type, keyword))
                )
                .groupBy(movie)
                .orderBy(getOrderSpecifier(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(
                result
                , pageable
                , pageable.getPageSize()
        );
    }

    private OrderSpecifier[] getOrderSpecifier(Sort sort) {
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Movie.class, "movie");
            return new OrderSpecifier(direction, orderByExpression.get(prop));
        }).toArray(OrderSpecifier[]::new);
    }

    private static BooleanBuilder isSearchable(String type, String keyword) {
        BooleanBuilder conditonBuilder = new BooleanBuilder();
        if (type != null) {
            String[] typeArr = type.split("");
            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditonBuilder.or(movie.title.contains(keyword));
                        break;
                    case "r":
                        conditonBuilder.or(movie.rating.contains(keyword));
                        break;
                    case "c":
                        conditonBuilder.or(movie.country.contains(keyword));
                        break;
                }
            }
            return conditonBuilder;
        }
        return conditonBuilder;
    }

    private static BooleanExpression mnoGreatherThanZero() {
        return movie.id.gt(0L);
    }
}
