package IMT3881;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame  {

    public   JFrame frame;
    private BufferedImage orig_img;
    private JLabel info = new JLabel("Please choose the operation from the Menu bar");

    //J slider slidebar
    static final int S_MIN = 0;
    static final int S_1 = 20;
    static final int S_2 = 50;
    static final int S_MAX = 70;

    Event e ;
    private int value ;


    public Interface (){
       this.e = new Event();
       this.value = e.getValue(); //value from slideBar

        frame = new JFrame("frame");
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

    }

    public BufferedImage readImage(){

        JFileChooser filechooser = new JFileChooser();
        filechooser.setDialogTitle("Choose Your File");
        filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        // below code selects the file
        int returnval = filechooser.showOpenDialog(this);
        if (returnval == JFileChooser.APPROVE_OPTION)
        {
            File file = filechooser.getSelectedFile();

            try {
                // display the image in a Jlabel
                this.orig_img = ImageIO.read(file);
                JLabel orig_img_label = new JLabel("");
                orig_img_label.setIcon(new ImageIcon(orig_img)); // original pic
                orig_img_label.setBounds(100,0,350,350);
                //orig_img.repaint();
                frame.add(orig_img_label);

                info.setBounds(100,150,350,350);
                frame.add(info);



            } catch(IOException e) {
                e.printStackTrace();
            }


        }
            return orig_img;
    }


    public BufferedImage binaryImage (BufferedImage img){

        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int pixel = img.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // get the sum of RGB pixels values
                int sum = red + green + blue;
                // 150 + 150 + 150 = 450 (middle value given by prof)

                if (sum >= 450){
                    bi.setRGB(i, j,  Color.WHITE.getRGB()); // 255
                }
                else{
                    bi.setRGB(i, j, Color.BLACK.getRGB()); // 0
                }

            }
        }

        return bi;
    }

    private BufferedImage brightImage (BufferedImage img){

        JSlider slider = new JSlider();
        slider = e.slideBar();
        slider.addChangeListener(e);
        frame.add(slider);
        System.out.println("the value is" + this.e.getValue());


        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage bright_image = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                int pixel = img.getRGB(i,j);
                //Creating a Color object from pixel value
                Color color = new Color(pixel, true);
                //Retrieving the R G B values
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                // get the sum of RGB pixels values
                int sum = red + green + blue;
                // 255 + 255 + 255 = 765 (sum of RGB pixels)
                int new_red = red + value;
                int new_green = green + value;
                int new_blue = blue + value;

                if(new_red > 255){
                    new_red = 255;
                }
                else if (new_blue > 255){
                    new_blue = 255;
                }
                else if(new_green > 255){
                    new_green = 255;
                }

                Color new_color = new Color(new_red,new_green,new_blue);

                int new_color_rgb = new_color.getRGB();
                bright_image.setRGB(i , j , new_color_rgb);

            }
        }


        //uploadImage(bright_image);



        return bright_image;
    }

    private JLabel uploadImage(BufferedImage img){

        JLabel img_label = new JLabel("");
        img_label.setIcon(new ImageIcon(img));
        img_label.setBounds(100,360,350,350);
        //img_label.repaint();
        frame.add(img_label);

        return img_label;
    }
    public void navigationBar(){



        // Main Menu bar
        JMenuBar menuBar = new JMenuBar();

        // Menu to operate on an Image
        JMenu menu = new JMenu("Operation");

        // Menu to upload an image
        JMenu menuIm = new JMenu("Image");

        // Menu items to opration menu
        JMenuItem menuBright = new JMenuItem("Brightness");
        JMenuItem menuBinary = new JMenuItem("Binary");
        JMenuItem menuSmooth = new JMenuItem("Smoothness");

        // Menu items to Image menu
        JMenuItem menuUpload = new JMenuItem("Upload an Image");

        //action listners
        menuUpload.addActionListener(a->readImage());
        //menuBright.addActionListener(a->);
        // add MenuBar to frame
        frame.setJMenuBar(menuBar);
        //add Menus to MenuBar
        menuBar.add(menu);
        menuBar.add(menuIm);

        //add action listner to menuBright
        menuBright.addActionListener(a->brightImage(orig_img));

        //add items to menus
        menu.add(menuBright);
        //add action listner to binary menuitem
        menuBinary.addActionListener(a->{uploadImage(binaryImage(orig_img));
        info.setText("After");
        });
        menu.add(menuBinary);
        menu.add(menuSmooth);
        menuIm.add(menuUpload);




    }



}
