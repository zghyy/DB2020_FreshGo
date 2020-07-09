package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.UserManage;
import xyz.zghy.freshgo.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * @author ghy
 * @date 2020/7/4 下午1:02
 */
public class FrmRegister extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnOk = new Button("注册");
    private Button btnCancel = new Button("取消");

    private JLabel labelUser = new JLabel("用户名:");
    private JLabel labelPwd = new JLabel("密码:");
    private JLabel labelPwd2 = new JLabel("确认密码:");
    private JLabel labelSex = new JLabel("性别:");
    private JLabel labelPhone = new JLabel("电话号码:");
    private JLabel labelCity = new JLabel("所在城市:");
    private JLabel lableEmail = new JLabel("邮箱:");

    private JTextField editUser = new JTextField(20);
    private JPasswordField editPwd = new JPasswordField(20);
    private JPasswordField editPwd2 = new JPasswordField(20);
    private JRadioButton jr1 = new JRadioButton("男");
    private JRadioButton jr2 = new JRadioButton("女");
    private JTextField editPhone = new JTextField(11);
    private JTextField editCity = new JTextField(20);
    private JTextField editEmail = new JTextField(20);

    private String SexType = null;


    public FrmRegister(Dialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(this.btnOk);
        toolBar.add(this.btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);
        ButtonGroup rbtgroup = new ButtonGroup();
        rbtgroup.add(jr1);
        rbtgroup.add(jr2);


        GroupLayout.ParallelGroup vpgUser = glMain.createParallelGroup();
        vpgUser.addComponent(labelUser).addComponent(editUser);
        GroupLayout.ParallelGroup vpgPwd = glMain.createParallelGroup();
        vpgPwd.addComponent(labelPwd).addComponent(editPwd);
        GroupLayout.ParallelGroup vpgPwd2 = glMain.createParallelGroup();
        vpgPwd2.addComponent(labelPwd2).addComponent(editPwd2);
        GroupLayout.ParallelGroup vpgPhone = glMain.createParallelGroup();
        vpgPhone.addComponent(labelPhone).addComponent(editPhone);
        GroupLayout.ParallelGroup vpgCity = glMain.createParallelGroup();
        vpgCity.addComponent(labelCity).addComponent(editCity);
        GroupLayout.ParallelGroup vpgEmail = glMain.createParallelGroup();
        vpgEmail.addComponent(lableEmail).addComponent(editEmail);
        GroupLayout.ParallelGroup vpgSex = glMain.createParallelGroup();
        vpgSex.addComponent(jr1).addComponent(jr2);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgUser).addGroup(vpgPwd).addGroup(vpgPwd2).addGroup(vpgPhone);
        vsp.addGroup(vpgCity).addGroup(vpgEmail).addGroup(vpgSex);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelUser).addComponent(labelPwd).addComponent(labelPwd2);
        hpgLabel.addComponent(labelPhone).addComponent(labelCity).addComponent(lableEmail);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(editUser).addComponent(editPwd).addComponent(editPwd2);
        hpgedit.addComponent(editPhone).addComponent(editCity).addComponent(editEmail);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgedit);
        GroupLayout.SequentialGroup hsgSex = glMain.createSequentialGroup();
        hsgSex.addComponent(jr1).addComponent(jr2);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText).addGroup(GroupLayout.Alignment.CENTER, hsgSex);
        glMain.setHorizontalGroup(hpg);

        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(305, 300);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        btnOk.addActionListener(this);
        btnCancel.addActionListener(this);
        jr1.addActionListener(this);
        jr2.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == this.jr1) {
            this.SexType = "男";
        } else if (e.getSource() == this.jr2) {
            this.SexType = "女";
        } else if (e.getSource() == this.btnOk) {
            //TODO 用户注册
                String userName = editUser.getText();
                String userPwd = new String(editPwd.getPassword());
                String userPwd2 = new String(editPwd2.getPassword());
                String userSex = this.SexType;
                int userPhone = Integer.parseInt(editPhone.getText());
                String userCity = editCity.getText();
                String userEmail = editEmail.getText();
            try {
                new UserManage().register(userName, userPwd, userPwd2, userSex, userPhone, userCity, userEmail);
                this.setVisible(false);
            } catch (BaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

        }

    }
}
