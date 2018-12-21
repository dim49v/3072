package game;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class main extends Frame{
	
	public static void main(String[] args) throws IOException {
		logWindow log = new logWindow ();      //Создаем объект класса
		log.setVisible (true);                                //Устанавливаем видимость окна
		log.addWindowListener (new WindowAdapter () { 
			public void windowClosing (WindowEvent e) {    // в качестве аргумента передаем событие
				e.getWindow ().dispose ();                               // уничтожает объект Frame
			}          
		});
	}
}
