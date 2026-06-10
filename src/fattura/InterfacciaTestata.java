package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaTestata extends JFrame {

    JTextField nomeForn;
    JTextField nomeDest;
    JTextField aziendaCed;
    JTextField aziendaDest;
    JTextField pIvaDest;
    JTextField pIvaCed;
    JTextField numFattura;
    JTextField dataFattura;
    JTextField tipoFattura;
    JTextField tipoPagamento;
    JTextField banca;
    JButton avanti;

    // ── Palette ──────────────────────────────────────────────
    private static final Color BG = new Color(0x1A1A2E);
    private static final Color CARD = new Color(0x16213E);
    private static final Color HEADER = new Color(0x0F3460);
    private static final Color ACCENT = new Color(0xE94560);
    private static final Color TEAL = new Color(0xA8DADC);
    private static final Color FG = new Color(0xEAEAEA);
    private static final Color FG_DIM = new Color(0x8899AA);
    private static final Color FIELD_BG = new Color(0x0D1B2A);
    private static final Color BORDER_C = new Color(0x2A3F5F);

    public InterfacciaTestata() {
        setTitle("Generatore Fattura");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);

        // ── Fields ────────────────────────────────────────────
        nomeForn = makeField("Mario Rossi");
        nomeDest = makeField("Luigi Bianchi");
        aziendaCed = makeField("Rossi S.r.l.");
        aziendaDest = makeField("Bianchi S.p.A.");
        pIvaCed = makeField("IT01234567890");
        pIvaDest = makeField("IT09876543210");
        numFattura = makeField("001/2024");
        dataFattura = makeField("gg/mm/aaaa");
        tipoFattura = makeField("Fattura ordinaria");
        tipoPagamento = makeField("Bonifico bancario");
        banca = makeField("Banca Intesa");
        avanti = makeButton("AVANTI  →");

        GestoreEventiTest gestore = new GestoreEventiTest(this);
        avanti.addActionListener(gestore);

        // ── Root layout ───────────────────────────────────────
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout(0, 0));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(24, 28, 24, 28));

        root.add(buildStepBar(1), BorderLayout.NORTH);
        root.add(buildFormArea(), BorderLayout.CENTER);
        root.add(buildFooter(), BorderLayout.SOUTH);

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    // ── Step indicator ────────────────────────────────────────
    private JPanel buildStepBar(int active) {
        JPanel bar = new JPanel(new GridBagLayout());
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(0, 0, 20, 0));

        String[] labels = {"Testata", "Corpo", "Piede"};
        GridBagConstraints gc = new GridBagConstraints();

        for (int i = 0; i < labels.length; i++) {
            // circle
            final int idx = i + 1;
            JLabel circle = new JLabel(String.valueOf(idx), SwingConstants.CENTER) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    boolean act = (idx == active);
                    g2.setColor(act ? ACCENT : BORDER_C);
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            circle.setPreferredSize(new Dimension(32, 32));
            circle.setFont(new Font("SansSerif", Font.BOLD, 13));
            circle.setForeground(i + 1 == active ? Color.WHITE : FG_DIM);

            // label
            JLabel lbl = new JLabel(labels[i], SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", i + 1 == active ? Font.BOLD : Font.PLAIN, 11));
            lbl.setForeground(i + 1 == active ? TEAL : FG_DIM);

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

    // ── Form ──────────────────────────────────────────────────
    private JPanel buildFormArea() {
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(BG);

        wrapper.add(buildSection("Cedente / Fornitore", new String[][]{
            {"Nome e Cognome", "nomeForn"},
            {"Azienda", "aziendaCed"},
            {"Partita IVA", "pIvaCed"}
        }));
        wrapper.add(Box.createVerticalStrut(14));
        wrapper.add(buildSection("Destinatario", new String[][]{
            {"Nome e Cognome", "nomeDest"},
            {"Azienda", "aziendaDest"},
            {"Partita IVA", "pIvaDest"}
        }));
        wrapper.add(Box.createVerticalStrut(14));
        wrapper.add(buildSection("Dati Fattura", new String[][]{
            {"Tipo Fattura", "tipoFattura"},
            {"Data", "dataFattura"},
            {"Numero", "numFattura"}
        }));
        wrapper.add(Box.createVerticalStrut(14));
        wrapper.add(buildSection("Pagamento", new String[][]{
            {"Tipo Pagamento", "tipoPagamento"},
            {"Banca d'Appoggio", "banca"}
        }));

        return wrapper;
    }

    private JPanel buildSection(String title, String[][] rows) {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(14, 16, 14, 16)
        ));

        // section header
        JPanel hdr = new JPanel(new BorderLayout());
        hdr.setBackground(CARD);
        hdr.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel hdrLbl = new JLabel(title.toUpperCase());
        hdrLbl.setFont(new Font("SansSerif", Font.BOLD, 10));
        hdrLbl.setForeground(TEAL);
        hdr.add(hdrLbl, BorderLayout.WEST);
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER_C);
        hdr.add(sep, BorderLayout.SOUTH);

        // fields grid
        JPanel grid = new JPanel(new GridLayout(0, 2, 16, 8));
        grid.setBackground(CARD);

        for (String[] row : rows) {
            JLabel lbl = new JLabel(row[0]);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
            lbl.setForeground(FG_DIM);
            grid.add(lbl);
            grid.add(getFieldByName(row[1]));
        }

        card.add(hdr, BorderLayout.NORTH);
        card.add(grid, BorderLayout.CENTER);
        return card;
    }

    private JTextField getFieldByName(String name) {
        switch (name) {
            case "nomeForn":
                return nomeForn;
            case "aziendaCed":
                return aziendaCed;
            case "pIvaCed":
                return pIvaCed;
            case "nomeDest":
                return nomeDest;
            case "aziendaDest":
                return aziendaDest;
            case "pIvaDest":
                return pIvaDest;
            case "tipoFattura":
                return tipoFattura;
            case "dataFattura":
                return dataFattura;
            case "numFattura":
                return numFattura;
            case "tipoPagamento":
                return tipoPagamento;
            case "banca":
                return banca;
            default:
                return new JTextField();
        }
    }

    private JPanel buildFooter() {
        JPanel fp = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        fp.setBackground(BG);
        fp.setBorder(new EmptyBorder(18, 0, 0, 0));
        fp.add(avanti);
        return fp;
    }

    // ── Factories ─────────────────────────────────────────────
    private JTextField makeField(String placeholder) {
        JTextField f = new JTextField(18);
        f.setBackground(FIELD_BG);
        f.setForeground(FG);
        f.setCaretColor(TEAL);
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        f.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_C, 1),
                new EmptyBorder(5, 8, 5, 8)
        ));
        // placeholder
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

    private JButton makeButton(String text) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isPressed() ? ACCENT.darker()
                        : getModel().isRollover() ? ACCENT.brighter() : ACCENT);
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
