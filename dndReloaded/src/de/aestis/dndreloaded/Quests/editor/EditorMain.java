package de.aestis.dndreloaded.Quests.editor;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.aestis.dndreloaded.Quests.Quest;

@SuppressWarnings("serial")
public class EditorMain extends JFrame {
	private JButton bLoadQuests = new JButton();
	private JComboBox<String> selectQuest = new JComboBox<String>();
	private DefaultComboBoxModel<String> selectQuestModel = new DefaultComboBoxModel<String>();
	private JButton bEdit = new JButton();
	private JButton bUpdateFiles = new JButton();
	private JButton bCreateQuest = new JButton();

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

		bLoadQuests.setBounds(16, 16, 115, 28);
		bLoadQuests.setText("Load Quests");
		bLoadQuests.setMargin(new Insets(2, 2, 2, 2));
		bLoadQuests.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bLoadQuests_ActionPerformed(evt);
			}
		});
		cp.add(bLoadQuests);
		selectQuest.setModel(selectQuestModel);
		selectQuest.setBounds(152, 16, 318, 28);
		cp.add(selectQuest);
		bEdit.setBounds(152, 56, 75, 28);
		bEdit.setText("Edit");
		bEdit.setMargin(new Insets(2, 2, 2, 2));
		bEdit.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bEdit_ActionPerformed(evt);
			}
		});
		cp.add(bEdit);
		bUpdateFiles.setBounds(16, 112, 115, 28);
		bUpdateFiles.setText("Update Files");
		bUpdateFiles.setMargin(new Insets(2, 2, 2, 2));
		bUpdateFiles.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bUpdateFiles_ActionPerformed(evt);
			}
		});
		cp.add(bUpdateFiles);
		bCreateQuest.setBounds(152, 112, 318, 28);
		bCreateQuest.setText("Create Quest");
		bCreateQuest.setMargin(new Insets(2, 2, 2, 2));
		bCreateQuest.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent evt) { 
				bCreateQuest_ActionPerformed(evt);
			}
		});
		cp.add(bCreateQuest);

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
			selectQuest.addItem(q.getTitle());
		}
	}

	public void bLoadQuests_ActionPerformed(ActionEvent evt) {
		selectQuest.removeAll();

		quests = QuestParser.loadQuests();

		updateDropDown();
	}

	public void bEdit_ActionPerformed(ActionEvent evt) {
		new QuestEditor(quests.get(selectQuest.getSelectedIndex()));
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

	public void bCreateQuest_ActionPerformed(ActionEvent evt) {
		if (quests == null) {
			selectQuest.removeAll();

			quests = QuestParser.loadQuests();
		}
		
		Quest q = new Quest(-1);
		q.setTitle("New Quest");
		q.setCreated(Calendar.getInstance().getTime());
		quests.add(q);
		
		updateDropDown();
	}
}
