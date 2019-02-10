package com.revin122.revtodo.views;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;

public class ViewItem extends Container {
	
	private TextField nameTextField;
	private CheckBox doneCheckBox;
	
	public ViewItem(String name, boolean checked, ActionListener onChange) {
		super(new BorderLayout());
		
		setUIID("Task");
		
		nameTextField = new TextField(name);
		nameTextField.setUIID("Label");
		nameTextField.addActionListener(onChange);
		
		doneCheckBox = new CheckBox();
		doneCheckBox.setSelected(checked);
		doneCheckBox.setToggle(true);
		FontImage.setMaterialIcon(doneCheckBox, FontImage.MATERIAL_CHECK, 4);
		doneCheckBox.addActionListener(onChange);
		
		add(CENTER, nameTextField);
		add(RIGHT, doneCheckBox);
	}
	
	public void edit() {
		nameTextField.startEditingAsync();
	}
	
	public boolean isChecked() {
		return doneCheckBox.isSelected();
	}
	
	public String getText() {
		return nameTextField.getText();
	}
}
