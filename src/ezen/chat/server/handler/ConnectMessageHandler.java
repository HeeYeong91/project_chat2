package ezen.chat.server.handler;

import java.io.IOException;
import java.util.Collection;

import ezen.chat.protocol.MessageBuilder;
import ezen.chat.protocol.MessageType;
import ezen.chat.server.ChatHandler;
import ezen.chat.server.ChatServer;

/**
 * 연결 메시지에 대한 응답 처리 (2023-06-02)
 * 
 * @author 이희영
 */
public class ConnectMessageHandler implements MessageListener {

	@Override
	public void doResponse(ChatServer chatServer, ChatHandler chatHandler, String clientMessage) throws IOException {
		chatServer.addChatClient(chatHandler);
		chatServer.sendMessageAll(clientMessage);

		// 연결된 모든 클라이언트 닉네임 목록 전송 구현
		Collection<ChatHandler> list = chatServer.getClients();
		StringBuilder sb = new StringBuilder();
		
		for (ChatHandler handler : list) {
			String nickName = handler.getNickName();
			sb.append(nickName).append(",");
		}

		String nicknameList = null;
		if (sb.length() != 0) {
			nicknameList = sb.substring(0, sb.lastIndexOf(","));
		}
		chatServer.sendMessageAll(MessageBuilder.build(MessageType.USER_LIST, chatHandler.getNickName(), nicknameList));
	}
}