package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.GoodsManage;
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
public class FrmLocationManage  extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加地址");
    private Button btnDelete = new Button("删除地址");
    private Object tblTitle[] = {"地址序号", "省", "市", "区", "联系人", "联系人手机号"};
    private Object tblData[][];
    List<BeanLocation> locations = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    private void reloadTable(){
        //TODO reloadLocation
    }









    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
