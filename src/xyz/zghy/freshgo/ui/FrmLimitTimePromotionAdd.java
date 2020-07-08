package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.control.LimitTimePromotionManage;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.model.BeanLimitTimePromotion;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午11:13
 */
public class FrmLimitTimePromotionAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("选择商品名称：");
    private JLabel labelPrice = new JLabel("商品价格");
    private JLabel labelCount = new JLabel("促销商品数量");
    private JLabel labelStartTime = new JLabel("开始时间");
    private JLabel labelEndTime = new JLabel("结束时间");
    private JTextField edtPrice = new JTextField(20);
    private JTextField edtCount = new JTextField(20);
    private JTextField edtStartTime = new JTextField(20);
    private JTextField edtEndTime = new JTextField(20);
    private List<BeanGoodsMsg> goods = new ArrayList<BeanGoodsMsg>();
    private JComboBox cmbGoods = null;


    public FrmLimitTimePromotionAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, true);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        try {
            List<BeanGoodsMsg> goodsMsgs = new GoodsManage().loadGoods();
            String[] strGoods = new String[goodsMsgs.size() + 1];
            strGoods[0] = "";
            for (int i = 0; i < goodsMsgs.size(); i++) {
                strGoods[i + 1] = goodsMsgs.get(i).getGoodsName();
                this.goods.add(goodsMsgs.get(i));
            }
            this.cmbGoods = new JComboBox(strGoods);
        } catch (BaseException e) {
            e.printStackTrace();
        }
        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);


        GroupLayout.ParallelGroup vpgName = glMain.createParallelGroup();
        vpgName.addComponent(labelName).addComponent(cmbGoods);
        GroupLayout.ParallelGroup vpgPrice = glMain.createParallelGroup();
        vpgPrice.addComponent(labelPrice).addComponent(edtPrice);
        GroupLayout.ParallelGroup vpgCount = glMain.createParallelGroup();
        vpgCount.addComponent(labelCount).addComponent(edtCount);
        GroupLayout.ParallelGroup vpgStartTime = glMain.createParallelGroup();
        vpgStartTime.addComponent(labelStartTime).addComponent(edtStartTime);
        GroupLayout.ParallelGroup vpgEndTime = glMain.createParallelGroup();
        vpgEndTime.addComponent(labelEndTime).addComponent(edtEndTime);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgName).addGroup(vpgPrice).addGroup(vpgCount)
                .addGroup(vpgStartTime).addGroup(vpgEndTime);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelName).addComponent(labelPrice).addComponent(labelCount)
                .addComponent(labelStartTime).addComponent(labelEndTime);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(cmbGoods).addComponent(edtPrice).addComponent(edtCount)
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
        if (e.getSource() == btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == btnCheck) {
            BeanLimitTimePromotion blp = new BeanLimitTimePromotion();
            blp.setGoodsId(this.goods.get(this.cmbGoods.getSelectedIndex() - 1).getGoodsId());
            blp.setGoodsName(this.goods.get(this.cmbGoods.getSelectedIndex() - 1).getGoodsName());
            blp.setLimitTimePromotionPrice(Integer.parseInt(this.edtPrice.getText()));
            blp.setLimitTimePromotionCount(Integer.parseInt(this.edtCount.getText()));
            try {
                blp.setLimitTimePromotionStartTime(SystemUtil.SDF.parse(this.edtStartTime.getText()));
                blp.setLimitTimePromotionEndTime(SystemUtil.SDF.parse(this.edtEndTime.getText()));

            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(null, "请按照yyyy-MM-dd HH:mm:ss的格式填写日期", "错误", JOptionPane.ERROR_MESSAGE);
            }
            try {
                new LimitTimePromotionManage().addLimitTimePromotion(blp);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);


        }
    }
}
