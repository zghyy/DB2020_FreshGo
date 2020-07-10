package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.CommentsManage;
import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.model.BeanComments;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.model.BeanOrderDetail;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/4 上午8:25
 */
public class FrmMain extends JFrame implements ActionListener {
    private JPanel mainPanel = new JPanel();
    private JPanel orderPanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JPanel statusBar = new JPanel();

    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuAdmin = new JMenu("管理员维护");
    private JMenuItem freshManage = new JMenuItem("生鲜类型管理");
    private JMenuItem goodsManage = new JMenuItem("商品管理");
    private JMenuItem menuManage = new JMenuItem("菜谱管理");
    private JMenuItem couponManage = new JMenuItem("优惠券管理");
    private JMenuItem fullDiscountManage = new JMenuItem("满折信息管理");
    private JMenuItem limitTimeManage = new JMenuItem("限时促销管理");
    private JMenuItem purchase = new JMenuItem("商品采购");

    private JMenu menuUser = new JMenu("用户选择");
    private JMenuItem orders = new JMenuItem("查看我的订单");
    private JMenuItem locateManage = new JMenuItem("配送地址管理");

    private JLabel labelShopping = new JLabel("购物车");
    private JLabel labelGoods = new JLabel("商品详情");
    private Button btAdd = new Button("添加至购物车");
    private Button btDelete = new Button("移除购物车");
    private Button btSub = new Button("提交订单");


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

    //评价
    private Object tblTitleComments[] = {"评论人", "评论内容", "星级", "评论日期"};
    private Object tblDataComments[][];
    List<BeanComments> comments = null;
    DefaultTableModel tblModComments = new DefaultTableModel();
    private JTable dataTableComments = new JTable(tblModComments);


    //订单
    private Object tblTitleOrdersDetail[] = {"商品名称", "商品单价", "商品数量"};
    private Object tblDataOrdersDetail[][];
    List<BeanOrderDetail> orderDetails = new ArrayList<BeanOrderDetail>();
    DefaultTableModel tblModOrderDetails = new DefaultTableModel();
    private JTable dataTableOrderDetails = new JTable(tblModOrderDetails);


    private FrmLogin Login = null;


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
        System.out.println(goodsMsgs.size());
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

    void reloadComments() {
        comments = new CommentsManage().loadComments(this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsId());
        tblDataComments = new Object[comments.size()][4];
        for (int i = 0; i < comments.size(); i++) {
            tblDataComments[i][0] = comments.get(i).getUserName();
            tblDataComments[i][1] = comments.get(i).getCommentMsg();
            tblDataComments[i][2] = comments.get(i).getCommentStar();
            tblDataComments[i][3] = SystemUtil.SDF.format(comments.get(i).getCommentDate());

        }
        tblModComments.setDataVector(tblDataComments, tblTitleComments);
        this.dataTableComments.validate();
        this.dataTableComments.repaint();
    }

    void reloadEmptyComments() {
        tblDataComments = new Object[0][4];
        tblModComments.setDataVector(tblDataComments, tblTitleComments);
        this.dataTableComments.validate();
        this.dataTableComments.repaint();
    }


    void reloadOrderDetails() {
        tblDataOrdersDetail = new Object[this.orderDetails.size()][5];
        for (int i = 0; i < this.orderDetails.size(); i++) {
            tblDataOrdersDetail[i][0] = orderDetails.get(i).getGoodsName();
            tblDataOrdersDetail[i][1] = orderDetails.get(i).getGoodsPrice();
            tblDataOrdersDetail[i][2] = orderDetails.get(i).getGoodsCount();
        }
        tblModOrderDetails.setDataVector(tblDataOrdersDetail, tblTitleOrdersDetail);
        this.dataTableOrderDetails.validate();
        this.dataTableOrderDetails.repaint();
        SystemUtil.globalOrderDetails = this.orderDetails;

    }


    public FrmMain() {
        this.setExtendedState(FrmMain.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超");
        Login = new FrmLogin(this, "登录", true);
        Login.setVisible(true);

        if ("管理员".equals(SystemUtil.currentLoginType)) {
            this.menuAdmin.add(this.freshManage);
            this.freshManage.addActionListener(this);
            this.menuAdmin.add(this.goodsManage);
            this.goodsManage.addActionListener(this);
            this.menuAdmin.add(this.menuManage);
            this.menuManage.addActionListener(this);
            this.menuAdmin.add(this.couponManage);
            this.couponManage.addActionListener(this);
            this.menuAdmin.add(this.fullDiscountManage);
            this.fullDiscountManage.addActionListener(this);
            this.menuAdmin.add(this.limitTimeManage);
            this.limitTimeManage.addActionListener(this);
            this.menuAdmin.add(this.purchase);
            this.purchase.addActionListener(this);
            menuBar.add(menuAdmin);
            this.setJMenuBar(menuBar);
        } else {
            this.menuUser.add(orders);
            this.orders.addActionListener(this);
            this.menuUser.add(locateManage);
            this.locateManage.addActionListener(this);
            menuBar.add(menuUser);
            this.setJMenuBar(menuBar);

            this.mainPanel.setLayout(new BorderLayout());
            this.getContentPane().add(this.mainPanel, BorderLayout.NORTH);

            this.labelGoods.setHorizontalAlignment(SwingConstants.CENTER);
            this.labelGoods.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            this.mainPanel.add(this.labelGoods, BorderLayout.NORTH);

            this.mainPanel.add(new JScrollPane(this.dataTableFresh), BorderLayout.WEST);
            this.dataTableFresh.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reloadGoodsTable(FrmMain.this.freshTypes.get(FrmMain.this.dataTableFresh.getSelectedRow()));
                }
            });
            JScrollPane newComments = new JScrollPane(this.dataTableComments);
            newComments.setPreferredSize(new Dimension(750, 0));
            this.mainPanel.add(newComments, BorderLayout.EAST);

            JScrollPane newGoods = new JScrollPane(this.dataTableGoods);
            this.mainPanel.add(newGoods, BorderLayout.CENTER);
            this.dataTableGoods.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    reloadComments();
//                    super.mouseClicked(e);
                }
            });


            this.orderPanel.setLayout(new BorderLayout());
            this.mainPanel.add(this.orderPanel, BorderLayout.SOUTH);

            this.labelShopping.setHorizontalAlignment(SwingConstants.CENTER);
            this.labelShopping.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

            this.orderPanel.add(this.labelShopping, BorderLayout.NORTH);
            this.orderPanel.add(new JScrollPane(this.dataTableOrderDetails), BorderLayout.CENTER);
            dataTableOrderDetails.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);


            this.buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            this.buttonPanel.add(btAdd);
            this.btAdd.addActionListener(this);
            this.btDelete.addActionListener(this);
            this.btSub.addActionListener(this);
            this.buttonPanel.add(btDelete);
            this.buttonPanel.add(btSub);
            this.orderPanel.add(this.buttonPanel, BorderLayout.SOUTH);


            this.reloadFreshTable();
            this.reloadEmptyGoodsTable();
            this.reloadEmptyComments();
            this.reloadOrderDetails();

        }


        statusBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel label = null;
        if ("管理员".equals(SystemUtil.currentLoginType)) {
            label = new JLabel("您好!   尊敬的管理员:" + SystemUtil.currentAdmin.getAdminName());
        } else {
            label = new JLabel("您好! " + SystemUtil.currentUser.getUserName());
        }
        statusBar.add(label);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.freshManage) {
            FrmFreshTypeManage dlg = new FrmFreshTypeManage(this, "生鲜类型管理", true);
            dlg.setVisible(true);
        } else if (e.getSource() == this.goodsManage) {
            FrmGoodsManage dlg = new FrmGoodsManage(this, "商品管理", true);
            dlg.setVisible(true);
        } else if (e.getSource() == this.locateManage) {
            FrmLocationManage dlg = new FrmLocationManage(this, "配送地址管理", true);
            dlg.setVisible(true);
        } else if (e.getSource() == this.purchase) {
            FrmPurchaseManage dlg = new FrmPurchaseManage(this, "商品采购", true);
            dlg.setVisible(true);
        } else if (e.getSource() == this.limitTimeManage) {
            FrmLimitTimePromotionManage dlg = new FrmLimitTimePromotionManage(this, "显示促销商品", true);
            dlg.setVisible(true);
        } else if (e.getSource() == this.btAdd) {
            if (this.dataTableGoods.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "未选中商品", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                BeanOrderDetail bod = new BeanOrderDetail();
                for (BeanOrderDetail indexbod : this.orderDetails) {
                    if (this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsName() == indexbod.getGoodsName()) {
                        indexbod.setGoodsCount(indexbod.getGoodsCount() + 1);
                        this.reloadOrderDetails();
                        return;
                    }
                }
                bod.setGoodsName(this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsName());
                bod.setGoodsCount(1);
                bod.setGoodsPrice(this.goodsMsgs.get(this.dataTableGoods.getSelectedRow()).getGoodsPrice());
                this.orderDetails.add(bod);
                this.reloadOrderDetails();
            }
        } else if (e.getSource() == this.btDelete) {
            int i = this.dataTableOrderDetails.getSelectedRow();
            if (i == -1) {
                JOptionPane.showMessageDialog(null, "未选中商品", "错误", JOptionPane.ERROR_MESSAGE);
            } else if (this.orderDetails.get(i).getGoodsCount() > 1) {
                this.orderDetails.get(i).setGoodsCount(this.orderDetails.get(i).getGoodsCount() - 1);
                this.reloadOrderDetails();
            } else {
                this.orderDetails.remove(i);
                this.reloadOrderDetails();
            }
        } else if (e.getSource() == this.btSub) {
            if (this.orderDetails.size() == 0) {
                JOptionPane.showMessageDialog(null, "购物车还是空的，无法提交订单哦！", "错误", JOptionPane.ERROR_MESSAGE);

            } else {
                FrmSelectLocation dlg = new FrmSelectLocation(this, "地址选择界面", true);
                dlg.setVisible(true);
            }
        }
    }
}

