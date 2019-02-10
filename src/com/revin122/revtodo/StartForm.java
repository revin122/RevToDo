package com.revin122.revtodo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.revin122.revtodo.views.ViewItem;
import com.codename1.ui.CN;
import com.codename1.ui.Component;

public class StartForm extends Form {
	private final static String FILENAME = "saveitems";
	private ActionListener saveListener;
	
	public StartForm() {
		super("RevMappy", BoxLayout.y());
		
		getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_CLEAR_ALL, e -> clearAll());
		
		FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
		fab.bindFabToContainer(this);
		fab.addActionListener(e -> addItem());
		
		load();
	}
	
	private ActionListener getAutoSave() {
		if(saveListener == null) {
			saveListener = (e) -> save();
		}
		return saveListener;
	}
	
	private void addItem() {
		ViewItem viewItem = new ViewItem("", false, getAutoSave());
		add(viewItem);
		revalidate();
		viewItem.edit();
	}
	
	private void clearAll() {
		int cc = getContentPane().getComponentCount();
		for(int i = cc - 1; i >= 0; i--) {
			ViewItem vi = (ViewItem)getContentPane().getComponentAt(i);
			if(vi.isChecked()) {
				vi.remove();
			}
		}
		save();
		getContentPane().animateLayout(300);
	}
	
	private void load() {
		if(CN.existsInFileSystem(StartForm.FILENAME)) {
			try(DataInputStream inputStream = new DataInputStream(
					CN.createStorageInputStream(StartForm.FILENAME));) {
				int size = inputStream.readInt();
				for (int iter = 0; iter < size; iter++) {
					boolean checked = inputStream.readBoolean();
					ViewItem vi = new ViewItem(inputStream.readUTF(), checked, getAutoSave());
					add(vi);
				}
			} catch (Exception e) {
				Log.e(e);
				ToastBar.showErrorMessage("Error loading list");
			}
		}
	}
	
	private void save() {
		try(DataOutputStream outputStream = new DataOutputStream(
				CN.createStorageOutputStream(StartForm.FILENAME))) {
			outputStream.writeInt(getContentPane().getComponentCount());
			for(Component c : getContentPane()) {
				ViewItem vi = (ViewItem)c;
				outputStream.writeBoolean(vi.isChecked());
				outputStream.writeUTF(vi.getText());
			}
		} catch (IOException e) {
			Log.e(e);
			ToastBar.showErrorMessage("Error saving list");
		}
				
	}
}
