package org.yunhwan.moviereview.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.yunhwan.moviereview.entity.Member;
import org.yunhwan.moviereview.entity.Movie;
import org.yunhwan.moviereview.entity.Review;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Test
    @Transactional
    public void 특정영화관련리뷰삭제() throws Exception {
        //given
        Member mem = Member.builder()
                .email("test@email.com")
                .build();

        Movie m = Movie.builder()
                .title("TEST")
                .build();

        Review r = Review.builder()
                .text("TEST REVIEW1")
                .member(mem)
                .movie(m)
                .build();
        Review r1 = Review.builder()
                .text("TEST REVIEW2")
                .member(mem)
                .movie(m)
                .build();
        Review r2 = Review.builder()
                .text("TEST REVIEW3")
                .member(mem)
                .movie(m)
                .build();

        memberRepository.save(mem);
        movieRepository.save(m);
        reviewRepository.save(r);
        reviewRepository.save(r1);
        reviewRepository.save(r2);
        List<Review> byMovie = reviewRepository.findByMovie(m);
        if (byMovie.isEmpty()) {
            System.out.println("Empty List");
        } else {
            byMovie.forEach(review -> System.out.println(review));
        }
        //when

///        reviewRepository.deleteByMovie_Mno(m.getMno());
        //then
        List<Review> after = reviewRepository.findByMovie(m);
        System.out.println(after);
        if (after.isEmpty()) {
            System.out.println("Empty List");
        } else {
            after.forEach(review -> System.out.println(review));
        }
        assertTrue(after.isEmpty());
    }

    @Test
    public void insertReviews() throws Exception {
        //given
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random() * 100) + 1; //영화

            Long mid = (long)(Math.random() * 100) + 1; //멤버

            Review review = Review.builder()
                    .movie(Movie.builder().mno(mno).build())
                    .member(Member.builder().mid(mid).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 나의 평가는...?" + i + "번째 리뷰")
                    .build();

            reviewRepository.save(review);
        });
        //when

        //then
    }

    /**
     * Member Fetch 방식이 LAZY이므로 한번에 두 객체를 조회할 수 없다.
     *
     * 따라서, 1. Query를 이용해 조인 처리 2. @EntityGraph를 이용해 Review객체를 가져올 때 Member객체를 로딩시킴
     * 둘 중 하나의 방법을 사용해야만 한다.
     * @throws Exception
     */
    @Test
    public void 특정영화의_모든_리뷰_조회() throws Exception {
        //given
        Movie movie = Movie.builder().mno(6L).build(); // 댓글 개수 4개 예상

        //when
        List<Review> result = reviewRepository.findByMovie(movie);
        //then
        Assertions.assertThat(4).isEqualTo(result.size());
        result.forEach(movieReview ->{
            System.out.print(movieReview.getReviewnum());
            System.out.print("\t" + movieReview.getGrade());
            System.out.print("\t" + movieReview.getText());
            System.out.print("\t" + movieReview.getMember().getEmail());
            System.out.println("------------END-----------------------");
        });
    }

    // 1. FK 쪽을 먼저 삭제하고 PK쪽을 삭제해야만 한다. 2. 두 가지 삭제 행동은 한번에 수행되어야만 하기 때문에 @Transactional과 @Commit을 넣어준다.
    @Test
    public void 회원의_모든_댓글삭제_후_회원삭제_실패테스트() throws Exception {
        //given
        Long mid = 1L;
        //when
        Member member = Member.builder().mid(mid).build();
        memberRepository.deleteById(mid);
        reviewRepository.deleteByMember(member);
        //then
    }

    @Commit
    @Transactional
    @Test
    public void 회원의_모든_댓글삭제_후_회원삭제_성공테스트() throws Exception {
        //given
        Long mid = 1L;
        //when
        Member member = Member.builder().mid(mid).build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(mid);
        //then
    }
}