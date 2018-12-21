package game;
public class Field {
	private int[][] arrf;			//поле
	private int size;				//размер поля
	private int score=0;			//счет
	private int steps=-1;			//количество шагов
	//конструктор с параметром размера поля
	public Field(int sizeField){
		size=sizeField;
		arrf = new int[size][size];
		addVal(size-1);
	}
	//функция, возвращающая поле
	public int[][] getField(){
		return arrf;
	}
	//следующий шаг
	//возвращает статус хода (совершен или нет)
	public boolean step(int direction){
		if(shift(direction)){
			return addVal(1);
		}
		return addVal(0);
	}
	//сдвиг плиток с параметром направления(1-вверх, 2-вправо, 3-вниз, 4-влево)
	//возвращает статус сдвига (совершен или нет)
	private boolean shift(int direction){
		boolean fl;
		boolean fl2=false;
		switch (direction){
		case 1:		//up
			for(int i=0; i<size; i++){
				int ymin=0;
				for(int u=1; u<size; u++){
					int y=u;
					fl=arrf[y][i]!=0;
					while(fl){
						if(arrf[y-1][i]==0){
							arrf[y-1][i]=arrf[y][i];
							arrf[y][i]=0;
							y--;
							if(y==0){
								fl=false;
							}
							fl2=true;
						}
						else{
							if(arrf[y-1][i]==arrf[y][i] && y>ymin){
								arrf[y-1][i]*=2;
								score+=arrf[y-1][i];
								arrf[y][i]=0;
								ymin=y;
								fl2=true;
							}
							else{
								ymin=y-1;
							}
							fl=false;
						}
					}
				}
			}
			break;
		case 2:		//right
			for(int i=0; i<size; i++){
				int ymin=size-1;
				for(int u=size-2; u>=0; u--){
					int y=u;
					fl=arrf[i][y]!=0;
					while(fl){
						if(arrf[i][y+1]==0){
							arrf[i][y+1]=arrf[i][y];
							arrf[i][y]=0;
							y++;
							if(y==size-1){
								fl=false;
							}
							fl2=true;
						}
						else{
							if(arrf[i][y+1]==arrf[i][y] && y<ymin){
								arrf[i][y+1]*=2;
								score+=arrf[i][y+1];
								arrf[i][y]=0;
								ymin=y;
								fl2=true;
							}
							else{
								ymin=y+1;
							}
							fl=false;
						}
					}
				}
			}
			break;
		case 3:		//down
			for(int i=0; i<size; i++){
				int ymin=size-1;
				for(int u=size-2; u>=0; u--){
					int y=u;
					fl=arrf[y][i]!=0;
					while(fl){
						if(arrf[y+1][i]==0){
							arrf[y+1][i]=arrf[y][i];
							arrf[y][i]=0;
							y++;
							if(y==size-1){
								fl=false;
							}
							fl2=true;
						}
						else{
							if(arrf[y+1][i]==arrf[y][i] && y<ymin){
								arrf[y+1][i]*=2;
								score+=arrf[y+1][i];
								arrf[y][i]=0;
								ymin=y;
								fl2=true;
							}
							else{
								ymin=y+1;
							}
							fl=false;
						}
					}
				}
			}
			break;
		case 4:		//left
			for(int i=0; i<size; i++){
				int ymin=0;
				for(int u=1; u<size; u++){
					int y=u;
					fl=arrf[i][y]!=0;
					while(fl){
						if(arrf[i][y-1]==0){
							arrf[i][y-1]=arrf[i][y];
							arrf[i][y]=0;
							y--;
							if(y==0){
								fl=false;
							}
							fl2=true;
						}
						else{
							if(arrf[i][y-1]==arrf[i][y] && y>ymin){
								arrf[i][y-1]*=2;
								score+=arrf[i][y-1];
								arrf[i][y]=0;
								ymin=y;
								fl2=true;
							}
							else{
								ymin=y-1;
							}
							fl=false;
						}
					}
				}
			}
			break;
		}
		return fl2;
	}
	//добавление новых плиток на поле с параметром количества этих плиток
	//возвращает статус добавления (совершено или нет)
	private boolean addVal(int count){
		int q=0, x=0, y=0;
		boolean fl=false;
		for (int i=0; i<size && !fl; i++) {
		    for (int u=0; u<size && !fl; u++) {
		    	if(arrf[i][u]==0){
		    		fl=true;
		    	}
		    	if(i<size-1 && u<size-1){
		    		if(arrf[i][u]==arrf[i][u+1] || arrf[i][u]==arrf[i+1][u]) {
		    			fl=true;
		    		}
		    	}
		    	else{
		    		if(i<size-1){
		    			if(arrf[i][size-1]==arrf[i+1][size-1]){
		    				fl=true;
		    			}
		    		}
		    		if(u<size-1){
		    			if(arrf[size-1][u]==arrf[size-1][u+1]){
		    				fl=true;
		    			}
		    		}
		    	}
		    }
		}
		if(fl){
			while(q<count && fl){
				while(fl){
					x = (int)(Math.random()*size);
					y = (int)(Math.random()*size);
					fl= arrf[x][y]!=0;
				}
				arrf[x][y]=3;
				q++;
				fl=false;
				for (int i=0; i<size; i++) {
				    for (int u=0; u<size; u++) {
				    	if(arrf[i][u]==0){
				    		fl=true;
				    	}
				    }
				}
			}
			if(q>0){
				steps++;
			}
		}
		else{
			System.out.println("Game over! Score = "+score);
			return false;
		}
		return true;
	}
	//вывод поля для отладки
	public void print(){
		System.out.println(); 
		for (int i=0; i<size; i++) {
			for (int u=0; u<size; u++) {
	    		System.out.print(arrf[i][u] + "\t");
	    	}
	    	System.out.println();
		}
	}
	//функция, возвращающая размер поля
	public int getSize(){
		return size;
	}
	//функция, возвращающая счет
	public int getScore(){
		return score;
	}
	//функция, возвращающая количество шагов
	public int getSteps(){
		return steps;
	}
	//функция, возвращающая значение плитки с координатами (i,j)
	//возвращает 0, если плитки нет
	public int getAt(int i, int j){
		if(i>=0 && i<size && j>=0 && j<size){
			return arrf[i][j];
		}
		return -1;
	}
}
