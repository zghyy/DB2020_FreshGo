package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.MenuDetailManage;
import xyz.zghy.freshgo.control.MenuManage;
import xyz.zghy.freshgo.model.BeanMenuMsg;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/14 上午8:38
 */
public class FrmMenuAddInfo extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("菜谱名称：");
    private JLabel labelDesc = new JLabel("菜谱描述：");
    private JTextField edtName = new JTextField(20);
    private JTextField edtDesc = new JTextField(50);

    public FrmMenuAddInfo (JDialog owner, String title, boolean modal) {
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
        if(e.getSource()==btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource()==btnCheck){
            BeanMenuMsg bmm = new BeanMenuMsg();
            bmm.setMenuName(this.edtName.getText());
            bmm.setMenuDesc(this.edtDesc.getText());
            try {
                int id = new MenuManage().createMenu(bmm);
                new MenuDetailManage().addMenuDetail(id);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);
        }

    }
}
