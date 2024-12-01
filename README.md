📚 도서 관리 시스템 (Book Management System)

이 프로젝트는 동국대학교 데이터베이스설계-2024년 2학기 과제로 제출하기 위한 용도입니다. 작성자는 2020112062_이태환 입니다. 해당 프로젝트에서 가장 중요한 엔티티는 2개만 존재합니다. 사람에 해당하는 테이블인 User와 책에 해당하는 테이블인 Book입니다. 이 프로젝트를 만들어달라고 요청할 법한 사람이 제 자신이라 생각하고, 이제 이 테이블들에 추가할 기능과 이 두 테이블사이에 발생할 '관계'를 고려하여 기존의 테이블에서 이를 구현하기 위한 변수를 추가하였습니다. 변수를 추가하다보니, 데이터의 중복이 발생하기 시작했습니다. 따라서, 중간고사 전후로 공부하였던 데이터베이스 정규화에 대해 다시 공부하고 난잡해진 테이블들을 다시 정리하였습니다. 자세한 내용은 보고서에서 다루겠습니다. 다음으로 소개할 내용은 이 깃허브 코드를 실행하였을 때 이 프로그램을 통해 실행할 수 있는 기능에 대한 API들을 추가로 작성하였습니다. 

🚀 주요 기능

- 도서 관리: 도서의 등록, 수정, 삭제, 검색 및 상태 관리.  
- 리뷰 관리: 도서 리뷰 작성, 수정, 삭제 및 조회, 평균 평점 확인.  
- 카테고리 및 태그 관리: 카테고리 생성 및 도서와의 연동, 태그 추가 및 검색.  
- 알림 시스템: 연체 알림 및 반납 마감 알림 전송.  
- 대여 이력 관리: 대출 및 반납 기록 관리.  
- 유저 관리: 신규 유저 등록.  

📑 API 목록

📚 도서(Book) 관련 API
| Method | URL               | 설명                        |
|--------|-------------------|-----------------------------|
| PUT    | /books/update/{id}| 도서 정보 수정              |
| POST   | /books/search     | 도서 검색                   |
| POST   | /books/return     | 도서 반납                   |
| POST   | /books/borrow     | 도서 대출                   |
| POST   | /books/add        | 도서 추가                   |
| GET    | /books/list       | 도서 목록 조회               |
| GET    | /books/bookinfo/{id}| 도서 상세 정보 조회          |
| DELETE | /books/delete/{id}| 도서 삭제                   |


✍ 리뷰(Review) 관련 API
| Method | URL                                   | 설명                        |
|--------|---------------------------------------|-----------------------------|
| PUT    | /reviews/update/{id}                 | 리뷰 수정                   |
| POST   | /reviews/add                         | 리뷰 추가                   |
| GET    | /reviews/book/{bookId}               | 특정 도서에 대한 리뷰 조회   |
| GET    | /reviews/book/{bookId}/average-rating| 특정 도서의 평점 평균 조회   |
| DELETE | /reviews/delete/{id}                 | 리뷰 삭제                   |


🏷 태그(Tag) 관련 API
| Method | URL                 | 설명                |
|--------|---------------------|---------------------|
| POST   | /tags/add/{bookId}  | 태그 추가           |
| GET    | /tags/search        | 태그로 도서 검색     |
| GET    | /tags/book/{bookId} | 도서의 태그 조회     |

🗂 카테고리(Category) 관련 API
| Method | URL                    | 설명              |
|--------|------------------------|-------------------|
| POST   | /categories/add        | 카테고리 추가      |
| POST   | /categories/add-to-book| 도서에 카테고리 추가|
| GET    | /categories/all        | 모든 카테고리 조회 |

🔔 알림(Notification) 관련 API
| Method | URL                                   | 설명                  |
|--------|---------------------------------------|-----------------------|
| POST   | /notifications/read/{notificationId} | 알림 읽음 처리         |
| GET    | /notifications/user/{userId}          | 특정 사용자의 알림 조회 |

📜 대여 이력(History) 관련 API
| Method | URL                     | 설명                |
|--------|-------------------------|---------------------|
| GET    | /history/all-rentals    | 모든 대출 기록 조회   |
| GET    | /history/active-rentals | 활성 대출 기록 조회   |

👤 유저(User) 관련 API
| Method | URL       | 설명        |
|--------|-----------|-------------|
| POST   | /user/join| 사용자 가입 |

 
