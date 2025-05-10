import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
	static int[] AmntActions = new int[1];
	static boolean[] dead = new boolean[1];
	static int[] TargetConsumption = new int[1];
	static int score;
	static JLabel text;
	static JLabel journaltext;
	static ArrayList<InventorySlot> Inventory;
	
    public static void main (String[]args){
    	ArrayList<Species>dict = new ArrayList<>();
        dict.add(Dict.Puffball);
        dict.add(Dict.Chanterelle);
        dict.add(Dict.FalsePuffball);
        
        
        JFrame frame = new JFrame("resume consumption");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridLayout(4,1));
        
        JButton entry = new JButton("entry");
        entry.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		if(!dead[0]) {
        			if(Inventory.size() == 0){
        				journaltext.setText("Nothing in inventory");
        			}
        			else{
        				entry(Inventory.get(Inventory.size()-1).mush);
        			}
        			AmntActions[0]--;
        			message();
        		}
        	}
        });
        
        JButton forage = new JButton("forage");
        forage.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		if(!dead[0]) {
        			Inventory.add(new InventorySlot(dict.get((int)(Math.random()*3))));
        			AmntActions[0]--;
        			message();
        		}
        	}
        });
        
        JButton consume = new JButton("consume");
        consume.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		if(!dead[0]) {
        			if(Inventory.size() == 0){
        				journaltext.setText("You don't have food");
        			}
        			else if(Inventory.get(Inventory.size()-1).mush.deadly == true){
        				dead[0] = true;
        				AmntActions[0] = 0;
        			}
        			else{
        				Inventory.remove(Inventory.size()-1);
        				TargetConsumption[0]--;
        			}
        			AmntActions[0]--;
        			message();
        		}
        	}
        });
        
        JButton toss = new JButton("toss");
        toss.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		if(!dead[0]) {
        			Inventory.remove(Inventory.size()-1);
        			AmntActions[0]--;
        			message();
        		}
        	}
        });
        JButton restart = new JButton("respawn");
        restart.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent evt) {
        		restart();
        	}
        });
        
        JPanel panel = new JPanel();
        panel.add(entry);
        panel.add(forage);
        panel.add(consume);
        panel.add(toss);
        frame.add(panel);
        
        text = new JLabel("");
        JPanel textpanel = new JPanel();
        textpanel.add(text);
        frame.add(textpanel);
        
        journaltext = new JLabel("");
        JPanel journaltextpanel = new JPanel();
        journaltextpanel.add(journaltext);
        frame.add(journaltextpanel);
        
        JPanel jprestart = new JPanel();
        jprestart.add(restart);
        frame.add(jprestart);
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        
        restart();
    }
    
    static void message() {
    	if(dead[0] || (AmntActions[0] <= 0 && TargetConsumption[0] > 0)) {
    		dead[0] = true;
            AmntActions[0] = 0;
            placemessage();
    		journaltext.setText("<html>you died<br/>score: " + score + "<html/>");
    	}
    	else if(AmntActions[0] <= 0) {
    		AmntActions[0] = 5;
            score++;
            TargetConsumption[0] = (int)(Math.random() * 5);
    		placemessage();
    	}
    	else {
            placemessage();
    	}
    }
    static void placemessage() {
    	text.setText("<html>You have " + AmntActions[0] + " actions left today. <br/>"
    			+ "You must consume " + TargetConsumption[0] + " more mushrooms.<br/>"
    			+ "What would you like to do?<html/>");
    }
    
    static void entry(Species sp){
    	if(sp.deadly) journaltext.setText("<html>" + sp.name + ": " 
    			+ sp.description + "<br/>Deadly to consume");
        else journaltext.setText("<html>" + sp.name + ": " 
    			+ sp.description + "<br/>Safe to consume");
    }
    
    static void restart() {
    	AmntActions[0] = 5;
    	dead[0] = false;
    	TargetConsumption[0] = 1;
    	score = 0;
    	journaltext.setText("");
    	Inventory = new ArrayList<>();
    	placemessage();
    }
}
class InventorySlot{
    Species mush;
    int count;
    InventorySlot(Species sp){
        mush = sp;
        count = 1;
    }
}

class Species{
    String name;
    String description;
    boolean deadly;
    
    Species(String n, String desc, boolean d){
        name = n;
        description = desc;
        deadly = d;
    }
}

class Dict{
    static Species Puffball = new Species("PUFFBALL","its body bursts on impact, releasing a cloud of spores",false);
    static Species Chanterelle = new Species("CHANTERELLE","often found in well-drained coniferous woodland",false);
    static Species FalsePuffball = new Species("FALSE PUFFBALL","cream-colored mass, a species of slime mold",true);
}
