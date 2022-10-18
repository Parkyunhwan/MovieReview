# 영화 리뷰 게시판

## 목록 조회 페이지

* `/moive/list` 

[//]: # (<img width="800" alt="스크린샷 2021-05-06 오전 11 35 58" src="https://user-images.githubusercontent.com/47476276/117234060-40f73100-ae5f-11eb-81d3-56abafc62c5c.png">)


## 영화 조회 페이지

* `/moive/{mno}` (mno 는 movie_num)
  * method : GET

[//]: # (<img width="600" alt="스크린샷 2021-05-06 오전 11 39 39" src="https://user-images.githubusercontent.com/47476276/117234328-c0850000-ae5f-11eb-9625-c0f9763989a7.png">)

## 영화 수정 삭제 페이지
* `/moive/{mno}` (mno 는 movie_num)
  * method : PUT -> 수정
  * mtehod : DELETE -> 삭제
  
[//]: # (<img width="802" alt="스크린샷 2021-05-06 오전 11 41 11" src="https://user-images.githubusercontent.com/47476276/117234440-f75b1600-ae5f-11eb-8417-8928611cd00d.png">)

## 리뷰 API

* `/reviews/{mno}` (mno 는 movie_num)
  * 영화의 모든 리뷰 조회
    * `/reviews/{mno}` 
    * method : GET
  
[//]: # (  <img width="379" alt="스크린샷 2021-05-06 오전 11 48 03" src="https://user-images.githubusercontent.com/47476276/117234976-efe83c80-ae60-11eb-8977-2fe608a6fc61.png">)
  
  * 영화의 리뷰 추가
    * `/reviews/{mno}` 
    * method : POST
    
  * 영화의 특정 리뷰 수정
    * `/reviews/{mno}/{reviewnum}`
    * method : PUT
    
  * 영화의 특정 리뷰 삭제
    * `/reviews/{mno}/{reviewnum}`
    * method : DELETE

# 리팩터링

## 1차 리팩터링 적용 사항
* [x] Convention에 맞춰 리팩터링 진행
* [x] model에 넘겨주는 데이터를 넘겨주는 구조에서 REST API 형태로 변경
* [] 페이징 시 스프링 page 처리 객체로 변환 (pageRequset)
* [] 이벤트스토밍을 통해 도메인 이해
* [x] 영화 검색 빌더형태의 queryDsl로 리팩터링
  * [x] 반환값이 VO값을 갖도록 전환
* [] vo -> dto 생성자 전달

 
