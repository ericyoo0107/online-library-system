#  온라인 도서 관리 시스템

- 온라인 도서 관리 시스템을 구현한 프로젝트로 Spring Boot 기반의 REST API를 제공합니다. <br>
- JWT를 이용한 인증/인가, 도서 대출 및 반납, 동적 쿼리를 이용한 도서 검색, 사용자 관리 등의 기능을 제공합니다.

---

## 🔧 기술 스택
  - Java 17
  - Spring boot 3.3.1
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Querydsl
  - MySQL 
  - Junit 5
  - Swagger (OAS 3.0)


---
## 🚀 API 설명

- **인가가 필요한 API**는 HTTP **Authorization Header**에 **Bearer Token**을 넣어야 합니다.

### 📌 유저 관련 API

| API        | URL                              | 설명                     | 인증 필요 여부  |
|------------|----------------------------------|------------------------|-----------------|
| **회원가입**   | `POST /api/v1/user/signup`       | 사용자 계정을 생성 후 JWT 토큰 반환 | ❌ 토큰 불필요  |
| **로그인**    | `POST /api/v1/user/login`        | 사용자 인증 후 JWT 토큰 반환       | ❌ 토큰 불필요  |
| **사용자 조회** | `GET /api/v1/user/list`          | 전체 사용자 조회                | ❌ 토큰 불필요     |
| **사용자 검색** | `GET /api/v1/user/list/{userId}` | 특정 사용자를 ID로 조회           | ❌ 토큰 불필요     |


### 📌 도서 관련 API

| API                 | URL                                   | 설명                                                                                      | 인증 필요 여부 |
|---------------------|---------------------------------------|----------------------------------------------------------------------------------|----------|
| **도서 등록**   | `POST /api/v1/book/register`          | 새 도서 등록                                                                                 | ✅ 토큰 필요  |
| **도서 검색**         | `GET /api/v1/book/list/find`          | - 도서 검색 by 도서 제목, 저자 이름<br/>- 도서 정렬 기능 by 제목, 출판일<br/>- 태그 기반 필터링 가능<br/>- 페이징 처리 기술 이용 | ❌ 토큰 불필요 |
| **도서 수정**         | `PUT /api/v1/book/update/{bookId}`    | 도서 정보 수정                                                                                | ✅ 토큰 필요  |
| **도서 삭제**         | `DELETE /api/v1/book/delete/{bookId}` | 도서 삭제                                                                                   | ✅ 토큰 필요  |


### 📌 대출 관련 API

| API            | URL                                    | 설명                                                     | 인증 필요 여부  |
|----------------|----------------------------------------|--------------------------------------------------------|-----------------|
| **대출 등록**      | `POST /api/v1/borrow/register`         | 도서 대출 등록 <br/>- 동시에 여러 명이 같은 책을 대출하지 않도록 락으로 동시성 문제 해결 | ✅ 토큰 필요    |
| **대출 상태 확인**   | `GET /api/v1/borrow/check/{bookId}`    | 대출 상태 확인                     | ❌ 토큰 불필요   |
| **도서 반납**     | `PATCH /api/v1/borrow/return/{bookId}` | 도서 반납                        | ✅ 토큰 필요    |

---

## 🚀 예외 처리
- 예외 처리는 `@ControllerAdvice`를 이용하여 전역적으로 처리하며, 각 예외에 대한 상세한 처리를 위해 `@ExceptionHandler`를 사용합니다.
#### 📌 공통 예외
- RequestBody가 잘못된 경우 : `@Valid`로 처리 (400)
- 인가 문제 : AuthException (401)

#### 📌 유저
- 회원가입
  - 이미 가입한 유저일 경우 : BadSignupException (400)
- 로그인
  - 잘못된 아이디인 경우 : BadLoginException (400)
  - 잘못된 비밀번호일 경우 : BadLoginException (400)
- 사용자 조회
- 사용자 검색
  - 검색 결과가 없을 경우 : UserNotFoundException (404)

#### 📌 도서
- 도서 등록
  - 중복 되는 ISBN이 존재할 경우 : DuplicateIsbnException (409)
- 도서 검색
- 도서 수정
  - 수정 시도한 도서가 존재하지 않는 도서일 경우 : BookNotFoundException (404)
- 도서 삭제
  - 삭제 시도한 도서가 존재하지 않는 도서일 경우 : BookNotFoundException (404)
  - 삭제 시도한 도서가 대출 중인 도서일 경우 : BookConflictException (409)

#### 📌 대출
- 대출 등록
  - 이미 누군가 대출 중인 도서일 경우 : AlreadyBorrowException (409)
  - 유저가 존재하지 않을 경우 : UserNotFoundException (404)
  - 도서가 존재하지 않을 경우 : BookNotFoundException (404)
- 대출 상태 확인
  - 도서가 존재하지 않을 경우 : BookNotFoundException (404)
  - 동일한 책이 반납되지 않은 상태가 2건 이상 조회 될 경우 : RuntimeException (500)
- 도서 반납
  - 도서가 존재하지 않을 경우 : BookNotFoundException (404)
  - 이미 반납한 도서인 경우 : AlreadyBorrowException (409)
  - 동일한 책이 반납되지 않은 상태가 2건 이상 조회 될 경우 : RuntimeException (500)

---
## 📝 API 명세서 확인 방법
- 방법 1 : 로컬 실행 후 http://localhost:8080/swagger-ui/index.html 접속
- 방법 2 : Swagger API 명세서(docs/swagger_apis.json)를 통해 API 사용 방법 확인
- Swagger API 테스트 순서 (User-Flow)
  1. 회원 가입
  2. 로그인
  3. 로그인 이후 발급 받은 토큰을 헤더에 삽입 ex) Bearer {토큰}
  4. 사용자 전체 조회
  5. 사용자 검색
  6. 도서 등록
  7. 도서 검색
  8. 도서 수정
  9. 도서 검색 (tag가 programming에서 코딩으로 변경)
  10. 대출 등록
  11. 대출 상태 확인
  12. 도서 반납
  13. 대출 상태 확인
  14. 도서 삭제

---

## 🎯 캐시 적용 전 코드 : problem1
- 캐시 적용 전 코드는 problem1 브랜치에서 확인할 수 있습니다.
```bash
$ git checkout problem1
```