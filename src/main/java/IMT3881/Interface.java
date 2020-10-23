package IMT3881;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Interface extends JFrame {

    private static JFrame frame;
    private BufferedImage file_orig_img;
    private JLabel info = new JLabel("Please choose the operation from the Menu bar");
    private JLabel display_orig_image;
    private JLabel display_new_image;

    private static final int BINARY = 0;
    private static final int SMOOTH = 1;
    private static final int BRIGHT = 2;

    private int curr_action;

    static final int S_MIN = 0;
    static final int S_MAX = 70;

    private JSlider slider;
    private JLabel label;
   private TheSlider theSlider;
    private int value; // value from slideBar

    // Menus
    JMenuBar menuBar;
    JMenu menuOp, menuIm;
    JMenuItem menuBright, menuBinary, menuSmooth, menuUpload;



    public Interface() {

        frame = new JFrame("frame");
        frame.setSize(1000, 1000);

        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void navigationBar() {


        theSlider = new TheSlider();
        display_new_image = new JLabel();
        // Main Menu bar
        menuBar = new JMenuBar();

        // Menu to operate on an Image
        menuOp = new JMenu("Operation");

        // Menu to upload an image
        menuIm = new JMenu("Image");

        // Menu items to opration menu
        menuBright = new JMenuItem("Brightness");
        menuBinary = new JMenuItem("Binary");
        menuSmooth = new JMenuItem("Smoothness");

        // Menu items to Image menu
        menuUpload = new JMenuItem("Upload an Image");

        // Action listeners
        menuUpload.addActionListener(a -> {
            readImage();
            menuBright.setEnabled(true);
            menuBinary.setEnabled(true);
            menuSmooth.setEnabled(true);
        });

        menuBright.addActionListener(a -> {
            //uploadImage(brightImage(orig_img,this.value));

            slider = theSlider.slideBar();
            slider.addChangeListener(e -> {
                JSlider source = (JSlider) e.getSource();
                value = source.getValue();
                display_orig_image.setIcon(new ImageIcon(brightImage(file_orig_img,value)));


            });

            frame.add(slider);


            // uploadImage(brightImage(file_orig_img));
            // info.setText("After");
            //display_orig_image.setIcon(new ImageIcon(brightImage(file_orig_img)));
        });

        menuBinary.addActionListener(a -> {
            uploadImage(binaryImage(file_orig_img));
            info.setText("After");
        });

        menuSmooth.addActionListener(a -> {
            uploadImage(smoothImage(file_orig_img));
            info.setText("After");
        });

        // Disable before image
        menuBright.setEnabled(false);
        menuBinary.setEnabled(false);
        menuSmooth.setEnabled(false);

        // add items to menus
        menuIm.add(menuUpload);
        menuBar.add(menuIm);

        menuOp.add(menuBright);
        menuOp.add(menuBinary);
        menuOp.add(menuSmooth);
        menuBar.add(menuOp);


        frame.setJMenuBar(menuBar);
    }


    public BufferedImage readImage() {

        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Choose Your File");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // below code selects the file
        int returnval = filechooser.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION) {
            File file = filechooser.getSelectedFile();

            try {
                // display the image in a Jlabel

                this.file_orig_img = ImageIO.read(file);
                ImageIcon original = new ImageIcon(file_orig_img);
                display_orig_image = new JLabel(original);
                display_orig_image.setBounds(100, 0, original.getIconWidth(), original.getIconHeight());
                frame.add(display_orig_image);
                //frame.add(orig_img_label);
                display_orig_image.repaint();
                info.setBounds(100, 150, 350, 350);

                this.file_orig_img = ImageIO.read(file);
                ImageIcon original_icon = new ImageIcon(file_orig_img);
                display_orig_image = new JLabel(original_icon);
                display_orig_image.setBounds(100, 0, original_icon.getIconWidth(), original_icon.getIconHeight());
                // file_orig_img.repaint();
                frame.add(display_orig_image);

                info.setBounds(10, 150, 350, 350);

                frame.add(info);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return file_orig_img;
    }

    private BufferedImage brightImage(BufferedImage img,int value) {
        curr_action = BRIGHT;

        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage bright_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = img.getRGB(i, j);
                // Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                // Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // get the sum of RGB pixels values
                int sum = red + green + blue;
                // 255 + 255 + 255 = 765 (sum of RGB pixels)
           /*     int new_red = red + value;
                int new_green = green + value;
                int new_blue = blue + value;

                if (value == 20 || value == 50 || value == 70) {
                    if (sum + value <= 765) {
                        sum = sum + value;

                    } else {
                        sum = 765;
                    }
                    bright_image.setRGB(i, j, sum);
                }

                if (new_red > 255) {
                    new_red = 255;
                } else if (new_blue > 255) {
                    new_blue = 255;
                } else if (new_green > 255) {
                    new_green = 255;
                }




                Color new_color = new Color(new_red, new_green, new_blue);

                int new_color_rgb = new_color.getRGB();
                bright_image.setRGB(i, j, new_color_rgb);

            }
        }


        return bright_image;*/


                int new_red = red;//red + value;
                int new_green = green;// + value;
                int new_blue = blue;// + value;

                if (value == 20 || value == 30 || value == 70) {

                    if (new_red + value <= 255)
                        new_red += value;
                    else if (new_green + value <= 255)
                        new_green += value;
                    else if (new_blue + value <= 255)
                        new_blue += value;
                    if (sum + value <= 765) {

                        sum += value;
                    } else {
                        sum = 765;
                    }
                    bright_image.setRGB(i, j, sum);

                    if (new_red >= 255) {
                        new_red = red;
                    } else if (new_blue >= 255) {
                        new_blue = blue;
                    } else if (new_green >= 255) {
                        new_green = green;
                    }
                }


                Color new_color = new Color(new_red, new_green, new_blue);

                int new_color_rgb = new_color.getRGB();
                bright_image.setRGB(i, j, new_color_rgb);


            }
        }
    return bright_image;
    }
    public BufferedImage binaryImage(BufferedImage img) {
        curr_action = BINARY;

        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = img.getRGB(i, j);
                // Creating a Color object from pixel value
                Color color = new Color(pixel, true);

                // Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();


                // get the sum of RGB pixels values
                int sum = red + green + blue;

                // 255 + 255 + 255 = 765 (sum of RGB pixels)



                // 150 + 150 + 150 = 450 (middle value given by prof)

                if (sum >= 450) {
                    bi.setRGB(i, j, Color.WHITE.getRGB()); // 255
                } else {
                    bi.setRGB(i, j, Color.BLACK.getRGB()); // 0
                }
            }
        }
        return bi;
    }

    private BufferedImage smoothImage(BufferedImage img) {
        curr_action = SMOOTH;

        int height = img.getHeight();
        int width = img.getWidth();

        BufferedImage smoothed_image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int window[] = new int[9];
        int rgb[] = new int[3];
        int rgbSum;
        int edges = 1;

        Color color;

        for (int x = 2; x < width; x++) { // For each
            for (int y = 2; y < height; y++) { // pixel

                for (int c = 0; c < 3; c++) { // For each color (R, G, B)

                    int i = 0;
                    for (int fx = 0; fx < 3; fx++) { // For each
                        for (int fy = 0; fy < 3; fy++) { // neighbour
                            window[i] = getPixelValue(img, c, x + fx - edges, y + fy - edges); // Put each neighbour
                                                                                               // into array
                            i++;
                        }
                    }
                    Arrays.sort(window); // Sort the array
                    rgb[c] = window[4]; // The median of a fixed size 9
                }
                color = new Color(rgb[0], rgb[1], rgb[2]); // Create new color
                rgbSum = color.getRGB(); // get pixel RGB value
                smoothed_image.setRGB(x, y, rgbSum); // Set the value
            }

        }
        return smoothed_image;
    }

    public int getPixelValue(BufferedImage img, int color, int x, int y) {
        Color col = new Color(img.getRGB(x - 1, y - 1));
        switch (color) {
            case 0:
                return col.getRed();
            case 1:
                return col.getGreen();
            case 2:
                return col.getBlue();
            default:
                return -1;
        }
    }

    private JLabel uploadImage(BufferedImage img) {

        ImageIcon original = new ImageIcon(img);

        JLabel img_label = new JLabel(original);
        img_label.setBounds(100, 360, original.getIconWidth(), original.getIconHeight());
        img_label.setIcon(original);

        frame.add(img_label);

        img_label.repaint();

        return img_label;

    }

}
