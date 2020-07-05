package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author ghy
 * @date 2020/7/4 上午8:25
 */
public class FrmMain extends JFrame implements ActionListener {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFresh = new JMenu("生鲜类型管理");
    private JMenu menuGoods = new JMenu("商品管理");
    private JMenu menuMenu = new JMenu("菜谱管理");
    private JMenu menuCoupon = new JMenu("优惠券管理");
    private JMenu menuFullDiscount = new JMenu("满折信息管理");
    private JMenu menuLimitTime = new JMenu("限时促销管理");

    private JMenuItem addFreshType = new JMenuItem("添加生鲜类型");
    private JMenuItem deleteFreshType = new JMenuItem("删除生鲜类型");

    private JMenuItem addGoods = new JMenuItem("添加商品");
    private JMenuItem deleteGoods = new JMenuItem("删除商品");
    private JMenuItem modifyGoods = new JMenuItem("修改商品信息");

    private JMenuItem addMenu = new JMenuItem("添加菜谱");
    private JMenuItem deleteMenu = new JMenuItem("删除菜谱");

    private JMenuItem addCoupon = new JMenuItem("添加优惠券");
    private JMenuItem deleteCoupon = new JMenuItem("删除优惠券");

    private JMenuItem addFullDiscount = new JMenuItem("添加满折信息");
    private JMenuItem deleteFullDiscount = new JMenuItem("删除满折信息");

    private JMenuItem addLimitTime = new JMenuItem("添加限时促销");
    private JMenuItem deleteLimitTime = new JMenuItem("删除限时促销");


    private FrmLogin Login = null;

    public FrmMain() {
        this.setExtendedState(FrmMain.MAXIMIZED_BOTH);
        this.setTitle("生鲜网超");
        Login = new FrmLogin(this, "登录", true);
        Login.setVisible(true);

        this.menuFresh.add(this.addFreshType);
        this.addFreshType.addActionListener(this);
        this.menuFresh.add(this.deleteFreshType);
        this.deleteFreshType.addActionListener(this);

        this.menuGoods.add(this.addGoods);
        this.addGoods.addActionListener(this);
        this.menuGoods.add(this.deleteGoods);
        this.deleteGoods.addActionListener(this);
        this.menuGoods.add(this.modifyGoods);
        this.modifyGoods.addActionListener(this);

        this.menuMenu.add(this.addMenu);
        this.addMenu.addActionListener(this);
        this.menuMenu.add(this.deleteMenu);
        this.deleteMenu.addActionListener(this);

        this.menuCoupon.add(this.addCoupon);
        this.addCoupon.addActionListener(this);
        this.menuCoupon.add(this.deleteCoupon);
        this.deleteCoupon.addActionListener(this);

        this.menuFullDiscount.add(this.addFullDiscount);
        this.addFullDiscount.addActionListener(this);
        this.menuFullDiscount.add(this.deleteFullDiscount);
        this.deleteFullDiscount.addActionListener(this);

        this.menuLimitTime.add(this.addLimitTime);
        this.addLimitTime.addActionListener(this);
        this.menuLimitTime.add(this.deleteLimitTime);
        this.deleteLimitTime.addActionListener(this);

        menuBar.add(menuFresh);
        menuBar.add(menuGoods);
        menuBar.add(menuMenu);
        menuBar.add(menuCoupon);
        menuBar.add(menuFullDiscount);
        menuBar.add(menuLimitTime);

        this.setJMenuBar(menuBar);

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });

        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.menuFresh){

        }
    }
}
