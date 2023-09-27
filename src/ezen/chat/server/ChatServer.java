package ezen.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

/**
 * 채팅 서버 (2023-06-02)
 * 
 * @author 이희영
 */
public class ChatServer {

	private static final int SERVER_PORT = 7777;
	private ServerSocket serverSocket;
	private boolean running;
	private Map<String, ChatHandler> clients;

	/**
	 * ChatServer 구동
	 * 
	 * @throws IOException
	 */
	public void startup() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		System.out.println("========= [ChatServer(" + SERVER_PORT + ")] Start =========");
		running = true;
		clients = new Hashtable<String, ChatHandler>();
		
		try {
			while (running) {
				System.out.println("[ChatServer(" + SERVER_PORT + ")] ChatClient Connect Listenning ..");
				Socket socket = serverSocket.accept();
				System.out.println("[ChatCleint(" + socket.getInetAddress().getHostAddress() + ")] 연결해옴...");

				// 데이터 송수신 스레드 생성 및 실행
				ChatHandler chatHandler = new ChatHandler(socket, this);
				chatHandler.start();
			}
		} catch (IOException e) {
			System.err.println("[ChatServer(" + SERVER_PORT + ")] 실행 중 아래와 같은 오류가 발생하였습니다.");
			System.err.println("오류 내용 :  " + e.getMessage());
		}
	}

	/**
	 * 접속한 클라이언트를 콜렉션에 추가
	 * 
	 * @param chatHandler
	 */
	public void addChatClient(ChatHandler chatHandler) {
		clients.put(chatHandler.getNickName(), chatHandler);
		System.err.println("[현재 채팅에 참여중인 클라이언트 수] : " + clients.size());
	}

	/**
	 * 접속한 클라이언트를 콜렉션에 제거
	 * 
	 * @param chatHandler
	 */
	public void removeChatClient(ChatHandler chatHandler) {
		clients.remove(chatHandler.getNickName());
		System.err.println("[현재 채팅에 참여중인 클라이언트 수] : " + clients.size());
	}

	/**
	 * 접속한 모든 클라이언트에게 메시지 전송
	 * 
	 * @param message 메세지 입력
	 * @throws IOException
	 */
	public void sendMessageAll(String message) throws IOException {
		Collection<ChatHandler> list = clients.values();
		for (ChatHandler chatHandler : list) {
			chatHandler.sendMessage(message);
		}
	}

	/**
	 * 접속중인 모든 클라이언트 대화명 목록 반환
	 * 
	 * @return 참여자 리스트
	 */
	public Collection<ChatHandler> getClients() {
		return clients.values();
	}

	/**
	 * 대화명에 해당하는 클라이언트 검색 반환
	 * 
	 * @param nickName DM 받는 사람 닉네임
	 * @return 닉네임
	 */
	public ChatHandler findClient(String nickName) {
		return clients.get(nickName);
	}

	/**
	 * ChatServer 종료
	 */
	public void shutdown() {
		try {
			serverSocket.close();
			System.err.println("[ChatServer(" + SERVER_PORT + ")] 종료됨...");
		} catch (IOException e) {}
	}
}