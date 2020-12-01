package game;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class logWindow extends JFrame{   /*Создаем подкласс logWindow  класса Frame*/
	private int n=3;
	private boolean endGame;
	private Field field;
	private final int side=600;
	private final HashMap <Integer,Color> col = new HashMap<>();
	JPanel myPanel;
	JLabel myLabel;
	JTextField myField;
	JButton myButton;
	public logWindow (){   /*Конструктор класса*/
		
		
		super ("3072");    /*Вызываем конструктор суперкласса и передаем ему параметр, в данном случае имя программы*/
		setSize(side+10,side+70);  /*Метод суперкласса для установкиразмеров окна, в пикселях*/
		setResizable(false);

		col.put(0, Color.GRAY);
		col.put(3, new Color(0xdea93e));
		col.put(6, new Color(0xd17519));
		col.put(12, new Color(0xb85402));
		col.put(24, new Color(0xa80000));
		col.put(48, new Color(0xdb3987));
		col.put(96, new Color(0xf77ce7));
		col.put(192, new Color(0xb05fc7));
		col.put(384, new Color(0x8741c4));
		col.put(768, new Color(0x2c00f0));
		col.put(1536, new Color(0x5c87ff));
		col.put(3072, new Color(0x81dbfc));
		col.put(6144, new Color(0x6bedc8));
		col.put(12288, new Color(0x11d628));
		col.put(24576, new Color(0x98b861));
		col.put(49152, new Color(0xdea93e));
		myField = new JTextField("Ваше имя");
		myField.setPreferredSize( new Dimension(100,30));
		myField.addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) {
		    	if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    		focus();
		    	}
		    }
		    public void keyReleased(KeyEvent e) {}
		    public void keyTyped(KeyEvent e) {}
		});
		String[] items = {"3*3","4*4","5*5","6*6","7*7","8*8","9*9"};
		JComboBox myComboBox = new JComboBox(items);
		myComboBox.setBackground(Color.WHITE);
		myLabel = new JLabel ("Ваш счет: 0. Количество шагов: 0"); /* Создаем текстовое поле и надпись в нем*/
		myLabel.setPreferredSize( new Dimension(330,30));
		myButton = new JButton("Рекорды");
		myButton.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {}
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			@Override
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent arg0) {}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				recordWindow record = null;
				try {
					record = new recordWindow(n);
					endGame=false;
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}      //Создаем объект класса
	    		record.setVisible (true);                                //Устанавливаем видимость окна
	    		record.addWindowListener (new WindowAdapter () { 
	    			public void windowClosing (WindowEvent e) {    // в качестве аргумента передаем событие
	    				e.getWindow ().dispose ();                               // уничтожает объект Frame
	    			}          
	    		});
	    	}
		});
		myPanel = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        int n=field.getSize();
		        for(int i=0;i<n;i++){
		        	for(int j=0;j<n;j++){
		        	     g.setColor(col.get(field.getAt(j,i)));
		        		 g.fillRoundRect(side*i/n+3, side*j/n+3,side/n-3, side/n-3, 100/n, 100/n);
		        		 g.drawString(String.valueOf(field.getAt(i,j)), (side*i/n+side/(n*2)), (side*j/n+side/(n*2)));
		        	}
		        }
		        g.setColor(Color.BLACK);
		        int fontSize=160/n;
		        Font font = new Font("TimesRoman", Font.BOLD|Font.ITALIC, fontSize);
		        g.setFont(font);
		        for(int i=0;i<n;i++){
		        	for(int j=0;j<n;j++){
		        		if(field.getAt(j,i)!=0){
		        			g.drawString(String.valueOf(field.getAt(j,i)), (side*i/n+10), (side*j/n+fontSize*2+10));
		        		}
		        	}
		        }
		    }
		};
		myPanel.setPreferredSize(new Dimension(side,side));
		myPanel.setBackground(Color.GRAY);
		Container container = getContentPane();
	    container.setLayout (new FlowLayout(FlowLayout.CENTER));
	    container.add (myComboBox);
	    container.add (myLabel);
	    container.add (myField);
	    container.add (myButton);
	    container.add (myPanel);
		
		ActionListener actionListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	 JComboBox box = (JComboBox)e.getSource();
		    	 n=box.getSelectedIndex()+3;
		    	 start();
		    }
		};
		myComboBox.addActionListener(actionListener);
		start();
		this.addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) {
		    	int direction=0;
		    	switch (e.getKeyCode()){
		    		case KeyEvent.VK_UP:
		    			direction=1;
		    			break;
		    		case KeyEvent.VK_RIGHT:
		    			direction=2;
		    			break;
		    		case KeyEvent.VK_DOWN:
		    			direction=3;
		    			break;
		    		case KeyEvent.VK_LEFT:
		    			direction=4;
		    			break;
		    	}
		    	if (field.step(direction)){
		    		myLabel.setText("Ваш счет: "+field.getScore()+".Количество шагов: "+field.getSteps());
		    	}
		    	else{
		    		if(endGame){
		    			myLabel.setText("Игра окончена! Ваш счет: "+field.getScore()+".Количество шагов: "+field.getSteps());
			    		recordWindow record = null;
						try {
							record = new recordWindow(n,myField.getText(),n,field.getScore(),field.getSteps());
							endGame=false;
							
						} catch (IOException e1) {
							e1.printStackTrace();
						}      //Создаем объект класса
			    		record.setVisible (true);                                //Устанавливаем видимость окна
			    		record.addWindowListener (new WindowAdapter () { 
			    			public void windowClosing (WindowEvent e) {    // в качестве аргумента передаем событие
			    				e.getWindow ().dispose ();                               // уничтожает объект Frame
			    			}          
			    		});
		    		}
		    	}
		    	myPanel.repaint();
		    }
		    public void keyReleased(KeyEvent e) {}
		    public void keyTyped(KeyEvent e) {}
		     
		});
	}	
	private void start(){
		field = new Field(n);
		myLabel.setText("Ваш счет: "+field.getScore()+".Количество шагов: "+field.getSteps());
		myPanel.repaint();
		focus();
		endGame=true;
	}
	private void focus(){
		this.requestFocusInWindow();
	}
}
