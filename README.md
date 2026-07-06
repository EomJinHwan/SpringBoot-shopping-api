# Spring Boot Shopping API

Spring Boot 기반 쇼핑몰 API 프로젝트입니다.  
기존 기초 쇼핑몰 API에 MySQL 연동, 비밀번호 암호화, Spring Security, JWT 인증, Role 기반 인가 처리를 단계적으로 적용했습니다.

## 프로젝트 개요

초기 프로젝트는 회원가입, 로그인, 상품 조회, 장바구니 기능을 중심으로 구현되어 있었지만 인증/인가 구조가 단순한 상태였습니다.

기존에는 로그인한 사용자를 서버 내부 필드로 관리하거나, 장바구니 API에서 URL에 `userId`를 직접 전달받는 방식으로 사용자를 구분했습니다. 이 방식은 서버가 로그인 상태를 직접 관리해야 하고, 클라이언트가 다른 사용자의 `userId`를 URL에 넣어 요청할 수 있다는 문제가 있었습니다.

이를 개선하기 위해 Spring Security와 JWT 기반 인증 방식을 적용했습니다. 로그인 성공 시 서버는 Access Token을 발급하고, 클라이언트는 이후 요청마다 `Authorization` 헤더에 Bearer 토큰을 담아 요청합니다. 서버는 JWT 필터에서 토큰을 검증한 뒤, 토큰에서 추출한 사용자 정보를 `SecurityContext`에 저장하여 현재 인증된 사용자를 식별합니다.

또한 비밀번호는 BCrypt를 사용해 단방향 해시로 저장하고, 사용자 권한을 `ROLE_USER`, `ROLE_ADMIN`으로 구분하여 상품 관리 API와 회원 조회 API는 관리자만 접근할 수 있도록 제한했습니다.

## 주요 개선 내용

### MySQL 연동

기존 H2 기반 프로젝트를 MySQL 기반으로 전환했습니다.  
이를 통해 서버를 재시작해도 회원, 상품, 장바구니 데이터가 유지되도록 개선했습니다.

### 비밀번호 암호화

기존에는 회원가입 시 입력한 비밀번호가 DB에 그대로 저장되었습니다.  
이를 개선하기 위해 Spring Security의 `PasswordEncoder`와 `BCryptPasswordEncoder`를 사용하여 비밀번호를 단방향 해시로 저장했습니다.

### Spring Security 설정

Spring Security를 적용하고 REST API 환경에 맞게 CSRF, Form Login, HTTP Basic 인증을 비활성화했습니다.  
JWT 인증을 사용하기 위해 세션 정책은 `STATELESS`로 설정했습니다.

### JWT 인증 구현

로그인 성공 시 JWT Access Token을 발급하도록 구현했습니다.  
이후 클라이언트는 `Authorization: Bearer {accessToken}` 형식으로 토큰을 전달하고, 서버는 JWT 필터에서 토큰을 검증하여 인증된 사용자로 처리합니다.

### API URL 구조 개선

기존에는 장바구니 API에서 `/cart/{userId}`처럼 URL에 사용자 ID를 직접 전달했습니다.  
JWT 적용 후에는 URL에서 `userId`를 제거하고, 토큰에서 현재 사용자를 식별하도록 변경했습니다.

### Role 기반 권한 처리

사용자 권한을 `ROLE_USER`, `ROLE_ADMIN`으로 구분했습니다.  
일반 사용자는 상품 조회와 장바구니 기능을 사용할 수 있고, 관리자는 상품 등록, 수정, 삭제 및 회원 조회 API에 접근할 수 있도록 제한했습니다.

## 인증 흐름

```text
1. 사용자가 로그인 요청
2. 서버가 아이디와 비밀번호 검증
3. 검증 성공 시 JWT Access Token 발급
4. 클라이언트는 이후 요청마다 Authorization 헤더에 토큰 전달
5. JwtAuthenticationFilter가 토큰 추출 및 검증
6. 토큰에서 userId와 role 추출
7. Authentication 객체 생성
8. SecurityContext에 인증 정보 저장
9. Spring Security가 인증/인가 여부 판단

## 향후 개선 사항
- 응답 DTO 분리로 Entity 직접 반환 제거
- 공통 예외 처리 추가
- Refresh Token 도입
- 배포 환경 구성
