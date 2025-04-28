import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String pathOfCSVFile = "C:/users/addyx/IdeaProjects/DrawingWithEpicycles/src/Data/edge coordinates.csv";
        Image image = new Image(new Scanner(new File(pathOfCSVFile)).useDelimiter(","));

        Double[] imageXCoords = image.getAllX();
        Double[] imageYCoords = image.getAllY();
        // Double[] imageXCoords = image.getSamplesX(0.1);
        // Double[] imageYCoords = image.getSamplesY(0.1);

        int frameWidth = 1000;
        int frameHeight = 700;

        JFrame f = new JFrame("Drawing by Epicycles");
        f.setSize(frameWidth, frameHeight);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.add(new EpicyclesDrawing(frameWidth, frameHeight, imageXCoords, imageYCoords));
        f.setVisible(true);
    }
}
