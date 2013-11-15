/**
 * 
 */
package com.taobao.tbbpm.designer.action;

import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;

import com.taobao.tbbpm.designer.util.ModelDataUtils;
import com.taobao.tbbpm.designer.util.DataUtils;

/**
 * ����������
 * 
 * @author wuxiang junyu.wy
 * 
 */
public class ProcessClassGenerator {

	private String processName;// ����������

	public void generateTestClass(IResource tbpmResource) throws Exception {

		/** �ȴ����ļ��� */
		IProject project = tbpmResource.getProject();
		IFolder sourceFolder = project.getFolder("src/test/java");
		if (!sourceFolder.exists()) {
			if (!project.getFolder("src").exists()) {
				project.getFolder("src").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test").exists()) {
				project.getFolder("src/test").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test/java").exists()) {
				project.getFolder("src/test/java").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test/resources").exists()) {
				project.getFolder("src/test/resources").create(true, true,
						new NullProgressMonitor());
			}
		}

		/** ����java�������� */
		IJavaProject javaProject = (IJavaProject) project
				.getNature("org.eclipse.jdt.core.javanature");
		IPackageFragmentRoot srcRoot = javaProject
				.getPackageFragmentRoot(sourceFolder);
		IPackageFragment pack = srcRoot.createPackageFragment("", false, null);
		InputStream in = new FileInputStream(tbpmResource.getLocationURI()
				.getPath());
		if (DataUtils.isNotEmptyFile(in)) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(),
					"���ļ�Ϊ���ļ�", "���ļ�Ϊ���ļ�");
			return;
		}
		StringBuffer[] s = ModelDataUtils.getJavaTestCodeAndName(in);
		processName = s[1].toString() + ".java";
		IFile f = ((IFolder) pack.getResource()).getFile(processName);
		if (f.exists()) {
			String message = String.format(
					"���������Ѿ����� for process '%s', aborting",
					new Object[] { processName });
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(),
					"���������Ѿ����ڣ����ֹ�ɾ��������", message);
			return;
		}
		pack.createCompilationUnit(processName, s[0].toString(), false, null);

	}

	public void generateJUnitTestClass(IResource tbpmResource,
			IWorkbenchPart workbenchPart) throws Exception {

		/** �ȴ����ļ��� */
		IProject project = tbpmResource.getProject();
		IFolder sourceFolder = project.getFolder("src/test/java");
		if (!sourceFolder.exists()) {
			if (!project.getFolder("src").exists()) {
				project.getFolder("src").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test").exists()) {
				project.getFolder("src/test").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test/java").exists()) {
				project.getFolder("src/test/java").create(true, true,
						new NullProgressMonitor());
			}
			if (!project.getFolder("src/test/resources").exists()) {
				project.getFolder("src/test/resources").create(true, true,
						new NullProgressMonitor());
			}
		}

		InputStream in = new FileInputStream(tbpmResource.getLocationURI()
				.getPath());
		if (DataUtils.isNotEmptyFile(in)) {
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(),
					"���ļ�Ϊ���ļ�", "���ļ�Ϊ���ļ�");
			return;
		}
		StringBuffer[] s = ModelDataUtils.getJavaTestCodeAndName(in);
		/** ����java�������� */
		IJavaProject javaProject = (IJavaProject) project
				.getNature("org.eclipse.jdt.core.javanature");
		IPackageFragmentRoot srcRoot = javaProject
				.getPackageFragmentRoot(sourceFolder);
		String packageName = ModelDataUtils.getPackageName(s[1].toString());
		IPackageFragment pack = srcRoot.createPackageFragment(packageName,
				false, new NullProgressMonitor());
		processName = ModelDataUtils.getSimpleClassName(s[1].toString())
				+ ".java";
		IFile f = ((IFolder) pack.getResource()).getFile(processName);
		if (f.exists()) {
			String message = String.format(
					"���������Ѿ����� for process '%s', aborting",
					new Object[] { processName });
			MessageDialog.openWarning(Display.getCurrent().getActiveShell(),
					"���������Ѿ����ڣ����ֹ�ɾ��������", message);
			return;
		}
		pack.createCompilationUnit(processName, s[0].toString(), true,
				new NullProgressMonitor());

	}
}
