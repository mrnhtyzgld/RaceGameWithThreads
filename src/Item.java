public abstract class Item extends Thread{
    final int refresh_ms;
    private int x, y;
    private CarRace cr;
    public Item(CarRace cr, int speed) {
        this.cr = cr;
        this.refresh_ms = 1000 / speed;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getQuarterIn() { // 1 2 3 4
        if (x >= y && y >= 720 - x) {
            return 2;
        } else if (x >= y &&  y < 720 - x) {
            return 1;
        } else if (x < y &&  y >= 720 - x) {
            return 3;
        } else if (x < y &&  y < 720 - x) {
            return 4;
        }
        return -1;
    }

    public boolean isColliding() {
        int[][] coordinates = cr.getCoordinates();


        for (int[] xy : coordinates) {
            if ((xy[0] != this.x || xy[1] != this.y) && (Math.abs(xy[0] - x) <= 10 && Math.abs(xy[1] - y) <= 10)) {
                return true;
            }
        }
        if ((cr.getPlayer_0().getX() != this.x || cr.getPlayer_0().getY() != this.y) && (Math.abs(cr.getPlayer_0().getX() - x) <= 10 && Math.abs(cr.getPlayer_0().getY() - y) <= 10)) {
            return true;
        }
        if ((cr.getPlayer_1().getX() != this.x || cr.getPlayer_1().getY() != this.y) && (Math.abs(cr.getPlayer_1().getX() - x) <= 10 && Math.abs(cr.getPlayer_1().getY() - y) <= 10)) {
            return true;
        }


        // inner circle
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y - 390), 2) <= 200*200)
            return true;
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) <= 200*200)
            return true;
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y - 390), 2) <= 200*200)
            return true;
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) <= 200*200)
            return true;

        // outer circle
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y - 390), 2) > 350*350)
            return true;
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) > 350*350)
            return true;
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y - 390), 2) > 350*350)
            return true;
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) > 350*350)
            return true;




        return false;
    }

    public Object collision() {
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y - 390), 2) <= 200*200)
            return "inner";
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) <= 200*200)
            return "inner";
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y - 390), 2) <= 200*200)
            return "inner";
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) <= 200*200)
            return "inner";

        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y - 390), 2) > 350*350)
            return "outer";
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) > 350*350)
            return "outer";
        if (Math.pow(Math.abs(x + 10 - 370), 2)  + Math.pow(Math.abs(y - 390), 2) > 350*350)
            return "outer";
        if (Math.pow(Math.abs(x - 370), 2)  + Math.pow(Math.abs(y + 10 - 390), 2) > 350*350)
            return "outer";


        int[][] coordinates = cr.getCoordinates();


        for (int[] xy : coordinates) {
            if ((xy[0] != this.x || xy[1] != this.y) && (Math.abs(xy[0] - x) <= 10 && Math.abs(xy[1] - y) <= 10)) {
                return new int[]{xy[0], xy[1]};
            }
        }
        if ((cr.getPlayer_0().getX() != this.x || cr.getPlayer_0().getY() != this.y) && (Math.abs(cr.getPlayer_0().getX() - x) <= 10 && Math.abs(cr.getPlayer_0().getY() - y) <= 10)) {
            return new int[]{cr.getPlayer_0().getX(), cr.getPlayer_0().getY()};
        }
        if ((cr.getPlayer_1().getX() != this.x || cr.getPlayer_1().getY() != this.y) && (Math.abs(cr.getPlayer_1().getX() - x) <= 10 && Math.abs(cr.getPlayer_1().getY() - y) <= 10)) {
            return new int[]{cr.getPlayer_1().getX(), cr.getPlayer_1().getY()};
        }

        return null;


    }

    public void sleep() {
        try {
            Thread.sleep(refresh_ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void sleep(int time) {
        try {
            Thread.sleep(time );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
