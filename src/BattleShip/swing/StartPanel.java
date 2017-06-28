package BattleShip.swing;

import BattleShip.CommandHandler;
import BattleShip.Server;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class StartPanel extends JPanel {
    MainFrame mf;

    public StartPanel(MainFrame m) {
        this.mf = m;
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(layout);

        this.setSize(MainFrame.WIN_WIDTH, MainFrame.WIN_HEIGHT);
        JLabel lb_title = new JLabel(Server.GAME_NAME);
        lb_title.setHorizontalAlignment(JLabel.CENTER);
        lb_title.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, 40));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_title, gbc);
        this.add(lb_title);

        JLabel lb_imgShip;
        ImageIcon ico_ship = new ImageIcon(ClassLoader.getSystemResource("BattleShip/res/title_s.png"));
        lb_imgShip = new JLabel(ico_ship);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5d;
        gbc.weighty = 0.5d;
        layout.setConstraints(lb_imgShip, gbc);
        this.add(lb_imgShip);

        JButton bt_start = new JButton("Start");
        bt_start.setSize(200, 200);
        bt_start.setFont(new Font(MainFrame.FONT_NAME, Font.BOLD, MainFrame.FONT_SIZE));
        bt_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputAddress = JOptionPane.showInputDialog("サーバーのアドレスを入力してください。", "localhost");
                if (inputAddress != null) {
                    if (establishConnection(inputAddress)) {
                        mf.gp = new GroupPanel(mf);
                        mf.sp.setVisible(false);
                        mf.sequencer.stop();
                        mf.add(mf.gp);
                        mf.gp.setVisible(true);
                    }

                }
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_start, gbc);
        this.add(bt_start);


        try {
            mf.sequencer = MidiSystem.getSequencer();
            mf.sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
            mf.sequencer.open();
            InputStream inputStream = ClassLoader.getSystemResourceAsStream("BattleShip/res/game_maoudamashii_1_battle36.mid");
            mf.sequence = MidiSystem.getSequence(inputStream);
            inputStream.close();
            mf.sequencer.setSequence(mf.sequence);
            mf.sequencer.start();
        } catch (MidiUnavailableException | IOException | InvalidMidiDataException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    private boolean establishConnection(String address) {
        InetAddress inetAddress;
        try {
            inetAddress = InetAddress.getByName(address);
        } catch (UnknownHostException uhe) {
            JOptionPane.showMessageDialog(mf, "サーバーが見つかりませんでした。アドレスが正しいか確認してください。",
                    "サーバーが見つかりません", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Socket socket = new Socket(inetAddress, Server.PORT_NO);
            JOptionPane.showMessageDialog(mf.sp, "サーバーとの接続に成功しました。"
                    + "\nsocket = " + socket);
            mf.cmd = new CommandHandler(socket);
            return true;
        } catch (ConnectException ce) {
            JOptionPane.showMessageDialog(mf, "サーバーに接続を拒否されました。",
                    "接続拒否", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(mf, "サーバー接続中にエラーが発生しました。",
                    "接続エラー", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
