package ezen.chat.client;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import ezen.chat.protocol.MessageBuilder;
import ezen.chat.protocol.MessageType;

/**
 * 채팅창 구현 (2023-06-02)
 * 
 * @author 이희영
 */
@SuppressWarnings("serial")
public class ChatFrame extends Frame {

	Label nicknameL;
	TextField nicknameTF, inputTF;
	Button loginB, sendB;
	TextArea messageTA, nicknameList;
	Choice typeC;

	Panel northP, southP;

	ChatClient chatClient;
	String nickName;

	public ChatFrame() {
		this("No-Title");
	}

	public ChatFrame(String title) {
		super(title);
		// 닉네임 & 연결 버튼
		nicknameL = new Label("닉네임");
		nicknameTF = new TextField();
		loginB = new Button("연 결");
		// 채팅입력
		inputTF = new TextField();
		// 채팅 상대 선택
		typeC = new Choice();
		typeC.add("모두에게");
		// 채팅 전송
		sendB = new Button("보내기");
		// 채팅창
		messageTA = new TextArea();
		// 채팅 상대 리스트
		nicknameList = new TextArea(10, 10);

		northP = new Panel(new BorderLayout(5, 5));
		southP = new Panel(new BorderLayout(5, 5));
	}

	/**
	 * 컴포넌트 배치
	 */
	public void init() {
		northP.add(nicknameL, BorderLayout.WEST);
		northP.add(nicknameTF, BorderLayout.CENTER);
		northP.add(loginB, BorderLayout.EAST);

		southP.add(typeC, BorderLayout.WEST);
		southP.add(inputTF, BorderLayout.CENTER);
		southP.add(sendB, BorderLayout.EAST);

		add(northP, BorderLayout.NORTH);
		add(messageTA, BorderLayout.CENTER);
		add(nicknameList, BorderLayout.EAST);
		add(southP, BorderLayout.SOUTH);
	}

	/**
	 * 닉네입 입력 후 연결
	 */
	private void connect() {
		nickName = nicknameTF.getText();
		if (!Validator.hasText(nickName)) {
			JOptionPane.showMessageDialog(this, "닉네임을 입력하세요");
			return;
		}
		chatClient = new ChatClient(this);
		try {
			chatClient.connectServer();
			setEnable(false);
			chatClient.sendMessage(MessageBuilder.build(MessageType.CONNECT, nickName));
			chatClient.receiveMessage();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "ChatServer를 연결할 수 없습니다..");
		}
	}

	/**
	 * 닉네임 입력란, 닉네임 리스트 비활성화
	 * 
	 * @param enable
	 */
	private void setEnable(boolean enable) {
		nicknameTF.setEnabled(enable);
		nicknameList.setEnabled(enable);
	}

	/**
	 * 대화내역 출력
	 * 
	 * @param message 메세지내용
	 */
	public void appendMessage(String message) {
		messageTA.append(message + "\n");
	}

	/**
	 * 대화내역 초기화
	 */
	public void clearMessage() {
		messageTA.setText("");
	}

	/**
	 * 채팅 참여자 리스트 초기화
	 */
	public void resetList() {
		nicknameList.setText("");
	}

	/**
	 * 참여자 리스트에 닉네임 추가
	 * 
	 * @param nickNameList 대화 참여자 리스트
	 */
	public void appendNicknameList(String nickNameList) {
		nicknameList.append(nickNameList);
	}

	/**
	 * 초이스에 참여자 닉네임 추가
	 * 
	 * @param nickNameList 대화 참여자 리스트
	 */
	public void appendNicknameChoice(String nickNameList) {
		typeC.removeAll();
		typeC.add("모두에게");
		String[] nickNames = nickNameList.split("\n");
		
		for (String nickName : nickNames) {
			if (!nickName.equals(nicknameTF.getText())) {
				typeC.add(nickName);
			}
		}
	}

	/**
	 * 채팅 메세지 전송
	 */
	private void sendChatMessage() {
		String message = inputTF.getText();
		
		if (Validator.hasText(message)) {
			try {
				// DM 전송에 따른 분기
				if (typeC.getSelectedIndex() != 0) {
					String receiveNickname = typeC.getSelectedItem();
					chatClient.sendMessage(
							MessageBuilder.build(MessageType.DM_MESSAGE, nickName, receiveNickname, message));
				} else {
					chatClient.sendMessage(MessageBuilder.build(MessageType.CHAT_MESSAGE, nickName, message));
				}
				inputTF.setText("");
			} catch (IOException e) {}
		}
	}

	/**
	 * 서버로 연결해제 메세지 전달
	 */
	private void disConnect() {
		try {
			if (nickName != null) {
				chatClient.sendMessage(MessageBuilder.build(MessageType.DIS_CONNECT, nickName));
			}
			exit();
		} catch (IOException e) {}
	}

	/**
	 * 종료 버튼
	 */
	private void exit() {
		setVisible(false);
		dispose();
		System.exit(0);
	}

	public void addEventListener() {
		// 종료 처리
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disConnect();
			}
		});

		// 연결 & 종료
		loginB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buttonName = loginB.getName();
				
				if (buttonName.equals("button0")) {
					connect();
					
					if (!nicknameTF.getText().isEmpty()) {
						loginB.setLabel("종 료");
						loginB.setName("button1");
					} else {
						return;
					}
				} else if (buttonName.equals("button1")) {
					disConnect();
				}
			}
		});

		// 닉네임 입력창에서 Enter키로 연결
		nicknameTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buttonName = loginB.getName();
				
				if (buttonName.equals("button0")) {
					connect();
					
					if (!nicknameTF.getText().isEmpty()) {
						loginB.setLabel("종 료");
						loginB.setName("button1");
					} else {
						return;
					}
				}
			}
		});

		// 전송 처리 (입력 후 엔터 & 버튼 클릭)
		inputTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});

		sendB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sendChatMessage();
			}
		});
	}
}