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

package com.alee.managers.style.skin.web;

import com.alee.extended.painter.AbstractPainter;
import com.alee.extended.painter.PartialDecoration;
import com.alee.global.StyleConstants;
import com.alee.managers.focus.DefaultFocusTracker;
import com.alee.managers.focus.FocusManager;
import com.alee.managers.focus.FocusTracker;
import com.alee.utils.GraphicsUtils;
import com.alee.utils.LafUtils;
import com.alee.utils.NinePatchUtils;
import com.alee.utils.ShapeCache;
import com.alee.utils.laf.PainterShapeProvider;
import com.alee.utils.ninepatch.NinePatchIcon;
import com.alee.utils.swing.DataProvider;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * Web-style background painter for any component.
 * Commonly used as a base painter class for various Swing components like JPanel, JButton and others.
 *
 * @param <E> component type
 * @param <U> component UI type
 * @author Mikle Garin
 */

public class WebDecorationPainter<E extends JComponent, U extends ComponentUI> extends AbstractPainter<E, U>
        implements PainterShapeProvider<E>, PartialDecoration
{
    /**
     * todo 1. Border stroke -> create stroke format for XML and allow to specify it there (XStream converter for Stroke)
     * todo 2. Inner shadow paint methods and settings
     * todo 3. Return correct preferred size according to large shade 9-patch icon
     */

    /**
     * Shape cache keys.
     */
    protected static final String BORDER_SHAPE = "border";
    protected static final String BACKGROUND_SHAPE = "background";

    /**
     * Style settings.
     */
    protected int round = WebDecorationPainterStyle.round;
    protected int shadeWidth = WebDecorationPainterStyle.shadeWidth;
    protected float shadeTransparency = WebDecorationPainterStyle.shadeTransparency;
    protected Stroke borderStroke = WebDecorationPainterStyle.borderStroke;
    protected Color borderColor = WebDecorationPainterStyle.borderColor;
    protected Color disabledBorderColor = WebDecorationPainterStyle.disabledBorderColor;
    protected boolean undecorated = WebDecorationPainterStyle.undecorated;
    protected boolean paintFocus = WebDecorationPainterStyle.paintFocus;
    protected boolean paintBackground = WebDecorationPainterStyle.paintBackground;
    protected boolean webColoredBackground = WebDecorationPainterStyle.webColoredBackground;
    protected boolean paintTop = true;
    protected boolean paintLeft = true;
    protected boolean paintBottom = true;
    protected boolean paintRight = true;
    protected boolean paintTopLine = false;
    protected boolean paintLeftLine = false;
    protected boolean paintBottomLine = false;
    protected boolean paintRightLine = false;

    /**
     * Listeners.
     */
    protected FocusTracker focusTracker;

    /**
     * Runtime variables.
     */
    protected boolean focused = false;

    /**
     * Painting variables.
     */
    protected boolean actualPaintLeft;
    protected boolean actualPaintRight;
    protected boolean actualPaintLeftLine;
    protected boolean actualPaintRightLine;
    protected int w;
    protected int h;

    @Override
    public void install ( final E c, final U ui )
    {
        super.install ( c, ui );

        // Installing FocusTracker to keep an eye on focused state
        focusTracker = new DefaultFocusTracker ()
        {
            @Override
            public boolean isTrackingEnabled ()
            {
                return !undecorated && paintFocus;
            }

            @Override
            public void focusChanged ( final boolean focused )
            {
                WebDecorationPainter.this.focused = focused;
                repaint ();
            }
        };
        FocusManager.addFocusTracker ( c, focusTracker );
    }

    @Override
    public void uninstall ( final E c, final U ui )
    {
        // Removing listeners
        FocusManager.removeFocusTracker ( focusTracker );
        focusTracker = null;

        super.uninstall ( c, ui );
    }

    @Override
    public Shape provideShape ( final E component, final Rectangle bounds )
    {
        return undecorated ? bounds : getShape ( component, true );
    }

    @Override
    public boolean isUndecorated ()
    {
        return undecorated;
    }

    @Override
    public void setUndecorated ( final boolean undecorated )
    {
        if ( this.undecorated != undecorated )
        {
            this.undecorated = undecorated;
            updateAll ();
        }
    }

    /**
     * Returns whether focus should be painted or not.
     *
     * @return true if focus should be painted, false otherwise
     */
    public boolean isPaintFocus ()
    {
        return paintFocus;
    }

    /**
     * Sets whether focus should be painted or not.
     *
     * @param paint whether focus should be painted or not
     */
    public void setPaintFocus ( final boolean paint )
    {
        if ( this.paintFocus != paint )
        {
            this.paintFocus = paint;
            repaint ();
        }
    }

    /**
     * Returns decoration corners rounding.
     *
     * @return decoration corners rounding
     */
    public int getRound ()
    {
        return round;
    }

    /**
     * Sets decoration corners rounding.
     *
     * @param round decoration corners rounding
     */
    public void setRound ( final int round )
    {
        if ( this.round != round )
        {
            this.round = round;
            repaint ();
        }
    }

    @Override
    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    @Override
    public void setShadeWidth ( final int width )
    {
        if ( this.shadeWidth != width )
        {
            this.shadeWidth = width;
            revalidate ();
        }
    }

    /**
     * Returns decoration shade transparency.
     *
     * @return decoration shade transparency
     */
    public float getShadeTransparency ()
    {
        return shadeTransparency;
    }

    /**
     * Sets decoration shade transparency.
     *
     * @param transparency new decoration shade transparency
     */
    public void setShadeTransparency ( final float transparency )
    {
        if ( this.shadeTransparency != transparency )
        {
            this.shadeTransparency = transparency;
            repaint ();
        }
    }

    /**
     * Returns decoration border stroke.
     *
     * @return decoration border stroke
     */
    public Stroke getBorderStroke ()
    {
        return borderStroke;
    }

    /**
     * Sets decoration border stroke.
     *
     * @param stroke decoration border stroke
     */
    public void setBorderStroke ( final Stroke stroke )
    {
        if ( this.borderStroke != stroke )
        {
            this.borderStroke = stroke;
            repaint ();
        }
    }

    /**
     * Returns decoration border color.
     *
     * @return decoration border color
     */
    public Color getBorderColor ()
    {
        return borderColor;
    }

    /**
     * Sets decoration border color.
     *
     * @param color decoration border color
     */
    public void setBorderColor ( final Color color )
    {
        if ( this.borderColor != color )
        {
            this.borderColor = color;
            repaint ();
        }
    }

    /**
     * Returns decoration disabled border color.
     *
     * @return decoration disabled border color
     */
    public Color getDisabledBorderColor ()
    {
        return disabledBorderColor;
    }

    /**
     * Sets decoration disabled border color.
     *
     * @param color decoration disabled border color
     */
    public void setDisabledBorderColor ( final Color color )
    {
        if ( this.disabledBorderColor != color )
        {
            this.disabledBorderColor = color;
            repaint ();
        }
    }

    /**
     * Returns whether should paint decoration background or not.
     *
     * @return true if should paint decoration background, false otherwise
     */
    public boolean isPaintBackground ()
    {
        return paintBackground;
    }

    /**
     * Sets whether should paint decoration background or not.
     *
     * @param paint whether should paint decoration background or not
     */
    public void setPaintBackground ( final boolean paint )
    {
        if ( this.paintBackground != paint )
        {
            this.paintBackground = paint;
            repaint ();
        }
    }

    /**
     * Sets whether should paint web-styled background or not.
     *
     * @return true if should paint web-styled background, false otherwise
     */
    public boolean isWebColoredBackground ()
    {
        return webColoredBackground;
    }

    /**
     * Sets whether should paint web-styled background or not.
     *
     * @param webColored whether should paint web-styled background or not
     */
    public void setWebColoredBackground ( final boolean webColored )
    {
        if ( this.webColoredBackground != webColored )
        {
            this.webColoredBackground = webColored;
            repaint ();
        }
    }

    /**
     * Returns whether should paint top side or not.
     *
     * @return true if should paint top side, false otherwise
     */
    public boolean isPaintTop ()
    {
        return paintTop;
    }

    @Override
    public void setPaintTop ( final boolean top )
    {
        if ( this.paintTop != top )
        {
            this.paintTop = top;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint left side or not.
     *
     * @return true if should paint left side, false otherwise
     */
    public boolean isPaintLeft ()
    {
        return paintLeft;
    }

    @Override
    public void setPaintLeft ( final boolean left )
    {
        if ( this.paintLeft != left )
        {
            this.paintLeft = left;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint bottom side or not.
     *
     * @return true if should paint bottom side, false otherwise
     */
    public boolean isPaintBottom ()
    {
        return paintBottom;
    }

    @Override
    public void setPaintBottom ( final boolean bottom )
    {
        if ( this.paintBottom != bottom )
        {
            this.paintBottom = bottom;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint right side or not.
     *
     * @return true if should paint right side, false otherwise
     */
    public boolean isPaintRight ()
    {
        return paintRight;
    }

    @Override
    public void setPaintRight ( final boolean right )
    {
        if ( this.paintRight != right )
        {
            this.paintRight = right;
            updateAll ();
        }
    }

    @Override
    public void setPaintSides ( final boolean top, final boolean left, final boolean bottom, final boolean right )
    {
        if ( this.paintTop != top || this.paintLeft != left || this.paintBottom != bottom || this.paintRight != right )
        {
            this.paintTop = top;
            this.paintLeft = left;
            this.paintBottom = bottom;
            this.paintRight = right;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint top side line or not.
     *
     * @return true if should paint top side line, false otherwise
     */
    public boolean isPaintTopLine ()
    {
        return paintTopLine;
    }

    @Override
    public void setPaintTopLine ( final boolean top )
    {
        if ( this.paintTopLine != top )
        {
            this.paintTopLine = top;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint left side line or not.
     *
     * @return true if should paint left side line, false otherwise
     */
    public boolean isPaintLeftLine ()
    {
        return paintLeftLine;
    }

    @Override
    public void setPaintLeftLine ( final boolean left )
    {
        if ( this.paintLeftLine != left )
        {
            this.paintLeftLine = left;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint bottom side line or not.
     *
     * @return true if should paint bottom side line, false otherwise
     */
    public boolean isPaintBottomLine ()
    {
        return paintBottomLine;
    }

    @Override
    public void setPaintBottomLine ( final boolean bottom )
    {
        if ( this.paintBottomLine != bottom )
        {
            this.paintBottomLine = bottom;
            updateAll ();
        }
    }

    /**
     * Returns whether should paint right side line or not.
     *
     * @return true if should paint right side line, false otherwise
     */
    public boolean isPaintRightLine ()
    {
        return paintRightLine;
    }

    @Override
    public void setPaintRightLine ( final boolean right )
    {
        if ( this.paintRightLine != right )
        {
            this.paintRightLine = right;
            updateAll ();
        }
    }

    @Override
    public void setPaintSideLines ( final boolean top, final boolean left, final boolean bottom, final boolean right )
    {
        if ( this.paintTopLine != top || this.paintLeftLine != left || this.paintBottomLine != bottom || this.paintRightLine != right )
        {
            this.paintTopLine = top;
            this.paintLeftLine = left;
            this.paintBottomLine = bottom;
            this.paintRightLine = right;
            updateAll ();
        }
    }

    @Override
    public Boolean isOpaque ()
    {
        // Returns null to disable automatic opacity changes by default
        // You may still provide a non-null opacity in your own painter implementations
        return undecorated ? null : false;
    }

    @Override
    public Insets getBorders ()
    {
        if ( undecorated )
        {
            // Empty borders
            return null;
        }
        else
        {
            // Decoration border
            // todo Return larger border if shade image is used?
            final int spacing = shadeWidth + 1;
            final int top = paintTop ? spacing : paintTopLine ? 1 : 0;
            final int left = paintLeft ? spacing : paintLeftLine ? 1 : 0;
            final int bottom = paintBottom ? spacing : paintBottomLine ? 1 : 0;
            final int right = paintRight ? spacing : paintRightLine ? 1 : 0;
            return new Insets ( top, left, bottom, right );
        }
    }

    @Override
    public void paint ( final Graphics2D g2d, final Rectangle bounds, final E c, final U ui )
    {
        if ( !undecorated )
        {
            actualPaintLeft = ltr ? paintLeft : paintRight;
            actualPaintRight = ltr ? paintRight : paintLeft;
            actualPaintLeftLine = ltr ? paintLeftLine : paintRightLine;
            actualPaintRightLine = ltr ? paintRightLine : paintLeftLine;
            w = c.getWidth ();
            h = c.getHeight ();

            // Checking need of painting
            final boolean anyBorder = paintTop || paintLeft || paintBottom || paintRight;
            final boolean anyLine = paintTopLine || paintLeftLine || paintBottomLine || paintRightLine;
            if ( anyBorder || anyLine || paintBackground )
            {
                final Object aa = GraphicsUtils.setupAntialias ( g2d );
                final boolean enabled = c.isEnabled ();

                // Border shape
                final Shape borderShape = getShape ( c, false );
                final Shape backgroundShape = getShape ( c, true );

                // Outer shadow
                if ( anyBorder && shadeWidth > 0 )
                {
                    paintShade ( g2d, bounds, borderShape );
                }

                // Background
                if ( paintBackground )
                {
                    paintBackground ( g2d, bounds, backgroundShape );
                }

                // Border
                if ( ( anyBorder || anyLine ) && ( enabled ? borderColor != null : disabledBorderColor != null ) )
                {
                    paintBorder ( g2d, bounds, borderShape );
                }

                GraphicsUtils.restoreAntialias ( g2d, aa );
            }
        }
        else if ( c.isOpaque () )
        {
            // Paint simple background if undecorated & opaque
            // Otherwise component will cause various visual glitches
            g2d.setPaint ( c.getBackground () );
            g2d.fillRect ( bounds.x, bounds.y, bounds.width, bounds.height );
        }
    }

    /**
     * Paints outer decoration shade.
     *
     * @param g2d         graphics context
     * @param bounds      painting bounds
     * @param borderShape component border shape
     */
    @SuppressWarnings ( "UnusedParameters" )
    protected void paintShade ( final Graphics2D g2d, final Rectangle bounds, final Shape borderShape )
    {
        if ( shadeWidth < 4 )
        {
            // Paint shape-based small shade
            final Composite oc = GraphicsUtils.setupAlphaComposite ( g2d, shadeTransparency, shadeTransparency < 1f );
            final Color shadeColor = paintFocus && focused ? StyleConstants.fieldFocusColor : StyleConstants.shadeColor;
            GraphicsUtils.drawShade ( g2d, borderShape, shadeColor, shadeWidth );
            GraphicsUtils.restoreComposite ( g2d, oc, shadeTransparency < 1f );
        }
        else
        {
            // Retrieve shade 9-patch icon
            final NinePatchIcon shade = NinePatchUtils.getShadeIcon ( shadeWidth, round, shadeTransparency );

            // Calculate shade bounds and paint it
            final int x = actualPaintLeft ? 0 : -shadeWidth * 2;
            final int width = w + ( actualPaintLeft ? 0 : shadeWidth * 2 ) + ( actualPaintRight ? 0 : shadeWidth * 2 );
            final int y = paintTop ? 0 : -shadeWidth * 2;
            final int height = h + ( paintTop ? 0 : shadeWidth * 2 ) + ( paintBottom ? 0 : shadeWidth * 2 );
            shade.paintIcon ( g2d, x, y, width, height );
        }
    }

    /**
     * Paints decoration background.
     *
     * @param g2d             graphics context
     * @param bounds          painting bounds
     * @param backgroundShape component background shape
     */
    protected void paintBackground ( final Graphics2D g2d, final Rectangle bounds, final Shape backgroundShape )
    {
        if ( webColoredBackground )
        {
            // Setup cached gradient paint
            final Rectangle bgBounds = backgroundShape.getBounds ();
            g2d.setPaint ( LafUtils.getWebGradientPaint ( 0, bgBounds.y, 0, bgBounds.y + bgBounds.height ) );
        }
        else
        {
            // Setup single color paint
            g2d.setPaint ( component.getBackground () );
        }
        g2d.fill ( backgroundShape );
    }

    /**
     * Paints decoration border.
     *
     * @param g2d         graphics context
     * @param bounds      painting bounds
     * @param borderShape component border shape
     */
    protected void paintBorder ( final Graphics2D g2d, final Rectangle bounds, final Shape borderShape )
    {
        final Stroke os = GraphicsUtils.setupStroke ( g2d, borderStroke, borderStroke != null );
        g2d.setPaint ( component.isEnabled () ? borderColor : disabledBorderColor );

        // Painting smart border
        g2d.draw ( borderShape );

        // Painting enabled side lines
        if ( !paintTop && paintTopLine )
        {
            final int x = actualPaintLeft ? shadeWidth : 0;
            g2d.drawLine ( x, 0, x + component.getWidth () - ( actualPaintLeft ? shadeWidth : 0 ) -
                    ( actualPaintRight ? shadeWidth + 1 : 0 ), 0 );
        }
        if ( !paintBottom && paintBottomLine )
        {
            final int x = actualPaintLeft ? shadeWidth : 0;
            g2d.drawLine ( x, component.getHeight () - 1, x + component.getWidth () - ( actualPaintLeft ? shadeWidth : 0 ) -
                    ( actualPaintRight ? shadeWidth + 1 : 0 ), component.getHeight () - 1 );
        }
        if ( !actualPaintLeft && actualPaintLeftLine )
        {
            final int y = paintTop ? shadeWidth : 0;
            g2d.drawLine ( 0, y, 0, y + component.getHeight () - ( paintTop ? shadeWidth : 0 ) -
                    ( paintBottom ? shadeWidth + 1 : 0 ) );
        }
        if ( !actualPaintRight && actualPaintRightLine )
        {
            final int y = paintTop ? shadeWidth : 0;
            g2d.drawLine ( component.getWidth () - 1, y, component.getWidth () - 1,
                    y + component.getHeight () - ( paintTop ? shadeWidth : 0 ) - ( paintBottom ? shadeWidth + 1 : 0 ) );
        }

        GraphicsUtils.restoreStroke ( g2d, os, borderStroke != null );
    }

    /**
     * Returns decoration border shape.
     *
     * @param c          painted component
     * @param background whether should return background shape or not
     * @return decoration border shape
     */
    protected Shape getShape ( final E c, final boolean background )
    {
        return ShapeCache.getShape ( c, background ? BACKGROUND_SHAPE : BORDER_SHAPE, new DataProvider<Shape> ()
        {
            @Override
            public Shape provide ()
            {
                return createShape ( c, background );
            }
        }, getCachedShapeSettings ( c ) );
    }

    /**
     * Returns an array of shape settings cached along with the shape.
     *
     * @param c painted component
     * @return an array of shape settings cached along with the shape
     */
    @SuppressWarnings ("UnusedParameters")
    protected Object[] getCachedShapeSettings ( final E c )
    {
        return new Object[]{ w, h, ltr, round, shadeWidth, paintTop, actualPaintLeft, paintBottom, actualPaintRight, paintTopLine,
                actualPaintLeftLine, paintBottomLine, actualPaintRightLine };
    }

    /**
     * Returns decoration border shape.
     *
     * @param c          painted component
     * @param background whether should return background shape or not
     * @return decoration border shape
     */
    @SuppressWarnings ("UnusedParameters")
    protected Shape createShape ( final E c, final boolean background )
    {
        if ( background )
        {
            final Point[] corners = new Point[ 4 ];
            final boolean[] rounded = new boolean[ 4 ];

            corners[ 0 ] = p ( actualPaintLeft ? shadeWidth : 0, paintTop ? shadeWidth : 0 );
            rounded[ 0 ] = actualPaintLeft && paintTop;

            corners[ 1 ] = p ( actualPaintRight ? w - shadeWidth : w, paintTop ? shadeWidth : 0 );
            rounded[ 1 ] = actualPaintRight && paintTop;

            corners[ 2 ] = p ( actualPaintRight ? w - shadeWidth : w, paintBottom ? h - shadeWidth : h );
            rounded[ 2 ] = actualPaintRight && paintBottom;

            corners[ 3 ] = p ( actualPaintLeft ? shadeWidth : 0, paintBottom ? h - shadeWidth : h );
            rounded[ 3 ] = actualPaintLeft && paintBottom;

            return LafUtils.createRoundedShape ( round > 0 ? round + 1 : 0, corners, rounded );
        }
        else
        {
            final GeneralPath shape = new GeneralPath ( GeneralPath.WIND_EVEN_ODD );
            boolean connect = false;
            boolean moved = false;
            if ( paintTop )
            {
                shape.moveTo ( actualPaintLeft ? shadeWidth + round : 0, shadeWidth );
                if ( actualPaintRight )
                {
                    shape.lineTo ( w - shadeWidth - round - 1, shadeWidth );
                    shape.quadTo ( w - shadeWidth - 1, shadeWidth, w - shadeWidth - 1, shadeWidth + round );
                }
                else
                {
                    shape.lineTo ( w - 1, shadeWidth );
                }
                connect = true;
            }
            if ( actualPaintRight )
            {
                if ( !connect )
                {
                    shape.moveTo ( w - shadeWidth - 1, paintTop ? shadeWidth + round : 0 );
                    moved = true;
                }
                if ( paintBottom )
                {
                    shape.lineTo ( w - shadeWidth - 1, h - shadeWidth - round - 1 );
                    shape.quadTo ( w - shadeWidth - 1, h - shadeWidth - 1, w - shadeWidth - round - 1, h - shadeWidth - 1 );
                }
                else
                {
                    shape.lineTo ( w - shadeWidth - 1, h - 1 );
                }
                connect = true;
            }
            else
            {
                connect = false;
            }
            if ( paintBottom )
            {
                if ( !connect )
                {
                    shape.moveTo ( actualPaintRight ? w - shadeWidth - round - 1 : w - 1, h - shadeWidth - 1 );
                    moved = true;
                }
                if ( actualPaintLeft )
                {
                    shape.lineTo ( shadeWidth + round, h - shadeWidth - 1 );
                    shape.quadTo ( shadeWidth, h - shadeWidth - 1, shadeWidth, h - shadeWidth - round - 1 );
                }
                else
                {
                    shape.lineTo ( 0, h - shadeWidth - 1 );
                }
                connect = true;
            }
            else
            {
                connect = false;
            }
            if ( actualPaintLeft )
            {
                if ( !connect )
                {
                    shape.moveTo ( shadeWidth, paintBottom ? h - shadeWidth - round - 1 : h - 1 );
                    moved = true;
                }
                if ( paintTop )
                {
                    shape.lineTo ( shadeWidth, shadeWidth + round );
                    shape.quadTo ( shadeWidth, shadeWidth, shadeWidth + round, shadeWidth );
                    if ( !moved )
                    {
                        shape.closePath ();
                    }
                }
                else
                {
                    shape.lineTo ( shadeWidth, 0 );
                }
            }
            return shape;
        }
    }
}