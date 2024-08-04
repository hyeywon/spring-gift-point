# spring-gift

## 📪 Features

### 👤 회원 관리
- 회원가입
  - 이메일과 비밀번호를 입력하여 회원가입
  - 이메일은 중복될 수 없음
  - **회원가입 후 토큰 발급**
- 로그인
  - 이메일과 비밀번호를 입력하여 로그인
  - **회원가입 시 발급 받은 토큰을 이용해 로그인**
- `카카오` 로그인
  - 이메일 제공 동의가 되어 있지 않은 경우 로그인 불가능
  - 이메일이 유효하고, 회원가입이 되어 있는 경우 로그인 성공
- 내 정보 조회
  - 회원별 본인 정보 조회
  - 이메일 및 보유 포인트 조회

>#### ⚠️ 비밀번호 조건
> - 영어 대문자, 소문자, 숫자 필수 포함
> - 최소 8글자 이상
> - 공백 포함 불가능

### 🎁 상품 관리
- 상품 조회
  - 요청된 정렬 조건에 따라 페이지별 조회
  - `이름` 또는 `가격`에 따라 정렬
- 상품 추가
- 상품 수정
- 상품 삭제

>#### ⚠️ 상품 이름 조건
>- 최대 15자
>- 숫자 및 공백 포함 가능
>- 아래의 특수 문자만 사용 가능
>  - `(` `)` `[` `]` `+` `-` `&` `/` `-`
>- `카카오`가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능

### 🏷️ 상품 카테고리
```
1차 카테고리만 있으며, 2차 카테고리는 없음
```
- 전체 카테고리 조회
  - 요청된 정렬 조건에 따라 페이지별 조회
- 상품 추가 시 카테고리 지정
- 상품 수정 시 카테고리 수정 가능

### 🎨 상품 옵션
- 상품 옵션 조회
- 상품 옵션 추가
- 옵션 수량 차감
  - 차감한 수량이 0개 미만일 경우 예외 발생

> #### ⚠️ 옵션 이름 조건
> - 최대 50자
> - 숫자 및 공백 포함 가능
>- 아래의 특수 문자만 사용 가능
>  - `(` `)` `[` `]` `+` `-` `&` `/` `-`
>- 동일한 상품 내의 옵션 이름 중복 불가능

> #### ⚠️ 옵션 수량 조건
> - 최소 1개 이상
> - 최대 1억 개 미만

### 📜 위시리스트 관리
```
유효한 토큰을 포함한 요청에 대해서만 아래 기능 수행
```
- 위시리스트 조회
  - 요청된 정렬 조건에 따라 페이지별 조회
  - **상품**의 `이름` 또는 `가격`에 따라 정렬
- 위시리프트 상품 추가
  - 하나의 요청당 하나의 상품 추가
- 위시리스트 상품 수량 변경
  - 요청된 변경 수량이 0일 경우 삭제
- 위시리스트 상품 삭제
  - 하나의 요청당 하나의 상품 삭제

### 🛒 주문하기
```
카카오 로그인을 통해서만 주문 가능
```
- 요청된 **옵션**과 **수량**만큼 상품 주문
  - 요청된 수량만큼 옵션 수량이 차감됨
  - 위시리스트에 해당 상품이 있을 경우 삭제됨
  - 총 구매 가격의 `1%`만큼 포인트가 적립됨 (소수점 버림)
- 요청된 **메시지**로 나에게 카카오톡 메시지 전송
- 요청된 **포인트**만큼 사용자 포인트 차감
  - 보유 포인트를 초과할 경우 예외 발생