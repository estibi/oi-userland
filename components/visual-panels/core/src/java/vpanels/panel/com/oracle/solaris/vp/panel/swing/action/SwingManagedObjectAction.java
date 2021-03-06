/*
 * CDDL HEADER START
 *
 * The contents of this file are subject to the terms of the
 * Common Development and Distribution License (the "License").
 * You may not use this file except in compliance with the License.
 *
 * You can obtain a copy of the license at usr/src/OPENSOLARIS.LICENSE
 * or http://www.opensolaris.org/os/licensing.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL HEADER in each
 * file and include the License file at usr/src/OPENSOLARIS.LICENSE.
 * If applicable, add the following below this CDDL HEADER, with the
 * fields enclosed by brackets "[]" replaced with your own identifying
 * information: Portions Copyright [yyyy] [name of copyright owner]
 *
 * CDDL HEADER END
 */

/*
 * Copyright (c) 2009, 2012, Oracle and/or its affiliates. All rights reserved.
 */

package com.oracle.solaris.vp.panel.swing.action;

import java.beans.*;
import java.util.*;
import javax.swing.Icon;
import com.oracle.solaris.vp.panel.common.ClientContext;
import com.oracle.solaris.vp.panel.common.model.ManagedObject;
import com.oracle.solaris.vp.panel.swing.control.SwingControl;
import com.oracle.solaris.vp.util.misc.ObjectUtil;
import com.oracle.solaris.vp.util.swing.HasComponent;

@SuppressWarnings({"serial"})
public class SwingManagedObjectAction<S extends ManagedObject, I, O>
    extends SwingStructuredAction<List<S>, I, O> {

    //
    // Instance data
    //

    private PropertyChangeListener refreshListener =
	new PropertyChangeListener() {
	    @Override
	    public void propertyChange(PropertyChangeEvent e) {
		refresh();
	    }
	};

    //
    // Constructors
    //

    @SuppressWarnings({"unchecked"})
    public SwingManagedObjectAction(String text,
	Collection<? extends Icon> icons, ClientContext context,
	HasComponent hasComponent) {

	super(text, (List<S>)(List)Collections.emptyList(), icons, context,
	    hasComponent);
    }

    @SuppressWarnings({"unchecked"})
    public SwingManagedObjectAction(String text,
	Collection<? extends Icon> icons, SwingControl control) {

	super(text, (List<S>)(List)Collections.emptyList(), icons, control);
    }

    //
    // StructuredAction methods
    //

    @Override
    public void setPresetInput(List<S> selection) {
	List<S> oldSelection = getPresetInput();
        if (!ObjectUtil.equals(oldSelection, selection)) {
	    if (oldSelection != null) {
		for (S object : oldSelection) {
		    object.removePropertyChangeListener(refreshListener);
		}
	    }

	    if (selection != null) {
		for (S object : selection) {
		    object.addPropertyChangeListener(refreshListener);
		}
            }

            super.setPresetInput(selection);
        }
    }
}
