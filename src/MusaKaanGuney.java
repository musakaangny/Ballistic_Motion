import java.awt.Font;
import java.awt.event.KeyEvent;

public class MusaKaanGuney {
    public static void main(String[] args) {
        // Game Parameters
        int width = 1600; //screen width
        int height = 800; // screen height
        double gravity = 9.80665; // gravity
        double x0 = 120; // x and y coordinates of the bullet’s starting position on the platform
        double y0 = 120;
        double bulletVelocity = 180.0; // initial velocity
        double bulletAngle = 45.0; // initial angle
        double[][] obstacleArray = {
                {1200, 0, 60, 220},
                {1000, 0, 60, 160},
                {600, 0, 60, 80},
                {600, 180, 60, 160},
                {220, 0, 120, 180}
        };
        double[][] targetArray = {
                {1160, 0, 30, 30},
                {730, 0, 30, 30},
                {150, 0, 20, 20},
                {1480, 0, 60, 60},
                {340, 80, 60, 30},
                {1500, 600, 60, 60}
        };


        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);
        StdDraw.enableDoubleBuffering();

        double circle_center_x = 120;
        double circle_center_y = 120;
        double initialX = 120;
        double initialY = 120;
        double x_velocity = Math.cos(bulletAngle * Math.PI / 180) * bulletVelocity;
        double y_velocity = Math.sin(bulletAngle * Math.PI / 180) * bulletVelocity;
        double start = System.currentTimeMillis();
        boolean started = false;
        boolean checker = false;
        boolean movement = true;
        while (true) {
            double current = System.currentTimeMillis();
            double dt = (current - start) * 0.003;
            start = System.currentTimeMillis();
            if (!started && !checker) {
                StdDraw.clear();
            }
            if (movement) {
                if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT) && !started) {
                    bulletVelocity -= 1.0;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT) && !started) {
                    bulletVelocity += 1.0;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_UP) && !started) {
                    bulletAngle += 1.0;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN) && !started) {
                    bulletAngle -= 1.0;
                }
                StdDraw.pause(30);

            for (int i = 0; i < obstacleArray.length; i++) {
                StdDraw.setPenColor(StdDraw.DARK_GRAY);
                StdDraw.filledRectangle(obstacleArray[i][0] + obstacleArray[i][2] / 2, obstacleArray[i][1] + obstacleArray[i][3] / 2, obstacleArray[i][2] / 2, obstacleArray[i][3] / 2);
            }

            for (int j = 0; j < targetArray.length; j++) {
                StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                StdDraw.filledRectangle(targetArray[j][0] + targetArray[j][2] / 2, targetArray[j][1] + targetArray[j][3] / 2, targetArray[j][2] / 2, targetArray[j][3] / 2);
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledRectangle(x0 / 2, y0 / 2, 60, 60);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.005);
            double x1 = x0 + Math.cos(bulletAngle * Math.PI / 180) * bulletVelocity / 3, y1 = y0 + Math.sin(bulletAngle * Math.PI / 180) * bulletVelocity / 3;
            StdDraw.line(x0, y0, x1, y1);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Serif", Font.PLAIN, 20));
            StdDraw.textLeft(35, 80, "a: " + bulletAngle);

            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(new Font("Serif", Font.PLAIN, 20));
            StdDraw.textLeft(35, 50, "v: " + bulletVelocity);

            if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && !started) {
                started = true;
                x_velocity = Math.cos(bulletAngle * Math.PI / 180) * bulletVelocity;
                y_velocity = Math.sin(bulletAngle * Math.PI / 180) * bulletVelocity;
            }

            if (started){
                initialX = circle_center_x;
                initialY = circle_center_y;
                y_velocity -= gravity * 2.940 * dt;
                circle_center_y += y_velocity * dt;
                circle_center_x += x_velocity * dt;
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.001);
                StdDraw.line(initialX, initialY, circle_center_x, circle_center_y);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                StdDraw.point(circle_center_x, circle_center_y);

                if (checkCollision(circle_center_x, circle_center_y)){
                    StdDraw.show();
                    started = false;
                    checker = true;
                    movement = false;
                }
                else if (isFail(circle_center_x, circle_center_y)){
                    StdDraw.show();
                    started = false;
                    checker = true;
                    movement = false;
                }
            }
            }
            if (StdDraw.isKeyPressed(KeyEvent.VK_R) && !started && !movement) {
                StdDraw.clear();
                checker = false;
                movement = true;
                circle_center_x = 120;
                circle_center_y = 120;
                initialX = 120;
                initialY = 120;
                bulletVelocity = 180.0;
                bulletAngle = 45.0;
                StdDraw.show();
            }
            StdDraw.show();
            StdDraw.pause(3);
        }
    }

    public static boolean checkCollision(double circle_center_x, double circle_center_y){
        if (circle_center_x>1160 && circle_center_x<1190 && circle_center_y<30 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        if (circle_center_x>730 && circle_center_x<760 && circle_center_y<30 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        if (circle_center_x>150 && circle_center_x<170 && circle_center_y<20 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        if (circle_center_x>1480 && circle_center_x<1540 && circle_center_y<60 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        if (circle_center_x>340 && circle_center_x<400 && circle_center_y<110 && circle_center_y>80){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        if (circle_center_x>1500 && circle_center_x<1560 && circle_center_y<660 && circle_center_y>600){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Congratulations: You hit the target!");
            return true;
        }
        return false;
    }

    public static boolean isFail(double circle_center_x, double circle_center_y){
        if (circle_center_x>1200 && circle_center_x<1260 && circle_center_y<220 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit an obstacle. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_x>1000 && circle_center_x<1060 && circle_center_y<160 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit an obstacle. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_x>600 && circle_center_x<660 && circle_center_y<80 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit an obstacle. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_x>600 && circle_center_x<660 && circle_center_y<340 && circle_center_y>180){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit an obstacle. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_x>220 && circle_center_x<340 && circle_center_y<180 && circle_center_y>0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit an obstacle. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_y < 0){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Hit the ground. Press 'r' to shoot again.");
            return true;
        }
        if (circle_center_x > 1600) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont( new Font("Roboto", Font.PLAIN, 25) );
            StdDraw.textLeft(35, 775, "Max X reached. Press 'r' to shoot again.");
            return true;
        }
        return false;
    }
}
