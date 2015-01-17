package com.turbo.build.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.turbo.build.cg.CallGraph;
import com.turbo.build.util.ClassListLoader;
import com.turbo.build.util.Console;
import com.turbo.build.util.ProjectInfo;

public class DiagnoseAction implements IObjectActionDelegate {

//	private Shell shell;
	
	private ISelection selection;
	/**
	 * Constructor for OptimizeAction.
	 */
	public DiagnoseAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		Console.clearConsole();
		ClassListLoader loader = new ClassListLoader();
		loader.loadClassList();
		for(String clazz : loader.getClazzList()) {
			Console.println(clazz);			
		}
		
		CallGraph.buildCallGraphFromClasses(loader.getClazzList());
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
//		selection.
		this.selection = selection;
		
		ProjectInfo.projectInfo(this.selection);
	}
}
