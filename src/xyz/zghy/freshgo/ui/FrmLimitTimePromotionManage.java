package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanLimitTimePromotion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 下午6:59
 */
public class FrmLimitTimePromotionManage extends JDialog implements ActionListener {

    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加限时促销商品");
    private Button btnDelete = new Button("删除显示促销商品");
    private Object tblTitle[] = {"类型序号", "促销商品名称","促销价格", "促销数量","起始日期","结束日期"};
    private Object tblData[][];
    List<BeanLimitTimePromotion> limitTimePromotions = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable(){

    }


    FrmLimitTimePromotionManage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar,BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable),BorderLayout.CENTER);

        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnAdd.addActionListener(this);
        this.btnDelete.addActionListener(this);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
