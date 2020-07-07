package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.GoodsManage;
import xyz.zghy.freshgo.control.LocationManage;
import xyz.zghy.freshgo.model.BeanGoodsMsg;
import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.util.BaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/7 上午12:22
 */
public class FrmLocationManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加地址");
    private Button btnDelete = new Button("删除地址");
    private Object tblTitle[] = {"地址序号", "省", "市", "区", "详细地址", "联系人", "联系人手机号"};
    private Object tblData[][];
    List<BeanLocation> locations = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    private void reloadTable() {
        //TODO reloadLocation
        locations = new LocationManage().loadLocation();
        tblData = new Object[locations.size()][7];
        for (int i = 0; i < locations.size(); i++) {
            tblData[i][0] = locations.get(i).getLocateOrder();
            tblData[i][1] = locations.get(i).getProvince();
            tblData[i][2] = locations.get(i).getCity();
            tblData[i][3] = locations.get(i).getArea();
            tblData[i][4] = locations.get(i).getLocationDesc();
            tblData[i][5] = locations.get(i).getLinkman();
            tblData[i][6] = locations.get(i).getPhoneNumber();
        }
        tblMod.setDataVector(tblData, tblTitle);
        this.dataTable.validate();
        this.dataTable.repaint();
    }

    public FrmLocationManage(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.add(btnAdd);
        toolBar.add(btnDelete);
        this.getContentPane().add(toolBar, BorderLayout.NORTH);

        this.reloadTable();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(1000, 600);
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
        if (e.getSource() == btnAdd) {
            FrmLocationAdd dlg = new FrmLocationAdd(this, "添加地址", true);
            dlg.setVisible(true);
            reloadTable();
        } else if (e.getSource() == btnDelete) {

        }
    }
}
