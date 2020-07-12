package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.control.FullDiscountConnectMannage;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.model.BeanFullDiscountConnent;
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
 * @date 2020/7/12 下午5:07
 */
public class FrmFullDiscountGoodsConnection extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加商品绑定类型");
    private Button btnDelete = new Button("删除商品绑定类型");
    private Object tblTitle[] = {"绑定编号", "商品名称", "开始日期", "结束日期"};
    private Object tblData[][];
    List<BeanFullDiscountConnent> fullDiscountConnents = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);


    private void reloadTable() {
        fullDiscountConnents = new FullDiscountConnectMannage().loadFullDiscountConnect();
        tblData = new Object[fullDiscountConnents.size()][4];
        for (int i = 0; i < fullDiscountConnents.size(); i++) {
            tblData[i][0] = fullDiscountConnents.get(i).getFdcOrder();
            tblData[i][1] = fullDiscountConnents.get(i).getgName();
            tblData[i][2] = SystemUtil.SDF.format(fullDiscountConnents.get(i).getStartDate());
            tblData[i][3] = SystemUtil.SDF.format(fullDiscountConnents.get(i).getEndDate());
        }
        tablmod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmFullDiscountGoodsConnection(Frame owner, String title, boolean modal) {
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
        if(e.getSource()==btnAdd){
            FrmFullDiscountConnectGoodsAdd dlg = new FrmFullDiscountConnectGoodsAdd(this,"商品促销绑定",true);
            dlg.setVisible(true);
            this.reloadTable();
        }else if(e.getSource()==btnDelete){
        //TODO 商品连接删除部分
            int i = this.dataTable.getSelectedRow();
            try {
                new FullDiscountConnectMannage().deleteFullDiscountConnect(this.fullDiscountConnents.get(i));
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();
        }

    }
}
