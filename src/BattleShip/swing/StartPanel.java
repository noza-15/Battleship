package BattleShip.swing;

import BattleShip.CommandHandler;
import BattleShip.Server;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;


public class StartPanel extends JPanel {
    //TODO:メソッド内で良い?
    JButton bt_start = new JButton("start");
    //TODO:不要?
    String str;
    MainFrame mf;

    public StartPanel(MainFrame m, String s) {
        this.mf = m;
//        str = s;
        this.setName(s);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(layout);

        this.setSize(mf.width, mf.height);
        JLabel lb_title = new JLabel(Server.GAME_NAME);
        lb_title.setHorizontalAlignment(JLabel.CENTER);
        lb_title.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, 50));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(lb_title, gbc);
        LineBorder border = new LineBorder(Color.RED, 2, true);
        lb_title.setBorder(border);
        this.add(lb_title);

        bt_start.setSize(200, 200);
        bt_start.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, mf.font));
        bt_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                while (!establishConnection()) ;
                try {
                    mf.gp = new GroupPanel(mf, "GroupPanel");
                } catch (SocketException se) {
                    se.printStackTrace();
                }
                mf.add(mf.gp);
                setVisible(false);
                mf.gp.setVisible(true);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 2.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(bt_start, gbc);
        this.add(bt_start);
    }

    private boolean establishConnection() {
        String inputAddress = JOptionPane.showInputDialog("サーバーのアドレスを入力してください。", "localhost");
        InetAddress address;
        try {
            address = InetAddress.getByName(inputAddress);
        } catch (UnknownHostException uhe) {
            JOptionPane.showMessageDialog(mf, "サーバーが見つかりませんでした。アドレスが正しいか確認してください。",
                    "サーバーが見つかりません", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Socket socket = new Socket(address, Server.PORT_NO);
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
