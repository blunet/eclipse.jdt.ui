/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jdt.astview.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;

import org.eclipse.jdt.core.Signature;

import org.eclipse.jdt.core.dom.ASTNode;

public class ASTViewLabelProvider extends LabelProvider implements IColorProvider {
	
	private int fSelectionStart;
	private int fSelectionLength;
	
	public ASTViewLabelProvider() {
		fSelectionStart= -1;
		fSelectionLength= -1;
	}
	
	public void setSelectedRange(int start, int length) {
		fSelectionStart= start;
		fSelectionLength= length;
	}
	

	public String getText(Object obj) {
		StringBuffer buf= new StringBuffer();
		if (obj instanceof ASTNode) {
			getNodeType((ASTNode) obj, buf);
		} else {
			buf.append(obj.toString());
		}
		return buf.toString(); 
	}
	
	private void getNodeType(ASTNode node, StringBuffer buf) {
		buf.append(Signature.getSimpleName(node.getClass().getName()));
		buf.append(" ["); //$NON-NLS-1$
		buf.append(node.getStartPosition());
		buf.append(", "); //$NON-NLS-1$
		buf.append(node.getLength());
		buf.append(']');
	}
	
	
	public Image getImage(Object obj) {
		return null;
//		String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
//		if (obj instanceof ASTNode) {
//			imageKey = ISharedImages.IMG_OBJ_FOLDER;
//		}
//		return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(java.lang.Object)
	 */
	public Color getForeground(Object element) {
		if (element instanceof ASTNode) {
			ASTNode node= (ASTNode) element;
			int start= node.getStartPosition();
			int end= start + node.getLength();
			
			ASTNode parent= node.getParent();
			int parentstart= parent.getStartPosition();
			int parentend= start + parent.getLength();
			
			if (start < parentstart || end > parentend) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
			}

			return Display.getCurrent().getSystemColor(SWT.COLOR_DARK_GRAY);
		} else if (element instanceof BindingProperty) {
			if (((BindingProperty) element).getBinding() == null) {
				return Display.getCurrent().getSystemColor(SWT.COLOR_RED);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(java.lang.Object)
	 */
	public Color getBackground(Object element) {
		if (fSelectionStart != -1 && isInside(element)) {
			return Display.getCurrent().getSystemColor(SWT.COLOR_YELLOW);
		}
		return null;
	}
	
	private boolean isInsideNode(ASTNode node) {
		int start= node.getStartPosition();
		int end= start + node.getLength();
		if (start <= fSelectionStart && (fSelectionStart + fSelectionLength) < end) {
			return true;
		}
		return false;
	}
	
	private boolean isInside(Object element) {
		if (element instanceof ASTNode) {
			return isInsideNode((ASTNode) element);
		} else if (element instanceof NodeProperty) {
			NodeProperty property= (NodeProperty) element;
			Object object= property.getNode();
			if (object instanceof ASTNode) {
				return isInsideNode((ASTNode) object);
			}
		}
		return false;
	}
	
}
