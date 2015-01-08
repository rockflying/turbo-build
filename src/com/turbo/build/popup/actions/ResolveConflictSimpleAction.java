package com.turbo.build.popup.actions;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.turbo.build.opt.BuildPathModifier;
import com.turbo.build.util.Console;

public class ResolveConflictSimpleAction implements IObjectActionDelegate {

	private Shell shell;
	
	private ISelection selection;
	/**
	 * Constructor for OptimizeAction.
	 */
	public ResolveConflictSimpleAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
//		MessageDialog.openInformation(
//			shell,
//			"BuildPathTurbo",
//			"New Action was executed.");
//		System.out.println("Finding conflict jars");
//		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
//		IProject[] projects = root.getProjects();
//		Console.println(projects.length);
		
//		for(int i = 0; i < projects.length; ++i) {
//			Console.println(projects[i].getLocation());
//			Console.println(projects[i].getName());
//		}
//		
//		Console.println(root.getProject("test").getLocation());
//		Console.println(selection.toString());
		Console.clearConsole();
		
		Object element = ((IStructuredSelection)selection).getFirstElement();
		
		String classpath = ((IResource) element).getProject().getLocation()
				+ File.separator + ".classpath";
		
		File file = new File(classpath);
		if(!file.exists()) {
			MessageDialog.openInformation(shell, "Build Path Turbo",
					".classpath does not exist");
			return;
		}
		
		BuildPathModifier buildPath = new BuildPathModifier(classpath);
		buildPath.optimizeBuildPath();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
//		selection.
		this.selection = selection;
	}
}
