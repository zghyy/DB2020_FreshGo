package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FullDiscountConnectMannage;
import xyz.zghy.freshgo.control.FullDiscountTypeMannage;
import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.model.BeanFullDiscountMsg;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/12 下午3:14
 */
public class FrmFullDiscountConnectGoodsAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel infoPanel = new JPanel();
    private Button btnConnect = new Button("确认绑定");
    private Button btnCancel = new Button("取消");
//    private JLabel labelGoods = new JLabel("商品列表");
//    private JLabel labelFD = new JLabel("满折列表");
    private Object tblTitleFD[] = {"满折编号", "内容", "适用商品数量", "折扣", "起始日期", "结束日期"};
    private Object tblDataFD[][];
    List<BeanFullDiscountMsg> fullDiscountMsgs = null;
    DefaultTableModel tblModFD = new DefaultTableModel();
    private JTable dataTableFD = new JTable(tblModFD);

    private Object tblTitleGoods[] = {"商品序号", "类型名称", "商品名称", "商品价格", "会员价格", "商品数量"};
    private Object tblDataGoods[][];
    List<BeanGoodsMsg> goodsMsgs = null;
    DefaultTableModel tblModGoods = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tblModGoods);

    private void reloadTableGoods() {
        try {
            goodsMsgs = new GoodsManage().loadGoods();
            System.out.println(goodsMsgs.size());
            tblDataGoods = new Object[goodsMsgs.size()][6];
            for (int i = 0; i < goodsMsgs.size(); i++) {
                tblDataGoods[i][0] = goodsMsgs.get(i).getGoodsOrder();
                tblDataGoods[i][1] = goodsMsgs.get(i).getTypeName();
                tblDataGoods[i][2] = goodsMsgs.get(i).getGoodsName();
                tblDataGoods[i][3] = goodsMsgs.get(i).getGoodsPrice();
                tblDataGoods[i][4] = goodsMsgs.get(i).getGoodsVipPrice();
                tblDataGoods[i][5] = goodsMsgs.get(i).getGoodsCount();
            }
            tblModGoods.setDataVector(tblDataGoods, tblTitleGoods);
            this.dataTableGoods.validate();
            this.dataTableGoods.repaint();
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    void reloadTableFullDiscount() {
        fullDiscountMsgs = new FullDiscountTypeMannage().loadFullDiscountMsg();
        tblDataFD = new Object[fullDiscountMsgs.size()][6];
        for (int i = 0; i < fullDiscountMsgs.size(); i++) {
            tblDataFD[i][0] = fullDiscountMsgs.get(i).getFullDiscountOrder();
            tblDataFD[i][1] = fullDiscountMsgs.get(i).getFullDiscountDesc();
            tblDataFD[i][2] = fullDiscountMsgs.get(i).getFullDiscountNeedCount();
            tblDataFD[i][3] = fullDiscountMsgs.get(i).getFullDiscountData();
            tblDataFD[i][4] = SystemUtil.SDF.format(fullDiscountMsgs.get(i).getFullDiscountStartDate());
            tblDataFD[i][5] = SystemUtil.SDF.format(fullDiscountMsgs.get(i).getFullDiscountEndDate());
        }
        tblModFD.setDataVector(tblDataFD, tblTitleFD);
        this.dataTableFD.validate();
        this.dataTableFD.repaint();
    }


    public FrmFullDiscountConnectGoodsAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnConnect);
        toolBar.add(btnCancel);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        this.infoPanel.setLayout(new BorderLayout());
        this.getContentPane().add(infoPanel,BorderLayout.CENTER);

//        this.labelGoods.setHorizontalAlignment(SwingConstants.CENTER);
//        this.labelGoods.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        this.infoPanel.add(new JScrollPane(this.dataTableGoods),BorderLayout.NORTH);

//        this.labelFD.setHorizontalAlignment(SwingConstants.CENTER);
//        this.labelFD.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        this.infoPanel.add(new JScrollPane(this.dataTableFD),BorderLayout.CENTER);


        this.reloadTableFullDiscount();
        this.reloadTableGoods();

        this.setSize(800, 850);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnConnect.addActionListener(this);
        this.btnCancel.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCancel) {
            this.setVisible(false);
        } else if (e.getSource() == btnConnect) {
            int i =this.dataTableGoods.getSelectedRow();
            int j = this.dataTableFD.getSelectedRow();
            try {
                new FullDiscountConnectMannage().addFullDiscountConnect(this.goodsMsgs.get(i),this.fullDiscountMsgs.get(j));
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.setVisible(false);

        }
    }
}
