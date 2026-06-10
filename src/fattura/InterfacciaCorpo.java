package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.List;

public class InterfacciaCorpo extends JFrame {

    JTextField articolo;
    JTextField descrizione;
    JTextField qta;
    JTextField prezzoPezzo;
    JTextField importo;
    JButton aggiungi;
    JButton avanti;
    JButton calcola;

    // ── Palette ──────────────────────────────────────────────
    private static final Color BG = new Color(0x1A1A2E);
    private static final Color CARD = new Color(0x16213E);
    private static final Color ACCENT = new Color(0xE94560);
    private static final Color TEAL = new Color(0xA8DADC);
    private static final Color FG = new Color(0xEAEAEA);
    private static final Color FG_DIM = new Color(0x8899AA);
    private static final Color FIELD_BG = new Color(0x0D1B2A);
    private static final Color BORDER_C = new Color(0x2A3F5F);
    private static final Color ROW_ALT = new Color(0x0F2030);

    public InterfacciaCorpo(Fattura fatturaOggetto) {
        setTitle("Generatore Fattura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);

        articolo = makeField("Nome articolo");
        descrizione = makeField("Descrizione servizio/bene");
        qta = makeField("1");
        prezzoPezzo = makeField("0.00");
        importo = makeField("");
        importo.setEditable(false);
        importo.setForeground(TEAL);

        aggiungi = makeButton("+ AGGIUNGI", ACCENT);
        calcola = makeButton("= CALCOLA", new Color(0x0F3460));
        avanti = makeButton("AVANTI  →", ACCENT);

        GestoreEventiCorp gestore = new GestoreEventiCorp(this, fatturaOggetto);
        aggiungi.addActionListener(gestore);
        avanti.addActionListener(gestore);
        calcola.addActionListener(gestore);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(24, 28, 24, 28));

        root.add(buildStepBar(2), BorderLayout.NORTH);
        root.add(buildInputCard(), BorderLayout.CENTER);
        root.add(buildTableCard(fatturaOggetto.getListaArticoli()), BorderLayout.EAST);
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    // ── Step indicator (step 2 active) ────────────────────────
    private JPanel buildStepBar(int active) {
        JPanel bar = new JPanel(new GridBagLayout());
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(0, 0, 20, 0));
        String[] labels = {"Testata", "Corpo", "Piede"};
        GridBagConstraints gc = new GridBagConstraints();
        for (int i = 0; i < labels.length; i++) {
            final int idx = i + 1;
            JLabel circle = new JLabel(String.valueOf(idx), SwingConstants.CENTER) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(idx <= active ? ACCENT : BORDER_C);
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            circle.setPreferredSize(new Dimension(32, 32));
            circle.setFont(new Font("SansSerif", Font.BOLD, 13));
            circle.setForeground(idx <= active ? Color.WHITE : FG_DIM);
            JLabel lbl = new JLabel(labels[i], SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", idx == active ? Font.BOLD : Font.PLAIN, 11));
            lbl.setForeground(idx == active ? TEAL : (idx < active ? FG_DIM : FG_DIM));
            JPanel cell = new JPanel(new BorderLayout(0, 4));
            cell.setBackground(BG);
            cell.add(circle, BorderLayout.CENTER);
            cell.add(lbl, BorderLayout.SOUTH);
            gc.gridx = i * 2;
            gc.gridy = 0;
            gc.anchor = GridBagConstraints.CENTER;
            gc.insets = new Insets(0, 0, 0, 0);
            bar.add(cell, gc);
            if (i < labels.length - 1) {
                JPanel line = new JPanel();
                line.setBackground(i + 1 < active ? ACCENT : BORDER_C);
                line.setPreferredSize(new Dimension(80, 2));
                gc.gridx = i * 2 + 1;
                gc.fill = GridBagConstraints.HORIZONTAL;
                gc.weightx = 1;
                gc.insets = new Insets(0, 0, 16, 0);
                bar.add(line, gc);
                gc.fill = GridBagConstraints.NONE;
                gc.weightx = 0;
                gc.insets = new Insets(0, 0, 0, 0);
            }
        }
        return bar;
    }

    // ── Input card ────────────────────────────────────────────
    private JPanel buildInputCard() {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        JLabel hdr = new JLabel("NUOVO ARTICOLO");
        hdr.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdr.setForeground(TEAL);

        JPanel grid = new JPanel(new GridLayout(0, 2, 16, 8));
        grid.setBackground(CARD);

        addRow(grid, "Articolo", articolo);
        addRow(grid, "Descrizione", descrizione);
        addRow(grid, "Quantità", qta);
        addRow(grid, "Prezzo al Pezzo", prezzoPezzo);
        addRow(grid, "Importo", importo);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(CARD);
        btnRow.add(calcola);
        btnRow.add(aggiungi);

        card.add(hdr, BorderLayout.NORTH);
        card.add(grid, BorderLayout.CENTER);
        card.add(btnRow, BorderLayout.SOUTH);
        return card;
    }

    // ── Articles table ────────────────────────────────────────
    private JPanel buildTableCard(List<Articolo> articoli) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        card.setPreferredSize(new Dimension(460, 0));

        JLabel hdr = new JLabel("ARTICOLI AGGIUNTI  (" + articoli.size() + ")");
        hdr.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdr.setForeground(TEAL);

        String[] cols = {"Articolo", "Descrizione", "Q.tà", "Prezzo", "Importo"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        for (Articolo a : articoli) {
            model.addRow(new Object[]{
                a.getNome(), a.getDescrizione(), a.getQuantita(),
                String.format("%.2f €", a.getPrezzoPezzo()),
                String.format("%.2f €", a.getImporto())
            });
        }

        JTable table = new JTable(model);
        table.setBackground(FIELD_BG);
        table.setForeground(FG);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));
        table.setRowHeight(26);
        table.setGridColor(BORDER_C);
        table.setShowGrid(true);
        table.getTableHeader().setBackground(new Color(0x0F3460));
        table.getTableHeader().setForeground(TEAL);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 11));
        table.setSelectionBackground(ACCENT.darker());
        table.setSelectionForeground(Color.WHITE);

        // alternating rows
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBackground(sel ? ACCENT.darker() : (row % 2 == 0 ? FIELD_BG : ROW_ALT));
                setForeground(sel ? Color.WHITE : FG);
                setBorder(new EmptyBorder(0, 6, 0, 6));
                return this;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(FIELD_BG);
        scroll.setBorder(BorderFactory.createLineBorder(BORDER_C, 1));

        card.add(hdr, BorderLayout.NORTH);
        card.add(scroll, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildFooter() {
        JPanel fp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        fp.setBackground(BG);
        fp.setBorder(new EmptyBorder(6, 0, 0, 0));
        fp.add(avanti);
        return fp;
    }

    private void addRow(JPanel grid, String label, JTextField field) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl.setForeground(FG_DIM);
        grid.add(lbl);
        grid.add(field);
    }

    // ── Factories ─────────────────────────────────────────────
    private JTextField makeField(String placeholder) {
        JTextField f = new JTextField(16);
        f.setBackground(FIELD_BG);
        f.setForeground(FG);
        f.setCaretColor(TEAL);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        if (!placeholder.isEmpty()) {
            f.setText(placeholder);
            f.setForeground(FG_DIM);
            f.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusGained(java.awt.event.FocusEvent e) {
                    if (f.getText().equals(placeholder)) {
                        f.setText("");
                        f.setForeground(FG);
                    }
                }

                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    if (f.getText().isEmpty()) {
                        f.setText(placeholder);
                        f.setForeground(FG_DIM);
                    }
                }
            });
        }
        return f;
    }

    private JButton makeButton(String text, Color color) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? color.darker()
                        : getModel().isRollover() ? color.brighter() : color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setFont(new Font("SansSerif", Font.BOLD, 12));
        b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(140, 36));
        return b;
    }
}
