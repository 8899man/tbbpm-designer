/**
 * 
 */
package com.taobao.tbbpm.designer.action;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * �һ�������java���룬 ���ɵ�Ԫ���Դ����Ѿ��ڱ༭������Բ鿴�ˣ��ʷ���
 * 
 * <pre>
 * ����java����
 * </pre>
 * 
 * @author wuxiang
 * 
 */
@Deprecated
public class GenerateFLowClassAction implements IObjectActionDelegate {

	private Shell shell;
	private IResource tbpmFile;

	@Override
	public void run(IAction action) {
		try {
			new ProcessClassGenerator().generateTestClass(this.tbpmFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection selection) {
		if ((selection != null & selection instanceof IStructuredSelection)) {
			IStructuredSelection strucSelection = (IStructuredSelection) selection;
			this.tbpmFile = ((IResource) strucSelection.getFirstElement());
		}
	}

	@Override
	public void setActivePart(IAction arg0, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

}
