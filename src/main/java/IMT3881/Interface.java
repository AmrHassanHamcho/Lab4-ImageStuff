package IMT3881;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame {

    private static JFrame frame;
    private BufferedImage orig_img;
    private JLabel info = new JLabel("Please choose the operation from the Menu bar");
    private JLabel display_orig_image;
    private JLabel display_new_image;

    private static final int BINARY = 0;
    private static final int SMOOTH = 1;
    private static final int BRIGHT = 2;

    private int curr_action;

    // J slider slidebar
    static final int S_MIN = 0;
    static final int S_MAX = 70;

    private JSlider slider;
    private JLabel label;
    private int value; // value from slideBar
    Event e;

    // Menus
    JMenuBar menuBar;
    JMenu menuOp, menuIm;
    JMenuItem menuBright, menuBinary, menuSmooth, menuUpload;

    public Interface() {

        this.e = new Event();
        this.value = e.getValue(); // value from slideBar

        frame = new JFrame("frame");
        frame.setSize(1000, 1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
    }

    public void navigationBar() {

        display_new_image = new JLabel();

        // Main Menu bar
        menuBar = new JMenuBar();

        // Menu to operate on an Image
        menuOp = new JMenu("Operatizon");

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
            BufferedImage bright_image = brightImage(orig_img);

            slider = new JSlider();
            slider = e.slideBar();
            slider.addChangeListener(e1 -> display_orig_image.setIcon(new ImageIcon(bright_image)));
            frame.add(slider);

            // uploadImage(brightImage(orig_img));
            // info.setText("After");

            display_orig_image.setIcon(new ImageIcon(brightImage(orig_img)));
        });

        menuBinary.addActionListener(a -> {
            uploadImage(binaryImage(orig_img));
            info.setText("After");
        });

        // Disable before image
        menuBright.setEnabled(false);
        menuBinary.setEnabled(false);
        menuSmooth.setEnabled(false);

        // add items to menus
        menuOp.add(menuBright);
        menuOp.add(menuBinary);
        menuOp.add(menuSmooth);

        menuIm.add(menuUpload);

        menuBar.add(menuOp);
        menuBar.add(menuIm);

        frame.setJMenuBar(menuBar);
    }

    /*
     * private void slideBar() { slider = new JSlider(JSlider.HORIZONTAL, S_MIN,
     * S_MAX, 0); slider.setMajorTickSpacing(20); slider.setPaintTicks(true);
     * slider.setBounds(100, 500, 350, 350); slider.setVisible(true);
     * 
     * frame.add(slider); slider.addChangeListener(e); <<<<<<< HEAD } ======= }
     */

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
                this.orig_img = ImageIO.read(file);
                ImageIcon original_icon = new ImageIcon(orig_img);
                display_orig_image = new JLabel(original_icon);
                display_orig_image.setBounds(100, 0, original_icon.getIconWidth(), original_icon.getIconHeight());
                // orig_img.repaint();
                frame.add(display_orig_image);

                info.setBounds(100, 150, 350, 350);
                frame.add(info);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return orig_img;
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

    private BufferedImage brightImage(BufferedImage img) {
        curr_action = BRIGHT;
        reset();

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
                int new_red = red + value;
                int new_green = green + value;
                int new_blue = blue + value;

                if (value == 20 || value == 50 || value == 70) {
                    if (sum + value <= 765) {
                        sum = sum + value;
                        System.out.println("Value: " + value);
                        System.out.println("e.get.Value(): " + e.getValue());
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

        return bright_image;
    }

    private void smoothImage() {
        reset();
        curr_action = SMOOTH;

    }

    private JLabel uploadImage(BufferedImage img) {

        ImageIcon original = new ImageIcon(img);
        display_new_image.setIcon(original);
        display_new_image.setBounds(100, 360, original.getIconWidth(), original.getIconHeight());

        // img_label.repaint();
        frame.add(display_new_image);

        return display_new_image;
    }

    private void reset() {
        if (curr_action == SMOOTH || curr_action == BINARY) {
            slider.setVisible(false);
            slider.setEnabled(false);
        }

    }

}
