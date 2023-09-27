package ezen.chat.server.handler;

import java.io.IOException;

import ezen.chat.server.ChatHandler;
import ezen.chat.server.ChatServer;

/**
 * 채팅 메시지에 대한 응답 처리 (2023-06-02)
 * OCP 원칙을 지키기 위해서 채팅 종류별 클래스 분리
 * 
 * @author 이희영
 */
public class ChatMessageHandler implements MessageListener {

	@Override
	public void doResponse(ChatServer chatServer, ChatHandler chatHandler, String clientMessage) throws IOException {
		chatServer.sendMessageAll(clientMessage);
	}
}