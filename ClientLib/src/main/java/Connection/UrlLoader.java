package Connection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.net.HttpStatus;
import java.util.ArrayList;
import java.util.HashMap;
import Manager.Control;

public class UrlLoader {

    public ArrayList<UrlTask> urlAgenda = new ArrayList<>();
    public HashMap<String, byte[]> output = new HashMap<>();
    public URLINTERFACE runnable;

    public UrlLoader(URLINTERFACE onCompletion, ArrayList<String> links) {
        this.runnable = onCompletion;
        for (String string : links) {
            urlAgenda.add(new UrlTask(string, this));
        }
    }

    public void newContent(UrlTask urlTask, byte[] file) {
        urlAgenda.remove(urlTask);
        output.put(urlTask.link, file);
        if (urlAgenda.size() == 0) {
            runnable.run(output);
        }
    }

}

class UrlTask {

    public String link;
    public long initializedAt;
    public UrlLoader urlLoader;
    public boolean completed;

    public UrlTask(String link, UrlLoader urlLoader) {
        initializedAt = System.currentTimeMillis();
        this.link = link;
        this.urlLoader = urlLoader;
        load();
    }

    public void load() {
        if (Control.isConnected()) {
            Net.HttpRequest httpRequest = new Net.HttpRequest();
            httpRequest.setUrl(link);
            httpRequest.setMethod(Net.HttpMethods.GET);
            UrlTask urlTask = this;
            Gdx.app.postRunnable(() -> Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    byte[] result = httpResponse.getResult();

                    System.out.println("Loaded:" + link);
                    if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
                        if (!link.equals("Config")) {
                            if (Gdx.files.local("Config/" + link).exists())
                                Gdx.files.local("Config/" + link).delete();
                            if (link.contains("/")) {
                                FileHandle fileHandle = Gdx.files.local(link.substring(0, link.lastIndexOf("/")));
                                if (!fileHandle.exists()){
                                    System.out.println("Made Directory:" + fileHandle);
                                    fileHandle.mkdirs();
                                }
                            }
                            Gdx.files.local("Config/" + link).writeBytes(result, true);
                        }
                        urlLoader.newContent(urlTask, result);
                        completed = true;
                    } else {
                        System.out.println("Failed To Get:" + link);
                        load();
                    }
                }

                @Override
                public void failed(Throwable t) {
                    System.out.println("Failed To Get:" + link);
                    load();
                }

                @Override
                public void cancelled() {
                    System.out.println("Cancelled:" + link);
                    load();
                }
            }));
        }
    }


}

interface URLINTERFACE {
    void run(HashMap<String, byte[]> output);
}
