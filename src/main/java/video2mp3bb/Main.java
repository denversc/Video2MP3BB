package video2mp3bb;

import net.rim.device.api.system.ApplicationDescriptor;
import net.rim.device.api.system.ApplicationManager;
import net.rim.device.api.system.ApplicationManagerException;
import net.rim.device.api.system.CodeModuleManager;
import net.rim.device.api.system.RuntimeStore;

import net.rim.blackberry.api.menuitem.ApplicationMenuItem;
import net.rim.blackberry.api.menuitem.ApplicationMenuItemRepository;

public class Main implements Runnable {

    // video2mp3bb.Main.GUID_TRANSIENT_DATA
    private static final long GUID_TRANSIENT_DATA = 0x95cf4ea7474b6aa7L;

    // video2mp3bb.Main.GUID_SET_URL
    public static final long GUID_SET_URL = 0x7d88cfb7d2e045bcL;

    private final String[] _args;

    public Main(String[] args) {
        this._args = Util.copy(args);
    }

    public void run() {
        if (this._args != null && this._args.length == 1 && "init".equals(this._args[0])) {
            this.runInit();
        } else {
            this.runNormal();
        }
    }

    public void runInit() {
        ApplicationDescriptor descriptor;
        try {
            descriptor = getDescriptor();
        } catch (final Throwable e) {
            descriptor = null;
        }

        final Video2MP3MenuItem menuItem = new Video2MP3MenuItem();
        final ApplicationMenuItemRepository amir = ApplicationMenuItemRepository.getInstance();
        amir.addMenuItem(ApplicationMenuItemRepository.MENUITEM_BROWSER, menuItem, descriptor);
        addCleanup(new RemoveApplicationMenuItemRunnable(
            ApplicationMenuItemRepository.MENUITEM_BROWSER, menuItem));
    }

    public void runNormal() {
        final Video2MP3BBApplication app = new Video2MP3BBApplication();
        final TransientData data = getTransientData();
        data.setApplication(app);
        app.enterEventDispatcher();
    }

    public static void addCleanup(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("runnable==null");
        }
        final TransientData data = getTransientData();
        data.addCleanup(runnable);
    }

    public static ApplicationDescriptor getDescriptor() throws ApplicationManagerException {
        final int handle = CodeModuleManager.getModuleHandleForClass(Main.class);
        String moduleName;
        try {
            moduleName = CodeModuleManager.getModuleName(handle);
        } catch (final IllegalArgumentException e) {
            throw new ApplicationManagerException("module " + handle + " not found");
        }
        if (moduleName == null) {
            moduleName = Integer.toString(handle);
        }

        ApplicationDescriptor[] descriptors;
        try {
            descriptors = CodeModuleManager.getApplicationDescriptors(handle);
        } catch (final IllegalArgumentException e) {
            descriptors = null;
        }

        ApplicationDescriptor descriptor = null;
        if (descriptors != null) {
            for (int i = 0; i < descriptors.length; i++) {
                final ApplicationDescriptor curDescriptor = descriptors[i];
                if (curDescriptor != null) {
                    final String[] args = curDescriptor.getArgs();
                    if (args == null || args.length == 0) {
                        descriptor = curDescriptor;
                        break;
                    }
                }
            }
        }

        if (descriptor == null) {
            throw new ApplicationManagerException("descriptor not found in module " + moduleName);
        }

        return descriptor;
    }

    public static TransientData getTransientData() {
        return getTransientData(true);
    }

    public static TransientData getTransientData(boolean createIfNotExists) {
        final RuntimeStore runtimeStore = RuntimeStore.getRuntimeStore();
        final Object instance = runtimeStore.get(GUID_TRANSIENT_DATA);
        if (instance instanceof TransientData) {
            return (TransientData) instance;
        } else if (!createIfNotExists) {
            return null;
        }

        final TransientData data = new TransientData();
        runtimeStore.put(GUID_TRANSIENT_DATA, data);
        data.addCleanup(new RemoveTransientDataFromRuntimeStoreRunnable());
        return data;
    }

    public static boolean launch() throws ApplicationManagerException {
        final ApplicationDescriptor descriptor = getDescriptor();
        final ApplicationManager am = ApplicationManager.getApplicationManager();
        final int pid = am.getProcessId(descriptor);
        am.runApplication(descriptor);

        return (pid != -1);
    }

    public static void main(String[] args) {
        new Main(args).run();
    }

    public static void runCleanups() {
        final TransientData data = getTransientData(false);
        if (data == null) {
            return;
        }

        final Runnable[] cleanups = data.getCleanups(true);
        final int cleanupsLen = (cleanups == null) ? 0 : cleanups.length;
        for (int i = cleanupsLen - 1; i >= 0; i--) {
            final Runnable runnable = cleanups[i];
            if (runnable != null) {
                try {
                    runnable.run();
                } catch (final Throwable e) {
                    // ignore
                }
            }
        }
    }

    private static class RemoveApplicationMenuItemRunnable implements Runnable {

        private final long _id;
        private final ApplicationMenuItem _menuItem;

        public RemoveApplicationMenuItemRunnable(long id, ApplicationMenuItem menuItem) {
            this._id = id;
            this._menuItem = menuItem;
        }

        public void run() {
            final ApplicationMenuItemRepository amir = ApplicationMenuItemRepository.getInstance();
            amir.removeMenuItem(this._id, this._menuItem);
        }
    }

    private static class RemoveTransientDataFromRuntimeStoreRunnable implements Runnable {
        public void run() {
            final RuntimeStore runtimeStore = RuntimeStore.getRuntimeStore();
            runtimeStore.remove(GUID_TRANSIENT_DATA);
        }
    }
}
