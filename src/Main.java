import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class Main {
    static BufferedImage image1;
    static BufferedImage image2;
    static int CALCULATION_ACCELERATION_MULTIPLIER = 50;
    static int c[] = {0, 0, 0, 0};

    public static void main(String[] args){
        try {
            image1 = ImageIO.read(new File(args[0]));
            image2 = ImageIO.read(new File(args[1]));
            System.out.println("images had been loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long beginCalculationtime = System.currentTimeMillis();

        int bestXShift = 0;
        int bestYShift = 0;
        int maxSum = 0;

        WritableRaster im1 = image1.getRaster();
        WritableRaster im2 = image2.getRaster();


        //for(int yShift = -im2.getHeight() + 1; yShift < im1.getHeight(); yShift++){
        for(int yShift = 130; yShift < im1.getHeight()/4; yShift++){
        //int yShift = 0;
            System.out.println("yShift: " + yShift);
            for(int xShift = -im2.getWidth() + 1; xShift < im1.getWidth(); xShift++){

                int sum = getCrossCorrelationHorizontal(im1, im2, xShift, yShift) + getCrossCorrelationVertical(im1, im2, xShift, yShift);
                if(sum > maxSum){
                    maxSum = sum;
                    bestXShift = xShift;
                    bestYShift = yShift;
                    //System.out.println("sum: " + sum);
                    //System.out.println(sum);

                }
            }
        }


        System.out.println("Best shift: " + bestXShift + " x, " + bestYShift + " y");
        System.out.println("Calculation time: " + (System.currentTimeMillis() - beginCalculationtime + " ms"));
    }

    static int getCrossCorrelationHorizontal(WritableRaster im1, WritableRaster im2, int xShift, int yShift){
        //int maxSum = 0;
        //int bestShift = 0;
        int sum = 0;
        for(int y = Math.max(0, yShift); y < Math.min(im1.getHeight() - 1, im2.getHeight() - 1 + yShift); y += CALCULATION_ACCELERATION_MULTIPLIER){
            //correlation between lines

            for (int x = Math.max(0, xShift); x < Math.min(im1.getWidth() - 1, im2.getWidth() - 1 + xShift); x++) {
                sum +=  x + y + xShift;//im1.getPixel(x, y, c)[2] * im2.getPixel(x - xShift, y, c)[2];
            }
        }

        return sum;
    }

    static int getCrossCorrelationVertical(WritableRaster im1, WritableRaster im2, int xShift, int yShift){
        //int maxSum = 0;
        //int bestShift = 0;
        int sum = 0;
        for (int x = Math.max(0, xShift); x < Math.min(im1.getWidth() - 1, im2.getWidth() - 1 + xShift); x += CALCULATION_ACCELERATION_MULTIPLIER) {
            //correlation between koloms
            int sumUntil = Math.min(im1.getHeight() - 1, im2.getHeight() - 1 + yShift);


            for (int y = Math.max(0, yShift); y < sumUntil; y++) {
                sum += x + y + yShift;//im1.getPixel(x, y, c)[2] * im2.getPixel(x, y - yShift, c)[2];
            }
        }

        return sum;
    }

}
