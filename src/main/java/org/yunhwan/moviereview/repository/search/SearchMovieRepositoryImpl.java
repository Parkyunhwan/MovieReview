package org.yunhwan.moviereview.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.QMember;
import org.yunhwan.moviereview.entity.QMovie;
import org.yunhwan.moviereview.entity.QReview;

import java.util.List;

@Log4j2
public class SearchMovieRepositoryImpl extends QuerydslRepositorySupport implements SearchMovieRepository {

    public SearchMovieRepositoryImpl() {
        super(Movie.class);
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
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("Movie SearchPage========================+");

        QMovie movie = QMovie.movie;
        QReview review = QReview.review;
        QMember member = QMember.member;

        JPQLQuery<Movie> jpqlQuery = from(movie);
        jpqlQuery.leftJoin(review).on(review.movie.eq(movie));
        jpqlQuery.leftJoin(member).on(review.member.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(movie, review.grade.avg(), review.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = movie.mno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditonBuilder = new BooleanBuilder();

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
            booleanBuilder.and(conditonBuilder);
        }
        tuple.where(booleanBuilder);

        tuple.groupBy(movie);

        List<Tuple> result = tuple.fetch();

        log.info(result);


        return null;
    }
}
