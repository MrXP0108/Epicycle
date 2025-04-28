import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

public class EpicyclesDrawing extends JPanel {

    private final int frameWidth, frameHeight;
    private final ArrayList<Coefficient> coeffsX, coeffsY;
    private double time = 0;
    private final double dt;
    private final JButton bt_showCircles = new JButton("Display Epicycles");
    private boolean circlesVisible = false;
    private final JButton bt_showLines = new JButton("Display Lines");
    private boolean linesVisible = false;

    public EpicyclesDrawing(int frameWidth, int frameHeight, Double[] imageXCoords, Double[] imageYCoords) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        coeffsX = DiscreteFourierTransform.transform(imageXCoords);
        coeffsY = DiscreteFourierTransform.transform(imageYCoords);
        coeffsX.sort(Comparator.comparing(Coefficient::ampl).reversed());
        coeffsY.sort(Comparator.comparing(Coefficient::ampl).reversed());

        dt = 2 * Math.PI / imageXCoords.length;
        int delay = 3000 / imageXCoords.length;
        Timer timer = new Timer(delay, e -> {
            time += dt;
            if (time >= 2 * Math.PI) {
                time = 0;
            }
            repaint();
        });
        timer.start();

        this.add(bt_showCircles);
        bt_showCircles.addActionListener(e -> {
            if (circlesVisible) {
                bt_showCircles.setText("Display Epicycles");
                circlesVisible = false;
            } else {
                bt_showCircles.setText("Hide Epicycles");
                circlesVisible = true;
            }
        });

        this.add(bt_showLines);
        bt_showLines.addActionListener(e -> {
            if (linesVisible) {
                bt_showLines.setText("Display Lines");
                linesVisible = false;
            } else {
                bt_showLines.setText("Hide Lines");
                linesVisible = true;
            }
        });

        JLabel lb_n_samples = new JLabel();
        lb_n_samples.setText("Number of samples: " + imageXCoords.length);
        this.add(lb_n_samples);
    }

    private final LinkedList<Point> pointsInGraph = new LinkedList<>();

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;

        double x = frameWidth / 2.0, y = frameHeight / 2.0;
        double x_prev, y_prev, ampl, freq, phase;

        for (int i = 1; i < coeffsX.size(); ++i) {
            x_prev = x;
            y_prev = y;
            ampl = coeffsX.get(i).ampl();
            freq = coeffsX.get(i).freq();
            phase = coeffsX.get(i).phase();

            x += ampl * Math.cos(freq * time + phase);
            y += ampl * Math.sin(freq * time + phase);

            if (circlesVisible) {
                g2.drawOval((int) (x_prev - ampl), (int) (y_prev - ampl), (int) (2 * ampl), (int) (2 * ampl));
                g2.drawLine((int) x_prev, (int) y_prev, (int) x, (int) y);
            }
        }

        for (int i = 1; i < coeffsY.size(); ++i) {
            x_prev = x;
            y_prev = y;
            ampl = coeffsY.get(i).ampl();
            freq = coeffsY.get(i).freq();
            phase = coeffsY.get(i).phase();

            x += ampl * Math.cos(freq * time + phase + Math.PI / 2);
            y += ampl * Math.sin(freq * time + phase + Math.PI / 2);

            if (circlesVisible) {
                g2.drawOval((int) (x_prev - ampl), (int) (y_prev - ampl), (int) (2 * ampl), (int) (2 * ampl));
                g2.drawLine((int) x_prev, (int) y_prev, (int) x, (int) y);
            }
        }

        if (pointsInGraph.size() > coeffsX.size() * 1) {
            pointsInGraph.remove();
        }
        pointsInGraph.add(new Point(x, y));

        if (linesVisible) {
            g2.setColor(Color.red);
            for (int i = 0; i < pointsInGraph.size() - 1; ++i) {
                if (Point.findDistance(pointsInGraph.get(i), pointsInGraph.get(i + 1)) < 30) {
                    g2.drawLine((int) pointsInGraph.get(i).x(), (int) pointsInGraph.get(i).y(),
                            (int) pointsInGraph.get(i + 1).x(), (int) pointsInGraph.get(i + 1).y());
                }
            }
            g2.setColor(Color.black);
        }
    }
}
