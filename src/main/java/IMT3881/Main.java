package IMT3881;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            Interface inter = new Interface();
            inter.navigationBar();
        });

    }
}
