
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class KConstant {

    public final static char[] keys = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', '(', ')', '=', '+', '-', '*', '/', '_'};
    public final static long INTERVAL = 50;
}

public class Keyboard extends InputStream {

    final private JPanel panel = new JPanel();

    public Keyboard() {
        super();
        JButton clear = new JButton("CLR");
        JButton delete = new JButton("DEL");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                queue.addLast(0);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                queue.removeLast();
            }
        });
        for (char c : KConstant.keys) {
            JButton button = new JButton(String.valueOf(c));
            button.addActionListener(new ButtonAction());
            panel.add(button);
        }
        panel.add(clear);
        panel.add(delete);
    }

    public JPanel getPanel() {
        return panel;
    }

    public ActionListener getCloseAction() {
        return new CloseAction();
    }

    class CloseAction implements ActionListener {

        public CloseAction() {
        }

        public void actionPerformed(ActionEvent event) {
            queue.addLast(-1);
        }
    }

    class ButtonAction implements ActionListener {

        public ButtonAction() {
        }

        public void actionPerformed(ActionEvent event) {
            queue.addLast((int) (event.getActionCommand().charAt(0)));
        }
    }
    private LinkedList<Integer> queue = new LinkedList<Integer>();

    @Override
    public int read() throws IOException {
        try {
            while (queue.isEmpty()) {
                Thread.sleep(KConstant.INTERVAL);
            }
        } catch (InterruptedException e) {
        }
        return queue.remove();
    }

    @Override
    public int available() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return 0;
        }
        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;
        return 1;
    }
}
