package swing;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    JButton btn = new JButton("start");
    MainFrame mf;
    String str;

    public StartPanel(MainFrame m, String s) {
        mf = m;
        str = s;
        this.setName(s);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(layout);

        this.setSize(m.width, m.height);
        JLabel title = new JLabel("ゲーム名");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, 80));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(title, gbc);
        LineBorder border = new LineBorder(Color.RED, 2, true);
        title.setBorder(border);
        this.add(title);

        btn.setSize(500, 500);
        btn.setFont(new Font(MainFrame.DISPLAY_FONT, Font.BOLD, 80));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                m.gp.setVisible(true);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 2.0d;
        gbc.weighty = 1.0d;
        layout.setConstraints(btn, gbc);
        this.add(btn);
    }
}
