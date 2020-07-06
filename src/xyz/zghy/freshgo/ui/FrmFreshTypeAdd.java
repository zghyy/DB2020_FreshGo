package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/6 上午9:38
 */
public class FrmFreshTypeAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("类型名称：");
    private JLabel labelDesc = new JLabel("类型描述：");
    private JTextField edtName = new JTextField(20);
    private JTextField edtDesc = new JTextField(50);

    public FrmFreshTypeAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup vpgUser = glMain.createParallelGroup();
        vpgUser.addComponent(labelName).addComponent(edtName);
        GroupLayout.ParallelGroup vpgPwd = glMain.createParallelGroup();
        vpgPwd.addComponent(labelDesc).addComponent(edtDesc);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgUser).addGroup(vpgPwd);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelName).addComponent(labelDesc);
        GroupLayout.ParallelGroup hpgEdit = glMain.createParallelGroup();
        hpgEdit.addComponent(edtName).addComponent(edtDesc);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgEdit);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText);
        glMain.setHorizontalGroup(hpg);

        this.getContentPane().add(workPane, BorderLayout.CENTER);
        this.setSize(300, 160);
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
        if(e.getSource()==this.btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource()==this.btnCheck){
            String typeName = this.edtName.getText();
            String typeDesc = this.edtDesc.getText();
            FreshTypeManage ftm = new FreshTypeManage();
            try {
                ftm.addFreshType(typeName,typeDesc);
                this.setVisible(false);
            } catch (BusinessException businessException) {
                businessException.printStackTrace();
            }
        }
    }
}
