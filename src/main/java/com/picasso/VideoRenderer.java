package com.picasso;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to render a simulation into a video
 *
 * @author picasso2005
 */
public class VideoRenderer {
    final private List<Planet> planets;
    final private Integer width;
    final private Integer height;
    final private Integer frameRate;
    final private Long frameNb;
    final private Float coordinateFactor;
    final private Float planetSizeFactor;
    final private String outputFile;

    /**
     * Class constructor, must be only called by builder
     *
     * @param builder Builder class used to pass every parameter to this class
     */
    private VideoRenderer(Builder builder) {
        this.planets = builder.planets;
        this.width = builder.width;
        this.height = builder.height;
        this.frameRate = builder.frameRate;
        this.frameNb = (long) builder.length * builder.frameRate;
        this.coordinateFactor = builder.coordinateFactor;
        this.planetSizeFactor = builder.planetSizeFactor;
        this.outputFile = builder.outputFile;
    }

    /**
     * Private method used to render 1 frame
     *
     * @return The frame generated
     */
    private BufferedImage renderFrame() {
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = image.getGraphics();

        for (Planet planet : this.planets) {
            int size = (Math.max((int) (planet.mass * this.planetSizeFactor), 1));

            graphics.setColor(planet.color);

            graphics.fillArc(
                    (int) (this.width / 2 - size / 2 + (planet.x * this.coordinateFactor)),
                    (int) (this.height / 2 - size / 2 + (planet.y * this.coordinateFactor)),
                    size, size, 0, 360);
        }

        return image;
    }

    /**
     * Builder class made to generate all parameters for VideoRenderer class
     *
     * @author picasso2005
     */
    public static class Builder {
        private List<Planet> planets;
        private Integer width;
        private Integer height;
        private Integer frameRate;
        private Integer length;
        private Float coordinateFactor;
        private Float planetSizeFactor;
        private String outputFile;

        /**
         * Define the planet list to use to simulate the video
         *
         * @param planets The planet list
         * @return Builder with this parameter updated
         */
        public Builder setPlanets(List<Planet> planets) {
            this.planets = planets;

            return this;
        }

        /**
         * Define video output size
         *
         * @param width  The width of generated video in pixels (must be greater than 0)
         * @param height The height of generated video in pixels (must be greater than 0)
         * @return Builder with this parameter updated
         */
        public Builder setOutputSize(int width, int height) {
            this.width = width;
            this.height = height;

            return this;
        }

        /**
         * Define the frame rate of the generated video
         *
         * @param frameRate The frame rate (must be greater than 0)
         * @return Builder with this parameter updated
         */
        public Builder setFrameRate(int frameRate) {
            this.frameRate = frameRate;

            return this;
        }

        /**
         * Define the length of the generated video
         *
         * @param length The frame rate (must be greater than 0)
         * @return Builder with this parameter updated
         */
        public Builder setLength(int length) {
            this.length = length;

            return this;
        }

        /**
         * Define the coordinate factor of the generated video
         *
         * @param coordinateFactor The factor coordinates will be multiplied with
         *                         (the higher, the more zoomed the video will be, must be greater than 0)
         * @return Builder with this parameter updated
         */
        public Builder setCoordinateFactor(float coordinateFactor) {
            this.coordinateFactor = coordinateFactor;

            return this;
        }

        /**
         * Define the planet size factor of the generated video
         *
         * @param planetSizeFactor The factor planet size will be multiplied with
         *                         (the higher, the more zoomed the video will be, must be greater than 0)
         * @return Builder with this parameter updated
         */
        public Builder setPlanetSizeFactor(float planetSizeFactor) {
            this.planetSizeFactor = planetSizeFactor;

            return this;
        }

        /**
         * Define the output file
         *
         * @param outputFile The output file path, must be mp4 format
         * @return Builder class with this parameter updated
         */
        public Builder setOutputFile(String outputFile) {
            this.outputFile = outputFile;

            return this;
        }

        /**
         * Build method that generate a video renderer class, setting any unset values to predefined ones or random ones
         *
         * @return The generated video renderer class
         */
        public VideoRenderer build() {
            if (this.planets == null) {
                List<Planet> planets = new ArrayList<>();

                for (int i = 0; i < 100; i++) {
                    planets.add(new Planet.Builder().build());
                }

                this.planets = planets;
            }

            if (this.width == null || this.height == null) {
                this.width = 1920;
                this.height = 1080;
            }

            if (this.frameRate == null) {
                this.frameRate = 60;
            }

            if (this.length == null) {
                this.length = 10;
            }

            if (this.coordinateFactor == null || this.coordinateFactor <= 0) {
                this.coordinateFactor = 1F;
            }

            if (this.planetSizeFactor == null || this.planetSizeFactor <= 0) {
                this.planetSizeFactor = 1F;
            }

            if (this.outputFile == null) {
                this.outputFile = System.getProperty("user.dir") + "/output.mp4";
            }

            if (!this.outputFile.substring(this.outputFile.length() - 4).equals(".mp4")) {
                this.outputFile += ".mp4";
            }

            return new VideoRenderer(this);
        }
    }

}
