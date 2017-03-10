package bytenote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class JavaFXNotFoundFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void open() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaFXNotFoundFrame frame = new JavaFXNotFoundFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JavaFXNotFoundFrame() {
		setType(Type.POPUP);
		setTitle("Error");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize( new Dimension(450, 300 ) );
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel lblError = new JLabel("Error");
		lblError.setForeground(Color.BLACK);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 32));
		GridBagConstraints gbc_lblError = new GridBagConstraints();
		gbc_lblError.gridwidth = 7;
		gbc_lblError.insets = new Insets(0, 0, 5, 0);
		gbc_lblError.gridx = 0;
		gbc_lblError.gridy = 0;
		contentPane.add(lblError, gbc_lblError);
		
		JTextArea txtrError = new JTextArea();
		txtrError.setEditable(false);
		txtrError.setLineWrap(true);
		txtrError.setText("You don't seem to hava a compatable Java JRE.\r\nYou JRE must include JavaFX for this application to function correctly.");
		GridBagConstraints gbc_txtrError = new GridBagConstraints();
		gbc_txtrError.gridwidth = 7;
		gbc_txtrError.fill = GridBagConstraints.BOTH;
		gbc_txtrError.gridx = 0;
		gbc_txtrError.gridy = 2;
		contentPane.add(txtrError, gbc_txtrError);
		
		
	}

}
