package com.tspgame.desktop;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.tspgame.DBHookUp;

public class Primary implements ActionListener{
	private JFrame frame;
	private JPanel main, scores, credits, story, tutorial, buttons, mainButtons;
	private JButton high, cred, stor, start, tutor, returns1,returns2, returns3,returns4;
	private JLabel theme, instruct, people, majors, top, scoresTop;
	
	Primary(){
		//Initial declarations, adding of listeners, and database linking.
		//Database link
		DBHookUp db = new DBHookUp();
		
		//Frame stuff
		frame = new JFrame("4 Byte Warrior");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.RED);
		frame.setResizable(false);
		//Panels
		main = new JPanel();
		scores = new JPanel();
		credits = new JPanel();
		story = new JPanel();
		tutorial = new JPanel();
		main.setLayout(new BoxLayout(main,BoxLayout.Y_AXIS));
		main.setBackground(Color.RED);
		main.setOpaque(true);
		scores.setLayout(new BorderLayout());
		scores.setBackground(Color.RED);
		scores.setOpaque(true);
		credits.setLayout(new BorderLayout());
		credits.setBackground(Color.RED);
		credits.setOpaque(true);
		//story.setLayout(new BoxLayout(story, BoxLayout.PAGE_AXIS));
		story.setLayout(new BorderLayout());
		story.setBackground(Color.RED);
		story.setOpaque(true);
		//tutorial.setLayout(new BoxLayout(tutorial, BoxLayout.PAGE_AXIS));
		tutorial.setLayout(new BorderLayout());
		tutorial.setBackground(Color.RED);
		tutorial.setOpaque(true);
		
		//Buttons
		start = new JButton("Go");
		start.addActionListener(this);
		start.setPreferredSize(new Dimension(125,35));
		start.setAlignmentX(start.CENTER_ALIGNMENT);
		start.setBackground(Color.BLACK);
		start.setForeground(Color.WHITE);
		start.setHorizontalAlignment(SwingConstants.CENTER);
		cred = new JButton("Credits");
		cred.setPreferredSize(new Dimension(125,35));
		cred.addActionListener(this);
		cred.setAlignmentX(cred.CENTER_ALIGNMENT);
		cred.setBackground(Color.BLACK);
		cred.setForeground(Color.WHITE);
		cred.setHorizontalAlignment(SwingConstants.CENTER);
		high = new JButton("High Scores");
		high.addActionListener(this);
		high.setPreferredSize(new Dimension(125, 35));
		high.setAlignmentX(high.CENTER_ALIGNMENT);
		high.setBackground(Color.BLACK);
		high.setForeground(Color.WHITE);
		high.setHorizontalAlignment(SwingConstants.CENTER);
		stor = new JButton("Next");
		stor.addActionListener(this);
		stor.setPreferredSize(new Dimension(100,35));
		stor.setAlignmentX(stor.CENTER_ALIGNMENT);
		stor.setBackground(Color.BLACK);
		stor.setForeground(Color.WHITE);
		stor.setHorizontalAlignment(SwingConstants.CENTER);
		tutor = new JButton("Play");
		tutor.addActionListener(this);
		tutor.setPreferredSize(new Dimension(100,35));
		tutor.setAlignmentX(tutor.CENTER_ALIGNMENT);
		tutor.setBackground(Color.BLACK);
		tutor.setForeground(Color.WHITE);
		returns1 = new JButton("Return");
		returns1.addActionListener(this);
		returns1.setPreferredSize(new Dimension(100,35));
		returns1.setAlignmentX(returns1.CENTER_ALIGNMENT);
		returns1.setBackground(Color.BLACK);
		returns1.setForeground(Color.WHITE);
		returns1.setHorizontalAlignment(SwingConstants.CENTER);
		returns2 = new JButton("Return");
		returns2.addActionListener(this);
		returns2.setPreferredSize(new Dimension(100,35));
		returns2.setAlignmentX(returns2.CENTER_ALIGNMENT);
		returns2.setBackground(Color.BLACK);
		returns2.setForeground(Color.WHITE);
		returns2.setHorizontalAlignment(SwingConstants.CENTER);
		returns3 = new JButton("Return");
		returns3.addActionListener(this);
		returns3.setPreferredSize(new Dimension(100,35));
		returns3.setAlignmentX(returns3.CENTER_ALIGNMENT);
		returns3.setBackground(Color.BLACK);
		returns3.setForeground(Color.WHITE);
		returns3.setHorizontalAlignment(SwingConstants.CENTER);
		returns4 = new JButton("Return");
		returns4.addActionListener(this);
		returns4.setPreferredSize(new Dimension(100,35));
		returns4.setAlignmentX(returns4.CENTER_ALIGNMENT);
		returns4.setBackground(Color.BLACK);
		returns4.setForeground(Color.WHITE);
		returns4.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Text display
		theme = new JLabel();
		String text = String.format("<html><div style=\"width:%dpx;\"><center><font size = \"4\">%s</font></center></div><html>", 300, "Binary was a land of peace, ruled by the good king Groshi. "
				+ "One day, the Dark Knight and his armies descended upon "
				+ "the kingdom, throwing it into chaos. The Dark Knight "
				+ "killed Groshi and kidnapped his daughter Elsie. "
				+ "Only one memember of the kingdom could hope to "
				+ "save the kingdom...<br/><br/>"
				+ "<font size = \"7\"> THE 4 BYTE WARRIOR </font>");
		theme.setText(text);
		theme.setHorizontalAlignment(JLabel.CENTER);
		theme.setVerticalAlignment(JLabel.CENTER);
		theme.setForeground(Color.WHITE);
		instruct = new JLabel();
		String instruction = String.format("<html><div style=\"width:%dpx;\"><center>%s</center></div><html>", 200, "Z Key: Attack<br/>X Key: Cycle through inventory<br/>D-Pad: Movement<br/>A: Pause<br/><br/><br/>Defeat the enemeies to advance. Fight the bosses, get through the 8 worlds of Binary, and save Princess Elsie.");
		instruct.setText(instruction);
		instruct.setHorizontalAlignment(JLabel.CENTER);
		instruct.setVerticalAlignment(JLabel.CENTER);
		instruct.setForeground(Color.WHITE);
		String names = String.format("<html><div style=\"width:%dpx;\"><center>%s</center></div><html>", 150, "Alexander Friebe<br/>Charles Heckel<br/>Nicholas Lindsley<br/>Ben McWerthy<br/>");
		people = new JLabel(names);
		people.setHorizontalAlignment(JLabel.CENTER);
		people.setVerticalAlignment(JLabel.CENTER);
		people.setForeground(Color.WHITE);
		String major = String.format("<html><div style=\"width:%dpx;\"><center>%s</center></div><html>", 150, "Computer Science<br/>Computer Engineering<br/>Computer Engineering<br/>Computer Science<br/>");
		majors = new JLabel(major);
		majors.setHorizontalAlignment(JLabel.CENTER);
		majors.setVerticalAlignment(JLabel.CENTER);
		majors.setForeground(Color.WHITE);
		String credi = String.format("<html><div style=\"width:%dpx;\"><center><font size = \"4\">%s</font></center></div><html>", 150, "Credits");
		top = new JLabel(credi);
		top.setHorizontalAlignment(JLabel.CENTER);
		top.setVerticalAlignment(JLabel.CENTER);
		top.setForeground(Color.WHITE);
		scoresTop = new JLabel();
		String scTop = String.format("<html><div style=\"width:%dpx;\"><center><font size = \"4\">%s</font></center></div><html>", 200, "High Scores for Enemies Killed");
		scoresTop.setText(scTop);
		scoresTop.setForeground(Color.WHITE);
		scoresTop.setHorizontalAlignment(JLabel.CENTER);
		scoresTop.setVerticalAlignment(JLabel.CENTER);
		JLabel dbScores = new JLabel();
		ArrayList<Integer> sc = db.getScores();
		System.out.println(sc.size());
		//System.out.println(sc.get(0));
		String actual = String.format("<html><div style=\"width:%dpx;\"><center><font size = \"4\">%s</font></center></div><html>", 100, sc.get(0)+"<br/>"+sc.get(1)+"<br/>"+sc.get(2)+"<br/>");
		dbScores.setText(actual);
		dbScores.setHorizontalAlignment(JLabel.CENTER);
		dbScores.setVerticalAlignment(JLabel.CENTER);
		dbScores.setForeground(Color.WHITE);
		
		
		
		//Additions to main
		mainButtons = new JPanel(new GridLayout(3,1));
		mainButtons.add(start);
		mainButtons.add(high);
		mainButtons.add(cred);
		main.add(mainButtons);
		
		//Additions to credits
		credits.add(top,BorderLayout.PAGE_START);
		//credits.add(people, BorderLayout.LINE_START);
		//credits.add(majors, BorderLayout.LINE_END);
		JPanel middle = new JPanel(new GridLayout(1,2));
		middle.add(people);
		middle.add(majors);
		middle.setBackground(Color.RED);
		JPanel bottom = new JPanel(new GridLayout(1,1));
		bottom.add(returns2);
		bottom.setBackground(Color.RED);
		credits.add(middle, BorderLayout.CENTER);
		credits.add(bottom, BorderLayout.PAGE_END);
		
		//Additions to high scores
		scores.add(scoresTop, BorderLayout.PAGE_START);
		scores.add(dbScores, BorderLayout.CENTER);
		JPanel bot = new JPanel(new GridLayout(1,1));
		bot.add(returns1);
		bot.setBackground(Color.RED);
		scores.add(bot,BorderLayout.PAGE_END);
		
		
		//Additions to story
		story.add(theme,BorderLayout.CENTER);
		buttons = new JPanel(new GridLayout(2,1));
		buttons.setBackground(Color.RED);
		//buttons.setOpaque(true);
		buttons.add(stor);
		buttons.add(returns3);
		story.add(buttons, BorderLayout.PAGE_END);
		
		//Additions to tutorial
		JPanel tutButtons = new JPanel(new GridLayout(2,1));
		tutorial.add(instruct, BorderLayout.CENTER);
		tutButtons.add(tutor);
		tutButtons.add(returns4);
		tutButtons.setBackground(Color.RED);
		tutorial.add(tutButtons, BorderLayout.PAGE_END);
		
		
		main.setBorder(new EmptyBorder(100,150,100,150));
		bot.setBorder(new EmptyBorder(0,150,0,150));
		//middle.setBorder(new EmptyBorder(0,150,0,150));
		bottom.setBorder(new EmptyBorder(0,150,0,150));
		buttons.setBorder(new EmptyBorder(0,150,0,150));
		tutButtons.setBorder(new EmptyBorder(0,150,0,150));
		frame.add(main);
		
		frame.pack(); //critical to call before you make the window visible in order to make everything look right 100% of the time
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public void actionPerformed(ActionEvent ae){
		JButton button = (JButton) ae.getSource();
		if(button == high){
			frame.remove(main);
			frame.setContentPane(scores);
		}
		if(button == cred){
			frame.remove(main);
			frame.setContentPane(credits);
		}
		if(button == start){
			frame.remove(main);
			frame.setContentPane(story);
		}
		if(button == stor){
			frame.remove(story);
			frame.setContentPane(tutorial);
		}
		if(button == tutor){
			frame.remove(main);
			DesktopLauncher a = new DesktopLauncher();
			a.run();
		}
		if(button == returns1){
			frame.remove(scores);
			frame.setContentPane(main);
		}
		if(button == returns2){
			frame.remove(credits);
			frame.setContentPane(main);
		}
		if(button == returns3){
			frame.remove(story);
			frame.setContentPane(main);
		}
		if(button == returns4){
			frame.remove(tutorial);
			frame.setContentPane(story);
		}
		frame.validate();
		frame.repaint();
	}
	public static void main(String[] args){
		new Primary();
	}
}