/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.laf.menu;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.language.LanguageContainerMethods;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.log.Log;
import com.alee.managers.style.StyleId;
import com.alee.utils.ReflectUtils;
import com.alee.utils.SizeUtils;
import com.alee.managers.style.ShapeProvider;
import com.alee.managers.style.Styleable;
import com.alee.utils.swing.SizeMethods;

import javax.swing.*;
import java.awt.*;

/**
 * This JPopupMenu extension class provides a direct access to WebPopupMenuUI methods.
 * It also has a few additional methods to simplify popup window positioning.
 *
 * @author Mikle Garin
 */

public class WebPopupMenu extends JPopupMenu implements Styleable, ShapeProvider, SizeMethods<WebPopupMenu>, LanguageContainerMethods
{
    /**
     * Constructs new popup menu.
     */
    public WebPopupMenu ()
    {
        super ();
    }

    /**
     * Constructs new popup menu with the specified style ID.
     *
     * @param id style ID
     */
    public WebPopupMenu ( final StyleId id )
    {
        super ();
        setStyleId ( id );
    }

    /**
     * Adds separator into menu.
     */
    @Override
    public void addSeparator ()
    {
        add ( new WebPopupMenu.Separator () );
    }

    /**
     * Adds separator into menu at the specified Z-index.
     *
     * @param index separator Z-index
     */
    public void addSeparator ( final int index )
    {
        add ( new WebPopupMenu.Separator (), index );
    }

    /**
     * Displays popup menu above the invoker component starting at its leading side.
     * This method takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showAbove ( final Component invoker )
    {
        return showAboveStart ( invoker );
    }

    /**
     * Displays popup menu above the invoker component starting at its leading side.
     * This method takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showAboveStart ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.aboveStart );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Displays popup menu above the invoker component at its middle.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showAboveMiddle ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.aboveMiddle );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Displays popup menu above the invoker component starting at its trailing side.
     * This method takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showAboveEnd ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.aboveEnd );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Displays popup menu under the invoker component starting at its leading side.
     * This method also takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showBelow ( final Component invoker )
    {
        return showBelowStart ( invoker );
    }

    /**
     * Displays popup menu under the invoker component starting at its leading side.
     * This method takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showBelowStart ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.belowStart );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Displays popup menu under the invoker component at its middle.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showBelowMiddle ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.belowMiddle );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Displays popup menu under the invoker component starting at its trailing side.
     * This method takes into account component orientation.
     *
     * @param invoker invoker component
     * @return this popup menu
     */
    public WebPopupMenu showBelowEnd ( final Component invoker )
    {
        setPopupMenuWay ( PopupMenuWay.belowEnd );
        show ( invoker, 0, 0 );
        return this;
    }

    /**
     * Assists popup menu to allow it choose the best position relative to invoker.
     * Its value nullified right after first usage to avoid popup menu display issues in future.
     *
     * @param way approximate popup menu display way
     * @return this popup menu
     */
    public WebPopupMenu setPopupMenuWay ( final PopupMenuWay way )
    {
        getWebUI ().setPopupMenuWay ( way );
        return this;
    }

    @Override
    public StyleId getStyleId ()
    {
        return getWebUI ().getStyleId ();
    }

    @Override
    public StyleId setStyleId ( final StyleId id )
    {
        return getWebUI ().setStyleId ( id );
    }

    @Override
    public Shape provideShape ()
    {
        return getWebUI ().provideShape ();
    }

    /**
     * Returns Web-UI applied to this class.
     *
     * @return Web-UI applied to this class
     */
    public WebPopupMenuUI getWebUI ()
    {
        return ( WebPopupMenuUI ) getUI ();
    }

    /**
     * Installs a Web-UI into this component.
     */
    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebPopupMenuUI ) )
        {
            try
            {
                setUI ( ( WebPopupMenuUI ) ReflectUtils.createInstance ( WebLookAndFeel.popupMenuUI ) );
            }
            catch ( final Throwable e )
            {
                Log.error ( this, e );
                setUI ( new WebPopupMenuUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }

    @Override
    public int getPreferredWidth ()
    {
        return SizeUtils.getPreferredWidth ( this );
    }

    @Override
    public WebPopupMenu setPreferredWidth ( final int preferredWidth )
    {
        return SizeUtils.setPreferredWidth ( this, preferredWidth );
    }

    @Override
    public int getPreferredHeight ()
    {
        return SizeUtils.getPreferredHeight ( this );
    }

    @Override
    public WebPopupMenu setPreferredHeight ( final int preferredHeight )
    {
        return SizeUtils.setPreferredHeight ( this, preferredHeight );
    }

    @Override
    public int getMinimumWidth ()
    {
        return SizeUtils.getMinimumWidth ( this );
    }

    @Override
    public WebPopupMenu setMinimumWidth ( final int minimumWidth )
    {
        return SizeUtils.setMinimumWidth ( this, minimumWidth );
    }

    @Override
    public int getMinimumHeight ()
    {
        return SizeUtils.getMinimumHeight ( this );
    }

    @Override
    public WebPopupMenu setMinimumHeight ( final int minimumHeight )
    {
        return SizeUtils.setMinimumHeight ( this, minimumHeight );
    }

    @Override
    public int getMaximumWidth ()
    {
        return SizeUtils.getMaximumWidth ( this );
    }

    @Override
    public WebPopupMenu setMaximumWidth ( final int maximumWidth )
    {
        return SizeUtils.setMaximumWidth ( this, maximumWidth );
    }

    @Override
    public int getMaximumHeight ()
    {
        return SizeUtils.getMaximumHeight ( this );
    }

    @Override
    public WebPopupMenu setMaximumHeight ( final int maximumHeight )
    {
        return SizeUtils.setMaximumHeight ( this, maximumHeight );
    }

    @Override
    public Dimension getPreferredSize ()
    {
        return SizeUtils.getPreferredSize ( this, super.getPreferredSize () );
    }

    @Override
    public WebPopupMenu setPreferredSize ( final int width, final int height )
    {
        return SizeUtils.setPreferredSize ( this, width, height );
    }

    @Override
    public void setLanguageContainerKey ( final String key )
    {
        LanguageManager.registerLanguageContainer ( this, key );
    }

    @Override
    public void removeLanguageContainerKey ()
    {
        LanguageManager.unregisterLanguageContainer ( this );
    }

    @Override
    public String getLanguageContainerKey ()
    {
        return LanguageManager.getLanguageContainerKey ( this );
    }
}