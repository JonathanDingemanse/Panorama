import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args){
        BufferedImage image1;
        BufferedImage image2;

        try {
            image1 = ImageIO.read(new File(args[0]));
            image2 = ImageIO.read(new File(args[1]));
            System.out.println("images had been loaded");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        long beginCalculationTime = System.currentTimeMillis();

        int[] shifts = ShiftFinder.bestShifts(image1, image2);

        int bestXShift = shifts[0];
        int bestYShift = shifts[1];

        System.out.println("Best shift: " + bestXShift + " x, " + bestYShift + " y");
        System.out.println("Calculation time: " + (System.currentTimeMillis() - beginCalculationTime + " ms"));
    }
}
