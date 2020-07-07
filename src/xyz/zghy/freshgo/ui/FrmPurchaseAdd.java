package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.control.PurchaseManage;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.model.BeanPurchase;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午2:18
 */
public class FrmPurchaseAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("选择商品名称：");
    private JLabel labelCount = new JLabel("商品数量：");
    private JTextField edtCount = new JTextField(20);
    private List<BeanGoodsMsg> goods = new ArrayList<BeanGoodsMsg>();
    private JComboBox cmbGoods = null;

    public FrmPurchaseAdd(JDialog owner, String title, boolean modal) {
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
        GroupLayout.ParallelGroup vpgCount = glMain.createParallelGroup();
        vpgCount.addComponent(labelCount).addComponent(edtCount);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgName).addGroup(vpgCount);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelName).addComponent(labelCount);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(cmbGoods).addComponent(edtCount);
        GroupLayout.SequentialGroup hsgText = glMain.createSequentialGroup();
        hsgText.addGroup(hpgLabel).addGroup(hpgedit);
        GroupLayout.ParallelGroup hpg = glMain.createParallelGroup();
        hpg.addGroup(hsgText);
        glMain.setHorizontalGroup(hpg);


        this.getContentPane().add(workPane, BorderLayout.CENTER);

        this.setSize(300, 150);
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
        }else if(e.getSource()==btnCheck){
            BeanPurchase bp = new BeanPurchase();
            bp.setAdminId(SystemUtil.currentAdmin.getAdminId());
            bp.setGoodsId(this.goods.get(this.cmbGoods.getSelectedIndex()-1).getGoodsId());
            bp.setGoodsName(this.goods.get(this.cmbGoods.getSelectedIndex()-1).getGoodsName());
            bp.setPurchaseNum(Integer.parseInt(this.edtCount.getText()));
            try {
                new PurchaseManage().addPurchase(bp);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);
        }

    }
}
