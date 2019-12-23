package kc.ml.seeker.entities;

import java.util.Arrays;
import java.util.Comparator;

public class Population {

    private Dot[] dots;

    public Population(int size, double posX, double posY) {
        dots = new Dot[size];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new Dot(posX, posY);
        }
    }

    public Dot[] getDots() {
        return dots;
    }

    public boolean isMoving() {
        for (Dot dot : dots) {
            if (dot.isMoving()) {
                return true;
            }
        }
        return false;
    }

    public void doNaturalSelection() {

        final Dot[] descendants = new Dot[dots.length];
        final Dot mostFit = Arrays.stream(dots).max(Comparator.comparingDouble(Dot::getFitness)).get();
        descendants[0] = new Dot(mostFit, 0);
        descendants[0].setMostFit(true);

        for (int i = 1; i < descendants.length; i++) {
            descendants[i] = selectDot();
            descendants[i].setMostFit(false);
        }

        dots = descendants;
    }

    private Dot selectDot() {
        final double cumulativeFitness = Arrays.stream(dots).mapToDouble(Dot::getFitness).sum();
        final double threshold = Math.random() * cumulativeFitness;

        double runningSum = 0;
        // FIXME biased towards beginning of array
        for (Dot dot : dots) {
            runningSum += dot.getFitness();
            if (runningSum > threshold) {
                return new Dot(dot, 0.01);
            }
        }

        return null;
    }
}
