package xyz.zghy.freshgo.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/4 上午8:25
 */
public class FrmMain extends JFrame implements ActionListener {

    private FrmLogin Login = null;

    public FrmMain(){
        this.setExtendedState(FrmMain.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超");
        Login = new FrmLogin(this,"登录",true);
        Login.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
