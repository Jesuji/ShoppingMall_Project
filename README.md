# 🛒 쇼핑몰 프로젝트

## 📎 프로젝트 개요
이 프로젝트는 전공 시간 과제로 진행한 사용자가 상품을 검색하고, 장바구니에 담고, 주문할 수 있는 **간단한  온라인 쇼핑몰** 웹 애플리케이션 입니다. Controller와 @RestController를 상황에 따라 적절히 분리하여 사용함으로써 뷰 템플릿 기반 페이지와 RESTfulAPI 기능을 동시에 구현하였습니다. 

<br>

## ⚒️ 개발 환경
- **IDE**: IntelliJ IDEA Community
- **JDK**: OpenJDK 17 
- **Database**: mysql 9.0.1
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript

<br>

## ⚒️ 기술 스택
### 📌 Backend (백엔드)
- **Java 17** 
- **Spring Boot 3.3.5**
- **JPA (Hibernate)**
- **Lombok**  

### 📌 Frontend (프론트엔드)
- **Thymeleaf** 
- **HTML / CSS / JavaScript**

### 📌 Database (데이터베이스)
- **H2 Database** -> 로컬 환경에서 빠르게 실행되나 일회용 메모리

<br>

### 📕 API 명세서 
https://spark-plot-2cc.notion.site/API-134a56a2c7a1809fb020c02fddc198f7?pvs=4

<br>

## ERD
<img width="800" alt="스크린샷 2025-03-12 오후 1 41 12" src="https://github.com/user-attachments/assets/a0669827-0229-4ae6-8d8c-758d638053d4" />

<br>


## 쇼핑몰 전체 구성도
<img width="800" alt="스크린샷 2025-03-12 오후 1 41 37" src="https://github.com/user-attachments/assets/7713c061-b5ca-4464-bb5e-618e60a111f2" />

<br>
<br>

## ⚒️ 주요 기능 
### ✅ 회원가입 및 로그인 (세션 인증 기반)
<img width="400" alt="스크린샷 2025-03-12 오후 3 35 47" src="https://github.com/user-attachments/assets/8489f127-a26b-4244-ba07-025281a4c573" /> <img width="400" alt="스크린샷 2025-03-12 오후 3 40 08" src="https://github.com/user-attachments/assets/922a3794-20ba-42f0-8520-511f682e536e" />

<br>


### ✅ 메인 화면에 동적 배너 생성
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/cac6be2d-2601-4663-af2c-f9e339a7d3e3" />

<br>


### ✅ 상품 목록 조회 및 검색 기능
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/b0309e5e-7415-46c4-b3d8-9205f8dc53d6" />
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/3d6a7df1-c161-47db-b68f-4091e977a10d" />

<br>


### ✅ 리뷰 CRUD
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/fcec6252-e7f2-440b-afc9-dd51feae3e40" />
<img width="700" alt="스크린샷 2025-03-12 오전 1 49 24" src="https://github.com/user-attachments/assets/7b79a4f8-4f7c-4bbd-babe-c8b241225b69" />

<br>

### ✅ 댓글 및 대댓글 달기
<img width="661" alt="스크린샷 2025-03-14 오후 3 05 11" src="https://github.com/user-attachments/assets/136ef2c7-fbbd-4776-8fd8-dc4a40952d8f" />

<br>

### ✅ 장바구니 담기 및 위시 리스트 담기
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/fe8e798c-4383-42ec-871d-bbd2a6fbaba7" />
<img width="500" alt="스크린샷 2025-03-12 오전 1 46 53" src="https://github.com/user-attachments/assets/b1d91cbc-cf86-414d-ba65-c8775c9bf4ab" />

<br>


### ✅ 주문하기
<img width="700" alt="Image" src="https://github.com/user-attachments/assets/c43f12c9-ed69-4e62-b6c0-6a250fcfb18a" />
<img width="700" alt="스크린샷 2025-03-12 오후 3 30 06" src="https://github.com/user-attachments/assets/b2e3b144-d77c-4a12-ad1a-113c91324a18" />

<br>

### ✅ 주문내역 조회
<img width="700" alt="스크린샷 2025-03-14 오후 2 53 30" src="https://github.com/user-attachments/assets/213675df-d36a-466d-8c8e-af91bb6f56ed" />

<br> 


### 📌 고찰
1️⃣ 엔티티 간의 양방향 관계에서 무한 루프 및 StackOverflow 오류가 있어 @ToString(exclude="cart") >> 이런 형식으로 해결했습니다.


2️⃣ 대댓글 구조를 구현하면서 대댓글이 부모 댓글과 연결되지 않은 상태로 DB에 저장되서 오류가 생겼습니다. DB 설계에서 부모 댓글 ID를 명확히 설정하고, 댓글 작성 시 해당 부모 댓글 ID를 함께 저장하도록 하여 오류를 해결했습니다.


3️⃣ 뷰 템플릿과 데이터를 Model 매핑하여 HTML 페이지를 반환할 때 뷰가 뜨지 않는 에러가 자주 생겼었는데 뷰 템플릿에서 그 데이터를 올바르게 참조하지 않아서 생긴 오류였다. 

### 📌 향후 업데이트 계획 
- 리뷰 작성할 때 이미지 추가 기능
- OAuth 로그인 추가 (카카오 로그인 연동)
- 예외 처리에 대해 Custom Exception 만들기

