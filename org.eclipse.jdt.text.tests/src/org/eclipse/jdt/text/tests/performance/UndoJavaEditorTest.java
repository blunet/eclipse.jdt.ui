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

package org.eclipse.jdt.text.tests.performance;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.ui.PartInitException;

public class UndoJavaEditorTest extends UndoEditorTest {
	
	private static final Class THIS= UndoJavaEditorTest.class;
	
	private static final String FILE= "org.eclipse.swt/Eclipse SWT Custom Widgets/common/org/eclipse/swt/custom/StyledText.java";

	private static final int WARM_UP_RUNS= 2;

	private static final int MEASURED_RUNS= 2;

	public static Test suite() {
		return new PerformanceTestSetup(new TestSuite(THIS));
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		setWarmUpRuns(WARM_UP_RUNS);
		setMeasuredRuns(MEASURED_RUNS);
	}
	
	public void testUndoJavaEditor2() throws PartInitException {
		measureUndo(ResourceTestHelper.findFile(FILE));
	}
}
