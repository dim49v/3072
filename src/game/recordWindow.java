package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class recordWindow extends Frame{
	JPanel myPanel = new JPanel();
    JLabel myLabel = new JLabel();
	JComboBox myComboBox;
	private int c;
	private int n;
	private final int side=600;
	private String[][][] sc;	
	int lastRec,lastRecn,lastReci,newRec,newRecn,newReci;
	public recordWindow(int nLast) throws IOException{
		this(nLast,"",0,0,0);
	}
	public recordWindow(int nLast, String str, int nWrite, int score, int steps) throws IOException{
		super ("Record");    /*Вызываем конструктор суперкласса и передаем ему параметр, в данном случае имя программы*/
		setSize(side,side);  /*Метод суперкласса для установкиразмеров окна, в пикселях*/
		setResizable(false);
		String[] items = {"3*3","4*4","5*5","6*6","7*7","8*8","9*9"};
		myComboBox = new JComboBox(items);
		myComboBox.setBackground(Color.WHITE);
		myComboBox.setSelectedIndex(nLast-3);
		n=nLast;
		myPanel.setPreferredSize( new Dimension(side,side));
		add (myComboBox, BorderLayout.NORTH);
		add (myPanel, BorderLayout.CENTER);
		ActionListener actionListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	 JComboBox box = (JComboBox)e.getSource();
		    	 n = box.getSelectedIndex()+3;
		    	 showRec();
		    }
		};
		myComboBox.addActionListener(actionListener);
		read();
		if(nWrite!=0){
			if(editRec(str, nWrite, score, steps)){
				write();
			}
		}
		showRec();
	}
	private void read() throws IOException{
		DataInputStream    fileIn = null;
		try{
			 fileIn = new DataInputStream(new FileInputStream("rec.txt"));
			 String str;
			 int score;
			 c = fileIn.readInt();
			 sc = new String[10][c][3];	
			 for(int i=3;i<=9;i++){
				 for(int u=0;u<c;u++){
					sc[i][u][0] = fileIn.readUTF();
					sc[i][u][1] = String.valueOf(fileIn.readInt());
					sc[i][u][2] = String.valueOf(fileIn.readInt());
					lastRec = fileIn.readInt();
					if(lastRec==1){
						lastRecn=i;
						lastReci=u;
					}
				 }
			 }
		}finally {
	         if (fileIn != null) {
	             fileIn.close();
	          }
		}
	}
	private boolean editRec(String str,int nWrite, int score, int steps){
		boolean fl=false;
		for(int i=c-1;i>=0;i--){
			if(score>Integer.valueOf(sc[nWrite][i][1])){
				fl=true;
				if(i<c-1){
					sc[nWrite][i+1][0]=sc[nWrite][i][0];
					sc[nWrite][i+1][1]=sc[nWrite][i][1];
					sc[nWrite][i+1][2]=sc[nWrite][i][2];
				}
			}else if(fl){
				sc[nWrite][i+1][0]=str;
				sc[nWrite][i+1][1]=String.valueOf(score);
				sc[nWrite][i+1][2]=String.valueOf(steps);
				lastRec=1;
				lastRecn=nWrite;
				lastReci=i+1;
				return true;
			}
		}
		if(fl){
			sc[nWrite][0][0]=str;
			sc[nWrite][0][1]=String.valueOf(score);
			sc[nWrite][0][2]=String.valueOf(steps);
			lastRec=1;
			lastRecn=nWrite;
			lastReci=0;
			return true;
		}
		return false;
	}
	private void writeNew() throws IOException{
		DataOutputStream  fileOut = null;
		try{
			fileOut = new DataOutputStream(new FileOutputStream("rec.txt"));
			 fileOut.writeInt(10);
			 for(int i=3;i<=9;i++){
				 for(int u=0;u<10;u++){
					fileOut.writeUTF("-пусто-");
					fileOut.writeInt(0);
					fileOut.writeInt(0);
					fileOut.writeInt(0);
				 }
			 }
		}finally {
	         if (fileOut != null) {
	        	 fileOut.close();
	          }
		}
	}
	private void write() throws IOException{
		DataOutputStream  fileOut = null;
		try{
			fileOut = new DataOutputStream(new FileOutputStream("rec.txt"));
			 fileOut.writeInt(c);
			 for(int i=3;i<=9;i++){
				 for(int u=0;u<c;u++){
					fileOut.writeUTF(sc[i][u][0]);
					fileOut.writeInt(Integer.valueOf(sc[i][u][1]));
					fileOut.writeInt(Integer.valueOf(sc[i][u][2]));
					if(lastRec==1 && lastRecn==i && lastReci==u){
						fileOut.writeInt(1);
					}else{
						fileOut.writeInt(0);
					}
				 }
			 }
		}finally {
	         if (fileOut != null) {
	        	 fileOut.close();
	          }
		}
	}
	private void showRec(){
		String text = "<html><table>";
		text=text+"<tr><th><b>--------Имя--------</b></td><td><b>-----Счет-----</b></td><td><b>-----Шаги-----</b></th></tr>";
		for (int i=0;i<c;i++){
			if(lastRec==1 && lastRecn==n && lastReci==i){
				text=text+"<tr><td><b>"+sc[n][i][0]+"</b></td><td><b>"+sc[n][i][1]+"</b></td><td><b>"+sc[n][i][2]+"</b></td></tr>";
			}
			else{
				text=text+"<tr ><td>"+sc[n][i][0]+"</td><td>"+sc[n][i][1]+"</td><td>"+sc[n][i][2]+"</td></tr>";
			}
		}
		text=text+"</table></html>";
		myLabel.setText(text);
		myLabel.setFont(new Font(null, Font.PLAIN, 30));
		myPanel.add(myLabel,BorderLayout.CENTER);
	}
}
