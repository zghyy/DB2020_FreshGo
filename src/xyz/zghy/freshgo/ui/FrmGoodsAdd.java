package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.util.BaseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ghy
 * @date 2020/7/6 上午11:49
 */
public class FrmGoodsAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel workPane = new JPanel();
    private Button btnCheck = new Button("确定");
    private Button btnCancel = new Button("取消");
    private JLabel labelName = new JLabel("商品名称：");
    private JLabel labelType = new JLabel("商品所属类型：");
    private JLabel labelPrice = new JLabel("商品价格");
    private JLabel labelVipPrice = new JLabel("商品会员价");
    private JLabel labelSpec = new JLabel("商品规格");
    private JLabel labelDesc = new JLabel("商品描述");
    private JTextField edtName = new JTextField(20);
    private JTextField edtPrice = new JTextField(20);
    private JTextField edtVipPrice = new JTextField(20);
    private JTextField edtSpec = new JTextField(20);
    private JTextField edtDesc = new JTextField(20);
    private Map<String, BeanFreshType> TypeMap_name = new HashMap<String, BeanFreshType>();
    private Map<Integer, BeanFreshType> TypeMap_id = new HashMap<Integer, BeanFreshType>();
    private JComboBox cmbType = null;

    public FrmGoodsAdd(JDialog owner, String title, boolean modal) throws BaseException {
        super(owner, title, true);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnCheck);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        GroupLayout glMain = new GroupLayout(workPane);
        workPane.setLayout(glMain);
        glMain.setAutoCreateGaps(true);
        glMain.setAutoCreateContainerGaps(true);

        try {
            List<BeanFreshType> types = (new FreshTypeManage().loadFreshType());
            String[] strTypes = new String[types.size() + 1];
            strTypes[0] = "";
            for (int i = 0; i < types.size(); i++) {
                strTypes[i + 1] = types.get(i).getTypeName();
                this.TypeMap_id.put(types.get(i).getTypeId(), types.get(i));
                this.TypeMap_name.put(types.get(i).getTypeName(), types.get(i));
            }
            this.cmbType = new JComboBox(strTypes);
        } catch (BaseException e) {
            e.printStackTrace();
        }


        GroupLayout.ParallelGroup vpgName = glMain.createParallelGroup();
        vpgName.addComponent(labelName).addComponent(edtName);
        GroupLayout.ParallelGroup vpgType = glMain.createParallelGroup();
        vpgType.addComponent(labelType).addComponent(cmbType);
        GroupLayout.ParallelGroup vpgPrice = glMain.createParallelGroup();
        vpgPrice.addComponent(labelPrice).addComponent(edtPrice);
        GroupLayout.ParallelGroup vpgVipPrice = glMain.createParallelGroup();
        vpgVipPrice.addComponent(labelVipPrice).addComponent(edtVipPrice);
        GroupLayout.ParallelGroup vpgSpec = glMain.createParallelGroup();
        vpgSpec.addComponent(labelSpec).addComponent(edtSpec);
        GroupLayout.ParallelGroup vpgDesc = glMain.createParallelGroup();
        vpgDesc.addComponent(labelDesc).addComponent(edtDesc);
        GroupLayout.SequentialGroup vsp = glMain.createSequentialGroup();
        vsp.addGroup(vpgName).addGroup(vpgType).addGroup(vpgPrice).addGroup(vpgVipPrice);
        vsp.addGroup(vpgSpec).addGroup(vpgDesc);
        glMain.setVerticalGroup(vsp);

        GroupLayout.ParallelGroup hpgLabel = glMain.createParallelGroup();
        hpgLabel.addComponent(labelName).addComponent(labelType).addComponent(labelPrice);
        hpgLabel.addComponent(labelVipPrice).addComponent(labelSpec).addComponent(labelDesc);
        GroupLayout.ParallelGroup hpgedit = glMain.createParallelGroup();
        hpgedit.addComponent(edtName).addComponent(cmbType).addComponent(edtPrice);
        hpgedit.addComponent(edtVipPrice).addComponent(edtSpec).addComponent(edtDesc);
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


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == this.btnCheck) {

            BeanGoodsMsg bgm = new BeanGoodsMsg();
            bgm.setGoodsName(this.edtName.getText());
//            double goodsPrice =0;
//            double goodsVipPrice =0;
            try {
                bgm.setGoodsPrice(Double.parseDouble(this.edtPrice.getText()));
                bgm.setGoodsVipPrice(Double.parseDouble(this.edtVipPrice.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "单价输入不正确", "错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            bgm.setGoodsSpecifications(this.edtSpec.getText());
            bgm.setGoodsDesc(this.edtDesc.getText());
            bgm.setTypeId(this.cmbType.getSelectedIndex());

            new GoodsManage().addGoods(bgm);
        }
    }
}
