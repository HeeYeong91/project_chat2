package ezen.chat.protocol;

/**
 * 채팅메시지 빌더 유틸리티 (2023-06-02)
 * 
 * @author 이희영
 */
public class MessageBuilder {

	// 구분자 수정의 효율성을 위해 상수처리
	public static final String DELIMETER = "★";

	/**
	 * StringBuilder를 이용한 토큰 생성 작업
	 * 
	 * @param messageType 메세지 타입(Enum)
	 * @param tokens      토큰 (요청자닉네임 & 받는사람닉네임)
	 * @return
	 */
	public static String build(MessageType messageType, String... tokens) {
		StringBuilder sb = new StringBuilder();
		sb.append(messageType);
		for (String token : tokens) {
			sb.append(DELIMETER);
			sb.append(token);
		}
		return sb.toString();
	}

	/**
	 * 메세지 파싱 기능
	 * 
	 * @param message 메세지 입력
	 * @return 토큰 배열
	 */
	public static String[] parse(String message) {
		String[] tokens = message.split(DELIMETER);
		return tokens;
	}

//	// 테스트 main
//	public static void main(String[] args) {
//		String message = MessageBuilder.build(MessageType.CONNECT, "방그리");
//		message = MessageBuilder.build(MessageType.CHAT_MESSAGE, "방그리", "하이루");
//		message = MessageBuilder.build(MessageType.DIS_CONNECT, "방그리");
//		System.out.println(message);
//
//		String[] tokens = parse(message);
//		for (String token : tokens) {
//			System.out.println(token);
//		}
//	}
}