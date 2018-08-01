package view;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;

import static properties.Properies.STATUS_BAR;
import static properties.Properies.STATUS_BAR_UPDATE;

/**
 * Created by hammer on 23.08.2017.
 */
public class StatusBar extends JPanel {
    private JLabel statusTime;
    private JLabel statusBar;
    private JProgressBar progressBar;

    private ArrayList bufferStatus = new ArrayList();

    public StatusBar() {
        setStatusEnd();
        statusTime = new JLabel();
        statusBar = new JLabel();
        setLayout(new BorderLayout());
        add(statusTime, BorderLayout.EAST);

        progressBar = progressBar();
        add(progressBar, BorderLayout.WEST);

        add(statusBar, BorderLayout.CENTER);
        UpdateStatus up = new UpdateStatus();
        up.setDaemon(true);
        if (STATUS_BAR) {
            up.start();
        }
    }

    private JProgressBar progressBar() {
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        return progressBar;
    }

    public void stopProgressBar() {
        progressBar.setVisible(false);
    }

    public void startProgressBar() {
        progressBar.setVisible(true);
    }

    private String getStatus() {
        if (bufferStatus.size() != 1)
            System.out.println(bufferStatus);
        String ret = "";
        if (bufferStatus.size() == 1)
            ret = (String) bufferStatus.get(0);
        else if (bufferStatus.size() > 1) {
            ret = (String) bufferStatus.get(1);
            bufferStatus.remove(1);
        }
        return ret;
    }

    public void setStatus(String str) {
        bufferStatus.add(str);
    }

    public void setStatusEnd() {
        bufferStatus.add("...");
    }


    private String time() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return format.format(new Date());
    }

    private class UpdateStatus extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    statusTime.setText(time());
                    if (!statusBar.equals("..."))
                        statusBar.setText(getStatus());
                    repaint();
                    Thread.sleep(STATUS_BAR_UPDATE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
