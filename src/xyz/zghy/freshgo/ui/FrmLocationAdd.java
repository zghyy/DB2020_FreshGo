package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.LocationManage;
import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/7 上午9:40
 */
public class FrmLocationAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelProvince = new JLabel("省：");
    private JLabel labelCity = new JLabel("市：");
    private JLabel labelArea = new JLabel("区：");
    private JLabel labelDesc = new JLabel("详细地址：");
    private JLabel labelLinkman = new JLabel("联系人姓名：");
    private JLabel labelPhoneNumber = new JLabel("联系人手机号：");

    private JTextField edtProvince = new JTextField(20);
    private JTextField edtCity = new JTextField(20);
    private JTextField edtArea = new JTextField(20);
    private JTextField edtDesc = new JTextField(20);
    private JTextField edtLinkman = new JTextField(20);
    private JTextField edtPhoneNumber = new JTextField(20);

    public FrmLocationAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup vpgProvince = glMain.createParallelGroup();
        vpgProvince.addComponent(labelProvince).addComponent(edtProvince);
        GroupLayout.ParallelGroup vpgCity = glMain.createParallelGroup();
        vpgCity.addComponent(labelCity).addComponent(edtCity);
        GroupLayout.ParallelGroup vpgArea = glMain.createParallelGroup();
        vpgArea.addComponent(labelArea).addComponent(edtArea);
        GroupLayout.ParallelGroup vpgDesc = glMain.createParallelGroup();
        vpgDesc.addComponent(labelDesc).addComponent(edtDesc);
        GroupLayout.ParallelGroup vpgLinkMan = glMain.createParallelGroup();
        vpgLinkMan.addComponent(labelLinkman).addComponent(edtLinkman);
        GroupLayout.ParallelGroup vpgPhoneNumber = glMain.createParallelGroup();
        vpgPhoneNumber.addComponent(labelPhoneNumber).addComponent(edtPhoneNumber);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgProvince).addGroup(vpgCity).addGroup(vpgArea)
                .addGroup(vpgDesc).addGroup(vpgLinkMan).addGroup(vpgPhoneNumber);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelProvince).addComponent(labelCity).addComponent(labelArea)
                .addComponent(labelDesc).addComponent(labelLinkman).addComponent(labelPhoneNumber);
        GroupLayout.ParallelGroup hpgEdit = glMain.createParallelGroup();
        hpgEdit.addComponent(edtProvince).addComponent(edtCity).addComponent(edtArea)
                .addComponent(edtDesc).addComponent(edtLinkman).addComponent(edtPhoneNumber);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgEdit);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText);
        glMain.setHorizontalGroup(hpg);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(400, 280);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.btnCancel.addActionListener(this);
        this.btnCheck.addActionListener(this);

        this.validate();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == btnCheck) {
            BeanLocation bl = new BeanLocation();
            bl.setProvince(this.edtProvince.getText());
            bl.setCity(this.edtCity.getText());
            bl.setArea(this.edtArea.getText());
            bl.setLocationDesc(this.edtDesc.getText());
            bl.setLinkman(this.edtLinkman.getText());
            bl.setPhoneNumber(Integer.parseInt(this.edtPhoneNumber.getText()));
            try {
                new LocationManage().addLocation(bl);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
