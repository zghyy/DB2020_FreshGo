package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.FreshTypeManage;
import xyz.zghy.freshgo.model.BeanFreshType;
import xyz.zghy.freshgo.util.BaseException;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * @author ghy
 * @date 2020/7/6 上午8:17
 */
public class FrmFreshTypeManage extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnAdd = new Button("添加生鲜类型");
    private Button btnDelete = new Button("删除生鲜类型");
    private Object tblTitle[] = {"类型编号", "类型名称", "类型描述"};
    private Object tblData[][];
    List<BeanFreshType> freshTypes = null;
    DefaultTableModel tablmod = new DefaultTableModel();
    private JTable dataTable = new JTable(tablmod);

    private void reloadTable() {
        try {
            freshTypes = new FreshTypeManage().loadFreshType();
            tblData = new Object[freshTypes.size()][3];
            for (int i = 0; i < freshTypes.size(); i++) {
                tblData[i][0] = freshTypes.get(i).getTypeId();
                tblData[i][1] = freshTypes.get(i).getTypeName();
                tblData[i][2] = freshTypes.get(i).getTypeDesc();
            }
            tablmod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        }
        catch (BaseException e){
            e.printStackTrace();
        }
    }

    public FrmFreshTypeManage(Frame owner, String title, boolean modal){
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
        if (e.getSource()==this.btnAdd){
            FrmFreshTypeAdd dlg = new FrmFreshTypeAdd(this,"添加生鲜类型",true);
            dlg.setVisible(true);
            this.reloadTable();
        }
        else if(e.getSource()==this.btnDelete){
            int i = this.dataTable.getSelectedRow();
            System.out.println(i);
            BeanFreshType deleteFT = this.freshTypes.get(i);
            try {
                new FreshTypeManage().deleteFreshType(deleteFT);
            } catch (BusinessException businessException) {
                JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
            this.reloadTable();
        }
    }
}
