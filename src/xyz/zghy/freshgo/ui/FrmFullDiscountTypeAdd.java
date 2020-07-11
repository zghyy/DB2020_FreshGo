package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FullDiscountTypeMannage;
import xyz.zghy.freshgo.model.BeanFullDiscountMsg;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

/**
 * @author ghy
 * @date 2020/7/11 下午3:10
 */
public class FrmFullDiscountTypeAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelContent = new JLabel("满折信息内容：");
    private JLabel labelCount = new JLabel("满折适用数量：");
    private JLabel labelData = new JLabel("满折折扣：");
    private JLabel labelStartDate = new JLabel("满折开始日期：");
    private JLabel labelEndDate = new JLabel("满折结束日期：");
    private JTextField edtContent = new JTextField(30);
    private JTextField edtCount = new JTextField(30);
    private JTextField edtData = new JTextField(30);
    private JTextField edtStartDate = new JTextField(30);
    private JTextField edtEndDate = new JTextField(30);


    public FrmFullDiscountTypeAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        GroupLayout.ParallelGroup vpgContent = glMain.createParallelGroup();
        vpgContent.addComponent(labelContent).addComponent(edtContent);
        GroupLayout.ParallelGroup vpgCount = glMain.createParallelGroup();
        vpgCount.addComponent(labelCount).addComponent(edtCount);
        GroupLayout.ParallelGroup vpgData = glMain.createParallelGroup();
        vpgData.addComponent(labelData).addComponent(edtData);
        GroupLayout.ParallelGroup vpgStartDate = glMain.createParallelGroup();
        vpgStartDate.addComponent(labelStartDate).addComponent(edtStartDate);
        GroupLayout.ParallelGroup vpgEndDate = glMain.createParallelGroup();
        vpgEndDate.addComponent(labelEndDate).addComponent(edtEndDate);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgContent).addGroup(vpgCount).addGroup(vpgData);
        vsp.addGroup(vpgStartDate).addGroup(vpgEndDate);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelContent).addComponent(labelCount).addComponent(labelData);
        hpgLabel.addComponent(labelStartDate).addComponent(labelEndDate);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(edtContent).addComponent(edtCount).addComponent(edtData);
        hpgedit.addComponent(edtStartDate).addComponent(edtEndDate);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgedit);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText);
        glMain.setHorizontalGroup(hpg);

        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(400, 270);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.btnCancel.addActionListener(this);
        this.btnCheck.addActionListener(this);

        this.validate();




        this.btnCancel.addActionListener(this);
        this.btnCheck.addActionListener(this);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.btnCancel){
            this.setVisible(false);
        }
        else if(e.getSource()==this.btnCheck){
            BeanFullDiscountMsg bfdm = new BeanFullDiscountMsg();
            bfdm.setFullDiscountDesc(this.edtContent.getText());
            bfdm.setFullDiscountNeedCount(Integer.parseInt(this.edtCount.getText()));
            try {
                bfdm.setFullDiscountData(Integer.parseInt(this.edtData.getText()));
                bfdm.setFullDiscountStartDate(SystemUtil.SDF.parse(this.edtStartDate.getText()));
                bfdm.setFullDiscountEndDate(SystemUtil.SDF.parse(this.edtStartDate.getText()));
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null, "请按照yyyy-MM-dd HH:mm:ss的格式填写日期", "错误", JOptionPane.ERROR_MESSAGE);
            }

            try {
                new FullDiscountTypeMannage().addFullDiscountMsg(bfdm);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }

            this.setVisible(false);
        }
    }
}
