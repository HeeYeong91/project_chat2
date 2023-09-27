package ezen.chat.server.handler;

import java.io.IOException;

import ezen.chat.server.ChatHandler;
import ezen.chat.server.ChatServer;

/**
 * 클라이언트 전송 메시지 처리를 위한 표준 명세(역할) (2023-06-02)
 * 
 * @author 이희영
 */
public interface MessageListener {

	public void doResponse(ChatServer chatServer, ChatHandler chatHandler, String clientMessage) throws IOException;
}