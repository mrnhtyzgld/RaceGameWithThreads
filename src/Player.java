public class Player extends Item{
    boolean isClicking_W = false, isClickind_A = false, isClicking_S = false, isClicking_D = false;
    public Player(int speed, int startingX, int startingY, CarRace cr) {
        super(cr, speed);
        setX(startingX);
        setY(startingY);

    }
    public void addInput(String input) {
        if (input.equals("w")) {
            isClicking_W = true;
        } if (input.equals("a")) {
            isClickind_A = true;

        } if (input.equals("s")) {
            isClicking_S = true;

        } if (input.equals("d")) {
            isClicking_D = true;

        }
    }
    public void deleteInput(String input) {
        if (input.equals("w")) {
            isClicking_W = false;

        } else if (input.equals("a")) {
            isClickind_A = false;

        } else if (input.equals("s")) {
            isClicking_S = false;

        } else if (input.equals("d")) {
            isClicking_D = false;

        }
    }

    @Override
    public void run() {
        while (true) {
            if (isColliding()) { // TODO
                Object collision = collision();
                if (collision != null && collision.equals("inner")) {
                    if (this.getQuarterIn() == 1 )   {
                        setY(getY() - 2);
                    } if (this.getQuarterIn() == 2 ) {
                        setX(getX() + 2);
                    } if (this.getQuarterIn() == 3 ) {
                        setY(getY() + 2);
                    } if (this.getQuarterIn() == 4 ) {
                        setX(getX() - 2);
                    }

                    sleep(500);
                } else if (collision != null && collision.equals("outer")) {
                    if (this.getQuarterIn() == 1 )   {
                        setY(getY() + 2);
                    } if (this.getQuarterIn() == 2 ) {
                        setX(getX() - 2);
                    } if (this.getQuarterIn() == 3 ) {
                        setY(getY() - 2);
                    } if (this.getQuarterIn() == 4 ) {
                        setX(getX() + 2);
                    }

                    sleep(500);
                } else if (collision != null && collision instanceof int[]) {
                    int otherX = ((int[])(collision))[0];
                    int otherY = ((int[])(collision))[1];

                    if (getX()-otherX > 0) {
                        setX(getX()+2);
                    } else {
                        setX(getX() - 2);
                    }

                    if (getY() - otherY > 0) {
                        setY(getY() + 2);
                    } else {
                        setY(getY() - 2);
                    }

                    sleep(500);
                }
            } else {
                if (isClicking_D)
                    setX(getX()+2);

                if (isClicking_S)
                    setY(getY()+2);

                if (isClickind_A)
                    setX(getX()-2);

                if (isClicking_W)
                    setY(getY()-2);

                sleep();

            }
        }

    }
}
