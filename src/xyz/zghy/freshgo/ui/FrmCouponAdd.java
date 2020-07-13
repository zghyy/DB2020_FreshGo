package xyz.zghy.freshgo.ui;


import xyz.zghy.freshgo.control.CouponManage;
import xyz.zghy.freshgo.model.BeanCoupon;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;


/**
 * @author ghy
 * @date 2020/7/13 下午1:24
 */
public class FrmCouponAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelDesc = new JLabel("优惠券描述");
    private JLabel labelPrice = new JLabel("适用价格");
    private JLabel labelDiscount = new JLabel("减免价格");
    private JLabel labelStartTime = new JLabel("开始时间");
    private JLabel labelEndTime = new JLabel("结束时间");
    private JTextField edtDesc = new JTextField(20);
    private JTextField edtPrice = new JTextField(20);
    private JTextField edtDiscount = new JTextField(20);
    private JTextField edtStartTime = new JTextField(20);
    private JTextField edtEndTime = new JTextField(20);


    public FrmCouponAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, true);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);


        GroupLayout.ParallelGroup vpgDesc = glMain.createParallelGroup();
        vpgDesc.addComponent(labelDesc).addComponent(edtDesc);
        GroupLayout.ParallelGroup vpgPrice = glMain.createParallelGroup();
        vpgPrice.addComponent(labelPrice).addComponent(edtPrice);
        GroupLayout.ParallelGroup vpgCount = glMain.createParallelGroup();
        vpgCount.addComponent(labelDiscount).addComponent(edtDiscount);
        GroupLayout.ParallelGroup vpgStartTime = glMain.createParallelGroup();
        vpgStartTime.addComponent(labelStartTime).addComponent(edtStartTime);
        GroupLayout.ParallelGroup vpgEndTime = glMain.createParallelGroup();
        vpgEndTime.addComponent(labelEndTime).addComponent(edtEndTime);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgDesc).addGroup(vpgPrice).addGroup(vpgCount)
                .addGroup(vpgStartTime).addGroup(vpgEndTime);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelDesc).addComponent(labelPrice).addComponent(labelDiscount)
                .addComponent(labelStartTime).addComponent(labelEndTime);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(edtDesc).addComponent(edtPrice).addComponent(edtDiscount)
                .addComponent(edtStartTime).addComponent(edtEndTime);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgedit);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText);
        glMain.setHorizontalGroup(hpg);


        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(300, 250);
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
            BeanCoupon bc = new BeanCoupon();
            bc.setCouponDesc(edtDesc.getText());
            bc.setCouponAmount(Double.parseDouble(edtPrice.getText()));
            bc.setCouponDiscount(Double.parseDouble(edtDiscount.getText()));
            try {
                bc.setCouponStartDate(SystemUtil.SDF.parse(edtStartTime.getText()));
                bc.setCouponEndDate(SystemUtil.SDF.parse(edtEndTime.getText()));
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
            try {
                new CouponManage().addCoupons(bc);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);

            }
            this.setVisible(false);
        }
    }
}
