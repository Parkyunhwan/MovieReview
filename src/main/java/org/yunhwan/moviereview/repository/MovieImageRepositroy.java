package org.yunhwan.moviereview.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yunhwan.moviereview.entity.MovieImage;

public interface MovieImageRepositroy extends JpaRepository<MovieImage, Long> {
    void deleteByMovie_Mno(Long mno);
}
