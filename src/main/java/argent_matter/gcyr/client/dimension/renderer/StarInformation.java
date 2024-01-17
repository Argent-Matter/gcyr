package argent_matter.gcyr.client.dimension.renderer;

import net.minecraft.Util;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.Random;
import java.util.function.BiFunction;

public class StarInformation {
    public static final int BASE_COLOUR = 0xFFFFFFFF;
    public static final int[] STAR_COLOURS = new int[]{
        BASE_COLOUR,
        0xFFCCEEFF,
        0xFFCC99FF,
        0xFFFF99FF,
        0xFFFFCC66
    };
    public static final BiFunction<Long, Integer, StarInformation> STAR_CACHE = Util.memoize(StarInformation::new);
    private final Vector3f[] param1;
    private final float[] multiplier;
    private final float[] randomPi;
    private final int[][] colour;

    public StarInformation(long seed, int stars) {
        param1 = new Vector3f[stars];
        multiplier = new float[stars];
        randomPi = new float[stars];
        colour = new int[stars][4];

        Random random = new Random(seed);
        for (int i = 0; i < stars; i++) {
            float d = random.nextFloat() * 2.0f - 1.0f;
            float e = random.nextFloat() * 2.0f - 1.0f;
            float f = random.nextFloat() * 2.0f - 1.0f;
            param1[i] = new Vector3f(d, e, f);
            multiplier[i] = 0.15f + random.nextFloat() * 0.01f;
            randomPi[i] = random.nextFloat() * Mth.TWO_PI;
            for (int j = 0; j < 4; j++) {
                colour[i][j] = STAR_COLOURS[random.nextInt(STAR_COLOURS.length)];
            }
        }
    }

    public Vector3f getParam1(int i) {
        return param1[i];
    }

    public float getMultiplier(int i) {
        return multiplier[i];
    }

    public float getRandomPi(int i) {
        return randomPi[i];
    }

    public int getColour(int i, int j, boolean coloured) {
        return coloured ? colour[i][j] : BASE_COLOUR;
    }
}