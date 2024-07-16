# KanbanBoard-1plus1
프로젝트 협업 도구 만들기

## 정보

### 팀원
- 박세미(리더)
- 이은규(팀원)
- 김가은(팀원)
- 이여재(팀원)

#### 사용 기술
- Java
- Spring boot
- JPA
- MySQL
- AWS S3
- OAuth2

## 프로젝트 설명
작업을 생성 및 관리하고 상태를 업데이트하는 등 여러 기능을 개발합니다
일본어로 간판을 뜻하는 칸반(kanban)에서 유래한 프로젝트 관리 방법론 입니다. 도요타 엔지니어가 제조 효율성을 높이기 위해 개발했습니다. 업무를 시각적으로 표현하여 팀이 작업과 프로세스를 효율적으로 관리할 수 있게 도와줍니다.

### 프로젝트 구성요소

#### 보드 : 프로젝트 별로 분리하기 위해 사용합니다.

#### 컬럼 : 진행 상태 별로 분리하기 위해 사용합니다.
1. 시작 전
    1. 프로젝트를 시작하기 전 작업에 해당하는 카드를 만들어 두는 곳
    2. 같은 말 : Backlog, Upcoming, Ice box
2. 진행 중
    1. 작업자 별로 카드가 분배되면 우선순위와 여러 사항들을 고려하여 ‘시작 전’에 있는 카드를 ‘진행 중’으로 옮긴 후 작업을 시작
    2. 같은 말 : In Progress
3. 완료
    1. ‘진행 중’에 있는 작업이 완료되면 해당 카드를 ‘완료’로 이동
    2. 특정 작업이 다른 작업과 관계가 있는 경우 해당 작업이 ‘완료’로 옮겨진 후 작업 시작 가능
    3. 같은 말 : Done

#### 카드 : 기능과 담당자 별로 분리하기 위해 사용합니다. 
1. 마감 일자를 지정하고 기능 별로 관련 내용을 모아 놓을 수 있어 카드만 보면 명확하게 알 수 있습니다.
2. 파일을 첨부할수있는 기능을 가집니다
3. 체크리스트를 체크할수있는 기능을 가집니다

#### 댓글 : 유저가 카드에 댓글을 달 수 있습니다.


### 프로젝트 구현 

#### 요구 기능
- 로그인 기능, 로그아웃 기능, 회원가입
- 보드 목록조회, 보드 생성, 보드 수정, 보드 삭제, 보드 초대
- 컬럼 생성, 컬럼 삭제, 컬럼 순서이동
- 카드 목록조회, 카드 수정, 카드 삭제
- 카드 댓글 작성, 카드 댓글 조회

#### 구현 기능(완료된 사항 체크)
- [x] 로그인 기능
- [x] 소셜 로그인
- [x] 로그아웃 기능
- [x] 회원 가입(유저/ 매니저)
- [x] 비밀번호 변경
- [x] 회원 탈퇴
- [x] 프로필 업로드
- [x] 프로필 조회
- [x] 보드 목록조회
- [x] 보드 생성
- [x] 보드 수정
- [x] 보드 삭제
- [x] 보드 초대
- [x] 보드 멤버 삭제
- [x] 컬럼 생성
- [x] 컬럼 삭제
- [x] 컬럼 순서이동
- [x] 카드 목록조회
- [x] 카드 생성
- [x] 카드 순서이동
- [x] 카드 수정
- [x] 카드 삭제
- [x] 카드 멤버 할당
- [x] 카드 멤버 할당 삭제
- [x] 카드 파일 첨부
- [x] 카드 파일 삭제
- [x] 카드 파일 다운로드
- [x] 카드 댓글 작성
- [x] 카드 댓글 조회
- [x] 체크리스트 조회
- [x] 체크리스트 추가

## 파일 관리 , 컨벤션

### 깃허브 관리
- 구현하려는 CRUD또는 특정 기능 구현, 리펙토링에 목적에 맞게 이슈생성
- 이슈에맞춰 브런치 생성
- 브런치에서 구현 기능 단위적 커밋
- 이슈 제목 커밋 메세지 커밋컨벤션 사용(feat, refacotr, docs, test 등)
- 머지전에 확인하고 머지
- 머지 시 슬랙으로 팀원과 공유하여 팀원모두 최신파일을 가지고 관리

### 통합적 컨벤션
- 도메인별 패키직관리(그 안에서 컨트롤러,서비스,레퍼지토리,dto별 관리)
- 메세지는 한국말로 작성
- 주석 사용하여 기능별 설명
- application.yml 으로 관리 (각 로컬별관리가 필요한 부분이나 노출시키면안되는정보는 환경변수로 관리)

### 인증인가
- `@Authentication`를 사용하여 로그인정보가 저장된 UserDetailsImpl를 가져올수있게한다
    - 게시물 등을 생성할때 로그인된 정보를 주입하여 데이터 넣어주기
    - 수정삭제같은 로직을 수행할때 로그인정보와 데이터의 정보 확인하여 권한 확인
    - 프로필업로드과 같은 로그인상태에서만 가능한  api요청 간소화및 보안화   user/1/profile(x)  user/profile(o)
- 인증인가를 하지않고 열어두어야하는 요청은  `WebSecurityConfig(시큐리티 설정)`, `JwtAuthorizationFilter(인가 필터)` 두곳에서  설정해준다

### Http 응답
- ResponseUtils : 요청에 따른 사용해야할 응답 메서드 정의
- HttpResponseDto : 요청에 따른 보내줘야할 응답DTO 정의(빌더를 사용하여 상태코드 ,메세지, 데이터를 선택적으로 응답)
- 기본적으로 요청에따른 성공은 상태코드만 반환
- 조회같이 데이터가필요한경우 상태코드와 데이터 반환
- 예상된 에러가 발생해여 실패한경우  `ResponseCodeEnum`을 사용해서 상태코드와,메세지를 이넘으로 정의하고 이것을 반환

### 예외처리
- `GlobalExceptionAdvice`에 글로벌 관리 하고 
  - `CustoEmxception` 핸들링
  - `MethodArgumentNotValidException`를 통해 dto, 엔티티 데이터 유효성 검사 관리
- 도메인별 예외처리 CustomException 를 상속받아 관리 ex) user/UserException
- 범용적 커스텀 에러 정의하고 비지니스로직에서 이넘메세지를 던져주어 예외 처리

### 각 파일별 적용
엔티티
- 모든 엔티티를 `Timestamped` 를 상속받아 자동으로 생성 시간과 수정 시간을 관리
- 세터는 엔티티 단위사용 아닌 필드 단위로 필요한 부분만 맞게 사용
- 일관성을 위해 모든 칼럼에 `@Column` 사용
- 필수적으로 가져야할 데이터 `@NotNull` 사용

DTO
- 필수적으로 보내야할 데이터 `@NotBlank` 사용

레퍼지토리
- null값의 가능성이있는 조회는 `Optional` 클래스 사용하여 조회

컨트롤러
- 엔티티가 달라도 논리적으로 상속 관계이면 api에 명시  comment/1  (x)  post/commnet/1 (o)

서비스
- DB 변경 비지니스로직은 `@Transactional` 사용
- 조회 로직도 `@Transactional(readOnly = true)`를 사용해서 성능 최적화

### 최적화
- jpql 활용
- 인덱스 활용
- JOIN FETCH 사용
- api 요청 단위로 테스트 및 진행

## API
https://documenter.getpostman.com/view/19185435/2sA3kPqjxN

## ERD
![image](https://github.com/user-attachments/assets/1ccec1c2-ab2d-46a9-9a4b-92867941a9c9)




## 역할분담 

### 유저, 인증인가 -> 공동 작업

### 보드, 체크리스트 Part -> 박세미

### 칼럼 Part -> 김가은

### 카드,파일 Part -> 이은규

### 댓글 Part -> 이여재
