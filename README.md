# 개요
간단한 서블릿 기반 커뮤니티 구현입니다.

## 세션 로그인
- 보안에는 취약하지만 다음 요소를 고려하여 구현했습니다
- 일정 시간동안만 허용됨
- 평문을 저장하지않고 session id를 저장하는 방식
  - 보통은 jwt 토큰방식으로 아예 정보를 브루트포스으로도 찾기 힘들게 해야함
  - 혹은 유효하지 않은 반복요청은 거부한다는 식으로 하는게 적합할 것입니다
- 또한 코드 그나마 나은 OOP를 위해서 세션 관리 부분인 UserSessionManager는 싱글톤 방식으로 만들었습니다.