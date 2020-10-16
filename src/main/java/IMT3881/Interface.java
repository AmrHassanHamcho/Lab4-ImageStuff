package IMT3881;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame {

    private static  JFrame frame;
    private BufferedImage img;




    public Interface (){
        frame = new JFrame("frame");
        frame.setSize(1000,1000);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);

    }

    public void uploadImageActionPerformed(){

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
                img = ImageIO.read(file);
                JLabel imgLabel_1 = new JLabel("       Before");
                JLabel imgLabel_2 = new JLabel("       After");
                imgLabel_1.setIcon(new ImageIcon(img));// original pic
                imgLabel_1.setBounds(100,0,350,350);
                frame.add(imgLabel_1);

                // after binary operation
                BufferedImage binaryImg = binaryImage(img);
                imgLabel_2.setIcon(new ImageIcon(binaryImg));
                imgLabel_2.setBounds(100,350,350,350);
                frame.add(imgLabel_2);

            } catch(IOException e) {
                e.printStackTrace();
            }
            this.pack();
        }

    }
    public BufferedImage binaryImage (BufferedImage img){
        int height = img.getHeight();
        System.out.println("binary img height is: " + height);
        int width = img.getWidth();
        System.out.println("binary img width is: " + width);
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

    public void navigationBar (){

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
        menuUpload.addActionListener(a->uploadImageActionPerformed());
        // add MenuBar to frame
        frame.setJMenuBar(menuBar);
        //add Menus to MenuBar
        menuBar.add(menu);
        menuBar.add(menuIm);

        //add items to menus
        menu.add(menuBright);
        menu.add(menuBinary);
        menu.add(menuSmooth);
        menuIm.add(menuUpload);




    }





}
