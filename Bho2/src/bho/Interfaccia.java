package bho;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.JTextComponent;

public class Interfaccia extends JFrame {

    JTextField txfNomeSito, txfUser, txfEmail, txfTelefono;
    JTextField txfProvincia, txfComune, txfVia, txfCivico;
    JTextField txfNome, txfCognome, txfDataNascita;
    JPasswordField txfPassword, txfPassword2;
    JCheckBox chkMostraPassword;
    JButton salva, annulla, tornaLogin;
    private final String adminUser, adminPass;

    // Palette colori
    private static final Color BG = new Color(15, 17, 26);
    private static final Color CARD = new Color(24, 27, 42);
    private static final Color CARD2 = new Color(30, 34, 52);
    private static final Color ACCENT = new Color(99, 102, 241);   // indigo
    private static final Color ACCENT2 = new Color(139, 92, 246);   // violet
    private static final Color TEXT = new Color(226, 232, 240);
    private static final Color MUTED = new Color(100, 116, 139);
    private static final Color BORDER = new Color(51, 65, 85);
    private static final Color INPUT_BG = new Color(15, 17, 26);
    private static final Color SUCCESS = new Color(52, 211, 153);
    private static final Color DANGER = new Color(248, 113, 113);

    public Interfaccia(String adminUser, String adminPass) {
        this.adminUser = adminUser;
        this.adminPass = adminPass;

        setTitle("🔐 Vault — " + adminUser);
        setUndecorated(false);
        getContentPane().setBackground(BG);

        // Campi
        txfNomeSito = creaTextFieldConPlaceholder("es. Steam, Gmail, Amazon...");
        txfUser = creaTextFieldConPlaceholder("es. mario_rossi, m.rossi99...");
        txfPassword = creaPasswordFieldConPlaceholder("Min. 8 car., 1 numero, 1 speciale (!@_#*)");
        txfPassword2 = creaPasswordFieldConPlaceholder("Ripeti la stessa password");
        txfEmail = creaTextFieldConPlaceholder("es. mario.rossi@gmail.com");
        txfTelefono = creaTextFieldConPlaceholder("es. 3331122334");
        txfProvincia = creaTextFieldConPlaceholder("es. MI, RM, NA  (sigla 2 lettere)");
        txfComune = creaTextFieldConPlaceholder("es. Milano, Roma, Napoli...");
        txfVia = creaTextFieldConPlaceholder("es. Via Roma, Corso Garibaldi, Piazza Navona...");
        txfCivico = creaTextFieldConPlaceholder("es. 12, 4B, snc");
        txfNome = creaTextFieldConPlaceholder("es. Mario");
        txfCognome = creaTextFieldConPlaceholder("es. Rossi");
        txfDataNascita = creaTextFieldConPlaceholder("gg/mm/aaaa  es. 15/03/1995");

        chkMostraPassword = new JCheckBox("Mostra password");
        styleCheckbox(chkMostraPassword);

        salva = creaBottone("💾  Salva Credenziali", ACCENT, Color.WHITE);
        annulla = creaBottoneSecondario("✕  Annulla");
        tornaLogin = creaBottoneSecondario("← Login");

        // Layout principale con scroll
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(0, 0, 0, 0));

        root.add(creaHeader(), BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(20, 30, 10, 30));

        // Sezione 1: Credenziali sito
        content.add(creaSezione("🌐  Credenziali Sito", new Object[][]{
            {"Sito Web", txfNomeSito, null},
            {"Username", txfUser, null},
            {"Password", txfPassword, null},
            {"Conferma Pass", txfPassword2, null},
            {null, chkMostraPassword, null}
        }));
        content.add(Box.createVerticalStrut(16));

        // Sezione 2: Dati personali
        content.add(creaSezione("👤  Dati Anagrafici", new Object[][]{
            {"Nome", txfNome, null},
            {"Cognome", txfCognome, null},
            {"Email", txfEmail, null},
            {"Telefono", txfTelefono, null},
            {"Data di Nascita", txfDataNascita, null}
        }));
        content.add(Box.createVerticalStrut(16));

        // Sezione 3: Indirizzo
        content.add(creaSezione("📍  Indirizzo", new Object[][]{
            {"Via / Piazza", txfVia, null},
            {"N° Civico", txfCivico, null},
            {"Provincia", txfProvincia, null},
            {"Comune / Città", txfComune, null}
        }));
        content.add(Box.createVerticalStrut(24));

        // Pulsanti
        content.add(creaPannelloPulsanti());
        content.add(Box.createVerticalStrut(20));

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);
        setPreferredSize(new Dimension(900, 1000));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Gestore eventi
        GestoreEventi gestore = new GestoreEventi(this);
        getRootPane().setDefaultButton(salva);
        salva.addActionListener(gestore);
        annulla.addActionListener(gestore);
        tornaLogin.addActionListener(gestore);
        chkMostraPassword.addActionListener(gestore);
        this.getRootPane().setDoubleBuffered(true);
    }

    // ── Header ──────────────────────────────────────────────────────────────
    private JPanel creaHeader() {
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
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(new EmptyBorder(0, 30, 0, 30));

        JLabel title = new JLabel("🔐  Vault Manager");
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        String modeText = adminPass.isEmpty() ? "  CSV (non cifrato)" : "  Cifrato (AES-256)";
        Color modeColor = adminPass.isEmpty() ? new Color(253, 224, 71) : SUCCESS;
        JLabel mode = new JLabel("● " + adminUser + modeText);
        mode.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mode.setForeground(modeColor);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setOpaque(false);
        left.add(Box.createVerticalGlue());
        left.add(title);
        left.add(mode);
        left.add(Box.createVerticalGlue());

        header.add(left, BorderLayout.CENTER);
        return header;
    }

    // ── Sezione card ─────────────────────────────────────────────────────────
    /**
     * rows: Object[]{label, component_left, component_right_or_null} Se
     * label==null, il componente occupa tutta la riga (es. checkbox)
     */
    private JPanel creaSezione(String titolo, Object[][] rows) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(BORDER);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 12, 12));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        card.setBorder(new EmptyBorder(16, 20, 16, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 12, 0);

        // Titolo sezione
        JLabel lblTitolo = new JLabel(titolo);
        lblTitolo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitolo.setForeground(ACCENT2);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 4;
        c.weightx = 1.0;
        c.insets = new Insets(0, 0, 14, 0);
        card.add(lblTitolo, c);

        int row = 1;
        for (Object[] r : rows) {
            c.insets = new Insets(0, 0, 8, 0);
            if (r[0] == null) {
                // Riga senza label (checkbox)
                c.gridx = 0;
                c.gridy = row;
                c.gridwidth = 4;
                card.add((Component) r[1], c);
            } else if (r[2] == null) {
                // Riga con label + un campo
                c.gridx = 0;
                c.gridy = row;
                c.gridwidth = 1;
                c.weightx = 0;
                c.insets = new Insets(0, 0, 8, 12);
                JLabel lbl = creaLabel((String) r[0]);
                lbl.setPreferredSize(new Dimension(120, 20));
                card.add(lbl, c);
                c.gridx = 1;
                c.gridwidth = 3;
                c.weightx = 1.0;
                c.insets = new Insets(0, 0, 8, 0);
                card.add((Component) r[1], c);
            } else {
                // Riga con label + due campi (es. Nome/Cognome, Via/Civico)
                c.gridx = 0;
                c.gridy = row;
                c.gridwidth = 1;
                c.weightx = 0;
                c.insets = new Insets(0, 0, 8, 12);
                JLabel lbl = creaLabel((String) r[0]);
                lbl.setPreferredSize(new Dimension(120, 20));
                card.add(lbl, c);
                c.gridx = 1;
                c.gridwidth = 1;
                c.weightx = 1.0;
                c.insets = new Insets(0, 0, 8, 8);
                card.add((Component) r[1], c);
                c.gridx = 2;
                c.gridwidth = 2;
                c.weightx = 0.5;
                c.insets = new Insets(0, 0, 8, 0);
                card.add((Component) r[2], c);
            }
            row++;
        }

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    // ── Pannello pulsanti ────────────────────────────────────────────────────
    private JPanel creaPannelloPulsanti() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        p.add(tornaLogin);
        p.add(annulla);
        p.add(salva);
        return p;
    }

    private JTextField creaTextFieldConPlaceholder(String placeholder) {
        JTextField f = new JTextField(18) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(new Color(71, 85, 105));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    Insets ins = getInsets();
                    FontMetrics fm = g2.getFontMetrics();
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(placeholder, ins.left, y);
                    g2.dispose();
                }
            }
        };
        styleInputField(f);
        return f;
    }

    private JPasswordField creaPasswordFieldConPlaceholder(String placeholder) {
        JPasswordField f = new JPasswordField(18) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    g2.setColor(new Color(71, 85, 105));
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    Insets ins = getInsets();
                    FontMetrics fm = g2.getFontMetrics();
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(placeholder, ins.left, y);
                    g2.dispose();
                }
            }
        };
        f.setEchoChar('•');
        styleInputField(f);
        return f;
    }

    private void styleInputField(JTextComponent f) {
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT);
        f.setCaretColor(ACCENT2);
        f.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(BORDER, 6),
                new EmptyBorder(6, 10, 6, 10)));
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    }

    private JLabel creaLabel(String text) {
        JLabel l = new JLabel(text + ":");
        l.setForeground(MUTED);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        return l;
    }

    private JButton creaBottone(String text, Color bg, Color fg) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c = getModel().isPressed() ? bg.darker()
                        : getModel().isRollover() ? bg.brighter() : bg;
                g2.setColor(c);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setForeground(fg);
        b.setFont(new Font("Segoe UI", Font.BOLD, 13));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(180, 38));
        return b;
    }

    private JButton creaBottoneSecondario(String text) {
        JButton b = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? CARD2 : CARD);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8));
                g2.setColor(BORDER);
                g2.draw(new RoundRectangle2D.Float(0.5f, 0.5f, getWidth() - 1, getHeight() - 1, 8, 8));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        b.setForeground(MUTED);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(110, 38));
        return b;
    }

    private void styleCheckbox(JCheckBox chk) {
        chk.setForeground(MUTED);
        chk.setBackground(new Color(0, 0, 0, 0));
        chk.setOpaque(false);
        chk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chk.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public String getAdminUser() {
        return adminUser;
    }

    public String getAdminPass() {
        return adminPass;
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
