package com.group10.paperlessexamwebservice.databaserequests.networkcontainer;

/**
 * NetworkContainer class is used to send objects across the network.
 * Request operation is used as an ENUM identifier of the container{@link RequestOperation}.
 * The second parameter must contain the Serialized JSON object.
 *
 * @author Marin Bilba
 * @version 1.0
 */
public class NetworkContainer {
    private RequestOperation requestOperation;
    private String serializedObject;

    /**
     * Instantiates a new Network container.
     *
     * @param requestOperation the request operation
     * @param serializedObject the serialized object
     */
    public NetworkContainer(RequestOperation requestOperation, String serializedObject) {
        this.requestOperation = requestOperation;
        this.serializedObject = serializedObject;

    }

    /**
     * Gets request operation.
     *
     * @return the request operation
     */
    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    /**
     * Sets request operation.
     *
     * @param requestOperation the request operation
     */
    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    /**
     * Gets object.
     *
     * @return the object
     */
    public String getSerializedObject() {
        return serializedObject;
    }

    /**
     * Sets object.
     *
     * @param serializedObject the object
     */
    public void setSerializedObject(String serializedObject) {
        this.serializedObject = serializedObject;
    }

}
