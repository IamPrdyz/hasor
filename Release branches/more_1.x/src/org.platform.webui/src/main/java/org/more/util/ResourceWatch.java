package org.more.util;
import java.io.IOException;
import java.net.URI;
import org.platform.Platform;
/**
 * 当资源有改动时该类会调用Run方法。
 * @version : 2012-8-2
 * @author 赵永春 (zyc@byshell.org)
 */
public abstract class ResourceWatch extends Thread {
    private URI  resourceURI   = null; //资源地址
    //
    private long lastHashCode  = 0;   //上一次检查资源的校验码，两次检查校验码不一致将执行reload
    private long checkSeepTime = 0;   //检查的时间间隔-15秒
    //
    public ResourceWatch() {
        this(null, 15 * 1000);
    }
    public ResourceWatch(URI resourceURI) {
        this(resourceURI, 15 * 1000);
    }
    public ResourceWatch(URI resourceURI, long checkSeepTime) {
        this.resourceURI = resourceURI;
        this.checkSeepTime = checkSeepTime;
    }
    /**首次装载调用。*/
    public abstract void firstLoad(URI resourceURI) throws IOException;
    /**当遇到资源改变之后调用。*/
    public abstract void reload(URI resourceURI) throws IOException;
    /**检查资源是否修改，并且返回修改的时间戳。当两次检查不一致时会调用{@link #reload(URI)}方法。*/
    public abstract long lastModify(URI resourceURI) throws IOException;
    /**首次启动会先执行load然后在启动线程*/
    @Override
    public synchronized void start() {
        try {
            this.firstLoad(this.resourceURI);
            this.lastHashCode = this.lastModify(this.resourceURI);
        } catch (Exception e) {
            Platform.warning("%s lastModify error.", this.resourceURI);
        }
        super.start();
    }
    @Override
    public final void run() {
        if (this.resourceURI == null)
            return;
        String schema = this.resourceURI.getScheme();
        schema = (schema == null) ? "" : schema;
        schema = schema.toLowerCase();
        while (true) {
            try {
                long lastHashCode = this.lastModify(this.resourceURI);
                if (this.lastHashCode != lastHashCode) {
                    try {
                        this.reload(this.resourceURI);
                        this.lastHashCode = lastHashCode;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sleep(this.checkSeepTime);
            } catch (Exception e) {}
        }
    }
    public URI getResourceURI() {
        return resourceURI;
    }
    public void setResourceURI(URI resourceURI) {
        this.resourceURI = resourceURI;
    }
    public long getCheckSeepTime() {
        return checkSeepTime;
    }
    public void setCheckSeepTime(long checkSeepTime) {
        this.checkSeepTime = checkSeepTime;
    }
}