import java.util.ArrayList;

public final class DiscreteFourierTransform {
    public static ArrayList<Coefficient> transform(Double[] func) {
        var coeff = new ArrayList<Coefficient>();
        int N = func.length;
        double re, im, ampl, freq, phase;

        for (int k = 0; k < N; ++k) {
            re = 0;
            im = 0;

            for (int n = 0; n < N; ++n) {
                double angle = 2 * Math.PI * k * n / N;
                re += func[n] * Math.cos(angle);
                im -= func[n] * Math.sin(angle);
            }

            re /= N;
            im /= N;
            ampl = Math.sqrt(re * re + im * im);
            freq = k;
            phase = Math.atan2(im, re);

            coeff.add(new Coefficient(ampl, freq, phase));
        }

        return coeff;
    }
}
