package example;
//-*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
//@homepage@
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;

public final class MainPanel extends JPanel {
    private MainPanel() {
        super(new GridLayout(2, 1));

        JTextField field = new JTextField();
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new FirstCharToUpperCaseDocumentFilter(field));
        field.setText("abcdefghijklmn");

        add(makeTitlePanel(new JTextField("abcdefghijklmn"), "Default"));
        add(makeTitlePanel(field, "FirstCharToUpperCase"));
        setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        setPreferredSize(new Dimension(320, 240));
    }
    private static JComponent makeTitlePanel(JComponent cmp, String title) {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1d;
        c.fill    = GridBagConstraints.HORIZONTAL;
        c.insets  = new Insets(5, 5, 5, 5);
        p.add(cmp, c);
        p.setBorder(BorderFactory.createTitledBorder(title));
        return p;
    }
    public static void main(String... args) {
        EventQueue.invokeLater(new Runnable() {
            @Override public void run() {
                createAndShowGUI();
            }
        });
    }
    public static void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new JFrame("@title@");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MainPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class FirstCharToUpperCaseDocumentFilter extends DocumentFilter {
    protected final JTextComponent textField;
    protected FirstCharToUpperCaseDocumentFilter(JTextComponent textField) {
        super();
        this.textField = textField;
    }
    @Override public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
        Document doc = fb.getDocument();
        if (offset == 0 && doc.getLength() - length > 0) {
            fb.replace(length, 1, doc.getText(length, 1).toUpperCase(Locale.ENGLISH), null);
            textField.setCaretPosition(offset);
        }
        fb.remove(offset, length);
    }
    @Override public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String str = text;
        if (offset == 0 && Objects.nonNull(text) && !text.isEmpty()) {
            str = text.substring(0, 1).toUpperCase(Locale.ENGLISH) + text.substring(1);
        }
        fb.replace(offset, length, str, attrs);
    }
}
