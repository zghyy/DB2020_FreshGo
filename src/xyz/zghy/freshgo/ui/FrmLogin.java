package xyz.zghy.freshgo.ui;


import xyz.zghy.freshgo.control.AdminManage;
import xyz.zghy.freshgo.control.UserManage;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ghy
 * @date 2020/7/4 上午8:28
 */
public class FrmLogin extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnUserReRegister = new Button("用户注册");
    private Button btnLogin = new Button("登陆");
    private Button btnCancel = new Button("退出");
    private JLabel labelUser = new JLabel("用户：");
    private JLabel labelPwd = new JLabel("密码：");
    private JTextField edtUserName = new JTextField(20);
    private JPasswordField edtPwd = new JPasswordField(20);
    private JRadioButton jr1 = new JRadioButton("用户", true);
    private JRadioButton jr2 = new JRadioButton("管理员");
    private String LoginAccountType = "用户";


    public FrmLogin(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnUserReRegister);
        toolBar.add(btnLogin);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        this.edtUserName.setText("1");
        this.edtPwd.setText("1");

        ButtonGroup rbtgroup = new ButtonGroup();
        rbtgroup.add(jr1);
        rbtgroup.add(jr2);

        GroupLayout.ParallelGroup vpgUser = glMain.createParallelGroup();
        vpgUser.addComponent(labelUser).addComponent(edtUserName);
        GroupLayout.ParallelGroup vpgPwd = glMain.createParallelGroup();
        vpgPwd.addComponent(labelPwd).addComponent(edtPwd);
        GroupLayout.ParallelGroup vpgType = glMain.createParallelGroup();
        vpgType.addComponent(jr1).addComponent(jr2);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgUser).addGroup(vpgPwd).addGroup(vpgType);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelUser).addComponent(labelPwd);
        GroupLayout.ParallelGroup hpgEdit = glMain.createParallelGroup();
        hpgEdit.addComponent(edtUserName).addComponent(edtPwd);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgEdit);
        GroupLayout.SequentialGroup hsgType = glMain.createSequentialGroup();
        hsgType.addComponent(jr1).addComponent(jr2);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText).addGroup(GroupLayout.Alignment.CENTER, hsgType);
        glMain.setHorizontalGroup(hpg);


        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 160);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnLogin.addActionListener(this);
        btnCancel.addActionListener(this);
        btnUserReRegister.addActionListener(this);
        jr1.addActionListener(this);
        jr2.addActionListener(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.jr1) {
            this.LoginAccountType = "用户";
        } else if (e.getSource() == this.jr2) {
            this.LoginAccountType = "管理员";
        } else if (e.getSource() == this.btnCancel) {
            System.exit(0);
        } else if (e.getSource() == this.btnLogin) {
            //TODO 用户和管理员登录
            String loginName = this.edtUserName.getText();
            String loginPwd = new String(this.edtPwd.getPassword());
            try {
                if ("用户".equals(this.LoginAccountType)) {
                    SystemUtil.currentLoginType = "用户";
                    SystemUtil.currentUser = new UserManage().login(loginName, loginPwd);
                    System.out.println("用户登录成功");
                    this.setVisible(false);
                } else if ("管理员".equals(this.LoginAccountType)) {
                    SystemUtil.currentLoginType = "管理员";
                    SystemUtil.currentAdmin = new AdminManage().login(loginName, loginPwd);
                    this.setVisible(false);
                } else {
                    throw new BusinessException("访问出错");
                }
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == this.btnUserReRegister) {
            //TODO 用户注册 需要注册ui
//            System.out.println(111);
            FrmRegister register = new FrmRegister(this, "注册", true);
            register.setVisible(true);
        }
    }
}
