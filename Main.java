import java.io.PrintStream;
import java.util.Scanner;
public class Main {
    public static Scanner in = new Scanner(System.in);
    public static PrintStream out = System.out;
    static class Table //стол с функцией стола (игроки включены)
    {
        private String pl1; //храним имена игроков
        private String pl2;
        private String[][] map; //игровое поле
        private int turn; //ходы
        public Table() {
            pl1 = "Игрок1";
            pl2 = "Игрок2";
            map = new String[][]{{"-","-","-"},{"-","-","-"},{"-","-","-"}};
            turn = 0;
        }
        public Table(String pl1, String pl2) {
            this.pl1=pl1;
            this.pl2=pl2;
            map = new String[][]{{"-","-","-"},{"-","-","-"},{"-","-","-"}};
            turn = 0;
        }
        public String getPl1() { //геттеры игроков
            return pl1;
        }
        public String getPl2() {
            return pl2;
        }
        public int getTurn() { //геттер хода, чтоб по четности/нечетности узнавать чей ход
            return turn;
        }
        public void nextTurn() { //чтоб ходы менять
            turn++;
        }
        public void printMap() { //вывод карты по шаблону (от себя дабавил указатель того, чей ход)
            out.println(pl1+" - o");
            if (turn%2==0) out.println("˅ ходит ˅"); //вот этот
            else out.println("˄ ходит ˄");
            out.println(pl2+" - x");
            out.printf("| %s | %s | %s |\n",map[0][0],map[0][1],map[0][2]);
            out.printf("| %s | %s | %s |\n",map[1][0],map[1][1],map[1][2]);
            out.printf("| %s | %s | %s |\n",map[2][0],map[2][1],map[2][2]);
        }
        public boolean cellQ(int x, int y) { //проверочка на лишнюю хромосому у пользователя (чтоб циферки нормальные вводил)
            return ((0<=x && x<=2) && (0<=y && y<=2) && map[x][y] == "-");
        }
        public void writeX(int x, int y) { //по сути два сеттера чтоб ходы на доске писать
            map[x][y] = "x";
        }
        public void writeO(int x, int y) {
            map[x][y] = "o";
        }
        public String endQ() { //большой и сложный проверка на победителя (проверка на ничью идет в основной программе)
            String q = "x";
            for (int i = 0; i<3; i++) {
                if (map[i][0]==q && map[i][1]==q && map[i][2]==q) {
                    return q;
                }
                if (map[0][i]==q && map[1][i]==q && map[2][i]==q) {
                    return q;
                }
            }
            if (map[0][0]==q && map[1][1]==q && map[2][2]==q) {
                return q;
            }
            if (map[2][0]==q && map[1][1]==q && map[0][2]==q) {
                return q;
            }
            q = "o";
            for (int i = 0; i<3; i++) {
                if (map[i][0]==q && map[i][1]==q && map[i][2]==q) {
                    return q;
                }
                if (map[0][i]==q && map[1][i]==q && map[2][i]==q) {
                    return q;
                }
            }
            if (map[0][0]==q && map[1][1]==q && map[2][2]==q) {
                return q;
            }
            if (map[2][0]==q && map[1][1]==q && map[0][2]==q) {
                return q;
            }
            return "-";
        }
    }
    static void gameLoop() {
        out.print("Введите имя первого игрока: "); //вводим имена игроков, если поля пустые то называем их насильно
        String pl1 = in.nextLine();
        if (pl1=="") pl1="Игрок1";
        in.nextLine();
        out.print("Введите имя второго игрока: ");
        String pl2 = in.nextLine();
        if (pl2=="") pl2="Игрок2";
        Table t = new Table(pl1,pl2);
        for (int tr = 0; tr<=9; tr++) { //тк клеток 9, то  и ходов можно сделать 9 (i от 0 до 8)
            if (tr==9) {t.printMap();out.println("Ничья (победил автор)");break;} //если доигрались до заполненной доски без победителя то ничья
            t.printMap(); //выводим досочку
            if (t.getTurn()%2==0) {
                boolean flg = false; //нужен только чтоб while без конца гонять
                int x=0, y=0;
                while (!flg) { //Крутим цикл ввода хода пока не введут что то вменяемое (см. условия в cellQ)
                    out.print("Введите номер клетки: ");
                    x = in.nextInt();
                    y = in.nextInt();
                    if (t.cellQ(x-1,y-1)) break; //Если ввели числа от 1 до 3 и эта клетка не занята, то цикл прерываем
                }
                t.writeX(x-1,y-1); //вписываем ход
                t.nextTurn(); //след ход
                String check = t.endQ(); //проверка на чью то победу
                if (check=="x") {t.printMap();out.println("Победил игрок " + t.getPl2());break;}
                else if (check=="o") {t.printMap();out.println("Победил игрок " + t.getPl1());break;}
            } else { // все то же самое но для ходов игрока, ставящего нолики
                boolean flg = false;
                int x=0, y=0;
                while (!flg) { //Крутим цикл ввода хода пока не введут что то вменяемое (см. условия в cellQ)
                    out.print("Введите номер клетки: ");
                    x = in.nextInt();
                    y = in.nextInt();
                    if (t.cellQ(x-1,y-1)) break; //Если ввели числа от 1 до 3 и эта клетка не занята, то цикл прерываем
                }
                t.writeO(x-1,y-1);
                t.nextTurn();
                String check = t.endQ();
                if (check=="x") {t.printMap();out.println("Победил игрок " + t.getPl2());break;}
                else if (check=="o") {t.printMap();out.println("Победил игрок " + t.getPl1());break;}
            }
        }
    }
    public static void main(String[] args) {
        boolean flag = true;
        while (flag) { //крутим цикл пока пользователь не наиграется
            gameLoop();
            out.print("Введите 1, чтобы сыграть снова: ");
            int s = in.nextInt();
            if (s!=1) break;
            in.nextLine();
        }
    }
}