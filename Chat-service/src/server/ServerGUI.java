package server;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListModel;
import javax.swing.WindowConstants;

import events.ClientConnectedEvent;
import events.ClientConnectedListener;
import events.MessageReceivedEvent;
import events.MessageReceivedListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ServerGUI extends javax.swing.JFrame {

	private JScrollPane clientListScrollPane;
	private JButton startButton;
	private DefaultListModel clientListModel = null;
	private JScrollPane jScrollPane1;
	private JTextArea messageTextArea;
	private JButton sendMessageButton;
	private JList clientList;
	private JScrollPane messageScrollPane;
	private JButton stopButton;
	private GameServer server = null;

	/**
	 * Auto-generated main method to display this JFrame
	 */

	public ServerGUI() {
		super();
		initGUI();
		initServer();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initGUI() {
		try {
			GroupLayout thisLayout = new GroupLayout((JComponent)getContentPane());
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

			{
				clientListScrollPane = new JScrollPane();
			}
			{
				messageScrollPane = new JScrollPane();
				{
					messageTextArea = new JTextArea();
					messageScrollPane.setViewportView(messageTextArea);
				}
			}
			{
				jScrollPane1 = new JScrollPane();
				{
					clientListModel = new DefaultListModel();
					clientList = new JList();
					jScrollPane1.setViewportView(clientList);
					clientList.setModel(clientListModel);
				}
			}
			{
				startButton = new JButton();
				startButton.setText("START");
			}
			{
				stopButton = new JButton();
				stopButton.setText("STOP");
				stopButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						System.out.println("stop button clicked");
					}
				});
			}
			{
				sendMessageButton = new JButton();
				sendMessageButton.setText("Send");
				sendMessageButton.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						(new Thread(new Runnable(){
							public void run(){
								while(true){
									try {
										Thread.sleep(700);
										server.sendToClient("hello", 0);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}
							}
						})).start();
						
					}
				});
			}
			thisLayout.setVerticalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				    .addComponent(sendMessageButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
				    .addComponent(startButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
				    .addComponent(stopButton, GroupLayout.Alignment.BASELINE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(clientListScrollPane, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 410, Short.MAX_VALUE))
				    .addComponent(messageScrollPane, GroupLayout.Alignment.LEADING, 0, 410, Short.MAX_VALUE)
				    .addComponent(jScrollPane1, GroupLayout.Alignment.LEADING, 0, 410, Short.MAX_VALUE))
				.addContainerGap());
			thisLayout.setHorizontalGroup(thisLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(thisLayout.createParallelGroup()
				    .addComponent(messageScrollPane, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 573, GroupLayout.PREFERRED_SIZE)
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addComponent(startButton, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				        .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
				        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				        .addComponent(sendMessageButton, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
				        .addGap(258)))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(thisLayout.createParallelGroup()
				    .addGroup(thisLayout.createSequentialGroup()
				        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 0, Short.MAX_VALUE))
				    .addGroup(GroupLayout.Alignment.LEADING, thisLayout.createSequentialGroup()
				        .addGap(129)
				        .addComponent(clientListScrollPane, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
				        .addGap(0, 35, Short.MAX_VALUE)))
				.addContainerGap());
			pack();
			this.setSize(783, 491);
			this.setTitle("Teh serva");
			setVisible(true);
		} catch (Exception e) {
			// add your error handling code here
			e.printStackTrace();
		}
	}

	private void initServer() {
	//	server = new GameServer();
		server.addClientConnectedListener(new ClientConnectedListener() {

			@Override
			public void clientConnected(ClientConnectedEvent evt) {
				clientListModel.addElement("CLIENT#" + evt.getClientID());
			}
		});
		server.addMessageRecievedListener(new MessageReceivedListener() {

			@Override
			public void MessageRecieved(MessageReceivedEvent evt) {
				System.out.println("received");
				ClientThread source = (ClientThread) evt.getSource();
				messageTextArea.append("[CLIENT#" + source.getClientID()
						+ "] --> [Server]: " + evt.getText() + "\n");
				
			}
		});
		server.start();
	}
}
