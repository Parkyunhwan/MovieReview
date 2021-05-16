package org.yunhwan.moviereview.repository.search;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.yunhwan.moviereview.entity.Movie;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Movie.class);
    }

    @Override
    public Movie Search1() {
        log.info("SEARCH 1");
        return null;
    }
}
