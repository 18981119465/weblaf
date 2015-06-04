package com.alee.laf.tabbedpane;

import com.alee.extended.painter.Painter;
import com.alee.extended.painter.SpecificPainter;

import javax.swing.*;

/**
 * Base interface for JTabbedPane component painters.
 *
 * @author Alexandr Zernov
 */

public interface TabbedPanePainter<E extends JTabbedPane, U extends WebTabbedPaneUI> extends Painter<E, U>, SpecificPainter
{
}