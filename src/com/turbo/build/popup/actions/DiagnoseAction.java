package com.turbo.build.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.turbo.build.graph.Graph;
import com.turbo.build.graph.Vertex;
import com.turbo.build.opt.ClassPathParser;
import com.turbo.build.util.ClassListLoader;
import com.turbo.build.util.Console;
import com.turbo.build.util.Jar;
import com.turbo.build.util.JarUtil;
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
//		for(String clazz : loader.getClazzList()) {
//			Console.println(clazz);			
//		}
		
		ClassPathParser parser = new ClassPathParser(ProjectInfo.classpath);
		parser.extractJars();
		
		JarUtil util = new JarUtil(parser.getEntries());

//		List<Jar[]> jars = util.findConflictJars();
//		for (Jar[] jar : jars) {
//			Console.println(jar[0].getFullname() + " | "
//					+ jar[1].getFullname());
//		}

		Graph g = new Graph(util.findConflictJars());
		Console.println("Possible conflict jars:", new Color(null, 255, 0, 0));
		for (Vertex key : g.getEdgeMap().keySet()) {
			Console.println(key.getName());
			for (Vertex v : g.getEdgeMap().get(key)) {
				Console.print("  >>>" + v.getName() + " ");
			}
			Console.println("");
		}
		
		Console.println("\nOriginal jar order:", new Color(null, 255, 0, 0));
		for(Jar jar : util.getJars() ) {
			Console.println(jar.getFullname());
		}
		
		Vertex[] res = g.topologicalSort();
		Console.println("\nBest order to solve possible conflicts:", new Color(null, 255, 0, 0));
		for (Vertex v : res) {
			Console.println(v.getName());
		}
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
