/* Our version of mouse listener 
 * we give our mouselistener a mousecontroller so that we can manipulate the controller methods
 * */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class view2MouseListener implements MouseListener{  
	//class attriubtes
	JLabel l;
	JLayeredPane p;
	java.lang.StringBuilder	arg;
	deadwoodMouseController dmc;
	// Code for the different button clicks
	//constructor
	public view2MouseListener(deadwoodMouseController dmc){
		this.dmc = dmc;
	}	
	public void mouseClicked(MouseEvent e) {

		if(e.getComponent().getName()!=null){//make sure the label has a name to avoid nullpointer errors
			arg = new StringBuilder();
			l=(JLabel)e.getComponent();
			p=(JLayeredPane)l.getParent();
			String[] arr;
			//each label is named with a useful bit of information we can pass to the mouseController
			//to manipulate the controller
			if(p.getLayer(l)==1){//layer 1 is the scene labels
				arg.append(l.getName());
				System.out.println(arg.toString());
				arr=arg.toString().split(" ");
				if(arr.length>1){
					for(String s : arr){
						System.out.print(s+" ");
					}
					System.out.println();
					dmc.move(arg.toString().split(" "));
				}
				else{
					dmc.move(arg.toString());
				}
			}
			else if(p.getLayer(l)==2){//layer 2 is the role labels
				System.out.println("Role = "+l.getName());
				arg.append("work ");
				arg.append(l.getName());
				System.out.println(arg.toString());
				dmc.work(l.getName());
			}
			else if(p.getLayer(l)==3){//layer 3 is the takes marker labels
				System.out.println(p.getLayer(l));
				System.out.println(arg.toString());
				dmc.act();
			}
			else if(p.getLayer(l)==4){//layer 4 is the dollar upgrade labels
				arg.append("upgrade $ ");
				arg.append(l.getName());
				//l.getName(
				System.out.println(arg.toString());

				dmc.dollarUpgrade(l.getName());
			}
			else if(p.getLayer(l)==5){//layer 5 is the fame upgrade labels
				arg.append("upgrade cr ");
				arg.append(l.getName());
				// System.out.println(arg.toString());
				System.out.println(l.getName());

				dmc.fameUpgrade(l.getName());
			}             
		}
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	//getters and setters //
	public void setDeadWoodMouseController(deadwoodMouseController dmc){
		this.dmc = dmc;
	}
	public deadwoodMouseController getDeadWoodMouseController() {
		return this.dmc;
	}
}
