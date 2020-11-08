package de.aestis.dndreloaded.Quests.editor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.aestis.dndreloaded.Quests.Quest;

@SuppressWarnings("serial")
public class EditorMain extends JFrame {
	private JButton bLoadQuests = new JButton();
	private JComboBox<String> jComboBox1 = new JComboBox<String>();
	private DefaultComboBoxModel<String> jComboBox1Model = new DefaultComboBoxModel<String>();
	private JButton bEdit = new JButton();
	private JButton bUpdateFiles = new JButton();
	
	private List<Quest> quests;
	
	public EditorMain() { 
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		int frameWidth = 512; 
		int frameHeight = 455;
		setSize(frameWidth, frameHeight);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (d.width - getSize().width) / 2;
		int y = (d.height - getSize().height) / 2;
		setLocation(x, y);
		setTitle("QuestSelector");
		setResizable(false);
		Container cp = getContentPane();
		cp.setLayout(null);

		bLoadQuests.setBounds(16, 16, 115, 25);
		bLoadQuests.setText("Load Quests");
		bLoadQuests.setMargin(new Insets(2, 2, 2, 2));
		bLoadQuests.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bLoadQuests_ActionPerformed(evt);
			}
		});
		cp.add(bLoadQuests);
		jComboBox1.setModel(jComboBox1Model);
		jComboBox1.setBounds(152, 16, 318, 28);
		cp.add(jComboBox1);
		bEdit.setBounds(152, 56, 75, 25);
		bEdit.setText("Edit");
		bEdit.setMargin(new Insets(2, 2, 2, 2));
		bEdit.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bEdit_ActionPerformed(evt);
			}
		});
		cp.add(bEdit);
		bUpdateFiles.setBounds(16, 112, 115, 25);
		bUpdateFiles.setText("Update Files");
		bUpdateFiles.setMargin(new Insets(2, 2, 2, 2));
		bUpdateFiles.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bUpdateFiles_ActionPerformed(evt);
			}
		});
		cp.add(bUpdateFiles);

		setVisible(true);
	}

	public static void main(String[] args) {
		new EditorMain();
	}

	private void updateDropDown() {
		for (Quest q:quests) {
			if (q == null) {
				continue;
			}
			jComboBox1.addItem(q.getTitle());
		}
	}
	
	public void bLoadQuests_ActionPerformed(ActionEvent evt) {
		jComboBox1.removeAll();
		
		quests = QuestParser.loadQuests();
		
		updateDropDown();
	}

	public void bEdit_ActionPerformed(ActionEvent evt) {
		new QuestEditor(quests.get(jComboBox1.getSelectedIndex()));
	}

	public void bUpdateFiles_ActionPerformed(ActionEvent evt) {		
		QuestParser.clearQuestFolder();
		for (Quest q:quests) {
			if (q == null) {
				continue;
			}
			QuestParser.storeQuest(q);
		}
	}
}
