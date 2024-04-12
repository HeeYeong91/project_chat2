# :incoming_envelope: 채팅 서비스 CHAT_2
<br />

## :page_facing_up: 목차
1. 프로젝트 소개
2. 프로젝트 기능
   * [1. 접속](#1-접속)
   * [2. 모두에게 대화](#2-모두에게-대화)
   * [3. 다이렉트 메세지 대화](#3-다이렉트-메세지-대화)
<br />

## :eyes: 1. 프로젝트 소개
Soket을 이용해 유저 간 채팅을 할 수 있는 간단한 애플리케이션 (화면 : AWT) <br />
AWT는 유니코드를 지원하지 않기 때문에 한글이 깨져서 화면에 출력된다. <br />
이를 해결하기 위해서는 이클립스에서 Run 메뉴에서 Run Configuration 메뉴를 선택해서 <br />
Arguments메뉴에 VM arguments 칸에 -Dfile.encoding=MS949 를 작성하고 Run 버튼을 눌러서 실행하면 한글이 정상적으로 출력된다.
<br /><br />
메세지는 '★'을 기준으로 파싱해서 진행했다.
예) DM_MESSAGE★보내는사람닉네임★받는사람닉네임★채팅내용 <br />
구분자는 수정의 효율성을 위해 상수처리로 변경 <br />
public static final String DELIMETER = "★";
<br /><br />
채팅 서비스 CHAT_1과 기능적인 차이는 없으며, OCP원칙을 지키기 위해 채팅 종류 별 클래스 분리를 진행했다.<br />
MessageListener 인터페이스를 구현한 메세지 핸들러로 채팅 종류 구분을 진행했다.
<br /><br />

:calendar: 프로젝트 기간 : 2023년 6월 2일 <br />
:hammer: Tools : <img src="https://img.shields.io/badge/Eclipse-FE7A16.svg?style=for-the-badge&logo=Eclipse&logoColor=white" /> <br />
:books: languages : ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) <br />
<br />

## :pushpin: 2. 프로젝트 기능
## 1. 접속
* ChatServer구동 후 EzenTalk을 실행시켜준다. (테스트를 위해 EzenTalk을 3개 실행해서 테스트 진행)
* 닉네임을 작성한 뒤 연결버튼을 누르면 채팅서버에 입장이 되고, 자기 자신과 접속 중인 유저에게 입장 메세지를 전달한다.
* 우측 유저 목록에 접속자가 추가된다. <br />

![1접속](https://github.com/HeeYeong91/project_chat1/assets/139057065/84467ec6-1854-48b9-9565-c7b31d4d75fa) <br />
[목차](#page_facing_up-목차)

## 2. 모두에게 대화
* '모두에게' 선택 후 내용을 작성 후 보내기버튼을 누르면 자기 자신을 포함한 접속자 모두에게 메세지를 전달한다.
* [닉네임] : '채팅내용'으로 화면에 표시한다. <br />

![2 모두에게대화](https://github.com/HeeYeong91/project_chat1/assets/139057065/8c393335-6fcd-47c5-8122-ffbbaba51703) <br />
[목차](#page_facing_up-목차)

## 3. 다이렉트 메세지 대화
* '닉네임' 선택 후 내용을 작성 후 보내기버튼을 누르면 자기 자신을 포함한 '닉네임' 유저에게 메세지를 전달한다.
* 받는사람은 [닉네임]님 DM 수신 : '채팅내용'으로 화면에 표시한다. <br />

![3 다이렉트메세지대화](https://github.com/HeeYeong91/project_chat2/assets/139057065/66c25788-488a-4199-adf6-1977a13dc2bb) <br />
[목차](#page_facing_up-목차)
