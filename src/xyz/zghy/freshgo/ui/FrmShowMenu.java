package xyz.zghy.freshgo.ui;

import xyz.zghy.freshgo.control.MenuDetailManage;
import xyz.zghy.freshgo.control.MenuManage;
import xyz.zghy.freshgo.model.BeanMenuDetail;
import xyz.zghy.freshgo.model.BeanMenuMsg;
import xyz.zghy.freshgo.util.SystemUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * @author ghy
 * @date 2020/7/13 下午11:00
 */
public class FrmShowMenu extends JDialog implements ActionListener {
    private JPanel toolBar = new JPanel();
    private Button btnSearch = new Button("查看我的推荐");
    private Button btnAdd = new Button("添加菜谱");
    private Object tblTitleMenu[] = {"菜谱序号", "菜谱名", "菜谱描述"};
    private Object tblDataMenu[][];
    List<BeanMenuMsg> menuMsgs = null;
    DefaultTableModel tblModMenu = new DefaultTableModel();
    private JTable dataTableMenu = new JTable(tblModMenu);

    private Object tblTitleMenuDetail[] = {"菜谱用料"};
    private Object tblDataMenuDetail[][];
    List<BeanMenuDetail> menuDetails = null;
    DefaultTableModel tblModMenuDetail = new DefaultTableModel();
    private JTable dataTableMenuDetail = new JTable(tblModMenuDetail);

    void reloadTableMenu() {
        menuMsgs = new MenuManage().loadMenuMsg();
        tblDataMenu = new Object[menuMsgs.size()][3];
        for (int i = 0; i < menuMsgs.size(); i++) {
            tblDataMenu[i][0] = menuMsgs.get(i).getMenuOrder();
            tblDataMenu[i][1] = menuMsgs.get(i).getMenuName();
            tblDataMenu[i][2] = menuMsgs.get(i).getMenuDesc();
        }
        tblModMenu.setDataVector(tblDataMenu, tblTitleMenu);
        this.dataTableMenu.validate();
        this.dataTableMenu.repaint();
    }

    void reloadTableMenuDetail() {
        int size = 0;
        if (this.dataTableMenu.getSelectedRow() == -1) {
            size = 0;
        } else {
            menuDetails = new MenuDetailManage().loadMenuDetail(this.menuMsgs.get(this.dataTableMenu.getSelectedRow()));
            size = menuDetails.size();
        }
        tblDataMenuDetail = new Object[size][3];
        for (int i = 0; i < size; i++) {
            tblDataMenuDetail[i][0] = menuDetails.get(i).getgName();
        }
        tblModMenuDetail.setDataVector(tblDataMenuDetail, tblTitleMenuDetail);
        this.dataTableMenuDetail.validate();
        this.dataTableMenuDetail.repaint();

    }

    public FrmShowMenu(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
        if ("用户".equals(SystemUtil.currentLoginType)) {
            toolBar.add(btnSearch);
        } else {
            toolBar.add(btnAdd);
        }
        this.getContentPane().add(toolBar, BorderLayout.SOUTH);

        this.reloadTableMenu();
        this.reloadTableMenuDetail();

        this.getContentPane().add(new JScrollPane(this.dataTableMenu), BorderLayout.CENTER);
        this.dataTableMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                reloadTableMenuDetail();
            }
        });

        JScrollPane detail = new JScrollPane(this.dataTableMenuDetail);
        detail.setPreferredSize(new Dimension(200, 0));

        this.getContentPane().add(detail, BorderLayout.EAST);

        this.setSize(800, 600);
        double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        this.setLocation((int) (width - this.getWidth()) / 2,
                (int) (height - this.getHeight()) / 2);

        this.validate();

        this.btnSearch.addActionListener(this);
        this.btnAdd.addActionListener(this);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSearch) {
            

        } else if (e.getSource() == btnAdd) {
            FrmMenuAdd dlg = new FrmMenuAdd(this,"添加菜谱",true);
            dlg.setVisible(true);
            this.reloadTableMenu();
        }

    }
}
