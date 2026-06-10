package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaPiede extends JFrame {

    JTextField totImp;
    JTextField ivaPerc;
    JTextField tot;
    JTextField totIva;
    JButton avanti;
    JButton calcola;

    // ── Palette ──────────────────────────────────────────────
    private static final Color BG = new Color(0x1A1A2E);
    private static final Color CARD = new Color(0x16213E);
    private static final Color ACCENT = new Color(0xE94560);
    private static final Color TEAL = new Color(0xA8DADC);
    private static final Color GREEN = new Color(0x4CAF50);
    private static final Color FG = new Color(0xEAEAEA);
    private static final Color FG_DIM = new Color(0x8899AA);
    private static final Color FIELD_BG = new Color(0x0D1B2A);
    private static final Color BORDER_C = new Color(0x2A3F5F);

    public InterfacciaPiede(Fattura fatturaOggetto) {
        setTitle("Generatore Fattura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);

        totImp = makeReadOnly(fatturaOggetto.getTotimp() + " €");
        ivaPerc = makeField("es. 22");
        totIva = makeReadOnly("");
        tot = makeReadOnly("");

        calcola = makeButton("= CALCOLA", new Color(0x0F3460));
        avanti = makeButton("✓ GENERA PDF", GREEN);

        GestoreEventiPiede gestore = new GestoreEventiPiede(this, fatturaOggetto);
        avanti.addActionListener(gestore);
        calcola.addActionListener(gestore);

        JPanel root = new JPanel(new BorderLayout(0, 16));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(24, 28, 24, 28));

        root.add(buildStepBar(3), BorderLayout.NORTH);
        root.add(buildSummaryCard(fatturaOggetto), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    // ── Step indicator (step 3 active) ────────────────────────
    private JPanel buildStepBar(int active) {
        JPanel bar = new JPanel(new GridBagLayout());
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(0, 0, 20, 0));
        String[] labels = {"Testata", "Corpo", "Piede"};
        GridBagConstraints gc = new GridBagConstraints();
        for (int i = 0; i < labels.length; i++) {
            final int idx = i + 1;
            JLabel circle = new JLabel(idx < active ? "✓" : String.valueOf(idx), SwingConstants.CENTER) {
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
            lbl.setForeground(idx == active ? TEAL : FG_DIM);
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

    // ── Summary card ──────────────────────────────────────────
    private JPanel buildSummaryCard(Fattura f) {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBackground(BG);

        // IVA input section
        JPanel inputCard = new JPanel(new BorderLayout(0, 10));
        inputCard.setBackground(CARD);
        inputCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        JLabel hdrInput = new JLabel("CALCOLO IVA");
        hdrInput.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdrInput.setForeground(TEAL);

        JPanel grid1 = new JPanel(new GridLayout(0, 2, 16, 8));
        grid1.setBackground(CARD);
        addRow(grid1, "Totale Imponibile", totImp);
        addRow(grid1, "Aliquota IVA (%)", ivaPerc);

        JPanel calcRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        calcRow.setBackground(CARD);
        calcRow.add(calcola);

        inputCard.add(hdrInput, BorderLayout.NORTH);
        inputCard.add(grid1, BorderLayout.CENTER);
        inputCard.add(calcRow, BorderLayout.SOUTH);

        // Totals section
        JPanel totalsCard = new JPanel(new BorderLayout(0, 10));
        totalsCard.setBackground(CARD);
        totalsCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        JLabel hdrTot = new JLabel("RIEPILOGO IMPORTI");
        hdrTot.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdrTot.setForeground(TEAL);

        JPanel grid2 = new JPanel(new GridLayout(0, 2, 16, 8));
        grid2.setBackground(CARD);
        addRow(grid2, "Totale IVA", totIva);
        addRow(grid2, "TOTALE FATTURA", tot);

        // style the total field differently
        tot.setFont(new Font("SansSerif", Font.BOLD, 15));
        tot.setForeground(TEAL);
        tot.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT, 2),
                new EmptyBorder(5, 8, 5, 8)
        ));

        totalsCard.add(hdrTot, BorderLayout.NORTH);
        totalsCard.add(grid2, BorderLayout.CENTER);

        outer.add(inputCard);
        outer.add(Box.createVerticalStrut(14));
        outer.add(totalsCard);

        // invoice summary info (read-only)
        JPanel infoCard = buildInfoCard(f);
        outer.add(Box.createVerticalStrut(14));
        outer.add(infoCard);

        return outer;
    }

    private JPanel buildInfoCard(Fattura f) {
        JPanel card = new JPanel(new GridLayout(0, 2, 16, 6));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        String[][] rows = {
            {"Numero Fattura", f.getNumeroFattura()},
            {"Data", f.getDataFattura()},
            {"Fornitore", f.getNomeFornitore()},
            {"Destinatario", f.getNomeDestinatario()},
            {"Articoli", f.getListaArticoli().size() + " voci inserite"}
        };
        for (String[] row : rows) {
            JLabel k = new JLabel(row[0]);
            k.setFont(new Font("SansSerif", Font.PLAIN, 11));
            k.setForeground(FG_DIM);
            JLabel v = new JLabel(row[1] != null ? row[1] : "—");
            v.setFont(new Font("SansSerif", Font.BOLD, 12));
            v.setForeground(FG);
            card.add(k);
            card.add(v);
        }

        JLabel hdr = new JLabel("RIEPILOGO FATTURA");
        hdr.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdr.setForeground(TEAL);

        JPanel wrapper = new JPanel(new BorderLayout(0, 10));
        wrapper.setBackground(CARD);
        wrapper.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));
        wrapper.add(hdr, BorderLayout.NORTH);
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
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
        f.setText(placeholder);
        f.setForeground(FG_DIM);
        f.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (f.getText().equals(placeholder)) {
                    f.setText("");
                    f.setForeground(FG);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setText(placeholder);
                    f.setForeground(FG_DIM);
                }
            }
        });
        return f;
    }

    private JTextField makeReadOnly(String text) {
        JTextField f = new JTextField(text, 16);
        f.setEditable(false);
        f.setBackground(FIELD_BG);
        f.setForeground(FG);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
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
        b.setFont(new Font("SansSerif", Font.BOLD, 13));
        b.setForeground(Color.WHITE);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(160, 38));
        return b;
    }
}
