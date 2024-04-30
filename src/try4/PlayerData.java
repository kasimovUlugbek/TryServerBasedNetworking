package try4;

import java.io.Serializable;


/**
 * 
 * In general, it is smart to make custom classes representing the data that you'd like to send
 * over the network. 
 * 
 * @author john_shelby
 *
 */
public class PlayerData implements Serializable {
	
	private static final long serialVersionUID = -6050080578707829643L;
	
	public String uniqueID;
	public String username;
	public double x, y;
	public int classType;//can prob make this a short or smth
	

}
