package game;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class main extends Frame{
	
	public static void main(String[] args) throws IOException {
		logWindow log = new logWindow ();      //������� ������ ������
		log.setVisible (true);                                //������������� ��������� ����
		log.addWindowListener (new WindowAdapter () { 
			public void windowClosing (WindowEvent e) {    // � �������� ��������� �������� �������
				e.getWindow ().dispose ();                               // ���������� ������ Frame
			}          
		});
	}
}
