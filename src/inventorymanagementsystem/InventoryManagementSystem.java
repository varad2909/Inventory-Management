
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class InventoryManagementSystem extends JFrame {

    private JTextField txtId, txtName, txtQty, txtPrice;
    private JTable table;
    private DefaultTableModel model;
    private JLabel status;

    public InventoryManagementSystem() {
        setTitle("Advanced Inventory Management System");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setContentPane(new GradientBackground());
        setLayout(new BorderLayout(15, 15));
        ((JComponent)getContentPane()).setBorder(new EmptyBorder(15,15,15,15));

       
        JLabel title = new JLabel("Inventory Dashboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        
        GlassPanel form = new GlassPanel();
        form.setLayout(new GridLayout(6,2,12,12));
        form.setBorder(new EmptyBorder(20,20,20,20));

        Font f = new Font("Segoe UI", Font.PLAIN, 14);

        form.add(label("Item ID"));
        txtId = field(f); form.add(txtId);

        form.add(label("Item Name"));
        txtName = field(f); form.add(txtName);

        form.add(label("Quantity"));
        txtQty = field(f); form.add(txtQty);

        form.add(label("Price"));
        txtPrice = field(f); form.add(txtPrice);

        FancyButton addBtn = new FancyButton("ADD", new Color(46,204,113));
        FancyButton updateBtn = new FancyButton("UPDATE", new Color(52,152,219));

        form.add(addBtn);
        form.add(updateBtn);

        
        GlassPanel tablePanel = new GlassPanel();
        tablePanel.setLayout(new BorderLayout(10,10));
        tablePanel.setBorder(new EmptyBorder(15,15,15,15));

        model = new DefaultTableModel(new String[]{"ID","Name","Qty","Price"},0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setSelectionBackground(new Color(52,152,219));

        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        FancyButton deleteBtn = new FancyButton("DELETE", new Color(231,76,60));
        tablePanel.add(deleteBtn, BorderLayout.SOUTH);

      
        JPanel center = new JPanel(new GridLayout(1,2,20,20));
        center.setOpaque(false);
        center.add(form);
        center.add(tablePanel);
        add(center, BorderLayout.CENTER);

     
        status = new JLabel(" Ready");
        status.setForeground(Color.WHITE);
        add(status, BorderLayout.SOUTH);

      
        addBtn.addActionListener(e -> addItem());
        updateBtn.addActionListener(e -> updateItem());
        deleteBtn.addActionListener(e -> deleteItem());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int r = table.getSelectedRow();
                txtId.setText(model.getValueAt(r,0).toString());
                txtName.setText(model.getValueAt(r,1).toString());
                txtQty.setText(model.getValueAt(r,2).toString());
                txtPrice.setText(model.getValueAt(r,3).toString());
            }
        });
    }

  
    void addItem() {
        if(valid()){
            model.addRow(new Object[]{
                    txtId.getText(), txtName.getText(),
                    txtQty.getText(), txtPrice.getText()
            });
            status.setText(" Item added successfully");
            clear();
        }
    }

    void updateItem() {
        int r = table.getSelectedRow();
        if(r>=0 && valid()){
            for(int i=0;i<4;i++)
                model.setValueAt(
                        new String[]{txtId.getText(),txtName.getText(),
                        txtQty.getText(),txtPrice.getText()}[i], r, i);
            status.setText(" Item updated");
            clear();
        }
    }

    void deleteItem() {
        int r = table.getSelectedRow();
        if(r>=0){
            model.removeRow(r);
            status.setText(" Item deleted");
        }
    }

    boolean valid(){
        return !txtId.getText().isEmpty() &&
               !txtName.getText().isEmpty() &&
               !txtQty.getText().isEmpty() &&
               !txtPrice.getText().isEmpty();
    }

    void clear(){
        txtId.setText("");
        txtName.setText("");
        txtQty.setText("");
        txtPrice.setText("");
    }

 

    JLabel label(String t){
        JLabel l = new JLabel(t);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return l;
    }

    JTextField field(Font f){
        JTextField t = new JTextField();
        t.setFont(f);
        t.setBorder(new CompoundBorder(
                new LineBorder(new Color(180,180,180)),
                new EmptyBorder(6,10,6,10)
        ));
        return t;
    }

 
    class GlassPanel extends JPanel{
        GlassPanel(){ setOpaque(false); }
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(255,255,255,200));
            g2.fillRoundRect(5,5,getWidth()-10,getHeight()-10,30,30);
            super.paintComponent(g);
        }
    }

    
    class GradientBackground extends JPanel{
        protected void paintComponent(Graphics g){
            Graphics2D g2=(Graphics2D)g;
            GradientPaint gp = new GradientPaint(
                    0,0,new Color(44,62,80),
                    0,getHeight(),new Color(52,152,219));
            g2.setPaint(gp);
            g2.fillRect(0,0,getWidth(),getHeight());
        }
    }

  
    class FancyButton extends JButton{
        Color base, hover;
        FancyButton(String text, Color c){
            super(text);
            base = c;
            hover = c.brighter();
            setBackground(base);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBorder(new EmptyBorder(10,20,10,20));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e){
                    setBackground(hover);
                }
                public void mouseExited(MouseEvent e){
                    setBackground(base);
                }
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new InventoryManagementSystem().setVisible(true));
    }
}
