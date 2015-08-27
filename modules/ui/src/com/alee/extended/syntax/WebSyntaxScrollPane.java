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

package com.alee.extended.syntax;

import com.alee.extended.painter.Painter;
import com.alee.managers.style.StyleId;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.scroll.WebScrollBar;
import com.alee.laf.scroll.WebScrollPaneBar;
import com.alee.laf.scroll.WebScrollPaneUI;
import com.alee.managers.language.LanguageContainerMethods;
import com.alee.managers.language.LanguageManager;
import com.alee.managers.log.Log;
import com.alee.managers.style.StyleManager;
import com.alee.utils.ReflectUtils;
import com.alee.utils.SizeUtils;
import com.alee.utils.laf.ShapeProvider;
import com.alee.utils.laf.Styleable;
import com.alee.utils.swing.SizeMethods;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.awt.*;

/**
 * Special scrollpane exclusively for {@link com.alee.extended.syntax.WebSyntaxArea} component.
 *
 * @author Mikle Garin
 */

public class WebSyntaxScrollPane extends RTextScrollPane
        implements Styleable, ShapeProvider, SizeMethods<WebSyntaxScrollPane>, LanguageContainerMethods
{
    /**
     * Constructs new empty syntax scrollpane.
     */
    public WebSyntaxScrollPane ()
    {
        super ();
        initialize ( StyleId.syntaxareaScroll );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param syntaxArea {@link com.alee.extended.syntax.WebSyntaxArea}
     */
    public WebSyntaxScrollPane ( final WebSyntaxArea syntaxArea )
    {
        super ( syntaxArea );
        initialize ( StyleId.syntaxareaScroll );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param syntaxArea  {@link com.alee.extended.syntax.WebSyntaxArea}
     * @param lineNumbers whether or not should display line numbers
     */
    public WebSyntaxScrollPane ( final WebSyntaxArea syntaxArea, final boolean lineNumbers )
    {
        super ( syntaxArea, lineNumbers );
        initialize ( StyleId.syntaxareaScroll );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param syntaxArea      {@link com.alee.extended.syntax.WebSyntaxArea}
     * @param lineNumbers     whether or not should display line numbers
     * @param lineNumberColor line numbers foreground
     */
    public WebSyntaxScrollPane ( final WebSyntaxArea syntaxArea, final boolean lineNumbers, final Color lineNumberColor )
    {
        super ( syntaxArea, lineNumbers, lineNumberColor );
        initialize ( StyleId.syntaxareaScroll );
    }

    /**
     * Constructs new empty syntax scrollpane.
     *
     * @param id style ID
     */
    public WebSyntaxScrollPane ( final StyleId id )
    {
        super ();
        initialize ( id );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param id         style ID
     * @param syntaxArea {@link com.alee.extended.syntax.WebSyntaxArea}
     */
    public WebSyntaxScrollPane ( final StyleId id, final WebSyntaxArea syntaxArea )
    {
        super ( syntaxArea );
        initialize ( id );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param id          style ID
     * @param syntaxArea  {@link com.alee.extended.syntax.WebSyntaxArea}
     * @param lineNumbers whether or not should display line numbers
     */
    public WebSyntaxScrollPane ( final StyleId id, final WebSyntaxArea syntaxArea, final boolean lineNumbers )
    {
        super ( syntaxArea, lineNumbers );
        initialize ( id );
    }

    /**
     * Constructs new syntax scrollpane with the specified syntax area inside.
     *
     * @param id              style ID
     * @param syntaxArea      {@link com.alee.extended.syntax.WebSyntaxArea}
     * @param lineNumbers     whether or not should display line numbers
     * @param lineNumberColor line numbers foreground
     */
    public WebSyntaxScrollPane ( final StyleId id, final WebSyntaxArea syntaxArea, final boolean lineNumbers, final Color lineNumberColor )
    {
        super ( syntaxArea, lineNumbers, lineNumberColor );
        initialize ( id );
    }

    /**
     * Initializes default scrollpane styling.
     *
     * @param id style ID
     */
    protected void initialize ( final StyleId id )
    {
        setStyleId ( id );
        setGutterStyleId ( StyleId.of ( StyleId.syntaxareaScrollGutter, this ) );
    }

    /**
     * Returns scrollpane painter.
     *
     * @return scrollpane painter
     */
    public Painter getPainter ()
    {
        return StyleManager.getPainter ( this );
    }

    /**
     * Sets scrollpane painter.
     * Pass null to remove scrollpane painter.
     *
     * @param painter new scrollpane painter
     * @return this scrollpane
     */
    public WebSyntaxScrollPane setPainter ( final Painter painter )
    {
        StyleManager.setCustomPainter ( this, painter );
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StyleId getStyleId ()
    {
        return getWebUI ().getStyleId ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStyleId ( final StyleId id )
    {
        getWebUI ().setStyleId ( id );
    }

    /**
     * Sets gutter panel style ID.
     *
     * @param id gutter panel style ID
     */
    public void setGutterStyleId ( final StyleId id )
    {
        id.set ( getGutter () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebScrollBar createHorizontalScrollBar ()
    {
        return new WebScrollPaneBar ( this, WebScrollBar.HORIZONTAL );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebScrollBar createVerticalScrollBar ()
    {
        return new WebScrollPaneBar ( this, WebScrollBar.VERTICAL );
    }

    /**
     * Returns horizontal {@link com.alee.laf.scroll.WebScrollBar} if it is installed in this scroll pane.
     *
     * @return {@link com.alee.laf.scroll.WebScrollBar} or null if it is not installed
     */
    public WebScrollBar getWebHorizontalScrollBar ()
    {
        return ( WebScrollBar ) super.getHorizontalScrollBar ();
    }

    /**
     * Returns vertical {@link com.alee.laf.scroll.WebScrollBar} if it is installed in this scroll pane.
     *
     * @return {@link com.alee.laf.scroll.WebScrollBar} or null if it is not installed
     */
    public WebScrollBar getWebVerticalScrollBar ()
    {
        return ( WebScrollBar ) super.getVerticalScrollBar ();
    }

    /**
     * {@inheritDoc}
     */
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
    private WebScrollPaneUI getWebUI ()
    {
        return ( WebScrollPaneUI ) getUI ();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUI ()
    {
        if ( getUI () == null || !( getUI () instanceof WebScrollPaneUI ) )
        {
            try
            {
                setUI ( ( WebScrollPaneUI ) ReflectUtils.createInstance ( WebLookAndFeel.scrollPaneUI ) );
            }
            catch ( final Throwable e )
            {
                Log.error ( this, e );
                setUI ( new WebScrollPaneUI () );
            }
        }
        else
        {
            setUI ( getUI () );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPreferredWidth ()
    {
        return SizeUtils.getPreferredWidth ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setPreferredWidth ( final int preferredWidth )
    {
        return SizeUtils.setPreferredWidth ( this, preferredWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPreferredHeight ()
    {
        return SizeUtils.getPreferredHeight ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setPreferredHeight ( final int preferredHeight )
    {
        return SizeUtils.setPreferredHeight ( this, preferredHeight );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumWidth ()
    {
        return SizeUtils.getMinimumWidth ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setMinimumWidth ( final int minimumWidth )
    {
        return SizeUtils.setMinimumWidth ( this, minimumWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinimumHeight ()
    {
        return SizeUtils.getMinimumHeight ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setMinimumHeight ( final int minimumHeight )
    {
        return SizeUtils.setMinimumHeight ( this, minimumHeight );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumWidth ()
    {
        return SizeUtils.getMaximumWidth ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setMaximumWidth ( final int maximumWidth )
    {
        return SizeUtils.setMaximumWidth ( this, maximumWidth );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaximumHeight ()
    {
        return SizeUtils.getMaximumHeight ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setMaximumHeight ( final int maximumHeight )
    {
        return SizeUtils.setMaximumHeight ( this, maximumHeight );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize ()
    {
        return SizeUtils.getPreferredSize ( this, super.getPreferredSize () );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WebSyntaxScrollPane setPreferredSize ( final int width, final int height )
    {
        return SizeUtils.setPreferredSize ( this, width, height );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLanguageContainerKey ( final String key )
    {
        LanguageManager.registerLanguageContainer ( this, key );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLanguageContainerKey ()
    {
        LanguageManager.unregisterLanguageContainer ( this );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLanguageContainerKey ()
    {
        return LanguageManager.getLanguageContainerKey ( this );
    }
}