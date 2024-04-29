package demo1;

import networking.frontend.NetworkManagementPanel;

public class Main {
	public static void main(String args[]) {

		ChatPanel panel = new ChatPanel();

		// To open the network management window, just create an object of type NetworkManagementPanel.
		NetworkManagementPanel nmp = new NetworkManagementPanel("SwingChat", 20, panel);  

	}
}
