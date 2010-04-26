package org.opennms.features.poller.remote.gwt.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.opennms.features.poller.remote.gwt.client.FilterPanel.Filters;
import org.opennms.features.poller.remote.gwt.client.FilterPanel.FiltersChangedEvent;
import org.opennms.features.poller.remote.gwt.client.FilterPanel.StatusSelectionChangedEvent;
import org.opennms.features.poller.remote.gwt.client.InitializationCommand.DataLoader;
import org.opennms.features.poller.remote.gwt.client.TagPanel.TagClearedEvent;
import org.opennms.features.poller.remote.gwt.client.TagPanel.TagSelectedEvent;
import org.opennms.features.poller.remote.gwt.client.events.ApplicationDeselectedEvent;
import org.opennms.features.poller.remote.gwt.client.events.ApplicationSelectedEvent;
import org.opennms.features.poller.remote.gwt.client.events.GWTMarkerClickedEvent;
import org.opennms.features.poller.remote.gwt.client.events.LocationManagerInitializationCompleteEvent;
import org.opennms.features.poller.remote.gwt.client.events.LocationManagerInitializationCompleteEventHander;
import org.opennms.features.poller.remote.gwt.client.events.LocationPanelSelectEvent;
import org.opennms.features.poller.remote.gwt.client.events.LocationsUpdatedEvent;
import org.opennms.features.poller.remote.gwt.client.events.MapPanelBoundsChangedEvent;
import org.opennms.features.poller.remote.gwt.client.location.LocationInfo;
import org.opennms.features.poller.remote.gwt.client.remoteevents.ApplicationUpdatedRemoteEvent;
import org.opennms.features.poller.remote.gwt.client.remoteevents.LocationUpdatedRemoteEvent;
import org.opennms.features.poller.remote.gwt.client.remoteevents.LocationsUpdatedRemoteEvent;
import org.opennms.features.poller.remote.gwt.client.remoteevents.UpdateCompleteRemoteEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.IncrementalCommand;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SplitLayoutPanel;

/**
 * <p>This class implements both {@link LocationManager} (the model portion of the webapp) and
 * {@link RemotePollerPresenter} (the controller portion of the webapp code). It is responsible
 * for maintaining the knowledgebase of {@link Location} objects and responding to events triggered when:</p>
 * <ul>
 * <li>{@link Location} instances are added or updated</li>
 * <li>the UI elements are clicked on by the user</li>
 * </ul>
 * 
 * <p>If this class ever grows too large, we can split it into separate model and controller classes.</p>
 */
public class DefaultLocationManager implements LocationManager, RemotePollerPresenter {

	protected final HandlerManager m_eventBus;

	private final HandlerManager m_handlerManager = new HandlerManager(this);

	private final LocationStatusServiceAsync m_remoteService = GWT.create(LocationStatusService.class);
	
	private final Map<String,LocationInfo> m_locations = new HashMap<String,LocationInfo>();
	private final Set<ApplicationInfo> m_applications = new TreeSet<ApplicationInfo>();

	private String m_selectedTag = null;
	
	private final Set<Status> m_selectedStatuses = new HashSet<Status>();
	
	private final MapPanel m_mapPanel;

	private LocationPanel m_locationPanel;

	private final SplitLayoutPanel m_panel;

	private boolean updated = false;

	private Set<ApplicationInfo> m_selectedApplications = new TreeSet<ApplicationInfo>();

	public DefaultLocationManager(final HandlerManager eventBus, final SplitLayoutPanel panel, final LocationPanel locationPanel, MapPanel mapPanel) {
		m_eventBus = eventBus;
		m_panel = panel;
		m_locationPanel = locationPanel;
		m_mapPanel = mapPanel;

		// Register for all relevant events thrown by the UI components
		m_eventBus.addHandler(LocationPanelSelectEvent.TYPE, this); 
		m_eventBus.addHandler(LocationsUpdatedEvent.TYPE, this); 
		m_eventBus.addHandler(MapPanelBoundsChangedEvent.TYPE, this); 
		m_eventBus.addHandler(FiltersChangedEvent.TYPE, this); 
		m_eventBus.addHandler(TagSelectedEvent.TYPE, this);
		m_eventBus.addHandler(TagClearedEvent.TYPE, this);
		m_eventBus.addHandler(StatusSelectionChangedEvent.TYPE, this);
		m_eventBus.addHandler(ApplicationDeselectedEvent.TYPE, this);
		m_eventBus.addHandler(ApplicationSelectedEvent.TYPE, this);
		m_eventBus.addHandler(GWTMarkerClickedEvent.TYPE, this);

		// by default, we select all statuses until the UI says otherwise
		for (final Status s : Status.values()) {
			m_selectedStatuses.add(s);
		}

		// Add some test data
		m_applications.addAll(getApplicationInfoTestData());
		ArrayList<ApplicationInfo> applicationList = new ArrayList<ApplicationInfo>();
		applicationList.addAll(m_applications);
		m_locationPanel.updateApplicationList(applicationList);
	}

    public void initialize() {
        DeferredCommand.addCommand(new InitializationCommand(this, createFinisher(), createDataLoaders()));
    }

    private Runnable createFinisher() {
        return new Runnable() {
            public void run() {
                initializationComplete();
            }
        };
    }

    protected DataLoader[] createDataLoaders() {
        return new DataLoader[] {
            new DataLoader() {
                @Override
                public void onLoaded() {
                    // Append the map panel to the main SplitPanel
                    getPanel().add(m_mapPanel.getWidget());
                }
            },
             new EventServiceInitializer(this)
        };
    }

    protected void initializationComplete() {
        m_handlerManager.fireEvent(new LocationManagerInitializationCompleteEvent());
    }
    
    protected LocationStatusServiceAsync getRemoteService() {
        return m_remoteService;
    }
    
    public void addLocationManagerInitializationCompleteEventHandler(LocationManagerInitializationCompleteEventHander handler) {
        m_handlerManager.addHandler(LocationManagerInitializationCompleteEvent.TYPE, handler);
    };
    
    protected void displayDialog(final String title, final String contents) {
    	final DialogBox db = new DialogBox();
    	db.setAutoHideEnabled(true);
    	db.setModal(true);
    	db.setText(title);
    	db.setWidget(new Label(contents, true));
    	db.show();
    }

    protected SplitLayoutPanel getPanel() {
        return m_panel;
    }

    public Set<String> getAllLocationNames() {
        return new TreeSet<String>(m_locations.keySet());
    }

    protected GWTBounds getLocationBounds() {
        BoundsBuilder bldr = new BoundsBuilder();
        for (LocationInfo l : m_locations.values()) {
            bldr.extend(l.getLatLng());
        }
        return bldr.getBounds();
    }

    public void createOrUpdateLocation(final LocationInfo info) {
    	m_locations.put(info.getName(), info);
        m_locationPanel.updateApplicationNames(getAllApplicationNames());
        if (info.getMarker() == null) {
        	info.setMarker(getMarkerForLocation(info));
        }
    }

    public void createOrUpdateApplication(ApplicationInfo info) {
    	info.setPriority(0L);
    	for (final String location : info.getLocations()) {
    		final LocationInfo locationInfo = m_locations.get(location);
    		if (locationInfo != null) {
    			info.setPriority(info.getPriority() + locationInfo.getPriority());
    		}
    	}
        m_applications.add(info);
        m_locationPanel.updateApplicationNames(getAllApplicationNames());
    }

    public void reportError(final String errorMessage, final Throwable throwable) {
    	// FIXME: implement error reporting in UI
    }

    public void fitMapToLocations() {
    	m_mapPanel.setBounds(getLocationBounds());
    }

    /**
     * TODO: Figure out if this public function is necessary or if we can get by just responding to
     * incoming events.
     */
    public ArrayList<LocationInfo> getVisibleLocations() {
        // Use an ArrayList so that it has good random-access efficiency
        // since the pageable lists use get() to fetch based on index.
    	final ArrayList<LocationInfo> visibleLocations = new ArrayList<LocationInfo>();
        for(LocationInfo location : m_locations.values()) {
        	final GWTMarkerState markerState = location.getMarker();
        	if (markerState.isSelected() && markerState.isVisible()) {
        		visibleLocations.add(location);
        	}
        }

        // TODO: this should use the current filter set eventually, for now sort by priority, then name
        // for now, LocationInfo is Comparable and has a natural sort ordering based on status, priority, and name
        Collections.sort(visibleLocations, new Comparator<LocationInfo>() {
			public int compare(LocationInfo o1, LocationInfo o2) {
				return o1.compareTo(o2);
			}
        });

        return visibleLocations;
    }

    private void updateAllMarkerStates() {
    	for (final LocationInfo location : m_locations.values()) {
        	final GWTMarkerState markerState = location.getMarker();

        	// if it's within the map bounds, it's visible
        	markerState.setVisible(location.isVisible(m_mapPanel.getBounds()));
        	if (markerState.isVisible()) {
        		// unless it's not in the list of selected statuses
    			markerState.setVisible(m_selectedStatuses.contains(location.getStatus()));
        	}

        	if (m_selectedTag == null) {
        		markerState.setSelected(true);
        	} else {
        		markerState.setSelected(location.getTags() != null && location.getTags().contains(m_selectedTag));
        	}
        	m_mapPanel.placeMarker(markerState);
    	}
    }

    public List<String> getAllTags() {
    	final List<String> retval = new ArrayList<String>();
    	for (final LocationInfo location : m_locations.values()) {
    		retval.addAll(location.getTags());
    	}
    	return retval;
    }

    public List<String> getTagsOnVisibleLocations() {
        List<String> retval = new ArrayList<String>();
        for (LocationInfo location : getVisibleLocations()) {
            retval.addAll(location.getTags());
        }
        return retval;
    }

    /**
     * Handler triggered when a user clicks on a specific location record.
     */
    public void onLocationSelected(final LocationPanelSelectEvent event) {
        showLocationDetails(event.getLocationName());
    }

    private void showLocationDetails(String locationName) {
    	// TODO: this needs a callback to get the location details, and fill in the content
    	final LocationInfo loc = m_locations.get(locationName);
        m_mapPanel.showLocationDetails(locationName, locationName + "(" + loc.getArea() + ")", "Need to implement content");
    }

    /**
     * Refresh the list of locations whenever the map panel boundaries change.
     */
    public void onBoundsChanged(final MapPanelBoundsChangedEvent e) {
    	// make sure each location's marker is up-to-date
    	updateAllMarkerStates();

        // Update the contents of the tag panel
        m_locationPanel.clearTagPanel();
        m_locationPanel.addAllTags(getAllTags());

        // Update the list of objects in the LHN
        m_locationPanel.updateLocationList(getVisibleLocations());

        // TODO: Update the application list based on map boundries?? 
        // TODO: Update the list of selectable applications based on the visible locations??
    }

    /**
     * Refresh the list of locations whenever they are updated.
     */
    public void onLocationsUpdated(final LocationsUpdatedEvent e) {
    	// make sure each location's marker is up-to-date
    	updateAllMarkerStates();

        // Update the contents of the tag panel
        m_locationPanel.clearTagPanel();
        m_locationPanel.addAllTags(getAllTags());

        m_locationPanel.updateApplicationNames(getAllApplicationNames());
        m_locationPanel.updateLocationList(getVisibleLocations());
    }

    /**
     * Invoked by the {@link LocationUpdatedRemoteEvent} and {@link LocationsUpdatedRemoteEvent}
     * events.
     */
    public void updateLocation(final LocationInfo info) {
        if (info == null) return;

        // Update the location information in the model
		createOrUpdateLocation(info);

        // Update the icon/caption in the LHN
        m_locationPanel.updateLocationList(getVisibleLocations());

        // Update the icon in the map
        GWTMarkerState m = getMarkerForLocation(info);
        m_mapPanel.placeMarker(m);
    }

    /**
     * Invoked by the {@link ApplicationUpdatedRemoteEvent} and {@link ApplicationsUpdatedRemoteEvent}
     * events.
     */
    public void updateApplication(final ApplicationInfo info) {
        if (info == null) return;

        // Update the location information in the model
        createOrUpdateApplication(info);

        // Update the icon/caption in the LHN
        // Use an ArrayList so that it has good random-access efficiency
        // since the pageable lists use get() to fetch based on index.
        ArrayList<ApplicationInfo> applicationList = new ArrayList<ApplicationInfo>();
        applicationList.addAll(m_applications);
        m_locationPanel.updateApplicationList(applicationList);

        // TODO: Update the icon in the map
        // Pseudocode:
        // for (Location location : info.getAllLocations()) {
        //    GWTMarkerState m = new GWTMarkerState(location);
        //    getMapPanel().placeMarker(m);
        // }
    }

    /**
     * Invoked by the {@link UpdateCompleteRemoteEvent} event.
     */
    public void updateComplete() {
    	if (!updated) {
    		DeferredCommand.addPause();
    		DeferredCommand.addCommand(new IncrementalCommand() {
    			public boolean execute() {
    				fitMapToLocations();
    				updated = true;
    				return false;
    			}
    		});
    	}
    }

    public void onFiltersChanged(Filters filters) {
        // TODO: Update state inside of this object to track the filter state (if necessary)
        // TODO: Update markers on the map panel
        // TODO: Update the list of objects in the LHN
    }

    public void onTagSelected(String tagName) {
        // Update state inside of this object to track the selected tag
        m_selectedTag = tagName;
    	// make sure each location's marker is up-to-date
    	updateAllMarkerStates();

        m_locationPanel.updateLocationList(getVisibleLocations());
    }

    public void onTagCleared() {
        // Update state inside of this object to track the selected tag
        m_selectedTag = null;

    	// make sure each location's marker is up-to-date
    	updateAllMarkerStates();

        // TODO: Update markers on the map panel
        // Update the list of objects in the LHN
        m_locationPanel.updateLocationList(getVisibleLocations());
    }

    /**
     * Fetch a list of all application names.
     */
    public Set<String> getAllApplicationNames() {
        Set<String> retval = new TreeSet<String>();
        for (ApplicationInfo application : m_applications) {
            retval.add(application.getName());
        }
        return retval;
    }

    public ApplicationInfo getApplicationInfo(String name) {
        if (name == null) {
            return null;
        }

        for (ApplicationInfo app : m_applications) {
            if (name.equals(app.getName())) {
                return app;
            }
        }
        return null;
    }

    public LocationInfo getLocation(String locationName) {
        return m_locations.get(locationName);
    }

    /**
     * Remove this function once we have real data.
     */
    @Deprecated
    private static List<ApplicationInfo> getApplicationInfoTestData() {
        List<ApplicationInfo> apps = new ArrayList<ApplicationInfo>();

        for(int i = 0; i < 10 ; i++) {
            ApplicationInfo application = new ApplicationInfo();
            application.setId(i);
            application.setName("name: " + i);
            application.setStatus(Status.UP);
            application.setLocations(getLocationSetTestData());
            application.setServices(getGWTMonitoredServiceTestData());
            apps.add(application);
        }

        return apps;
    }

    /**
     * Remove this function once we have real data.
     */
    @Deprecated
    private static Set<GWTMonitoredService> getGWTMonitoredServiceTestData() {
        Set<GWTMonitoredService> services = new HashSet<GWTMonitoredService>();
        GWTMonitoredService service = new GWTMonitoredService();
        service.setServiceName("HTTP");
        services.add(service);
        return services;
    }

    /**
     * Remove this function once we have real data.
     */
    @Deprecated
    private static Set<String> getLocationSetTestData() {
        Set<String> locations = new HashSet<String>();
        locations.add("19");
        return locations;
    }

    public void onGWTMarkerClicked(GWTMarkerClickedEvent event) {
        GWTMarkerState marker = event.getMarker();
        showLocationDetails(marker.getName());
    }

    public void onStatusSelectionChanged(Status status, boolean selected) {
        if (selected) {
            m_selectedStatuses.add(status);
        } else {
            m_selectedStatuses.remove(status);
        }

        updateMapMarkers();
    }

    private GWTMarkerState getMarkerForLocation(final String locationName) {
    	final LocationInfo location = m_locations.get(locationName);
    	if (location != null) {
        	return getMarkerForLocation(location);
    	}
    	return null;
    }

    private GWTMarkerState getMarkerForLocation(final LocationInfo location) {
    	if (location == null) {
    		return null;
    	}
    	GWTMarkerState state = location.getMarker();
    	if (state == null) {
    		state = new GWTMarkerState(location.getName(), location.getLatLng(), location.getStatus());
    		location.setMarker(state);
    	}
    	state.setVisible(m_selectedStatuses.contains(location.getStatus()));
    	return state;
	}

    private void updateMapMarkers() {
    	for (final String locationName : m_locations.keySet()) {
    		final GWTMarkerState state = getMarkerForLocation(locationName);
    		m_mapPanel.placeMarker(state);
    	}
    }

    public void onApplicationSelected(ApplicationSelectedEvent event) {
        // Add the application to the selected application list
        m_selectedApplications.add(event.getAppInfo());
        // Update the list of selected applications in the panel
        m_locationPanel.updateSelectedApplications(m_selectedApplications);
    }

    public void onApplicationDeselected(ApplicationDeselectedEvent event) {
        // Remove the application from the selected application list
        m_selectedApplications.remove(event.getAppInfo());
        // Update the list of selected applications in the panel
        m_locationPanel.updateSelectedApplications(m_selectedApplications);
    }
}
