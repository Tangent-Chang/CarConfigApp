package server;

/**
 * Created by Tangent Chang on 7/8/15.
 */
public interface SocketClientInterface {
        boolean openConnection();
        void handleSession();
        void closeSession();
}
