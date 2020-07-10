package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.CommentsManage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author ghy
 * @date 2020/7/9 下午7:56
 */
public class FrmCommentsAdd extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelCommentMsg = new JLabel("评论内容：");
    private JLabel labelStarts = new JLabel("星级(0~5)：");
    private JTextField edtCommentMsg = new JTextField(50);
    private JTextField edtStarts = new JTextField(10);



    public FrmCommentsAdd(JDialog owner, String title, boolean modal){
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup vpgComments = glMain.createParallelGroup();
        vpgComments.addComponent(labelCommentMsg).addComponent(edtCommentMsg);
        GroupLayout.ParallelGroup vpgStarts = glMain.createParallelGroup();
        vpgStarts.addComponent(labelStarts).addComponent(edtStarts);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgComments).addGroup(vpgStarts);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgComments = glMain.createParallelGroup();
        hpgComments.addComponent(labelCommentMsg).addComponent(labelStarts);
        GroupLayout.ParallelGroup hpgStarts = glMain.createParallelGroup();
        hpgStarts.addComponent(edtCommentMsg).addComponent(edtStarts);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgComments).addGroup(hpgStarts);
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
            new CommentsManage().addComments();
        }
    }
}
