import java.util.Random;

public class Bot extends Item{
    private String forbiddenDirection = "+y";

    public Bot(int speed, int x, int y, CarRace cr) {
        super(cr, speed);
        setX(x);
        setY(y);
    }

    @Override
    public void run() {
        Thread t1 = new Thread();
        Random r = new Random();
        while (true) {

            if (isColliding()) {
                if (isColliding()) { // TODO
                    Object collision = collision();
                    if (collision != null && collision.equals("inner")) {
                        if (this.getQuarterIn() == 1) {
                            setY(getY() - 2);
                        }
                        if (this.getQuarterIn() == 2) {
                            setX(getX() + 2);
                        }
                        if (this.getQuarterIn() == 3) {
                            setY(getY() + 2);
                        }
                        if (this.getQuarterIn() == 4) {
                            setX(getX() - 2);
                        }

                        sleep(500);
                    } else if (collision != null && collision.equals("outer")) {
                        if (this.getQuarterIn() == 1) {
                            setY(getY() + 2);
                        }
                        if (this.getQuarterIn() == 2) {
                            setX(getX() - 2);
                        }
                        if (this.getQuarterIn() == 3) {
                            setY(getY() - 2);
                        }
                        if (this.getQuarterIn() == 4) {
                            setX(getX() + 2);
                        }

                        sleep(500);
                    } else if (collision instanceof int[]) {
                        int otherX = ((int[]) (collision))[0];
                        int otherY = ((int[]) (collision))[1];
                        setX(2 * getX() - otherX);
                        setY(2 * getY() - otherY);

                        sleep(500);
                    }
                }


            } else {
                int[][] choices = new int[3][2];

                if (getX() + getY() >= 760 && getY() - getX() >= 20) {
                    choices = new int[][]{{-1, 0}, {0, 1}, {0, -1}};
                }
                if (getX() + getY() < 760 && getY() - getX() < 20) {
                    choices = new int[][]{{1, 0}, {0, 1}, {0, -1}};
                }
                if (getX() + getY() < 760 && getY() - getX() >= 20) {
                    choices = new int[][]{{1, 0}, {-1, 0}, {0, -1}};
                }
                if (getX() + getY() >= 760 && getY() - getX() < 20) {
                    choices = new int[][]{{1, 0}, {-1, 0}, {0, 1}};
                }
                int rand = r.nextInt(3);

                setX(getX() + choices[rand][0]);
                setY(getY() + choices[rand][1]);

                sleep();
            }
        }
    }


}
