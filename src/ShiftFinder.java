import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ShiftFinder {
    static BufferedImage image1;
    static BufferedImage image2;
    static final int CALCULATION_ACCELERATION_MULTIPLIER = 80;
    static final int RED = 0;
    static final int GREEN = 1;
    static final int BLUE = 2;
    static int c[] = {0, 0, 0, 0};

    static int matrix1Red[][];
    static int matrix2Red[][];


    static int[] bestShifts(BufferedImage i1, BufferedImage i2) {
        int bestXShift = 0;
        int bestYShift = 0;
        int maxSum = 0;

        WritableRaster im1 = i1.getRaster();
        WritableRaster im2 = i2.getRaster();

        matrix1Red = writableRasterToMatrix(im1, RED);
        matrix2Red = writableRasterToMatrix(im2, RED);

        System.out.println(matrix1Red[1][390]);

        //for(int yShift = -im2.getHeight() + 1; yShift < im1.getHeight(); yShift++){
        for(int yShift = 130; yShift < im1.getHeight()/4; yShift++){
            //int yShift = 0;
            System.out.println("yShift: " + yShift);
            for(int xShift = -im2.getWidth() + 1; xShift < im1.getWidth(); xShift++){

                int sum = ShiftFinder.getCrossCorrelationHorizontal(im1, im2, xShift, yShift) +
                        ShiftFinder.getCrossCorrelationVertical(im1, im2, xShift, yShift);
                if(sum > maxSum){
                    maxSum = sum;
                    bestXShift = xShift;
                    bestYShift = yShift;
                    //System.out.println("sum: " + sum);
                    //System.out.println(sum);

                }
            }
        }

        return new int[] {bestXShift, bestYShift};
    }

    static int getCrossCorrelationHorizontal(WritableRaster im1, WritableRaster im2, int xShift, int yShift){
        //int maxSum = 0;
        //int bestShift = 0;
        int sum = 0;
        for(int y = Math.max(0, yShift); y < Math.min(im1.getHeight() - 1, im2.getHeight() - 1 + yShift); y += CALCULATION_ACCELERATION_MULTIPLIER){
            //correlation between lines

            for (int x = Math.max(0, xShift); x < Math.min(im1.getWidth() - 1, im2.getWidth() - 1 + xShift); x++) {
                //sum +=  matrix1Red[x][y] * matrix2Red[x - xShift][y];
            }
        }
        return sum;
    }

    static int getCrossCorrelationVertical(WritableRaster im1, WritableRaster im2, int xShift, int yShift){
        //int maxSum = 0;
        //int bestShift = 0;
        int sum = 0;
        for (int x = Math.max(0, xShift); x < Math.min(im1.getWidth() - 1, im2.getWidth() - 1 + xShift); x += CALCULATION_ACCELERATION_MULTIPLIER) {
            //correlation between columns
            int sumUntil = Math.min(im1.getHeight() - 1, im2.getHeight() - 1 + yShift);


            for (int y = Math.max(0, yShift); y < sumUntil; y++) {
                sum += matrix1Red[x][y] * matrix2Red[x][y - yShift];
            }
        }
        return sum;
    }

    static int[][] writableRasterToMatrix(WritableRaster raster, int color){
        int iArray[] = new int [raster.getHeight()];
        int matrix[][] = new int[raster.getWidth()][raster.getHeight()];

        for(int x = 0; x < raster.getWidth(); x++ ){
            matrix[x] = raster.getSamples(x,0, 1, raster.getHeight(), color,  iArray);
        }
        return matrix;
    }
}
