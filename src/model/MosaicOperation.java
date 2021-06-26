package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * An operation that creates a 'stained glass' effect from an image by averaging the colors of
 * clusters of pixels closes to a specified number of seeds locations.
 */
public class MosaicOperation implements ImageOperation {

  private final int numSeeds;
  private final Random rand;

  /**
   * Creates a new MosaicOperation using the provided Random object to generate the seeds for each
   * image.
   *
   * @param numSeeds the number of seeds to use.
   * @param rand     the random object used to generate the seeds.
   */
  public MosaicOperation(int numSeeds, Random rand) {
    if (rand == null) {
      throw new IllegalArgumentException("Random cannot be null.");
    }
    if (numSeeds <= 0) {
      throw new IllegalArgumentException("Moasicing requires a positive number of seeds.");
    }

    this.numSeeds = numSeeds;
    this.rand = rand;
  }

  /**
   * Creates a new MosaicOperation that randomly creates the given number of seeds for each image.
   *
   * @param numSeeds the number of seeds to use.
   */
  public MosaicOperation(int numSeeds) {
    this(numSeeds, new Random());
  }

  @Override
  public Image apply(Image image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null.");
    }

    List<List<int[]>> clusters = generateClusters(image, generateSeeds(image));
    Pixel[][] pixels = new Pixel[image.getHeight()][image.getWidth()];

    for (List<int[]> cluster : clusters) {
      Pixel average = averageColor(cluster, image);
      for (int[] point : cluster) {
        pixels[point[0]][point[1]] = average;
      }
    }

    return image.fromPixels(pixels, true);
  }

  /**
   * Generates a set of numSeeds points in the image.
   *
   * @param image the image to generate the seeds for.
   * @return the set of points.
   * @throws IllegalArgumentException if the number of seeds is greater than the number of pixels in
   *                                  the image.
   */
  private List<int[]> generateSeeds(Image image) {
    if (image.getHeight() * image.getWidth() < numSeeds) {
      throw new IllegalArgumentException("Not enough pixels for the given number of seeds.");
    }
    int width = image.getWidth();
    int height = image.getHeight();

    Set<int[]> seeds = new HashSet<>();
    while (seeds.size() < numSeeds) {
      seeds.add(new int[]{rand.nextInt(height), rand.nextInt(width)});
    }
    return new ArrayList<>(seeds);
  }

  /**
   * Generates clusters of pixels where each cluster contains all of the pixels closest to a given
   * seed.
   *
   * @param image the image to generate clusters for.
   * @param seeds the set of points to generate clusters from.
   * @return the set of clusters.
   */
  private List<List<int[]>> generateClusters(Image image, List<int[]> seeds) {
    List<List<int[]>> clusters = new ArrayList<>(seeds.size());
    for (int count = 0; count < seeds.size(); count += 1) {
      clusters.add(new ArrayList<>());
    }

    for (int r = 0; r < image.getHeight(); r += 1) {
      for (int c = 0; c < image.getWidth(); c += 1) {
        int leastDist = Integer.MAX_VALUE;
        int leastSeedIndex = -1;
        for (int seedIndex = 0; seedIndex < seeds.size(); seedIndex += 1) {
          int[] seed = seeds.get(seedIndex);
          int dist = distSquared(r, c, seed[0], seed[1]);
          if (dist < leastDist) {
            leastDist = dist;
            leastSeedIndex = seedIndex;
          }
        }
        clusters.get(leastSeedIndex).add(new int[]{r, c});
      }
    }
    return clusters;
  }

  /**
   * Calculates the average color of pixels corresponding to a list of points by averaging the red,
   * green, and blue values separately.
   *
   * @param cluster the pixels to calculate the average color for.
   * @param image   the image containing the pixels.
   * @return a pixel representing the average color.
   */
  private Pixel averageColor(List<int[]> cluster, Image image) {
    int red = 0;
    int green = 0;
    int blue = 0;

    for (int[] point : cluster) {
      red += image.getRedValueAt(point[0], point[1]);
      green += image.getGreenValueAt(point[0], point[1]);
      blue += image.getBlueValueAt(point[0], point[1]);
    }
    if (cluster.size() > 0) {
      red /= cluster.size();
      green /= cluster.size();
      blue /= cluster.size();
    }
    return new RgbPixel(red, green, blue);
  }

  /**
   * Returns the distance between 2 points squared.
   *
   * @param r1 the row of the first point.
   * @param c1 the column of the first point.
   * @param r2 the row of the second point.
   * @param c2 the column of the second point.
   * @return the distance between the two points squared.
   */
  private int distSquared(int r1, int c1, int r2, int c2) {
    return (r1 - r2) * (r1 - r2) + (c1 - c2) * (c1 - c2);
  }
}
