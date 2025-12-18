import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMIApp {

    private JFrame frame;
    private JPanel mainPanel;
    private JPanel welcomePanel;
    private JPanel calculatorPanel;
    private CardLayout cardLayout;

    private Font farsiFontBold = new Font("Tahoma", Font.BOLD, 14);
    private Font farsiFontPlain = new Font("Tahoma", Font.PLAIN, 14);
    private Font titleFont = new Font("Tahoma", Font.BOLD, 28);

    private Color currentBackgroundColor = new Color(240, 240, 240);
    private Color farsiTextColor = Color.BLACK;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BMIApp().initialize();
        });
    }

    public void initialize() {
        frame = new JFrame("برنامه BMI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 650);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        welcomePanel = WelcomePanel();
        calculatorPanel = CalculatorPanel();

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(calculatorPanel, "Calculator");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel WelcomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(currentBackgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel titleLabel = new JLabel("برنامه BMI");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(farsiTextColor);

        JLabel descLabel = new JLabel("به ماشین حساب BMI خوش آمدید");
        descLabel.setFont(farsiFontPlain);
        descLabel.setForeground(farsiTextColor);

        JButton startButton = new JButton("شروع");
        startButton.setFont(farsiFontBold);
        startButton.setPreferredSize(new Dimension(120, 40));
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "Calculator"));

        panel.add(titleLabel, gbc);
        panel.add(descLabel, gbc);
        gbc.insets = new Insets(40, 0, 10, 0);
        panel.add(startButton, gbc);

        return panel;
    }

    private JPanel CalculatorPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(currentBackgroundColor);
        panel.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JLabel genderLabel = new JLabel("جنسیت:");
        genderLabel.setFont(farsiFontPlain);
        genderLabel.setForeground(farsiTextColor);
        genderLabel.setBounds(40, 20, 300, 20);
        panel.add(genderLabel);

        JRadioButton maleButton = new JRadioButton("مرد");
        maleButton.setFont(farsiFontPlain);
        maleButton.setForeground(farsiTextColor);
        maleButton.setOpaque(false);
        maleButton.setBounds(200, 50, 60, 30);

        JRadioButton femaleButton = new JRadioButton("زن");
        femaleButton.setFont(farsiFontPlain);
        femaleButton.setForeground(farsiTextColor);
        femaleButton.setOpaque(false);
        femaleButton.setBounds(270, 50, 60, 30);

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        panel.add(maleButton);
        panel.add(femaleButton);

        JLabel heightLabel = new JLabel("قد (سانتی‌متر):");
        heightLabel.setFont(farsiFontPlain);
        heightLabel.setForeground(farsiTextColor);
        heightLabel.setBounds(40, 90, 300, 20);
        panel.add(heightLabel);

        JTextField heightField = new JTextField();
        heightField.setFont(farsiFontPlain);
        heightField.setBounds(40, 120, 350, 35);
        panel.add(heightField);

        JLabel weightLabel = new JLabel("وزن (کیلوگرم):");
        weightLabel.setFont(farsiFontPlain);
        weightLabel.setForeground(farsiTextColor);
        weightLabel.setBounds(40, 170, 300, 20);
        panel.add(weightLabel);

        JTextField weightField = new JTextField();
        weightField.setFont(farsiFontPlain);
        weightField.setBounds(40, 200, 350, 35);
        panel.add(weightField);

        JButton calculateButton = new JButton("محاسبه BMI");
        calculateButton.setFont(farsiFontBold);
        calculateButton.setBounds(40, 260, 350, 40);
        panel.add(calculateButton);

        JLabel resultLabel = new JLabel("");
        resultLabel.setFont(farsiFontBold);
        resultLabel.setForeground(farsiTextColor);
        resultLabel.setBounds(20, 320, 390, 30);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(resultLabel);

        JLabel colorTitle = new JLabel("انتخاب رنگ پس‌زمینه:");
        colorTitle.setFont(farsiFontPlain);
        colorTitle.setForeground(farsiTextColor);
        colorTitle.setBounds(40, 380, 300, 20);
        panel.add(colorTitle);

        JPanel colorPalette = new JPanel(new GridLayout(2, 5, 5, 5));
        colorPalette.setBounds(40, 410, 350, 80);
        colorPalette.setOpaque(false);

        Color[] colors = {
                Color.WHITE, new Color(240, 240, 240), Color.LIGHT_GRAY,
                new Color(255, 200, 200), new Color(200, 255, 200),
                new Color(200, 200, 255), new Color(255, 255, 200),
                new Color(220, 190, 255), new Color(255, 220, 150), Color.CYAN
        };

        for (Color c : colors) {
            JButton btn = new JButton();
            btn.setBackground(c);
            btn.addActionListener(e -> {
                currentBackgroundColor = c;
                welcomePanel.setBackground(c);
                calculatorPanel.setBackground(c);
                maleButton.setBackground(c);
                femaleButton.setBackground(c);
            });
            colorPalette.add(btn);
        }
        panel.add(colorPalette);

        calculateButton.addActionListener(e -> {
            try {
                if (heightField.getText().isEmpty() || weightField.getText().isEmpty() ||
                        (!maleButton.isSelected() && !femaleButton.isSelected())) {
                    resultLabel.setText("لطفا تمام فیلدها را پر کنید.");
                    return;
                }
                double h = Double.parseDouble(heightField.getText()) / 100;
                double w = Double.parseDouble(weightField.getText());
                double bmi = w / (h * h);
                String status = (bmi < 18.5) ? "کمبود وزن" : (bmi < 25) ? "نرمال" : (bmi < 30) ? "اضافه وزن" : "چاقی";
                resultLabel.setText(String.format("BMI شما: %.2f (%s)", bmi, status));
            } catch (Exception ex) {
                resultLabel.setText("خطا در ورودی!");
            }
        });

        return panel;
    }
}