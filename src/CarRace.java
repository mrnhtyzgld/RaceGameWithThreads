import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CarRace extends JPanel {
    private int speed;
    private Bot[] bots = new Bot[5];
    private Player player_0, player_1;
    private Item winner;
    private String lastTime;


    final int sizeOfCars = 10; // px
    private boolean isContinue = true;
    private Timer timer;

    public int[][] coordinates = new int[7][2];

    public Player getPlayer_0() {
        return player_0;
    }

    public Player getPlayer_1() {
        return player_1;
    }

    public int[][] getCoordinates() {

        for (int a = 0; a < 5; a++) {
            coordinates[a][0] = bots[a].getX();
            coordinates[a][1] = bots[a].getY();
        }
        coordinates[5][0] = player_0.getX();
        coordinates[5][1] = player_0.getY();
        coordinates[6][0] = player_1.getX();
        coordinates[6][1] = player_1.getY();

        return coordinates;
    }

    class Keys implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 38) { // 2. oyuncu
                player_1.addInput("w");

            } if (e.getKeyCode() == 37) {
                player_1.addInput("a");

            } if (e.getKeyCode() == 40) {
                player_1.addInput("s");

            } if (e.getKeyCode() == 39) {
                player_1.addInput("d");

            } if (("" +e.getKeyChar()).equals("w")) { // 1. oyuncu
                player_0.addInput("w");

            } if (("" +e.getKeyChar()).equals("a")) {
                player_0.addInput("a");

            } if (("" +e.getKeyChar()).equals("s")) {
                player_0.addInput("s");

            } if (("" +e.getKeyChar()).equals("d")) {
                player_0.addInput("d");

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 38) { // 2. player
                player_1.deleteInput("w");

            } if (e.getKeyCode() == 37) {
                player_1.deleteInput("a");

            } if (e.getKeyCode() == 40) {
                player_1.deleteInput("s");

            } if (e.getKeyCode() == 39) {
                player_1.deleteInput("d");

            } if (("" +e.getKeyChar()).equals("w")) { // 1. player
                player_0.deleteInput("w");

            } if (("" +e.getKeyChar()).equals("a")) {
                player_0.deleteInput("a");

            } if (("" +e.getKeyChar()).equals("s")) {
                player_0.deleteInput("s");

            } if (("" +e.getKeyChar()).equals("d")) {
                player_0.deleteInput("d");

            }
        }
    }

    class Timer extends Thread {
        private long time;
        private long millisecond;
        private int minute, second, splitSecond;
        public Timer(){
            time = 0;
            start();
        }

        @Override
        public void run() {
            long start = System.nanoTime();
            while (isContinue) {
                time = System.nanoTime() - start;
                millisecond = time/1000000;
                splitSecond = ((int)millisecond/(1000/60))%60;
                second = ((int)millisecond / 1000)%60;
                minute = ((int)millisecond/(1000*60)%60);
            }
        }
    }

    public void game(int speed){
        JFrame frame = new JFrame();
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.addKeyListener(new Keys());
        frame.setLocationRelativeTo(null);
        frame.add(this);




        this.speed = speed;
        timer = new Timer();


        for (int a = 0; a < bots.length; a++) {
            bots[a] = new Bot(speed, 20 + 150/8*(a + 3), 390 - sizeOfCars/2, this);
        }

        player_0 = new Player(speed, 20 + 150/8, 390 - sizeOfCars/2, this);
        player_1 = new Player(speed, 20 + 2*150/8, 390 - sizeOfCars/2, this);

        getCoordinates();

        frame.setVisible(true);

        for (int a = 0; a < bots.length; a++) {
            bots[a].start();
        }
        player_0.start();
        player_1.start();

        int[] finishStates = new int[]{0, 0, 0, 0, 0, 0, 0}; // bots, player 0, player 1
        Integer[] olds = new Integer[]{null, null, null, null, null, null, null};
        int[] currents = new int[]{0, 0, 0, 0, 0, 0, 0};

        while (isContinue) {
            repaint();



            Item[] everybody = new Item[7];
            for (int a = 0; a < 5; a++) {
                everybody[a] = bots[a];
            }
            everybody[5] = player_0;
            everybody[6] = player_1;

            for (int a = 0; a < 7; a++) {
                if (everybody[a].getX() >= 10 && everybody[a].getX() <= 160) {
                    if (everybody[a].getY() > 390) {
                        currents[a] = -1;
                    } else if (everybody[a].getY() <= 390 && everybody[a].getY() >= 380) {
                        currents[a] = 0;
                    } else {
                        currents[a] = 1;
                    }
                }
            }

            for (int a = 0; a < 7; a++) {
                if (olds[a] != null && olds[a] == -1 && currents[a] == 0) {
                    finishStates[a]++;
                } if (olds[a] != null && olds[a] == 0 && currents[a] == -1) {
                    finishStates[a]--;
                }

            }
            for (int a = 0; a < 7; a++) {
                olds[a] = currents[a];
            }



            for (int a = 0; a < 7; a++) {
                if (finishStates[a] == 1) {
                    isContinue = false;
                    winner = everybody[a];
                    lastTime = ""+timer.minute + ":" + timer.second + ":" + timer.splitSecond;
                }
            }
        }

        JFrame popup = new JFrame();
        popup.setSize(500,100);
        frame.setLocationRelativeTo(null);
        popup.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        popup.setLayout(new BorderLayout());
        String text = "";
        if (winner.equals(player_0)) text = "1. Oyuncu";
        else if (winner.equals(player_1)) text = "2. Oyuncu";
        else for (int aa = 0; aa < 5; aa++) {
            if (winner.equals(bots[aa])) text = "" + aa + ". Bot";

        }
        JLabel label = new JLabel(text + " Kazandı! Suresi: " + lastTime);
        popup.add(new JPanel(), BorderLayout.SOUTH);
        popup.add(new JPanel(), BorderLayout.WEST);
        popup.add(label, BorderLayout.CENTER);
        popup.setVisible(true);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(getBackground());
        g.fillRect(0,0,800,800);
        g.setColor(Color.BLACK);
        g.drawString(""+timer.minute + ":" + timer.second + ":" + timer.splitSecond, 10,30);
        g.drawOval(20,40,700,700);
        g.drawOval(170,190,400,400);
        g.drawLine(20,390,170,390);


        g.setColor(Color.BLACK);
        for (Bot bot: bots) {
            g.fillRect(bot.getX(), bot.getY(), sizeOfCars, sizeOfCars);
        }

        g.setColor(Color.RED);
        g.fillRect(player_0.getX(), player_0.getY(), sizeOfCars, sizeOfCars);
        g.setColor(Color.GREEN);
        g.fillRect(player_1.getX(), player_1.getY(), sizeOfCars, sizeOfCars);

    }

}
