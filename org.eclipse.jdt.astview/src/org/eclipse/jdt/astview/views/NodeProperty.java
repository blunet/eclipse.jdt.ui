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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;

public class NodeProperty {
	private ASTNode fParent;
	private StructuralPropertyDescriptor fProperty;
	
	public NodeProperty(ASTNode parent, StructuralPropertyDescriptor property) {
		fParent= parent;
		fProperty= property;
	}
	
	public ASTNode getParent() {
		return fParent;
	}
	
	public Object getNode() {
		return fParent.getStructuralProperty(fProperty);
	}
	
	public String getPropertyName() {
		return toConstantName(fProperty.getId());
	}
	
	private static String toConstantName(String string) {
		StringBuffer buf= new StringBuffer();
		for (int i= 0; i < string.length(); i++) {
			char ch= string.charAt(i);
			if (i != 0 && Character.isUpperCase(ch)) {
				buf.append('_');
			}
			buf.append(Character.toUpperCase(ch));
		}
		return buf.toString();
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || !o.getClass().equals(getClass())) {
			return false;
		}
		NodeProperty castedObj= (NodeProperty) o;
		return  fParent.equals(castedObj.fParent) && (fProperty == castedObj.fProperty);
	}

	public int hashCode() {
		return fParent.hashCode() * 31 + fProperty.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf= new StringBuffer();
		buf.append(getPropertyName());
		
		if (fProperty.isSimpleProperty()) {
			buf.append(": "); //$NON-NLS-1$
			Object node= getNode();
			if (node != null) {
				buf.append('\'');
				buf.append(getNode().toString());
				buf.append('\'');
			} else {
				buf.append("null"); //$NON-NLS-1$
			}
		}
		return buf.toString();
	}


	
}
