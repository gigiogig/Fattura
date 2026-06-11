package bho;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;

public class InterfacciaLogin extends JFrame implements ActionListener {

    private JTextField txfAdminUser;
    private JPasswordField txfMasterKey;
    private JButton btnAccedi, btnDecripta;

    // Palette (stessa di Interfaccia)
    private static final Color BG = new Color(15, 17, 26);
    private static final Color CARD = new Color(24, 27, 42);
    private static final Color ACCENT = new Color(99, 102, 241);
    private static final Color ACCENT2 = new Color(139, 92, 246);
    private static final Color TEXT = new Color(226, 232, 240);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER = new Color(51, 65, 85);
    private static final Color INPUT_BG = new Color(15, 17, 26);
    private static final Color WARN = new Color(251, 191, 36);

    public InterfacciaLogin() {
        setTitle("Vault — Accesso Sicuro");
        getContentPane().setBackground(BG);

        // ── Header con gradiente ──────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, ACCENT, getWidth(), 0, ACCENT2);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        header.setPreferredSize(new Dimension(0, 80));
        header.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel ico = new JLabel("🔐");
        ico.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        JLabel title = new JLabel("Vault Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("Gestore password sicuro");
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(199, 210, 254));

        JPanel textStack = new JPanel();
        textStack.setLayout(new BoxLayout(textStack, BoxLayout.Y_AXIS));
        textStack.setOpaque(false);
        textStack.add(Box.createVerticalGlue());
        textStack.add(title);
        textStack.add(sub);
        textStack.add(Box.createVerticalGlue());

        JPanel hLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        hLeft.setOpaque(false);
        hLeft.add(ico);
        hLeft.add(textStack);
        header.add(hLeft, BorderLayout.CENTER);

        // ── Body ──────────────────────────────────────────────────────────
        JPanel body = new JPanel();
        body.setBackground(BG);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBorder(new EmptyBorder(24, 30, 24, 30));

        // Info badge
        body.add(creaBadgeInfo());
        body.add(Box.createVerticalStrut(20));

        // Card form
        body.add(creaFormCard());
        body.add(Box.createVerticalStrut(12));

        // Link decripta
        btnDecripta = creaLinkButton("🔓 Accedi all'utility di decifratura database");
        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setOpaque(false);
        linkPanel.add(btnDecripta);
        body.add(linkPanel);

        // ── Assembla ──────────────────────────────────────────────────────
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);
        setPreferredSize(new Dimension(440, 480));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(btnAccedi);
        btnAccedi.addActionListener(this);
        btnDecripta.addActionListener(this);
        this.getRootPane().setDoubleBuffered(true);
    }

    private JPanel creaBadgeInfo() {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(BORDER);
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 10, 10));
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(12, 14, 12, 14));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel l1 = new JLabel("<html><b style='color:#FCD34D'>☰ Modalità CSV</b>  —  Solo username, salva in chiaro (apribile con Excel)</html>");
        JLabel l2 = new JLabel("<html><b style='color:#6EE7B7'>🔒 Modalità Cifrata</b>  —  Username + password (16 char min)</html>");
        l1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l1.setForeground(TEXT);
        l2.setForeground(TEXT);
        p.add(l1);
        p.add(l2);
        return p;
    }

    private JPanel creaFormCard() {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(BORDER);
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(20, 24, 20, 24));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));

        txfAdminUser = new JTextField(18);
        styleInput(txfAdminUser);
        txfMasterKey = new JPasswordField(18);
        txfMasterKey.setEchoChar('•');
        styleInput(txfMasterKey);

        btnAccedi = new JButton("Accedi / Crea  →") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed() ? ACCENT.darker()
                        : getModel().isRollover() ? ACCENT.brighter() : ACCENT;
                g2.setColor(c);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnAccedi.setForeground(Color.WHITE);
        btnAccedi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAccedi.setBorderPainted(false);
        btnAccedi.setContentAreaFilled(false);
        btnAccedi.setOpaque(false);
        btnAccedi.setFocusPainted(false);
        btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 12, 0);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 12, 12);
        card.add(creaLabel("Username"), c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 12, 0);
        card.add(txfAdminUser, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.insets = new Insets(0, 0, 16, 12);
        card.add(creaLabel("Password"), c);

        c.gridx = 1;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 16, 0);
        card.add(txfMasterKey, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 0, 0);
        btnAccedi.setPreferredSize(new Dimension(0, 42));
        card.add(btnAccedi, c);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private JButton creaLinkButton(String text) {
        JButton b = new JButton(text);
        b.setForeground(new Color(148, 163, 184));
        b.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void styleInput(JTextComponent f) {
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT2);
        f.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(BORDER, 6),
                new EmptyBorder(8, 12, 8, 12)));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private JLabel creaLabel(String text) {
        JLabel l = new JLabel(text + ":");
        l.setForeground(MUTED);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        l.setPreferredSize(new Dimension(130, 20));
        return l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDecripta) {
            NavigationManager.navigateTo(new FinestraDecriptazione());
            return;
        }

        // btnAccedi
        String admin = txfAdminUser.getText().trim();
        String password = new String(txfMasterKey.getPassword());

        if (admin.isEmpty()) {
            mostraErrore("Devi inserire almeno uno Username per procedere.");
            return;
        }

        File dirCsv = new File("file generati/csv");
        File dirDat = new File("file generati/dat");
        dirCsv.mkdirs();
        dirDat.mkdirs();

        if (password.isEmpty()) {
            File fileCifrato = new File(dirDat, admin + ".dat");
            if (fileCifrato.exists()) {
                mostraErrore("Esiste già un database cifrato per questo utente.\nUsa un altro username per la modalità CSV.");
                return;
            }
            File fileCsv = new File(dirCsv, admin + ".csv");
            if (!fileCsv.exists()) {
                int conf = JOptionPane.showConfirmDialog(this,
                        "Nessun database CSV trovato per '" + admin + "'.\nVuoi creare un nuovo archivio?",
                        "Nuovo Archivio", JOptionPane.YES_NO_OPTION);
                if (conf != JOptionPane.YES_OPTION) {
                    return;
                }
            }
        } else {
            File fileCsv = new File(dirCsv, admin + ".csv");
            if (fileCsv.exists()) {
                mostraErrore("Esiste già un database CSV per questo utente.\nUsa un altro username per la modalità cifrata.");
                return;
            }
            File fileCifrato = new File(dirDat, admin + ".dat");
            if (!fileCifrato.exists()) {
                int conf = JOptionPane.showConfirmDialog(this,
                        "Nessun database cifrato trovato per '" + admin + "'.\nVuoi creare un nuovo archivio protetto?",
                        "Nuovo Archivio", JOptionPane.YES_NO_OPTION);
                if (conf != JOptionPane.YES_OPTION) {
                    return;
                }
            }
        }

        NavigationManager.navigateTo(new Interfaccia(admin, password));

    }

    private void mostraErrore(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Errore", JOptionPane.ERROR_MESSAGE);
    }

    // ── Border arrotondato ───────────────────────────────────────────────────
    private static class RoundBorder extends AbstractBorder {

        private final Color color;
        private final int radius;

        RoundBorder(Color color, int radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Float(x + 0.5f, y + 0.5f, w - 1, h - 1, radius, radius));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }
    }
}
