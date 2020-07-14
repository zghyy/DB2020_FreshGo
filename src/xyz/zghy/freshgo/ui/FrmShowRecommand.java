package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.LocationManage;
import xyz.zghy.freshgo.control.RecommandManage;
import xyz.zghy.freshgo.model.BeanLocation;
import xyz.zghy.freshgo.model.BeanRecommand;
import xyz.zghy.freshgo.util.BusinessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/14 上午10:04
 */
public class FrmShowRecommand extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnClose = new Button("关闭");
    private Object tblTitle[] = {"推荐商品名", "商品菜谱来源", "菜谱描述"};
    private Object tblData[][];
    List<BeanRecommand> recommands = null;
    DefaultTableModel tblMod = new DefaultTableModel();
    private JTable dataTable = new JTable(tblMod);

    void reloadRecommand() {
        try {
            recommands = new RecommandManage().loadRecommand();
            tblData = new Object[recommands.size()][3];
            for (int i = 0; i < recommands.size(); i++) {
                tblData[i][0] = recommands.get(i).getgName();
                tblData[i][1] = recommands.get(i).getmName();
                tblData[i][2] = recommands.get(i).getDesc();
            }
            tblMod.setDataVector(tblData, tblTitle);
            this.dataTable.validate();
            this.dataTable.repaint();
        } catch (BusinessException businessException) {
            JOptionPane.showMessageDialog(null, businessException.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
        }

    }


    public FrmShowRecommand(JDialog owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        toolBar.add(btnClose);
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        this.reloadRecommand();
        this.getContentPane().add(new JScrollPane(this.dataTable), BorderLayout.CENTER);
        this.setSize(1000, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnClose.addActionListener(this);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnClose) {
            this.setVisible(false);
        }
    }
}
