package umu.tds.apps.vistas;
import javax.swing.*;

import umu.tds.apps.AppChat.Contact;
import umu.tds.apps.AppChat.UserStatu;

import static umu.tds.apps.vistas.Theme.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

final public class ContactsPanelList {

    public enum FPLOrientation {
        HORIZONTAL(Adjustable.HORIZONTAL),
        VERTICAL(Adjustable.VERTICAL);


        final public int orientationAsConstant;


        FPLOrientation(final int orientationAsConstant) {


            this.orientationAsConstant = orientationAsConstant;
        }


    }
    
 // Cremos el panel correspondiente a un elemento de la lista.
    private static JPanel supplyPanel(Contact contacto) {
    	// Componentes del panel.
    	final JLabel icon = new JLabel("");
    	icon.setIcon(contacto.getIcon());
    	final JLabel labelUserStatus = new JLabel(contacto.getName() + ": " + contacto.getlastMessage());
    	labelUserStatus.setForeground(TEXT_COLOR_LIGHT);
        final JLabel labelDate = new JLabel(contacto.getDate());
        labelDate.setForeground(TEXT_COLOR_LIGHT);
        
        labelUserStatus.setHorizontalAlignment(SwingConstants.CENTER);
        labelUserStatus.setVerticalAlignment(SwingConstants.CENTER);

        // Creamos el panel
        final JPanel panel = new JPanel(new BorderLayout(0,
                                                         0));
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        panel.setBackground(MAIN_COLOR_LIGHT);
        
        panel.add(icon, BorderLayout.WEST);
        panel.add(labelUserStatus, BorderLayout.CENTER);
        panel.add(labelDate, BorderLayout.AFTER_LINE_ENDS);

        // Devolvemos un elemento de la lista.
        return panel;
    }

    // Properties.
    final public FPLOrientation orientation;
    final private double fractionOfExtentToScrollPerArrowClick;
    final private double fractionOfExtentToScrollPerTrackClick;
    final private double fractionOfExtentToScrollPerMouseWheelStep;
    final private boolean hideScrollbarWhenUnnecessary;

    final private JScrollBar scrollBar;
    final private int scrollBarWidth; // The default width it normally has in any GUI.
    final public JPanel container; // The container of it all.

    private int panelSize = 0;  // The horizontal or vertical extent of each contained panel.
    private int panelCount = 0; // The amount of panels, indeed max Integer.MAX_VALUE.

    private long contentSize = 0; // The sum total extent of all "contained panels". (They're not really contained, but nobody will see that.)
    private long actualScrollPosition = 0; // The true scroll position, think contentSize.
    private Dimension lastKnownContainerSize = new Dimension(0, 0);
    
	private List<Contact> contactos; // Lista de contactos.


    private Map<Integer, JPanel> knownPanels = new HashMap<>(); // All panels of which some pixels are currently potentially visible are cached here.

    // Constructor.
    public ContactsPanelList(final int panelSize, List<Contact> contactos, final int panelCount) {
    	// Users to show.
    	this.contactos = contactos;
    	// Element panel
    	// orientation Whether horizontal or the more common vertical arrangement.
        this.orientation = ContactsPanelList.FPLOrientation.VERTICAL;
        // fractionOfExtentToScrollPerArrowClick E.g. 0.1 for 10% of the visible area to become hidden/shown when you click a scrollbar arrow.
        this.fractionOfExtentToScrollPerArrowClick = Math.max(0, 0.1);
        // fractionOfExtentToScrollPerTrackClick E.g. 0.95 for 95% of the visible area to become hidden/shown when you click in the scrollbar track.
        this.fractionOfExtentToScrollPerTrackClick = Math.max(0, 0.95);
        this.fractionOfExtentToScrollPerMouseWheelStep = Math.max(0, 0.91);
        this.hideScrollbarWhenUnnecessary = false;
        setPanelSize(panelSize);
        setPanelCount(panelCount);

        scrollBarWidth = determineScrollBarDefaultWidth();
        scrollBar = new JScrollBar(orientation.orientationAsConstant, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        scrollBar.addAdjustmentListener(e -> update());

        container = new JPanel(null); // NULL: We want to layout everything manually.
        //        container.add(scrollBar);
        container.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(final ComponentEvent e) {

                update();
            }
        });

        container.addMouseWheelListener(this::mouseWheelEvent);

    }


    public void mouseWheelEvent(final MouseWheelEvent e) {

        final int rotation = e.getWheelRotation();
        final int extent = scrollBar.getModel().getExtent();
        final int increment = (int) Math.max(1, Math.min(extent,
                                                         extent * fractionOfExtentToScrollPerMouseWheelStep));

        scrollBar.setValue(scrollBar.getValue() + (rotation * increment));
    }


    private int determineScrollBarDefaultWidth() { // Called only ONE time.

        final JScrollPane dummyForDefaultSize = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                                                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        dummyForDefaultSize.setPreferredSize(new Dimension(1000, 1000));
        dummyForDefaultSize.setSize(dummyForDefaultSize.getPreferredSize());
        dummyForDefaultSize.doLayout();
        return dummyForDefaultSize.getVerticalScrollBar().getSize().width;
    }


    /**
     * FastPanelList requires each item to have the exact same size. This is where you define it (if you reconsidered
     * after your constructor call).
     *
     * @param panelSize Will become >=1
     */
    public void setPanelSize(final int panelSize) {

        this.panelSize = Math.max(1, panelSize);
    }


    /**
     * FastPanelList easily manages Integer.MAX_VALUE (about 2 billion) panels with no memory or performance problems.
     * You define the amount here. You don't add/remove panels in this thing: Instead, you will be asked to provide
     * panels as required depending on screen layout etc.
     *
     * @param panelCount Will become >=0
     */
    public void setPanelCount(final int panelCount) {

        this.panelCount = Math.max(0, panelCount);
    }


    /**
     * Clears the internal JPanel cache. Necessary if you want to repopulate the list. Setting the panel count and
     * calling update() is not sufficient. (Call update AFTER this method.)
     */
    public void clear() {

        knownPanels.clear();
    }


    public JPanel getItemUnderMouse(final MouseEvent e) {

        return getItemUnderMouse(e.getX(), e.getY());
    }


    public JPanel getItemUnderMouse(final int xInComponent,
                               final int yInComponent) {

        final long realPositionUnderMouse = (actualScrollPosition + (orientation == FPLOrientation.HORIZONTAL ? (long) xInComponent : (long) yInComponent));

        final int indexUnderMouse = (int) (realPositionUnderMouse / panelSize);
        return knownPanels.get(indexUnderMouse);
    }


    /**
     * This method is very lenient.
     *
     * @param index                   Anything.
     * @param callSupplierIfNotCached Depends on what you're trying to achieve. E.g. use FALSE if you want to set the
     *                                background color of one of the visible JPanels. The method would return null if
     *                                the panel is not visible, because then it is also no longer cached.
     * @return NULL if index is NULL, or is less than 0, or is equal to or greater than panelCount. Else the cached
     * JPanel (or whatever it secretly is via "extends"), meaning one of the panels that are currently visible or at the
     * edge of visibility. In all other cases, either null will be returned - or your supplier will be called, so you
     * get your own JPanel spat right back at you.
     */
    public JPanel getItem(final Integer index,
                     final boolean callSupplierIfNotCached) {

        JPanel ret = null;
        if (index != null && index >= 0 && index < panelCount) {
            ret = knownPanels.get(index);
            if (ret == null && callSupplierIfNotCached) {
                ret = supplyPanel(contactos.get(index));
                if (ret == null) {
                    throw new IllegalArgumentException("panelSupplier returned null for index " + index);
                }
            }
        }
        return ret;
    }


	/**
     * @return a NEW Map containing the Map entries of the internal knownPanels map. These maps contain all panels that
     * are currently visible on screen. The index is identical to the number handed to your Supplier.
     * <p>
     * The purpose of that internal map is to not request EVERY panel anew every time, but only the panels that are
     * scrolled in at the edge of the screen. Obviously, this is also very useful to you, because you can call this
     * method to get all panels to change their look, data, whatever. And ONLY THOSE panels need to be changed. All
     * others ... don't exist. They only exist in the fantasy of the user. Until they scroll there, then some have
     * become real while others have fallen out of existence.
     */
    public Map<Integer, JPanel> getCachedItems() {

        return new HashMap<>(knownPanels);
    }


    /**
     * This layouts the component. This is done automatically when the scrollbar is moved or the container is resized,
     * but any other action would require YOU to call this.
     */
    public void update() {

        container.removeAll();

        lastKnownContainerSize = container.getSize();

        final int containerSize;
        if (orientation == FPLOrientation.HORIZONTAL) {
            scrollBar.setLocation(0, lastKnownContainerSize.height - scrollBarWidth);
            scrollBar.setSize(lastKnownContainerSize.width, scrollBarWidth);
            containerSize = lastKnownContainerSize.width;
        } else {
            scrollBar.setLocation(lastKnownContainerSize.width - scrollBarWidth, 0);
            scrollBar.setSize(scrollBarWidth, lastKnownContainerSize.height);
            containerSize = lastKnownContainerSize.height;
        }


        contentSize = (long) panelCount * (long) panelSize;
        final long invisibleStuff = contentSize - containerSize;
        actualScrollPosition = Math.max(0, Math.min(invisibleStuff,
                                                    (long) (getScrollBarPosRatio() * (invisibleStuff))
        ));


        final int extent;
        if (contentSize > 0) {
            final double visibleRatio = containerSize / (double) contentSize;
            extent = (int) Math.max(0, Math.min(Integer.MAX_VALUE, Integer.MAX_VALUE * visibleRatio));
        } else {
            extent = Integer.MAX_VALUE;
        }
        final int unitIncrement = (int) Math.max(1, Math.min(extent,
                                                             extent * fractionOfExtentToScrollPerArrowClick));
        final int blockIncrement = (int) Math.max(1, Math.min(extent,
                                                              extent * fractionOfExtentToScrollPerTrackClick));
        scrollBar.getModel().setExtent(extent);
        scrollBar.setUnitIncrement(unitIncrement);
        scrollBar.setBlockIncrement(blockIncrement);
        scrollBar.setVisible(!hideScrollbarWhenUnnecessary || extent < Integer.MAX_VALUE);

        final Dimension panelSizes = getPanelSize();

        long n = actualScrollPosition;
        final long endOfScreen = actualScrollPosition + containerSize + panelSize;
        final Map<Integer, JPanel> newKnownPanels = new HashMap<>();

        while (n < endOfScreen) { // Loop ongoing = need more panels to fill the view.

            // Calc index of current panel.
            final long panelIndex = n / panelSize;
            if (panelIndex > Integer.MAX_VALUE) {
                throw new Error();
            } else if (panelIndex >= panelCount) {
                break;
            }
            final int panelIndexInt = (int) panelIndex;


            // Obtain current panel - if possible from cache, else from external provider (which might likely create it from scratch).
            JPanel panel = knownPanels.get(panelIndexInt);
            if (panel == null) {
                panel = supplyPanel(contactos.get(panelIndexInt));
                if (panel == null) {
                    throw new IllegalArgumentException("panelSupplier returned null for index " + panelIndex);
                }
            }
            newKnownPanels.put(panelIndexInt, panel);


            // Set position and size.
            final int panelPos = (int) ((panelIndex * panelSize) - actualScrollPosition);
            final Point location;
            if (orientation == FPLOrientation.HORIZONTAL) {
                location = new Point(panelPos, 0);
            } else {
                location = new Point(0, panelPos);
            }
            panel.setLocation(location);
            panel.setSize(panelSizes);


            n += panelSize;
        }
        knownPanels = newKnownPanels; // Will now contain all panels needed for display. All panels that were in the map, but are no longer needed, are now gone forever.


        // Layout.
        container.add(scrollBar);
        for (JPanel panel : newKnownPanels.values()) {
            container.add(panel);
            panel.revalidate();
        }


        container.repaint(); // required
    }


    /**
     * @return the correct width&height a contained JPanel needs to have. Is applied by update() automatically.
     */
    public Dimension getPanelSize() {

        if (orientation == FPLOrientation.HORIZONTAL) {
            return new Dimension(panelSize,
                                 lastKnownContainerSize.height - (scrollBar.isVisible() ? scrollBarWidth : 0));
        } else {
            return new Dimension(lastKnownContainerSize.width - (scrollBar.isVisible() ? scrollBarWidth : 0),
                                 panelSize);
        }
    }


    /**
     * @return 0 to 1, expressing position of scroll bar handle.
     */
    public double getScrollBarPosRatio() {

        final int scrollRangeSize = Integer.MAX_VALUE - scrollBar.getVisibleAmount(); // Which should really be named getExtent(). Or rather the other way round.
        return scrollBar.getValue() / (double) scrollRangeSize;
    }


}