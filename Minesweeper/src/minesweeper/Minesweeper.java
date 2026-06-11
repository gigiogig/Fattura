package minesweeper;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Minesweeper extends JFrame {

    // ── palette: dark terminal / industrial ───────────────────────────────────
    private static final Color BG                = new Color(0x1A1A1A);
    private static final Color HEADER_BG         = new Color(0x111111);
    private static final Color CELL_HIDDEN       = new Color(0x2E2E2E);
    private static final Color CELL_HOVER        = new Color(0x3A3A3A);
    private static final Color CELL_OPEN         = new Color(0x1A1A1A);
    private static final Color CELL_MINE_HIT     = new Color(0x3D0000);
    private static final Color CELL_BORDER_LIGHT = new Color(0x4A4A4A);
    private static final Color CELL_BORDER_DARK  = new Color(0x0F0F0F);
    private static final Color ACCENT            = new Color(0xFF6B35);
    private static final Color FLAG_COLOR        = new Color(0xFF6B35);
    private static final Color MINE_COLOR        = new Color(0xFF3333);
    private static final Color TEXT_DIM          = new Color(0x888888);
    private static final Color TEXT_BRIGHT       = new Color(0xEEEEEE);
    private static final Color WIN_COLOR         = new Color(0x81C784);

    private static final Color[] NUM_COLORS = {
        null,
        new Color(0x4FC3F7), // 1
        new Color(0x81C784), // 2
        new Color(0xEF5350), // 3
        new Color(0x7986CB), // 4
        new Color(0xFF8A65), // 5
        new Color(0x4DD0E1), // 6
        new Color(0xF06292), // 7
        new Color(0xBDBDBD), // 8
    };

    private static final Font FONT_CELL    = new Font("Courier New", Font.BOLD, 15);
    private static final Font FONT_UI      = new Font("Courier New", Font.BOLD, 13);
    private static final Font FONT_TITLE   = new Font("Courier New", Font.BOLD, 20);
    private static final Font FONT_COUNTER = new Font("Courier New", Font.BOLD, 22);

    // ── difficulty ────────────────────────────────────────────────────────────
    private static final int[][] PRESETS = {
        // rows, cols, mines
        { 9,  9,  10 },  // 0 = Easy
        { 16, 16, 40 },  // 1 = Medium
        { 16, 30, 99 },  // 2 = Hard
    };
    private static final String[] PRESET_NAMES = { "EASY", "MEDIUM", "HARD" };

    // ── state ─────────────────────────────────────────────────────────────────
    private int rows, cols, totalMines;
    private boolean[][] mines, revealed, flagged;
    private int[][] adjacent;
    private boolean gameOver, gameWon, firstClick;
    private int flagsLeft, seconds;
    private int currentPresetIdx = 1;

    private CellButton[][] cells;
    private JPanel gridPanel;
    private JLabel mineCounter, timerLabel, statusLabel;
    private javax.swing.Timer swingTimer;

    // ── entry point ───────────────────────────────────────────────────────────
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Minesweeper().setVisible(true));
    }

    public Minesweeper() {
        super("Minesweeper");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH);
        newGame(currentPresetIdx);
        pack();
        setLocationRelativeTo(null);
    }

    // ── header ────────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(HEADER_BG);

        // title
        JLabel title = new JLabel("// MINESWEEPER");
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT);
        title.setBorder(new EmptyBorder(14, 18, 14, 18));

        // stats row
        JPanel stats = new JPanel(new BorderLayout());
        stats.setBackground(new Color(0x181818));
        stats.setBorder(new CompoundBorder(
            new MatteBorder(1, 0, 1, 0, new Color(0x333333)),
            new EmptyBorder(10, 18, 10, 18)
        ));

        mineCounter = new JLabel("010");
        mineCounter.setFont(FONT_COUNTER);
        mineCounter.setForeground(MINE_COLOR);

        statusLabel = new JLabel("[ \u25C9 ]", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Courier New", Font.BOLD, 16));
        statusLabel.setForeground(TEXT_BRIGHT);
        statusLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        statusLabel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) { newGame(currentPresetIdx); }
            public void mouseEntered(MouseEvent e) { statusLabel.setForeground(ACCENT); }
            public void mouseExited(MouseEvent e)  { refreshStatusColor(); }
        });

        timerLabel = new JLabel("000");
        timerLabel.setFont(FONT_COUNTER);
        timerLabel.setForeground(new Color(0x4FC3F7));
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        stats.add(mineCounter, BorderLayout.WEST);
        stats.add(statusLabel, BorderLayout.CENTER);
        stats.add(timerLabel,  BorderLayout.EAST);

        // difficulty buttons
        JPanel diffRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        diffRow.setBackground(HEADER_BG);
        for (int i = 0; i < PRESET_NAMES.length; i++) {
            final int idx = i;
            JButton b = makeButton(PRESET_NAMES[i]);
            b.addActionListener(e -> { currentPresetIdx = idx; newGame(idx); });
            diffRow.add(b);
        }

        outer.add(title,   BorderLayout.NORTH);
        outer.add(stats,   BorderLayout.CENTER);
        outer.add(diffRow, BorderLayout.SOUTH);
        return outer;
    }

    private JButton makeButton(String text) {
        JButton b = new JButton(text);
        b.setFont(FONT_UI);
        b.setBackground(new Color(0x2A2A2A));
        b.setForeground(TEXT_DIM);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Border normal = new CompoundBorder(
            new LineBorder(new Color(0x3A3A3A), 1),
            new EmptyBorder(4, 14, 4, 14));
        Border hot = new CompoundBorder(
            new LineBorder(ACCENT, 1),
            new EmptyBorder(4, 14, 4, 14));
        b.setBorder(normal);
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setForeground(ACCENT);   b.setBorder(hot);    }
            public void mouseExited(MouseEvent e)  { b.setForeground(TEXT_DIM); b.setBorder(normal); }
        });
        return b;
    }

    private void refreshStatusColor() {
        if (gameOver)     statusLabel.setForeground(MINE_COLOR);
        else if (gameWon) statusLabel.setForeground(WIN_COLOR);
        else              statusLabel.setForeground(TEXT_BRIGHT);
    }

    // ── game ──────────────────────────────────────────────────────────────────
    private void newGame(int presetIdx) {
        if (swingTimer != null) swingTimer.stop();
        rows       = PRESETS[presetIdx][0];
        cols       = PRESETS[presetIdx][1];
        totalMines = PRESETS[presetIdx][2];

        mines    = new boolean[rows][cols];
        revealed = new boolean[rows][cols];
        flagged  = new boolean[rows][cols];
        adjacent = new int[rows][cols];
        gameOver = false; gameWon = false; firstClick = true;
        flagsLeft = totalMines; seconds = 0;

        mineCounter.setText(String.format("%03d", flagsLeft));
        timerLabel.setText("000");
        statusLabel.setText("[ \u25C9 ]");
        statusLabel.setForeground(TEXT_BRIGHT);

        // rebuild grid
        if (gridPanel != null) remove(gridPanel);
        int cellSize = (presetIdx == 2) ? 28 : 34;
        gridPanel = new JPanel(new GridLayout(rows, cols, 2, 2));
        gridPanel.setBackground(BG);
        gridPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        cells = new CellButton[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                cells[r][c] = new CellButton(r, c, cellSize);
                gridPanel.add(cells[r][c]);
            }
        add(gridPanel, BorderLayout.CENTER);
        pack();
        repaint();
    }

    private void placeMines(int safeR, int safeC) {
        Random rnd = new Random();
        int placed = 0;
        while (placed < totalMines) {
            int r = rnd.nextInt(rows), c = rnd.nextInt(cols);
            if (!mines[r][c] && !(Math.abs(r-safeR)<=1 && Math.abs(c-safeC)<=1)) {
                mines[r][c] = true; placed++;
            }
        }
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++) {
                if (mines[r][c]) continue;
                int cnt = 0;
                for (int dr = -1; dr <= 1; dr++)
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r+dr, nc = c+dc;
                        if (nr>=0&&nr<rows&&nc>=0&&nc<cols&&mines[nr][nc]) cnt++;
                    }
                adjacent[r][c] = cnt;
            }
    }

    private void reveal(int r, int c) {
        if (r<0||r>=rows||c<0||c>=cols||revealed[r][c]||flagged[r][c]) return;
        revealed[r][c] = true;
        cells[r][c].repaint();
        if (!mines[r][c] && adjacent[r][c] == 0)
            for (int dr = -1; dr <= 1; dr++)
                for (int dc = -1; dc <= 1; dc++)
                    reveal(r+dr, c+dc);
    }

    void handleClick(int r, int c) {
        if (gameOver || gameWon || flagged[r][c] || revealed[r][c]) return;
        if (firstClick) {
            firstClick = false;
            placeMines(r, c);
            startTimer();
        }
        if (mines[r][c]) { triggerGameOver(r, c); return; }
        reveal(r, c);
        checkWin();
    }

    void handleFlag(int r, int c) {
        if (gameOver || gameWon || revealed[r][c]) return;
        flagged[r][c] = !flagged[r][c];
        flagsLeft += flagged[r][c] ? -1 : 1;
        mineCounter.setText(String.format("%03d", Math.max(0, flagsLeft)));
        cells[r][c].repaint();
    }

    void handleChord(int r, int c) {
        if (!revealed[r][c] || adjacent[r][c] == 0) return;
        int flags = 0;
        for (int dr=-1;dr<=1;dr++)
            for (int dc=-1;dc<=1;dc++) {
                int nr=r+dr,nc=c+dc;
                if (nr>=0&&nr<rows&&nc>=0&&nc<cols&&flagged[nr][nc]) flags++;
            }
        if (flags == adjacent[r][c])
            for (int dr=-1;dr<=1;dr++)
                for (int dc=-1;dc<=1;dc++) {
                    int nr=r+dr,nc=c+dc;
                    if (nr>=0&&nr<rows&&nc>=0&&nc<cols&&!flagged[nr][nc]&&!revealed[nr][nc])
                        handleClick(nr,nc);
                }
    }

    private void triggerGameOver(int hitR, int hitC) {
        gameOver = true;
        if (swingTimer != null) swingTimer.stop();
        cells[hitR][hitC].hitMine = true;
        for (int r=0;r<rows;r++)
            for (int c=0;c<cols;c++)
                if (mines[r][c]) { revealed[r][c]=true; cells[r][c].repaint(); }
        statusLabel.setText("[ \u2715 ]");
        statusLabel.setForeground(MINE_COLOR);
    }

    private void checkWin() {
        for (int r=0;r<rows;r++)
            for (int c=0;c<cols;c++)
                if (!revealed[r][c] && !mines[r][c]) return;
        gameWon = true;
        if (swingTimer != null) swingTimer.stop();
        statusLabel.setText("[ \u2605 ]");
        statusLabel.setForeground(WIN_COLOR);
        mineCounter.setText("000");
    }

    private void startTimer() {
        swingTimer = new javax.swing.Timer(1000, e -> {
            if (seconds < 999) seconds++;
            timerLabel.setText(String.format("%03d", seconds));
        });
        swingTimer.start();
    }

    // ── cell ──────────────────────────────────────────────────────────────────
    class CellButton extends JPanel {
        final int row, col;
        boolean hover = false;
        boolean hitMine = false;
        private boolean leftDown = false, rightDown = false;

        CellButton(int r, int c, int size) {
            this.row = r; this.col = c;
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hover = true;  repaint(); }
                public void mouseExited(MouseEvent e)  { hover = false; repaint(); }
                public void mousePressed(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e))  leftDown  = true;
                    if (SwingUtilities.isRightMouseButton(e)) rightDown = true;
                    if (leftDown && rightDown) { handleChord(row,col); return; }
                    if (SwingUtilities.isLeftMouseButton(e))  handleClick(row,col);
                    if (SwingUtilities.isRightMouseButton(e)) handleFlag(row,col);
                }
                public void mouseReleased(MouseEvent e) {
                    if (SwingUtilities.isLeftMouseButton(e))  leftDown  = false;
                    if (SwingUtilities.isRightMouseButton(e)) rightDown = false;
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            int w = getWidth(), h = getHeight();

            if (revealed[row][col]) {
                g2.setColor(hitMine ? CELL_MINE_HIT : CELL_OPEN);
                g2.fillRect(0,0,w,h);
                g2.setColor(new Color(0x2A2A2A));
                g2.drawRect(0,0,w-1,h-1);

                if (mines[row][col]) {
                    drawMine(g2, w, h);
                } else if (adjacent[row][col] > 0) {
                    g2.setFont(FONT_CELL);
                    g2.setColor(NUM_COLORS[adjacent[row][col]]);
                    String s = String.valueOf(adjacent[row][col]);
                    FontMetrics fm = g2.getFontMetrics();
                    g2.drawString(s, (w-fm.stringWidth(s))/2, (h-fm.getHeight())/2+fm.getAscent());
                }
            } else {
                // hidden — 3-D bevel
                Color base = flagged[row][col] ? new Color(0x2A1A0A)
                           : hover             ? CELL_HOVER
                           :                    CELL_HIDDEN;
                g2.setColor(base);
                g2.fillRect(0,0,w,h);
                g2.setColor(CELL_BORDER_LIGHT);
                g2.drawLine(0,0,w-2,0);
                g2.drawLine(0,0,0,h-2);
                g2.setColor(CELL_BORDER_DARK);
                g2.drawLine(w-1,0,w-1,h-1);
                g2.drawLine(0,h-1,w-1,h-1);

                if (flagged[row][col]) drawFlag(g2, w, h);
            }
        }

        private void drawMine(Graphics2D g2, int w, int h) {
            int cx = w/2, cy = h/2;
            int r  = Math.min(w,h)/2 - 5;
            g2.setColor(hitMine ? new Color(0xFF6666) : new Color(0xCC3333));
            g2.fillOval(cx-r, cy-r, r*2, r*2);
            g2.setStroke(new BasicStroke(2f));
            for (int i = 0; i < 8; i++) {
                double a = Math.PI*2*i/8;
                int x1=(int)(cx+Math.cos(a)*(r+1)), y1=(int)(cy+Math.sin(a)*(r+1));
                int x2=(int)(cx+Math.cos(a)*(r+4)), y2=(int)(cy+Math.sin(a)*(r+4));
                g2.drawLine(x1,y1,x2,y2);
            }
            g2.setColor(new Color(0xFF9999));
            g2.fillOval(cx-r/3, cy-r/3, r/2, r/2);
        }

        private void drawFlag(Graphics2D g2, int w, int h) {
            int px = w/2, py1 = h/4, py2 = h*3/4;
            g2.setColor(FLAG_COLOR);
            g2.setStroke(new BasicStroke(2f));
            g2.drawLine(px, py1, px, py2);
            int[] fx = { px, px+8, px };
            int[] fy = { py1, py1+5, py1+10 };
            g2.fillPolygon(fx, fy, 3);
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawLine(px-4, py2, px+4, py2);
        }
    }
}