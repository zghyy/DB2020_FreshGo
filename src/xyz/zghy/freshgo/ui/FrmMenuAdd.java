package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.model.BeanMenuDetail;
import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/13 下午11:38
 */
public class FrmMenuAdd extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private JPanel mainPanel = new JPanel();
    private JLabel labelGoods = new JLabel("商品详情");


    private Button btnAdd = new Button("加入菜谱");
    private Button btnDelete = new Button("移出菜谱");
    private Button btnMake = new Button("生成菜谱");

    //生鲜分类
    private Object tblTitleFresh[] = {"类型序号", "类型名称", "类型描述"};
    private Object tblDataFresh[][];
    List<BeanFreshType> freshTypes = null;
    DefaultTableModel tablmodFresh = new DefaultTableModel();
    private JTable dataTableFresh = new JTable(tablmodFresh);


    //商品
    private Object tblTitleGoods[] = {"商品序号", "商品名称", "商品价格", "会员价格", "商品数量"};
    private Object tblDataGoods[][];
    List<BeanGoodsMsg> goodsMsgs = null;
    DefaultTableModel tblModGoods = new DefaultTableModel();
    private JTable dataTableGoods = new JTable(tblModGoods);

    private Object tblTitleMenuDetail[] = {"商品名称"};
    private Object tblDataMenuDetail[][];
    List<BeanMenuDetail> menuDetails = new ArrayList<BeanMenuDetail>();
    DefaultTableModel tblModMenuDetail = new DefaultTableModel();
    private JTable dataTableMenuDetail = new JTable(tblModMenuDetail);

    void reloadFreshTable() {
        try {
            freshTypes = new FreshTypeManage().loadFreshType();
            tblDataFresh = new Object[freshTypes.size()][3];
            for (int i = 0; i < freshTypes.size(); i++) {
                tblDataFresh[i][0] = freshTypes.get(i).getTypeOrder();
                tblDataFresh[i][1] = freshTypes.get(i).getTypeName();
                tblDataFresh[i][2] = freshTypes.get(i).getTypeDesc();
            }
            tablmodFresh.setDataVector(tblDataFresh, tblTitleFresh);
            this.dataTableFresh.validate();
            this.dataTableFresh.repaint();
        } catch (BaseException e) {
            e.printStackTrace();
        }
    }

    void reloadGoodsTable(BeanFreshType btf) {
        goodsMsgs = new GoodsManage().loadGoodsByFresh(btf);
        tblDataGoods = new Object[goodsMsgs.size()][5];
        for (int i = 0; i < goodsMsgs.size(); i++) {
            tblDataGoods[i][0] = goodsMsgs.get(i).getGoodsOrder();
            tblDataGoods[i][1] = goodsMsgs.get(i).getGoodsName();
            tblDataGoods[i][2] = goodsMsgs.get(i).getGoodsPrice();
            tblDataGoods[i][3] = goodsMsgs.get(i).getGoodsVipPrice();
            tblDataGoods[i][4] = goodsMsgs.get(i).getGoodsCount();
        }
        tblModGoods.setDataVector(tblDataGoods, tblTitleGoods);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    void reloadEmptyGoodsTable() {
        tblDataGoods = new Object[0][5];
        tblModGoods.setDataVector(tblDataGoods, tblTitleGoods);
        this.dataTableGoods.validate();
        this.dataTableGoods.repaint();
    }

    void reloadMenuDetail() {
        tblDataMenuDetail = new Object[this.menuDetails.size()][5];
        for (int i = 0; i < this.menuDetails.size(); i++) {
            tblDataMenuDetail[i][0] = menuDetails.get(i).getgName();
        }
        tblModMenuDetail.setDataVector(tblDataMenuDetail, tblTitleMenuDetail);
        this.dataTableMenuDetail.validate();
        this.dataTableMenuDetail.repaint();

    }


    public FrmMenuAdd(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        toolBar.add(btnMake);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        this.mainPanel.setLayout(new BorderLayout());
        this.getContentPane().add(this.mainPanel, BorderLayout.NORTH);

        this.labelGoods.setHorizontalAlignment(SwingConstants.CENTER);
        this.labelGoods.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
        this.mainPanel.add(this.labelGoods, BorderLayout.NORTH);


        JScrollPane types = new JScrollPane(this.dataTableFresh);
        types.setPreferredSize(new Dimension(300, 0));

        this.mainPanel.add(types, BorderLayout.WEST);
        this.dataTableFresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                reloadGoodsTable(FrmMenuAdd.this.freshTypes.get(FrmMenuAdd.this.dataTableFresh.getSelectedRow()));
            }
        });

        JScrollPane newGoods = new JScrollPane(this.dataTableGoods);
        this.mainPanel.add(newGoods, BorderLayout.CENTER);


        JScrollPane menuitem = new JScrollPane(this.dataTableMenuDetail);
        menuitem.setPreferredSize(new Dimension(200, 0));

        this.mainPanel.add(menuitem, BorderLayout.EAST);


        this.reloadFreshTable();
        this.reloadEmptyGoodsTable();
        this.reloadMenuDetail();

        this.setSize(1000, 600);
        // 屏幕居中显示
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnDelete.addActionListener(this);
        this.btnMake.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            if (this.dataTableGoods.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "未选中商品", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                BeanMenuDetail bmd = new BeanMenuDetail();
                for (BeanMenuDetail indexbod : this.menuDetails) {
                    if (this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsName() == indexbod.getgName()) {
                        JOptionPane.showMessageDialog(null, "不要重复添加哦", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                bmd.setgId(this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsId());
                bmd.setgName(this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsName());

                this.menuDetails.add(bmd);
                this.reloadMenuDetail();
            }
        } else if (e.getSource() == btnDelete) {
            int i = this.dataTableMenuDetail.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "未选中商品", "错误", JOptionPane.ERROR_MESSAGE);
            }
            else {
                this.menuDetails.remove(i);
                this.reloadMenuDetail();
            }
        } else if (e.getSource() == btnMake) {
            SystemUtil.globalMenuDetails = this.menuDetails;
            FrmMenuAddInfo dlg = new FrmMenuAddInfo(this,"菜谱详情",true);
            dlg.setVisible(true);
            this.menuDetails = SystemUtil.globalMenuDetails;
            this.reloadMenuDetail();
        }

    }
}
